package org.kodedevs.core.internal.runtime;

import java.io.Reader;
import java.nio.file.Paths;

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

    public int getLineNo(int offset) {
        return -1;
    }

    public int getColumnNo(int offset) {
        return -1;
    }

    public char getCharAt(int offset) {
        if (offset >= dataLength) return '\0';
        return data.charAt(offset);
    }

    public String getLiteral(int offset, int length) {
        return data.substring(offset, offset + length);
    }

    // ------------------------------------------------------------------------------------------------- named cons

    public static Source sourceFor(String name, String content) {
        return new Source(baseName(name), fileName(name), content);
    }

    // ------------------------------------------------------------------------------------------------- utility fns

    private static String baseName(final String name) {
        return Paths.get(name).normalize().toAbsolutePath().getParent().toString();
    }

    private static String fileName(final String name) {
        return Paths.get(name).normalize().toAbsolutePath().getFileName().toString();
    }

}
