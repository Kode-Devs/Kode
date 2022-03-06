package org.kodedevs.kode.internal.parser;

import org.kodedevs.kode.internal.errors.ParseException;
import org.kodedevs.kode.internal.source.Source;

public abstract sealed class AbstractCompiler permits Compiler {

    protected Token currentToken;
    private final Lexer lexer;

    protected AbstractCompiler(Source source) {
        this.lexer = new Lexer(source);
        this.lexer.lexify();
    }

    protected Token advance() {
        return currentToken;
    }

    protected void consume(TokenType type, String errMsg) {
        if (currentToken.tokenType() != type) {
            throw errorAtCurrent(errMsg);
        }

        advance();
    }

    public ParseException errorAtCurrent(String errMsg) {
        return errorAt(errMsg, currentToken);
    }

    public ParseException errorAt(String errMsg, Token identifierToken) {
        return errorAt(errMsg, identifierToken.offset());
    }

    public ParseException errorAt(String errMsg, int offset) {
        return new ParseException(errMsg, offset);
    }
}
