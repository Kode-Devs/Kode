package org.kodedevs.kode.internal.parser;

import org.kodedevs.kode.internal.errors.ParseException;
import org.kodedevs.kode.internal.source.Source;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class AbstractLexer permits Lexer {


    private final List<Token> tokens = new ArrayList<>();

    private final Source source;
    private final int length;
    private int current;
    private int base;

    protected AbstractLexer(Source source, int start, int current, int maxLength) {
        this.source = source;
        this.base = start;
        this.current = current;
        this.length = maxLength;
    }

    public final Token getToken(int i) {
        if (!isAtEnd()) lexify();
        return tokens.get(i);
    }

    protected abstract void lexify();

    protected final void rebase() {
        base = current;
    }

    protected final boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        advance();
        return true;
    }

    protected final char peek() {
        return peek(0);
    }

    protected final char peek(int offset) {
        if (current + offset >= length) return '\0';
        return source.charAt(current + offset);
    }

    protected final boolean isAtEnd() {
        return current >= length;
    }

    protected final char advance() {
        return source.charAt(current++);
    }

    protected final String getLiteral() {
        return source.literalAt(base, current - base);
    }

    protected final void addToken(TokenType type) {
        tokens.add(new Token(type, base, current - base));
    }

    protected final ParseException error(String errMsg) {
        return new ParseException(errMsg, base);
    }
}
