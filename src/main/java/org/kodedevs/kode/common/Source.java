package org.kodedevs.kode.common;

import java.util.Optional;

public interface Source {

    /**
     * Get the content of this source as a char array.
     *
     * @return the content of this source as a char array
     */
    char[] getContent();

    /**
     * Get the length in chars for this source
     *
     * @return length of this source
     */
    default int getLength() {
        return getContent().length;
    }

    /**
     * Get the user supplied name of this script.
     *
     * @return User supplied source name.
     */
    String getName();

    /**
     * Get the "directory" part of the file or "base" of the URL.
     *
     * @return base of file or URL.
     */
    Optional<String> getBase();

    /**
     * Fetch source content.
     *
     * @return source content.
     */
    default String getString() {
        return String.valueOf(getContent());
    }

    /**
     * Fetch a portion of source content.
     *
     * @param offset start index in source
     * @param count  length of portion
     * @return Source content portion.
     */
    default String getString(int offset, int count) {
        return String.valueOf(getContent(), offset, count);
    }

    /**
     * Returns whether this source was submitted via 'eval' call or not.
     *
     * @return true if this source represents code submitted via 'eval'
     */
    default boolean isEvalCode() {
        return false;
    }

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

    /**
     * Return line number of character position.
     *
     * @param offset Position of character in source content.
     * @return Line number.
     */
    default int getLine(int offset) {
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
     * Return column number of character position.
     *
     * @param offset Position of character in source content.
     * @return Column number.
     */
    default int getColumn(int offset) {
        return offset - findBOLN(offset);
    }

    /**
     * Return line text including character position.
     *
     * @param offset Position of character in source content.
     * @return Line text.
     */
    default String getSourceLine(int offset) {
        int first = findBOLN(offset);   // Find end of previous line.
        int last = findEOLN(offset);    // Find end of this line.
        return getString(first, last - first + 1);
    }
}
