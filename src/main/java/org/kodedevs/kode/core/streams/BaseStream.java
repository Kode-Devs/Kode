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

/**
 * Base class for all symbol streams. All other streams must implement at least one of this
 * interface or its derivatives to be recognized by the compiler.
 *
 * @param <T> data-type of the symbol it works on.
 * @author arpan
 */
public interface BaseStream<T> {

    String UNKNOWN_SOURCE_NAME = "<unknown>";

    /**
     * Consumes the current symbol in this stream.
     *
     * @throws IllegalStateException if it fails to consume the current symbol
     */
    void consume() throws IllegalStateException;

    /**
     * Gets the value of the symbol at offset {@code i} relative to the current position. When
     * {@code i==-1}, returns the value of the previously read symbol in the * stream. When {@code
     * i==0}, returns the value of the current symbol in the stream, and so on.
     *
     * @param i given offset, where i=-1..n
     * @return value of the symbol at given offset
     * @throws IndexOutOfBoundsException if value of {@code i} do not belong to {@code [-1..n)}
     *                                   where {@code n} depends on its underlying implementation
     */
    T lookAhead(int i) throws IndexOutOfBoundsException;

    /**
     * Gets the position index of the current symbol.
     *
     * @return index of the current symbol, where index=0..n-1
     */
    int index();

    /**
     * Gets the total number of symbols in the stream.
     *
     * @return total number of symbols
     * @throws UnsupportedOperationException if the size of the stream is unknown.
     */
    default int size() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unknown Stream Size");
    }

    /**
     * Gets the name of the underlying symbol source. If such a name is not known, this method
     * returns {@link BaseStream#UNKNOWN_SOURCE_NAME} by default.
     *
     * @return the name of the underlying symbol source
     */
    default String getSourceName() {
        return UNKNOWN_SOURCE_NAME;
    }
}
