package org.kodedevs.kode.source;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

public class Source implements CharSequence {

    private final char[] array;
    private final String name;
    private final String base;

    private Source(String name, String base, char[] array) {
        this.name = Objects.requireNonNull(name);
        this.base = base;
        this.array = Objects.requireNonNull(array);
    }

    public Source(String name, String base, String content) {
        this(name, base, content.toCharArray());
    }

    public Source(String name, String base, Reader reader) throws IOException {
        this(name, base, IOUtils.toCharArray(reader));
    }

    public Source(String name, String base, File file) throws IOException {
        this(name, base, Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8));
    }


    @Override
    public final int length() {
        return array.length;
    }

    @Override
    public final char charAt(int index) {
        return array[index];
    }

    @Override
    public final CharSequence subSequence(int start, int end)  {
        return new Source(name, base, Arrays.copyOfRange(array, start, end));
    }

    public String literalAt(int position, int length) {
        return String.valueOf(array, position, length);
    }
}
