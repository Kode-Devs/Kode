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

package org.kodedevs.kode.sdk;

import java.util.HashMap;
import java.util.Map;

public final class Lexer {

    private final char[] content;
    private int currIdx = 0;

    private final CodeSource source;

    // Keywords
    private static final Map<String, TokenType> KEYWORD_MAP = new HashMap<>();

    static {
        // Boolean
        KEYWORD_MAP.put("true", TokenType.TRUE);
        KEYWORD_MAP.put("false", TokenType.FALSE);

        // Logical
        KEYWORD_MAP.put("and", TokenType.AND);
        KEYWORD_MAP.put("or", TokenType.OR);
        KEYWORD_MAP.put("not", TokenType.NOT);
    }

    public Lexer(CodeSource source) {
        this.source = source;
        this.content = source.getChars();
    }

    //// Section: Lexer Impl

    public Token scanNextToken() {
        // Skip any unnecessary whitespaces
        loop:
        while (true) {
            switch (peek()) {
                case ' ', '\n', '\r', '\t':
                    advance();      // Skip
                    break;
                case '/':
                    if (peek(1) == '/') {
                        // A single line comment goes until the end of the line
                        while (peek() != '\n' && !isAtEnd()) advance();
                    } else {
                        break loop;     // It is not a whitespace character
                    }
                    break;
                default:
                    break loop;     // Otherwise
            }
        }

        // Mark Start Pointer
        final int startIdx = currIdx;

        // Scan the next token
        TokenType tokenType = TokenType.EOF;
        if (!isAtEnd()) {
            final char ch = advance();

            if (Character.isLetter(ch)) {
                // Scan for Keyword or Identifier
                while (Character.isLetterOrDigit(peek())) {
                    advance();      // Go until the end of the identifier
                }

                // Extract Identifier Name
                final String identifierName = new String(content, startIdx, currIdx - startIdx);

                // If identifierName is present in KEYWORD_MAP, then it is a Keyword, else Identifier
                tokenType = KEYWORD_MAP.getOrDefault(identifierName, TokenType.IDENTIFIER);

            } else if (Character.isDigit(ch)) {
                // Scan for Numbers
                while (Character.isDigit(peek())) {
                    advance();      // Go until the end of the number
                }

                // Look for a fractional part
                if (peek() == '.' && Character.isDigit(peek(1))) {
                    advance();      // Consume the '.'

                    while (Character.isDigit(peek())) {
                        advance();      // Go until the end of the fractional part
                    }
                }

                // All numbers belong to numeric token
                tokenType = TokenType.NUMERIC;

            } else {
                // Note: You can use yield keyword to return in switch expression
                tokenType = switch (ch) {
                    // Single Char Lexemes
                    case '\0' -> TokenType.EOF;             // End-of-file
                    case '(' -> TokenType.LEFT_PAREN;       // Left Parentheses
                    case ')' -> TokenType.RIGHT_PAREN;      // Right Parentheses
                    case '+' -> TokenType.PLUS;             // Addition or Unary plus
                    case '-' -> TokenType.MINUS;            // Subtraction or Unary minus
                    case '*' -> TokenType.ASTERISK;         // Multiplication
                    case '/' -> TokenType.SLASH;            // Division
                    case '\\' -> TokenType.BACK_SLASH;      // Floor division
                    case '&' -> TokenType.AMPERSAND;        // Bitwise AND
                    case '^' -> TokenType.CARET;            // Bitwise XOR
                    case '|' -> TokenType.PIPE;             // Bitwise OR

                    // Double Character Lexemes
                    case '=' -> TokenType.ASSIGN;           // Equals or Assignment

                    // Longer Lexemes
                    case '\'', '\"' -> {                    // Strings
                        while (!isAtEnd()) {
                            if (peek() == ch || peek() == '\n') break;
                            else advance();
                        }

                        // Unterminated string
                        if(!match(ch)) throw new SyntaxError("Unterminated string.");

                        yield TokenType.STRING;
                    }

                    // Default
                    default -> throw new SyntaxError("Unexpected character: " + ch);
                };

            }
        }

        // Build Token from collected info
        return new Token(tokenType, startIdx, currIdx, source);
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
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
