package org.kodedevs.kode.internal.parser;

import org.kodedevs.kode.internal.errors.ParseException;
import org.kodedevs.kode.internal.source.Source;

public abstract sealed class AbstractCompiler permits Compiler {

    private int k = 0;
    private final Lexer lexer;

    protected AbstractCompiler(Source source) {
        lexer = new Lexer(source);
    }

    protected boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    protected Token consume(TokenType type, String errMsg) {
        if (check(type)) return advance();

        throw errorAtCurrent(errMsg);
    }

    protected boolean check(TokenType type) {
        if (!isAtEnd()) return false;

        return peek().tokenType() == type;
    }

    protected Token peek() {
        return lexer.getToken(k);
    }

    protected Token previous() {
        return lexer.getToken(k - 1);
    }

    protected Token advance() {
        if (!isAtEnd()) k++;
        return previous();
    }

    protected boolean isAtEnd() {
        return peek().tokenType() == TokenType.TOKEN_EOF;
    }

    protected ParseException errorAtCurrent(String errMsg) {
        return errorAt(errMsg, peek());
    }

    protected ParseException errorAt(String errMsg, Token identifierToken) {
        return errorAt(errMsg, identifierToken.offset());
    }

    protected ParseException errorAt(String errMsg, int offset) {
        return new ParseException(errMsg, offset);
    }
}
