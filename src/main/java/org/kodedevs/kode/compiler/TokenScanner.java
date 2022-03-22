package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.ISource;
import org.kodedevs.kode.common.IToken;
import org.kodedevs.kode.common.TokenType;

import static org.kodedevs.kode.common.TokenType.EOF;

public final class TokenScanner extends BaseScanner {

    private final ISource source;
    private int start;
    private boolean matchedEOF;

    public TokenScanner(ISource source) {
        super(source.getContent());
        this.source = source;
        matchedEOF = false;
    }

    public void lexify() {
        while (!matchedEOF) {
            scanToken();
        }
    }

    private void scanToken() {
        // Handle EOF situation
        if (isAtEnd()) {
            addToken(EOF);
            matchedEOF = true;
        }

        // We are at the beginning of the next lexeme.
        start = current();
        char c = advance();
        switch (c) {
            case ' ', '\t', '\r', '\n' -> {
                // Ignore whitespace.
            }
            default -> throw new RuntimeException("Unexpected character " + c + ".");
        }
    }

    private void addToken(TokenType type) {
        IToken token = new CommonToken(type, start, current(), source);
        throw new UnsupportedOperationException();
    }
}
