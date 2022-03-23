package org.kodedevs.kode.common;

import java.util.Optional;

public abstract class CharStream
        implements Source, SymbolStream<Character> {

    public CharStream fromString(String source, boolean isEvalCode) {
        final char[] content = source.toCharArray();
        return new CharStream() {
            @Override
            public char[] getContent() {
                return content;
            }

            @Override
            public String getName() {
                return isEvalCode ? "<eval>" : "<shell>";
            }

            @Override
            public Optional<String> getBase() {
                return Optional.empty();
            }

            @Override
            public boolean isEvalCode() {
                return isEvalCode;
            }
        };
    }

    // ----------------------------------------------------------------------------------------

    private int p = 0;

    @Override
    public void consume() {
        p++;
    }

    @Override
    public Character lookAhead(int i) {
        if (p + i >= size()) return '\0';
        return getContent()[p + i];
    }

    @Override
    public int index() {
        return p;
    }

    @Override
    public int size() {
        return getLength();
    }

    @Override
    public String getSourceName() {
        return getName();
    }
}
