package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.ISource;
import org.kodedevs.kode.common.IToken;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

public class BufferedTokenStream {

    private final TokenScanner scanner;
    private int p = 0;
    private IToken last;

    private final int MAX_BUFFER_SIZE = 8;  // Initial Capacity of Buffer
    private transient int front = -1;
    private transient int rear = -1;
    private transient int size = 0;
    private final transient IToken[] buffer;

    public BufferedTokenStream(ISource source) {
        scanner = new TokenScanner(source);
        buffer = new IToken[MAX_BUFFER_SIZE];
    }

    public IToken get(int i) {
        int index = i - p;

        if (index == -1) {
            return last;
        }

        sync(index);
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Token with index " + i + " is not in token buffer window");
        }

        throw new UnsupportedOperationException();
    }

    private void sync(int index) {
        for (int i = 0; i < index && !isFull(); i++) {
            offer(scanner.nextToken());
        }
    }

    public void consume() {
        last = poll();
        p++;
        sync(1);
    }

    // Add an element to the buffer
    private void offer(IToken token) {
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
    private IToken poll() {
        synchronized (this) {
            if (isEmpty()) {
                throw new BufferUnderflowException();
            }
            IToken token = buffer[front];
            buffer[front] = null;
            front = (front + 1) % MAX_BUFFER_SIZE;
            size--;
            return token;
        }
    }

    // Get current buffer size i.e., no. of tokens present in the buffer
    private int size() {
        return size;
    }

    // Is the buffer full
    private boolean isFull() {
        return size() == MAX_BUFFER_SIZE;
    }

    // Is the buffer empty
    private boolean isEmpty() {
        return size() == 0;
    }
}
