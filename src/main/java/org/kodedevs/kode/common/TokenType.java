package org.kodedevs.kode.common;

/**
 * Description of all the tokens.
 */
public enum TokenType {

    // Specials.
    TOKEN_SEMICOLON,
    TOKEN_EOF,
    TOKEN_COMMA,
    TOKEN_DOT,

    // Bracket.
    TOKEN_LEFT_PAREN,
    TOKEN_RIGHT_PAREN,
    TOKEN_LEFT_BRACE,
    TOKEN_RIGHT_BRACE,
    TOKEN_LEFT_SQUARE,
    TOKEN_RIGHT_SQUARE,

    // Literals.
    TOKEN_STRING,
    TOKEN_NUMBER,

    // Unary.
    TOKEN_NOT,

    // Binary.
    TOKEN_AND,
    TOKEN_OR,
    TOKEN_PLUS,
    TOKEN_MINUS,
    TOKEN_STAR,
    TOKEN_SLASH,
    TOKEN_BACKSLASH,
    TOKEN_MOD,
    TOKEN_POWER,
    TOKEN_LEFT_SHIFT,
    TOKEN_RIGHT_SHIFT,
    TOKEN_EQUAL_EQUAL,
    TOKEN_BANG_EQUAL,
    TOKEN_GREATER,
    TOKEN_GREATER_EQUAL,
    TOKEN_LESS,
    TOKEN_LESS_EQUAL,

    // Keyword.
    TOKEN_IDENTIFIER,
    TOKEN_TRUE,
    TOKEN_FALSE,

    // Future.
}
