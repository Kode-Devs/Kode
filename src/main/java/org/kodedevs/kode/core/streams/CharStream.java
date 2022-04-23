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

package org.kodedevs.kode.core.streams;

import org.kodedevs.kode.common.runtime.Source;

public class CharStream implements BaseStream<Character> {
    private int p = 0;
    private Source source;

    @Override
    public void consume() throws IllegalStateException {
        p++;
    }

    @Override
    public Character lookAhead(int i) throws IndexOutOfBoundsException {
        if (p + i >= size()) return '\0';
        return source.getContent()[p + i];
    }

    @Override
    public int index() {
        return p;
    }

    @Override
    public int size() throws UnsupportedOperationException {
        return source.getLength();
    }

    @Override
    public String getSourceName() {
        return source.getName();
    }
}
