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

package org.kodedevs.kode.core.streams;

import org.kodedevs.kode.common.Token;
import org.kodedevs.kode.core.compiler.Lexer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

public class TokenStream implements BaseStream<Token> {
    private final Lexer lexer;
    private int p = 0;
    private Token last;

    // Ring Buffer
    private final int MAX_BUFFER_SIZE = 8;  // Initial Capacity of Buffer
    private transient int front = -1;
    private transient int rear = -1;
    private transient int size = 0;
    private final transient Token[] buffer;

    public TokenStream(Lexer lexer) {
        this.lexer = lexer;
        buffer = new Token[MAX_BUFFER_SIZE];
    }

    @Override
    public void consume() throws IllegalStateException {
        try {
            last = poll();
            p++;
            sync(1);
        } catch (BufferUnderflowException e) {
            throw new IllegalStateException("No tokens available to consume.");
        }
    }

    @Override
    public Token lookAhead(int i) throws IndexOutOfBoundsException {
        if (i == -1) {
            return last;
        }

        sync(i);
        if (i < 0 || i >= currentBufferSize()) {
            throw new IndexOutOfBoundsException("Token at offset " + i + " is not in token buffer window. Max look ahead offset: " + currentBufferSize());
        }
        return buffer[(front + i) % MAX_BUFFER_SIZE];
    }

    @Override
    public int index() {
        return p;
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
}
