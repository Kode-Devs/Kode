package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.ISource;
import org.kodedevs.kode.common.IToken;
import org.kodedevs.kode.common.TokenType;

import static org.kodedevs.kode.common.TokenType.EOF;

public abstract class BaseCompiler {

    private final BufferedTokenStream tokenStream;

    public BaseCompiler(ISource source) {
        tokenStream = new BufferedTokenStream(source);
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
        return peek().getType() == tokenType;
    }

    protected final IToken advance() {
        if (!isAtEnd()) {
            tokenStream.consume();
        }
        return previous();
    }

    protected final boolean isAtEnd() {
        return peek().getType() == EOF;
    }

    protected final IToken peek(int offset) {
        return tokenStream.get(offset);
    }

    protected final IToken peek() {
        return peek(0);
    }

    protected final IToken previous() {
        return peek(-1);
    }

}
