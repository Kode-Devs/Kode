package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.Recognizer;
import org.kodedevs.kode.common.Token;
import org.kodedevs.kode.common.TokenStream;

import static org.kodedevs.kode.common.TokenType.EOF;

public class Parser extends Recognizer<Token> {

    private final TokenStream tokenStream;

    public Parser(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }

    @Override
    protected boolean isAtEnd() {
        return peek().getType() == EOF;
    }

    @Override
    public TokenStream getStream() {
        return tokenStream;
    }
}
