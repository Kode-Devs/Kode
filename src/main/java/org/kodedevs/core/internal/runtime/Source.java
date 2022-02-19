package org.kodedevs.core.internal.runtime;

import java.io.Reader;

public class Source {

    private final String baseDir;
    private final String fileName;
    private final String data;
    private final int dataLength;

    private Source(String baseDir, String fileName, String data) {
        this.baseDir = baseDir;
        this.fileName = fileName;
        this.data = data;
        this.dataLength = data.length();
    }

    public int getDataLength() {
        return dataLength;
    }

    public int getLineNo(final int offset) {
        return -1;
    }

    public int getColumnNo(final int offset) {
        return -1;
    }

    public char getCharAt(final int offset) {
        if (offset >= dataLength) return '\0';
        return data.charAt(offset);
    }

    public String getLiteral(final int offset, final int length) {
        return data.substring(offset, offset + length);
    }

    // ------------------------------------------------------------------------------------------------- named cons

    public static Source sourceFor(final String name, final Reader reader) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    // ------------------------------------------------------------------------------------------------- utility fns

    private static String baseName(final String name) {
        return name;
    }

    private static String fileName(final String name) {
        return name;
    }

}
