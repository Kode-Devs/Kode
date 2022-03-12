package org.kodedevs.kode.parser;

import org.kodedevs.kode.common.Token;
import org.kodedevs.kode.common.TokenStream;
import org.kodedevs.kode.common.TokenType;
import org.kodedevs.kode.errors.ParseError;

import java.util.Optional;

public abstract class AbstractParser {

    private TokenStream _input;
    private Token _base;
    private Token _last;

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

    public final boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    public final Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(message);
    }

    public final boolean check(TokenType type) {
        if (!isNotAtEnd()) return false;
        return getCurrentToken().tokenType() == type;
    }

    public final Token advance() {
        Token t = getCurrentToken();
        if (isNotAtEnd()) advance_();
        return t;
    }

    private void advance_() {
        _last = _base;
        _base = _input.nextElement();
    }

    public final boolean isNotAtEnd() {
        return _input.hasMoreElements();
    }

    public final Token getCurrentToken() {
        // ensure NullPointerException
        if (_base == null) advance_();
        return _base;
    }

    public final Optional<Token> getConsumedToken() {
        return Optional.ofNullable(_last);
    }

    public final RuntimeException error(String message) {
        return error(message, getCurrentToken());
    }

    public final RuntimeException error(String message, Token token) {
        return new ParseError(message, token.offset());
    }
}
