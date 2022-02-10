package org.kodedevs.core.internal.token;

/**
 * Description of all the tokens.
 */
public enum TokenType {

    // Bracket.

    // Unary.

    // Binary.
    TOKEN_PLUS,
    TOKEN_MINUS,
    TOKEN_STAR,
    TOKEN_SLASH,

    // Literals.
    TOKEN_IDENTIFIER,
    TOKEN_STRING,
    TOKEN_NUMBER,
    TOKEN_TRUE,
    TOKEN_FALSE,

    // Keyword.

    // Specials.
    TOKEN_ERROR,
    TOKEN_EOF,

    // Future.
}
