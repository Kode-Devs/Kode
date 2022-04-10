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

import org.kodedevs.kode.core.streams.BaseStream;

public abstract class Recognizer<T> {

    @SafeVarargs
    protected final boolean match(T... symbols) {
        for (T symbol : symbols) {
            if (check(symbol)) {
                advance();
                return true;
            }
        }

        return false;
    }

    protected boolean check(T symbol) {
        return peek() == symbol;
    }

    protected T advance() {
        if (!isAtEnd()) {
            getStream().consume();
        }
        return previous();
    }

    protected abstract boolean isAtEnd();

    protected T peek(int offset) {
        return getStream().lookAhead(offset);
    }

    protected T peek() {
        return peek(0);
    }

    protected T previous() {
        return peek(-1);
    }

    public abstract BaseStream<? extends T> getStream();
}
