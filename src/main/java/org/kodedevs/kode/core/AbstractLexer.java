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

public abstract class AbstractLexer {

    private final char[] content;

    public AbstractLexer(char[] content) {
        this.content = content;
    }

    public abstract Token nextToken();


    //// Section: Character Stream

    private int p = 0;

    // Consumes the current symbol in this stream.
    private void consumeStream() {
        p++;
    }

    // Gets the value of the symbol at offset i relative to the current position, where i=-1..n.
    // When i==-1, returns the value of the previously read symbol in the * stream.
    // When i==0, returns the value of the current symbol in the stream, and so on.
    private char LA(int i) {
        if (p + i >= size() || p + i < 0) return '\0';
        return content[p + i];
    }

    // Gets the position index of the current symbol, where index=0..n-1
    protected int index() {
        return p;
    }

    // Gets the total number of symbols in the stream.
    private int size() {
        return content.length;
    }


    //// Section: Character Recognizer

    protected boolean match(char... symbols) {
        for (char symbol : symbols) {
            if (check(symbol)) {
                advance();
                return true;
            }
        }

        return false;
    }

    protected boolean check(char symbol) {
        return peek() == symbol;
    }

    protected char advance() {
        if (!isAtEnd()) {
            consumeStream();
        }
        return previous();
    }

    protected boolean isAtEnd() {
        return index() >= size();
    }

    protected char peek(int offset) {
        return LA(offset);
    }

    protected char peek() {
        return peek(0);
    }

    protected char previous() {
        return peek(-1);
    }
}
