/*
 * Copyright 2022 Kode Devs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kodedevs.kode.core;

import org.kodedevs.kode.KodeBug;
import org.kodedevs.kode.core.ast.*;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Parser {

    private final Lexer lexer;
    private int currIdx = 0;

    private final TokenBuffer buffer = new TokenBuffer();
    private Token lastToken = null;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    //// Section: Parser Impl

    public Expression parse() {
        return parseExpression();
    }

    // -* statements *-

    // -* expressions *-

    private Expression parseExpression() {
        return parseExpressionUsingPrattParser_(0);
    }

    // Parse Expressions using Pratt Parser
    private Expression parseExpressionUsingPrattParser_(int precedence) {

        Expression left;
        final ParseLet_ prefix = prefixMap_.get(peek().getTokenType());
        if (prefix != null) {
            final Token token = advance();
            left = new PrefixExpr(token, parseExpressionUsingPrattParser_(prefix.precedence));
        } else {
            // Go out of Pratt Parser Scope
            left = parseAtomic();
        }

        while (suffixMap_.containsKey(peek().getTokenType())
                && precedence < suffixMap_.get(peek().getTokenType()).precedence) {

            final Token operator = advance();
            final ParseLet_ suffix = suffixMap_.get(operator.getTokenType());
            if (suffix.isSpecial) {
                left = switch (operator.getTokenType()) {
                    case ASSIGN -> {                                    // Assignment
                        final Expression right = parseExpressionUsingPrattParser_(suffix.precedence - 1);

                        if (left instanceof FetchExpr fetch) {          // identifier = new_value
                            yield new StoreExpr(fetch.name(), right);
                        }
                        if (left instanceof GetterExpr getter) {        // obj.field = new_value
                            yield new SetterExpr(getter.object(), getter.name(), right);
                        }

                        throw new SyntaxError("Invalid assignment target.", peek());
                    }
                    default -> throw new KodeBug("Missing special parse-let handle");
                };
            } else if (suffix.isInfix) {
                // To handle right-associative operators, we allow a slightly lower precedence when parsing
                // the right-hand side. This will let a parse-let with the same precedence appear on
                // the right, which will then take this parse-let's result as its left-hand argument.
                left = new InfixExpr(left, operator, parseExpressionUsingPrattParser_(
                        suffix.precedence - (suffix.isRightAssociative ? 1 : 0)
                ));
            } else {
                return new PostfixExpr(left, operator);
            }
        }

        return left;
    }

    // Registers a prefix unary operator parse-let for the given token and precedence
    private static void prefix_(TokenType type, Precedence_ precedence) {
        prefixMap_.put(type, new ParseLet_(false, false, false, precedence.ordinal()));
    }

    // Registers a postfix unary operator parse-let for the given token and precedence
    private static void postfix_(TokenType type, Precedence_ precedence) {
        suffixMap_.put(type, new ParseLet_(false, false, false, precedence.ordinal()));
    }

    // Registers a postfix unary operator parse-let for the given token and precedence
    private static void special_(TokenType type, Precedence_ precedence) {
        suffixMap_.put(type, new ParseLet_(false, false, true, precedence.ordinal()));
    }

    // Registers a left-associative binary operator parse=let for the given token and precedence
    private static void infixLeft_(TokenType type, Precedence_ precedence) {
        suffixMap_.put(type, new ParseLet_(true, false, false, precedence.ordinal()));
    }

    // Registers a right-associative binary operator parse-let for the given token and precedence
    private static void infixRight_(TokenType type, Precedence_ precedence) {
        suffixMap_.put(type, new ParseLet_(true, true, false, precedence.ordinal()));
    }

    private static final Map<TokenType, ParseLet_> prefixMap_ = new HashMap<>();
    private static final Map<TokenType, ParseLet_> suffixMap_ = new HashMap<>();

    private record ParseLet_(boolean isInfix, boolean isRightAssociative, boolean isSpecial,
                             int precedence) {
    }

    // From low to high
    private enum Precedence_ {
        NONE,           //
        ASSIGNMENT,     // =
        LOGICAL_OR,     // or
        LOGICAL_AND,    // and
        LOGICAL_NOT,    // not
        COMPARISON,     // == != < > <= >=
        BITWISE_OR,     // |
        BITWISE_XOR,    // ^
        BITWISE_AND,    // &
        SHIFT,          // << >>
        TERM,           // + -
        FACTOR,         // * / \ %
        PREFIX,         // - + ~
        POSTFIX,        //
        EXPONENT,       // **
    }

    static {
        // Register the ones that need special parse-lets
        special_(TokenType.ASSIGN, Precedence_.ASSIGNMENT);

        // Register the simple operator parse-lets
        prefix_(TokenType.PLUS, Precedence_.PREFIX);
        prefix_(TokenType.MINUS, Precedence_.PREFIX);

        infixLeft_(TokenType.PLUS, Precedence_.TERM);
        infixLeft_(TokenType.MINUS, Precedence_.TERM);
        infixLeft_(TokenType.ASTERISK, Precedence_.FACTOR);
        infixLeft_(TokenType.SLASH, Precedence_.FACTOR);
    }

    // -* atoms *-

    private Expression parseAtomic() {

        // Literal
        if (match(TokenType.TRUE, TokenType.FALSE, TokenType.NUMERIC, TokenType.STRING)) {
            return new LiteralExpr(previous());
        }

        // Fetch Variable
        if (match(TokenType.IDENTIFIER)) {
            return new FetchExpr(previous());
        }

        // Parentheses
        if (match(TokenType.LEFT_PAREN)) {
            final Expression expression = parseExpression();
            consume(TokenType.RIGHT_PAREN, "Expect ')' after expression.");
            return new GroupExpr(expression);
        }

        // Otherwise
        throw new SyntaxError("Expect expression.", peek());
    }

    //// Section: Token Recognizer

    private boolean match(TokenType... symbols) {
        for (TokenType symbol : symbols) {
            if (check(symbol)) {
                advance();
                return true;    // match found
            }
        }
        return false;           // otherwise
    }

    private boolean check(TokenType symbol) {
        return peek().getTokenType() == symbol;
    }

    private Token consume(TokenType symbol, String errMsg) {
        if (peek().getTokenType() == symbol) return advance();
        else throw new SyntaxError(errMsg, peek());
    }

    private Token advance() {
        if (!isAtEnd()) {
            try {
                lastToken = buffer.poll();
                currIdx++;

                // Sync next token
                if (!buffer.isFull()) {
                    buffer.offer(lexer.scanNextToken());
                }
            } catch (NoSuchElementException e) {
                throw new KodeBug("No tokens available to consume");
            }
        }
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getTokenType() == TokenType.EOF;
    }

    private Token peek(int offset) {
        if (offset == -1) {
            return lastToken;
        }

        // Prime the buffer
        if (buffer.isEmpty()) {
            buffer.offer(lexer.scanNextToken());
        }

        // Sync n next tokens
        for (int i = 0; i < offset && !buffer.isFull(); i++) {
            buffer.offer(lexer.scanNextToken());
        }

        try {
            return buffer.get(offset);
        } catch (NoSuchElementException e) {
            throw new KodeBug("Token at offset " + offset + " is not in current token buffer window");
        }
    }

    private Token peek() {
        return peek(0);
    }

    private Token previous() {
        return peek(-1);
    }

    //// Section: Token Buffer

    private static final class TokenBuffer {

        private final int MAX_BUFFER_SIZE = 8;  // Initial Capacity of Buffer
        private transient int front = -1;
        private transient int rear = -1;
        private transient int size = 0;
        private final transient Token[] buffer = new Token[MAX_BUFFER_SIZE];

        // Add an element to the buffer
        public void offer(Token token) {
            synchronized (this) {
                if (isFull()) {
                    throw new BufferOverflowException();
                }
                rear = (rear + 1) % MAX_BUFFER_SIZE;
                buffer[rear] = token;
                size++;
                if (front == -1) {
                    front = rear;
                }
            }
        }

        // Remove an element from the buffer
        public Token poll() {
            synchronized (this) {
                if (isEmpty()) {
                    throw new BufferUnderflowException();
                }
                Token token = buffer[front];
                buffer[front] = null;
                front = (front + 1) % MAX_BUFFER_SIZE;
                size--;
                return token;
            }
        }

        // Is the buffer full?
        public boolean isFull() {
            return size == MAX_BUFFER_SIZE;
        }

        // Is the buffer empty?
        public boolean isEmpty() {
            return size == 0;
        }

        // Get the ith element from the buffer
        public Token get(int i) {
            if (i >= 0 && i < size) {
                return buffer[(front + i) % MAX_BUFFER_SIZE];
            }
            throw new NoSuchElementException();
        }
    }
}
