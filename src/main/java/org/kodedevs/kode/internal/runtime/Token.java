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

package org.kodedevs.kode.internal.runtime;

import org.kodedevs.kode.internal.parser.Source;

/**
 * Represents all Source Tokens. They are the smallest elements of a program which are identified by
 * the compiler. Generally, Tokens include identifiers, keywords, literals, operators and
 * separators.
 *
 * @author arpan
 */
public interface Token {

    /**
     * Gets the lexeme as represented by this token.
     * <p>
     * This is effectively same as that of calling {@code getTokenSource().getString(getStartIndex(),
     * getLength())}.
     *
     * @return the lexeme as string
     * @see Token#getTokenSource()
     * @see Token#getStartIndex()
     * @see Token#getLength()
     * @see Source#getString(int, int)
     */
    default String getText() {
        return getTokenSource().getString(getStartIndex(), getLength());
    }

    /**
     * Gets the token type of this token.
     *
     * @return the token type
     */
    TokenType getType();

    /**
     * Get the starting character index of this token.
     *
     * @return the starting character index
     */
    int getStartIndex();

    /**
     * Get the last/ending character index of this token.
     *
     * @return the last/ending character index
     */
    int getStopIndex();

    /**
     * Get the length of this token.
     * <p>
     * This is effectively same as that of {@code getStopIndex() - getStartIndex() + 1}.
     *
     * @return the length of this token, where length=1..n
     * @see Token#getStartIndex()
     * @see Token#getStopIndex()
     */
    default int getLength() {
        return getStopIndex() - getStartIndex() + 1;
    }

    /**
     * Get the line number at which the 1st character of this token was matched.
     * <p>
     * This is effectively same as that of calling {@code getTokenSource().getLineAt(getStartIndex())}.
     *
     * @return the line number, where line=1..n
     * @see Token#getTokenSource()
     * @see Token#getStartIndex()
     * @see Source#getLineAt(int)
     */
    default int getLine() {
        return getTokenSource().getLineAt(getStartIndex());
    }

    /**
     * Get the index of the first character of this token relative to the beginning of the line at
     * which it occurs.
     * <p>
     * This is effectively same as that of calling {@code getTokenSource().getColumnAt(getStartIndex())}.
     *
     * @return the index of the first character, where index=0..n-1
     * @see Token#getTokenSource()
     * @see Token#getStartIndex()
     * @see Source#getColumnAt(int)
     */
    default int getCharPositionInLine() {
        return getTokenSource().getColumnAt(getStartIndex());
    }

    /**
     * Gets the source object which created this token.
     *
     * @return the associated source object
     */
    Source getTokenSource();
}
