package org.kodedevs.kode.parser;

import org.kodedevs.kode.common.TokenStream;
import org.kodedevs.kode.common.TokenType;

public abstract class AbstractParser {

    private TokenStream _input;
    private TokenType _base;
    private TokenType _last;

    public AbstractParser(TokenStream input) {
        setTokenStream(input);
    }

    public void reset() {
        if (getTokenStream() != null) getTokenStream().reset();
        _base = null;
        _last = null;
    }

    public TokenStream getTokenStream() {
        return _input;
    }

    public void setTokenStream(TokenStream input) {
        this._input = null;
        reset();
        this._input = input;
    }
}
