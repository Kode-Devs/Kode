package org.kodedevs.core.internal.parser;

import java.util.Objects;

/**
 * Token record to represent entity Tokens
 */
public record Token(TokenType tokenType, int offset, int length) {
    public Token {
        Objects.requireNonNull(tokenType);
    }
}