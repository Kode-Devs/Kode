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

package org.kodedevs.kode.internal.parser;

/**
 * Represents all Source Objects. Generally all the source object implementations must implement at
 * least one of this interface or its derivatives to be recognized by the underlying environment.
 *
 * @author arpan
 */
public interface Source {

    /**
     * Gets the user supplied name of this script. This usually denotes the name of the file from
     * which the source is obtained.
     *
     * @return The user supplied name of this script, with leading and trailing whitespace stripped,
     *         or null if one is not already set
     */
    String getName();

    /**
     * Gets the 'directory' part of the file or 'base' of the URL. This usually denoted the path to
     * the directory containing the file from which the source is obtained.
     *
     * @return The 'directory' part of the file or 'base' of the URL, with leading and trailing
     *         whitespace stripped, or null if one is not already set
     */
    String getDir();

    /**
     * Is this source submitted via 'eval' call or not.
     *
     * @return true if this source represents code submitted via 'eval' call, else false
     */
    default boolean isEvalCode() {
        return false;
    }

    /**
     * Gets the content of this source object. This method retrieves the copy of the actual text as
     * contained by the file from which the source is obtained.
     *
     * @return the content in form of char array
     */
    char[] getContent();

    /**
     * Gets the content of this source object. This method retrieves the copy of the actual text as
     * contained by the file from which the source is obtained.
     * <p>
     * This is effectively same as that of calling {@code String.valueOf(getContent())}.
     *
     * @return the content in form of a string
     * @see Source#getContent()
     */
    default String getString() {
        return String.valueOf(getContent());
    }

    /**
     * Gets the specified portion of the content of this source object. This method retrieves the
     * copy of a specific portion from the actual text as contained by the file from which the
     * source is obtained.
     * <p>
     * This is effectively same as that of calling
     * {@code String.valueOf(getContent(), offset, count)}.
     *
     * @param offset initial offset of the specified portion
     * @param count  length of the specified portion
     * @return the specified portion of the content in form of a string
     * @see Source#getContent()
     */
    default String getString(int offset, int count) {
        return String.valueOf(getContent(), offset, count);
    }

    /**
     * Gets the length of the content represented by this source object.
     * <p>
     * This is effectively same as that of using {@code getContent().length}.
     *
     * @return the length of the content as an integer
     * @see Source#getContent()
     */
    default int getLength() {
        return getContent().length;
    }

    /**
     * Gets the line number denoted by the character position.
     *
     * @param offset position of the character in the source
     * @return an integer representing the line number, where line=1..n
     * @see Source#getSourceAt(int)
     */
    default int getLineAt(int offset) {
        final char[] content = getContent();
        int line = 1;   // Line count starts at 1.
        for (int i = 0; i < offset; i++) {
            final char ch = content[i];
            if (ch == '\n') {   // Works for both \n and \r\n.
                line++;
            }
        }
        return line;
    }

    /**
     * Gets the column number denoted by the character position.
     *
     * @param offset position of the character in the source
     * @return an integer representing the column number, where column=0..n-1
     */
    default int getColumnAt(int offset) {
        return offset - findBOLN(offset);
    }

    /**
     * Gets the line containing the character denoted by the character position.
     *
     * @param offset position of the character in the source
     * @return a string representing the line
     * @see Source#getLineAt(int)
     * @see Source#getString()
     */
    default String getSourceAt(int offset) {
        int first = findBOLN(offset);   // Find end of previous line.
        int last = findEOLN(offset);    // Find end of this line.
        return getString(first, last - first + 1);
    }

    //<editor-fold defaultstate="collapsed" desc="Private Methods">

    // Find the beginning of the line containing position. Index of first character of line.
    private int findBOLN(int offset) {
        final char[] content = getContent();
        for (int i = offset - 1; i > 0; i--) {
            final char ch = content[i];
            if (ch == '\n' || ch == '\r') {
                return i + 1;
            }
        }
        return 0;
    }

    // Find the end of the line containing position. Index of last character of line.
    private int findEOLN(int offset) {
        final char[] content = getContent();
        final int length = getLength();
        for (int i = offset; i < length; i++) {
            final char ch = content[i];
            if (ch == '\n' || ch == '\r') {
                return i - 1;
            }
        }
        return length - 1;
    }
    //</editor-fold>
}
