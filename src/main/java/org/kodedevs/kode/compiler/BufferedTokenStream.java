package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.ISource;
import org.kodedevs.kode.common.IToken;

public class BufferedTokenStream {
    private final TokenScanner scanner;
    private int currentTokenIndex;
    private IToken lastToken;
    private final int limit = 0;

    public BufferedTokenStream(ISource source) {
        this.scanner = new TokenScanner(source);
    }

    public IToken get(int i) {
        int index = i - currentTokenIndex;

        if (index == -1) {
            return lastToken;
        }

        sync(index);
        if (index < 0 || index >= limit) {
            throw new IndexOutOfBoundsException("Token with index " + i + " is not in token buffer window");
        }

        throw new UnsupportedOperationException();
    }

    private void sync(int index) {
        if (index >= limit) {
            scanner.lexify();
        }
    }

}
