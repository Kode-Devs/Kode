package org.kodedevs.kode.internal.parser;

import org.kodedevs.kode.internal.errors.ParseException;
import org.kodedevs.kode.internal.source.Source;

import java.util.ArrayList;
import java.util.List;

import static org.kodedevs.kode.internal.parser.TokenType.*;

public final class Lexer {

    private final List<Token> tokens = new ArrayList<>();

    private final Source source;
    private final int length;
    private int current;
    private int start;

    public Lexer(Source source) {
        this(source, 0, 0, source.length());
    }

    public Lexer(Source source, int start, int current, int maxLength) {
        this.source = source;
        this.start = start;
        this.current = current;
        this.length = maxLength;
    }

    public Token getToken(int i) {
        if (tokens.isEmpty()) {
            lexify();
        }
        return tokens.get(i);
    }

    public void lexify() {
        while (!isAtEnd()) {
            skipWhiteSpace();
            // We are at the beginning of the next lexeme.
            start = current;
            addToken(scan());
        }

        addToken(TOKEN_EOF);
    }

    private void skipWhiteSpace() {
        while (!isAtEnd()) {
            char c = advance();
            switch (c) {
                case ' ', '\r', '\t', '\n':
                    break;
                case '/':
                    if (peek() == '/') {
                        // A comment goes until the end of the line.
                        while (!isAtEnd() && peek() != '\n') advance();
                        continue;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private TokenType scan() {
        char c = advance();

        if (isAlphabet(c)) return identifier();
        if (isNumber(c)) return number();

        return switch (c) {
            case '(' -> TOKEN_LEFT_PAREN;
            case ')' -> TOKEN_RIGHT_PAREN;
            case '{' -> TOKEN_LEFT_BRACE;
            case '}' -> TOKEN_RIGHT_BRACE;
            case '[' -> TOKEN_LEFT_SQUARE;
            case ']' -> TOKEN_RIGHT_SQUARE;
            case ';' -> TOKEN_SEMICOLON;
            case ',' -> TOKEN_COMMA;
            case '.' -> TOKEN_DOT;
            case '+' -> TOKEN_PLUS;
            case '-' -> TOKEN_MINUS;
            case '*' -> TOKEN_STAR;
            case '/' -> TOKEN_SLASH;
            case '\\' -> TOKEN_BACKSLASH;
            case '%' -> TOKEN_MOD;
            case '^' -> TOKEN_POWER;
            case '"' -> string();
            default -> throw error(String.format("Unexpected character %c.", c));
        };
    }

    private TokenType string() {
        while (!isAtEnd() && peek() != '"') {
            advance();
        }

        if (isAtEnd()) throw error("Unterminated string.");

        // The closing quote
        advance();

        return TOKEN_STRING;
    }

    private TokenType number() {
        while (isNumber(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && isNumber(peek(1))) {
            // Consume the ".".
            advance();

            while (isNumber(peek())) advance();
        }

        return TokenType.TOKEN_NUMBER;
    }

    private TokenType identifier() {
        while (isAlphaNumeric(peek())) advance();

        String identifierName = getLiteral();

        return switch (identifierName) {
            case "true" -> TokenType.TOKEN_TRUE;
            case "false" -> TokenType.TOKEN_FALSE;
            default -> TokenType.TOKEN_IDENTIFIER;
        };
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        advance();
        return true;
    }

    private char peek() {
        return peek(0);
    }

    private char peek(int offset) {
        if (current + offset >= length) return '\0';
        return source.charAt(current + offset);
    }

    private boolean isAtEnd() {
        return current >= length;
    }

    private char advance() {
        return source.charAt(current++);
    }

    private String getLiteral() {
        return source.literalAt(start, current - start);
    }

    private void addToken(TokenType type) {
        tokens.add(new Token(type, start, current - start));
    }

    private ParseException error(String errMsg) {
        return new ParseException(errMsg, start);
    }

    private static boolean isNumber(final char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isAlphabet(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private static boolean isAlphaNumeric(final char c) {
        return isAlphabet(c) || isNumber(c);
    }
}
