package org.kodedevs.kode.internal.parser;

import org.kodedevs.kode.internal.source.Source;

import static org.kodedevs.kode.internal.parser.TokenType.*;

public final class Lexer extends AbstractLexer {

    public Lexer(Source source) {
        this(source, 0, 0, source.length());
    }

    public Lexer(Source source, int start, int current, int maxLength) {
        super(source, start, current, maxLength);
    }

    @Override
    protected void lexify() {
        while (!isAtEnd()) {
            skipWhiteSpace();
            // We are at the beginning of the next lexeme.
            rebase();
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
