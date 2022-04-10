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

package org.kodedevs.kode.core.compiler;

import org.kodedevs.kode.common.Token;
import org.kodedevs.kode.core.streams.CharStream;

public class Lexer extends Recognizer<Character> {
    private final CharStream charStream;

    public Lexer(CharStream charStream) {
        this.charStream = charStream;
    }

    public Token nextToken() {
        return null;
    }

    @Override
    protected boolean isAtEnd() {
        return charStream.index() >= charStream.size();
    }

    @Override
    public CharStream getStream() {
        return charStream;
    }

    protected static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    protected static boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    protected static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}