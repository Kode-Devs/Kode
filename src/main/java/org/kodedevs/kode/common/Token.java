package org.kodedevs.kode.common;

import java.util.Objects;

public final class Token {
    private final TokenType tokenType;
    private final int offset;
    private final int length;

    public Token(TokenType tokenType, int offset, int length) {
        Objects.requireNonNull(tokenType);
        this.tokenType = tokenType;
        this.offset = offset;
        this.length = length;
    }

    public TokenType tokenType() {
        return tokenType;
    }

    public int offset() {
        return offset;
    }

    public int length() {
        return length;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Token) obj;
        return Objects.equals(this.tokenType, that.tokenType) &&
                this.offset == that.offset &&
                this.length == that.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType, offset, length);
    }

    @Override
    public String toString() {
        return "Token[" +
                "tokenType=" + tokenType + ", " +
                "offset=" + offset + ", " +
                "length=" + length + ']';
    }

}
