package org.kodedevs.kode.common;

public enum TokenType {

    // Specials.
    UNKNOWN, SEMICOLON, EOF, COMMA, DOT,

    // Bracket.
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, LEFT_SQUARE, RIGHT_SQUARE,

    // Literals.
    STRING, NUMBER,

    // Unary.
    NOT,

    // Binary.
    AND, OR, PLUS, MINUS, STAR, SLASH, BACKSLASH, MOD, POWER, LEFT_SHIFT, RIGHT_SHIFT,
    EQUAL_EQUAL, BANG_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,

    // Keyword.
    IDENTIFIER, TRUE, FALSE,

    // Future.
}
