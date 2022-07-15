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

import org.kodedevs.kode.KodeException;

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
        TokenType type = TokenType.EOF;
        if (!isAtEnd()) {
            final char ch = advance();

            if (Character.isLetter(ch)) {
                // Scan for Keyword or Identifier
            } else if (Character.isDigit(ch)) {
                // Scan for Numbers
            } else {
                type = switch (ch) {        // Note: You can use yield keyword to return in switch expression
                    case '\0' -> TokenType.EOF;     // End-of-file
                    default -> throw new KodeException("Unexpected character: " + ch);
                };
            }
        }

        // Build Token from collected info
        return new Token(type, startIdx, currIdx, source);
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
