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

import org.kodedevs.kode.runtime.Token;
import org.kodedevs.kode.runtime.TokenType;
import org.kodedevs.kode.utils.CharUtils;

public class KodeLexer extends AbstractLexer {

    private final Source source;

    public KodeLexer(Source source) {
        super(source.getContent());
        this.source = source;
    }

    @Override
    public Token nextToken() {
        // Skip any whitespace
        skipWhiteSpace();
        final int start = mark();

        // Scan the next token
        final TokenType type = scanToken();

        // Build Token from collected info
        final int current = mark();
        return new KodeToken(type, start, current, source);
    }

    private void skipWhiteSpace() {
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

    private TokenType scanToken() {
        if (isAtEnd()) return TokenType.EOF;

        final char c = advance();

        if (CharUtils.isAlpha(c)) return identifier();
        if (CharUtils.isDigit(c)) return number();

        return switch (c) {
            case '\0' -> TokenType.EOF;
            default -> throw new RuntimeException("Unexpected character '" + c + "'.");
        };
    }

    private TokenType identifier() {
        return TokenType.EOF;
    }

    private TokenType number() {
        return TokenType.EOF;
    }

    private int mark() {
        return index();
    }
}
