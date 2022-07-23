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

import org.kodedevs.kode.KodeException;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.List;
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

    // Note: For expression parsing we will be using Pratt Parser
    private Expression parseExpression() {
        return null;
    }

    // -* atoms *-

    private Expression parseAtomic() {
        return null;
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
                throw new KodeException("No tokens available to consume");
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

        // Sync n next tokens
        for (int i = 0; i < offset && !buffer.isFull(); i++) {
            buffer.offer(lexer.scanNextToken());
        }

        try {
            return buffer.get(offset);
        } catch (NoSuchElementException e) {
            throw new KodeException("Token at offset " + offset + " is not in current token buffer window");
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
