package org.kodedevs.kode.internal.parser;

import org.kodedevs.kode.internal.errors.ParseException;
import org.kodedevs.kode.internal.source.Source;

public final class Lexer {

    private final Source source;
    private int currentPosition;
    private int startPosition;
    private final int maxPosition;

    public Lexer(Source source) {
        this(source, source.length());
    }

    public Lexer(Source source, int maxLength) {
        this(source, 0, 0, maxLength);
    }

    public Lexer(Source source, int start, int current, int maxLength) {
        this.source = source;
        this.startPosition = start;
        this.currentPosition = current;
        this.maxPosition = maxLength;
    }

    public Token lexify() {
        return scanTokenOnDemand();
    }

    private Token scanTokenOnDemand() {
        skipWhitespace();

        startPosition = currentPosition;
        if (currentPosition >= maxPosition) return buildToken(TokenType.TOKEN_EOF);

        char c = source.charAt(currentPosition++);
        if (isAlphabet(c)) return identifier();
        if (isNumber(c)) return number();

        return switch (c) {
            case '(' -> buildToken(TokenType.TOKEN_LEFT_PAREN);
            case ')' -> buildToken(TokenType.TOKEN_RIGHT_PAREN);
            case '{' -> buildToken(TokenType.TOKEN_LEFT_BRACE);
            case '}' -> buildToken(TokenType.TOKEN_RIGHT_BRACE);
            case '[' -> buildToken(TokenType.TOKEN_LEFT_SQUARE);
            case ']' -> buildToken(TokenType.TOKEN_RIGHT_SQUARE);
            case ';' -> buildToken(TokenType.TOKEN_SEMICOLON);
            case ',' -> buildToken(TokenType.TOKEN_COMMA);
            case '.' -> buildToken(TokenType.TOKEN_DOT);
            case '+' -> buildToken(TokenType.TOKEN_PLUS);
            case '-' -> buildToken(TokenType.TOKEN_MINUS);
            case '*' -> buildToken(TokenType.TOKEN_STAR);
            case '/' -> buildToken(TokenType.TOKEN_SLASH);
            case '\\' -> buildToken(TokenType.TOKEN_BACKSLASH);
            case '%' -> buildToken(TokenType.TOKEN_MOD);
            case '^' -> buildToken(TokenType.TOKEN_POWER);
            case '"' -> string();
            default -> throw error(String.format("Unexpected character %c.", c));
        };
    }

    private void skipWhitespace() {
        for (; ; ) {
            char c = peek(0);
            switch (c) {
                case ' ', '\r', '\t', '\n':
                    currentPosition++;
                    break;
                case '/':
                    if (peek(1) == '/')
                        // A comment goes until the end of the line.
                        while (currentPosition < maxPosition && peek(0) != '\n') currentPosition++;
                    else return;
                    break;
                default:
                    return;
            }
        }
    }

    private Token string() {
        while (currentPosition < maxPosition && peek(0) != '"') {
            currentPosition++;
        }

        if (currentPosition >= maxPosition) throw error("Unterminated string.");

        // The closing quote
        currentPosition++;

        return buildToken(TokenType.TOKEN_STRING);
    }

    private Token number() {
        while (isNumber(peek(0))) currentPosition++;

        // Look for a fractional part.
        if (peek(0) == '.' && isNumber(peek(1))) {
            // Consume the ".".
            currentPosition++;

            while (isNumber(peek(0))) currentPosition++;
        }

        return buildToken(TokenType.TOKEN_NUMBER);
    }

    private Token identifier() {
        while (isAlphaNumeric(peek(0))) currentPosition++;

        String identifierName = source.literalAt(startPosition, currentPosition - startPosition);

        return buildToken(switch (identifierName) {
            case "true" -> TokenType.TOKEN_TRUE;
            case "false" -> TokenType.TOKEN_FALSE;
            default -> TokenType.TOKEN_IDENTIFIER;
        });
    }

    private Token buildToken(TokenType type) {
        return new Token(type, startPosition, currentPosition - startPosition);
    }

    private char peek(int offset) {
        return source.charAt(currentPosition + offset);
    }

    private ParseException error(String errMsg) {
        return new ParseException(errMsg, startPosition);
    }

    public static boolean isNumber(final char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isAlphabet(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    public static boolean isAlphaNumeric(final char c) {
        return isAlphabet(c) || isNumber(c);
    }
}
