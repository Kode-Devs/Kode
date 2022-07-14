/*
 * Copyright 2022 Kode Devs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kodedevs.kode.core;

public final class Lexer {

    private final char[] content;
    private int currIdx = 0;

    private final CodeSource source;

    public Lexer(CodeSource source) {
        this.source = source;
        this.content = source.getChars();
    }

    //// Section: Lexer Impl

    public Token scanNextToken() {
        // Skip any whitespace
        skipAnyWhiteSpaces();
        final int startIdx = currIdx;

        // Scan the next token
        final TokenType type = scanToken();

        // Build Token from collected info
        return new Token(type, startIdx, currIdx, source);
    }

    private TokenType scanToken() {
        if (isAtEnd()) return TokenType.EOF;

        final char c = advance();

        if (_isAlpha(c)) return identifier();
        if (_isDigit(c)) return number();

        return switch (c) {
            case '\0' -> TokenType.EOF;
            default -> throw new RuntimeException("Unexpected character '" + c + "'.");
        };
    }

    private void skipAnyWhiteSpaces() {
        while (true) {
            switch (peek()) {
                case ' ', '\n', '\r', '\t':
                    advance(); // Skip
                    break;
                case '/':
                    if (peek(1) == '/') {
                        // A comment goes until the end of the line.
                        while (peek() != '\n' && !isAtEnd()) advance();
                    } else {
                        return;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    private TokenType identifier() {
        return TokenType.EOF;
    }

    private TokenType number() {
        return TokenType.EOF;
    }

    public static boolean _isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    public static boolean _isAlphaNumeric(char c) {
        return _isAlpha(c) || _isDigit(c);
    }

    public static boolean _isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    //// Section: Character Recognizer

    private boolean match(char... symbols) {
        for (char symbol : symbols) {
            if (check(symbol)) {
                advance();
                return true;    // match found
            }
        }
        return false;           // otherwise
    }

    private boolean check(char symbol) {
        return peek() == symbol;
    }

    private char advance() {
        if (!isAtEnd()) currIdx++;
        return previous();
    }

    private boolean isAtEnd() {
        return currIdx >= content.length;
    }

    private char peek(int offset) {
        if (currIdx + offset >= content.length || currIdx + offset < 0) return '\0';
        return content[currIdx + offset];
    }

    private char peek() {
        return peek(0);
    }

    private char previous() {
        return peek(-1);
    }
}
