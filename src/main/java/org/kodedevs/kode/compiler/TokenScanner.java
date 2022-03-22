package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.ISource;
import org.kodedevs.kode.common.IToken;
import org.kodedevs.kode.common.TokenType;

import static org.kodedevs.kode.common.TokenType.EOF;

public final class TokenScanner extends BaseScanner {

    private final ISource source;
    private int start;

    public TokenScanner(ISource source) {
        super(source.getContent());
        this.source = source;
    }

    public IToken nextToken() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            char c = advance();
            switch (c) {
                case ' ', '\t', '\r', '\n' -> {
                    // Ignore whitespace.
                }
                default -> throw new RuntimeException("Unexpected character " + c + ".");
            }
        }

        // Handle EOF situation
        return generateToken(EOF);
    }

    private IToken generateToken(TokenType type) {
        return new CommonToken(type, start, current, source);
    }
}
