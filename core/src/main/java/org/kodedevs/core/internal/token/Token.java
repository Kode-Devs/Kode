package org.kodedevs.core.internal.token;

import java.util.Objects;

/**
 * Token record to represent entity Tokens
 */
public record Token(TokenType tokenType, int startPosition, int length, int lineNo) {
    public Token {
        Objects.requireNonNull(tokenType);
    }
}
