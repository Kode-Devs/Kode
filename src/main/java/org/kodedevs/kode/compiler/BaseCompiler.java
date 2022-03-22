package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.ISource;
import org.kodedevs.kode.common.IToken;
import org.kodedevs.kode.common.TokenType;

import static org.kodedevs.kode.common.TokenType.EOF;

public abstract class BaseCompiler {

    private final TokenScanner scanner;

    public BaseCompiler(ISource source) {
        scanner = new TokenScanner(source);
    }

    protected final boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    protected final IToken consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw new RuntimeException(message);
    }

    protected final boolean check(TokenType tokenType) {
        if (isAtEnd()) return false;
        return peek().getType() == tokenType;
    }

    protected final IToken advance() {
        throw new UnsupportedOperationException();
    }

    protected final boolean isAtEnd() {
        return peek().getType() == EOF;
    }

    protected final IToken peek() {
        throw new UnsupportedOperationException();
    }

    protected final IToken previous() {
        throw new UnsupportedOperationException();
    }

}
