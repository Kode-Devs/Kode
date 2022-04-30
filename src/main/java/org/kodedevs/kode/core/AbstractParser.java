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

import org.kodedevs.kode.runtime.Token;
import org.kodedevs.kode.runtime.TokenType;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

public abstract class AbstractParser {

    private final AbstractLexer lexer;

    public AbstractParser(AbstractLexer lexer) {
        this.lexer = lexer;
    }


    //// Section: Token Stream

    private int p = 0;
    private Token last;

    // Ring Buffer
    private final int MAX_BUFFER_SIZE = 8;  // Initial Capacity of Buffer
    private transient int front = -1;
    private transient int rear = -1;
    private transient int size = 0;
    private final transient Token[] buffer = new Token[MAX_BUFFER_SIZE];

    // Consumes the current symbol in this stream.
    private void consumeStream() throws IllegalStateException {
        try {
            last = poll();
            p++;
            sync(1);
        } catch (BufferUnderflowException e) {
            throw new IllegalStateException("No tokens available to consume.");
        }
    }

    // Gets the value of the symbol at offset i relative to the current position, where i=-1..n.
    // When i==-1, returns the value of the previously read symbol in the * stream.
    // When i==0, returns the value of the current symbol in the stream, and so on.
    private Token LA(int i) throws IndexOutOfBoundsException {
        if (i == -1) {
            if (last == null) {
                throw new IndexOutOfBoundsException("Token at offset " + i + " is not in current token buffer window.");
            }

            return last;
        }

        sync(i);
        if (i < 0 || i >= currentBufferSize()) {
            throw new IndexOutOfBoundsException("Token at offset " + i + " is not in current token buffer window.");
        }
        return buffer[(front + i) % MAX_BUFFER_SIZE];
    }

    // Gets the position index of the current symbol, where index=0..n-1
    private int index() {
        return p;
    }

    // Gets the total number of symbols in the stream.
    private int size() {
        throw new UnsupportedOperationException("Unknown Stream Size");
    }

    // Sync next n elements to the buffer until full
    private void sync(int index) {
        for (int i = 0; i < index && !isFull(); i++) {
            offer(lexer.nextToken());
        }
    }

    // Add an element to the ring buffer
    private void offer(Token token) {
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

    // Remove an element from the ring buffer
    private Token poll() {
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

    // Get current buffer size i.e., no. of tokens present in the buffer
    private int currentBufferSize() {
        return size;
    }

    // Is the buffer full
    private boolean isFull() {
        return currentBufferSize() == MAX_BUFFER_SIZE;
    }

    // Is the buffer empty
    private boolean isEmpty() {
        return currentBufferSize() == 0;
    }


    //// Section: Token Recognizer

    protected boolean match(Token... symbols) {
        for (Token symbol : symbols) {
            if (check(symbol)) {
                advance();
                return true;
            }
        }

        return false;
    }

    protected boolean check(Token symbol) {
        return peek() == symbol;
    }

    protected Token advance() {
        if (!isAtEnd()) {
            consumeStream();
        }
        return previous();
    }

    protected boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    protected Token peek(int offset) {
        return LA(offset);
    }

    protected Token peek() {
        return peek(0);
    }

    protected Token previous() {
        return peek(-1);
    }
}
