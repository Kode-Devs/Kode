package org.kodedevs.kode.common;

import java.util.Enumeration;

public final class TokenStream implements Enumeration<Token> {

    // This method is called by a consumer before it begins consumption using incrementToken().
    public void reset() {

    }

    @Override
    public boolean hasMoreElements() {
        return false;
    }

    // Consumers use this method to advance the stream to the next token.
    @Override
    public Token nextElement() {
        return null;
    }
}
