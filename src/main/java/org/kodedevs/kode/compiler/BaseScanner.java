package org.kodedevs.kode.compiler;

public abstract class BaseScanner {

    private final char[] source;
    private int current = 0;

    public BaseScanner(char[] source) {
        this.source = source;
    }

    protected final int current() {
        return current;
    }

    protected final boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source[current] != expected) return false;

        current++;
        return true;
    }

    protected final char peek() {
        if (isAtEnd()) return '\0';
        return source[current];
    }

    protected final char peek(int offset) {
        if (current + offset >= source.length) return '\0';
        return source[current + offset];
    }

    protected static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    protected static boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    protected static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    protected final boolean isAtEnd() {
        return current >= source.length;
    }

    protected final char advance() {
        current++;
        return source[current - 1];
    }
}
