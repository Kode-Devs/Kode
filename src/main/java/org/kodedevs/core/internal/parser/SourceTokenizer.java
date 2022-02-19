package org.kodedevs.core.internal.parser;

import org.kodedevs.core.internal.runtime.Source;

public class SourceTokenizer {

    // Private Fields
    private final Source sourceCode;
    private int currentPosition;
    private int startPosition;
    private final int maxPosition;

    // Constructor
    protected SourceTokenizer(Source sourceCode) {
        currentPosition = 0;
        startPosition = 0;
        this.sourceCode = sourceCode;
        maxPosition = sourceCode.getDataLength();
    }

    public Source sourceCode() {
        return sourceCode;
    }

    // ------------------------------------------------------------------------------------------------- lexer fns

    public Token scanTokenOnDemand() {
        skipWhitespace();

        startPosition = currentPosition;
        if (currentPosition >= maxPosition) return buildToken(TokenType.TOKEN_EOF);

        char c = sourceCode.getCharAt(currentPosition++);
        if (isAlpha(c)) return identifier();
        if (isDigit(c)) return number();

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
            default -> error(String.format("Unexpected character %c.", c));
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

        if (currentPosition >= maxPosition) return error("Unterminated string.");

        // The closing quote
        currentPosition++;

        return buildToken(TokenType.TOKEN_STRING);
    }

    private Token number() {
        while (isDigit(peek(0))) currentPosition++;

        // Look for a fractional part.
        if (peek(0) == '.' && isDigit(peek(1))) {
            // Consume the ".".
            currentPosition++;

            while (isDigit(peek(0))) currentPosition++;
        }

        return buildToken(TokenType.TOKEN_NUMBER);
    }

    private Token identifier() {
        while (isAlphaNumeric(peek(0))) currentPosition++;

        String identifierName = sourceCode.getLiteral(startPosition, currentPosition - startPosition);

        return buildToken(switch (identifierName) {
            case "true" -> TokenType.TOKEN_TRUE;
            case "false" -> TokenType.TOKEN_FALSE;
            default -> TokenType.TOKEN_IDENTIFIER;
        });
    }

    private char peek(int offset) {
        return sourceCode.getCharAt(currentPosition + offset);
    }

    private Token error(String errMsg) {
        throw new ParseException(errMsg, startPosition);
    }

    private Token buildToken(TokenType type) {
        return new Token(type, startPosition, currentPosition - startPosition);
    }

    // ------------------------------------------------------------------------------------------------- utility fns

    /**
     * Checks weather the character resembles a single digit or not.
     */
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * Checks weather the character resembles either an alphabet or an underscore, or not.
     */
    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    /**
     * Checks weather the character resembles either an alphabet or a single digit or an underscore, or not.
     */
    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}
