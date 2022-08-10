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

package org.kodedevs.kode.sdk;

import org.kodedevs.kode.KodeBug;
import org.kodedevs.kode.sdk.ast.*;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.*;

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

    // From low to high
    private enum Precedence {
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

    private enum Flags {
        FLAG_AS_PREFIX,             // prefix unary operator
        FLAG_AS_POSTFIX,            // postfix unary operator
        FLAG_AS_INFIX,              // binary operator
        FLAG_AS_RIGHT_ASSOCIATIVE,  // right-associative (binary operator)
    }

    private static final Map<TokenType, ParseLet> prefixMap_ = new HashMap<>();
    private static final Map<TokenType, ParseLet> suffixMap_ = new HashMap<>();

    private record ParseLet(int precedence, EnumSet<Flags> flags) {

        public ParseLet {
            Objects.requireNonNull(flags);
        }

        public ParseLet(int precedence, Flags... flags) {
            this(precedence, EnumSet.noneOf(Flags.class));
            Collections.addAll(this.flags, flags);
        }

        public boolean is(Flags flag) {
            return this.flags.contains(flag);
        }
    }

    // Registers a parse-let for the given token and precedence
    private static void register(TokenType type, Precedence precedence, Flags... flags) {
        final ParseLet parseLet = new ParseLet(precedence.ordinal(), flags);

        if (parseLet.is(Flags.FLAG_AS_PREFIX)) {
            prefixMap_.put(type, parseLet);
        } else {
            suffixMap_.put(type, parseLet);
        }
    }

    static {
        // Register the ones that need special parse-lets
        register(TokenType.ASSIGN, Precedence.ASSIGNMENT);

        // Register the simple operator parse-lets
        register(TokenType.PLUS, Precedence.PREFIX, Flags.FLAG_AS_PREFIX);
        register(TokenType.MINUS, Precedence.PREFIX, Flags.FLAG_AS_PREFIX);

        register(TokenType.PLUS, Precedence.TERM, Flags.FLAG_AS_INFIX);
        register(TokenType.MINUS, Precedence.TERM, Flags.FLAG_AS_INFIX);
        register(TokenType.ASTERISK, Precedence.FACTOR, Flags.FLAG_AS_INFIX);
        register(TokenType.SLASH, Precedence.FACTOR, Flags.FLAG_AS_INFIX);
    }

    private Expression parseExpression() {
        return parseExpressionUsingPrattParser_(0);
    }

    // Parse Expressions using Pratt Parser
    private Expression parseExpressionUsingPrattParser_(int precedence) {

        Expression left;
        final ParseLet prefix = prefixMap_.get(peek().getTokenType());
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
            final ParseLet suffix = suffixMap_.get(operator.getTokenType());

            if (suffix.is(Flags.FLAG_AS_POSTFIX)) {
                left = new PostfixExpr(left, operator);

            } else if (suffix.is(Flags.FLAG_AS_INFIX)) {
                // To handle right-associative operators, we allow a slightly lower precedence when parsing
                // the right-hand side. This will let a parse-let with the same precedence appear on
                // the right, which will then take this parse-let's result as its left-hand argument.
                left = new InfixExpr(left, operator, parseExpressionUsingPrattParser_(
                        suffix.precedence - (suffix.is(Flags.FLAG_AS_RIGHT_ASSOCIATIVE) ? 1 : 0)
                ));

            } else {
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
            }
        }

        return left;
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
