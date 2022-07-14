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

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

public final class CodeSource {

    public CodeSource() {

    }

    // This denotes the path the file containing the source
    public URL getLocation() {
        return null;
    }

    // Is this source submitted via 'eval' call?
    public boolean isEvalCode() {
        return false;
    }

    // Gets the content of the source file
    public char[] getChars() {
        return new char[0];
    }

    // Gets the line number denoted by the character position
    public int getLineNoAt(int offset) {
        final char[] content = getChars();
        int lineCnt = 1;                            // Line count starts at 1
        for (int i = 0; i < offset; i++) {
            if (content[i] == '\n') lineCnt++;      // Works for both \n and \r\n
        }
        return lineCnt;
    }

    // Gets the column number denoted by the character position
    public int getColumnNoAt(int offset) {
        return offset - findBOLN(offset);
    }

    // Gets the line containing the character denoted by the character position
    public String getLineTextAt(int offset) {
        int first = findBOLN(offset);               // Find end of previous line.
        int last = findEOLN(offset);                // Find end of this line.
        return new String(getChars(), first, last - first + 1);
    }

    // Find the beginning of the line containing position
    private int findBOLN(int position) {
        final char[] content = getChars();
        for (int i = position - 1; i >= 0; i--) {
            final char ch = content[i];
            if (ch == '\n' || ch == '\r') {
                return i + 1;
            }
        }
        return 0;
    }

    // Find the end of the line containing position
    private int findEOLN(int position) {
        final char[] content = getChars();
        for (int i = position; i < content.length; i++) {
            final char ch = content[i];
            if (ch == '\n' || ch == '\r') {
                return i - 1;
            }
        }
        return content.length - 1;
    }
}
