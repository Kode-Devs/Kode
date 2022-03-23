package org.kodedevs.kode.common;

public abstract class Recognizer<Symbol> {

    @SafeVarargs
    protected final boolean match(Symbol... symbols) {
        for (Symbol symbol : symbols) {
            if (check(symbol)) {
                advance();
                return true;
            }
        }

        return false;
    }

    protected boolean check(Symbol symbol) {
        return peek() == symbol;
    }

    protected Symbol advance() {
        if (!isAtEnd()) {
            getStream().consume();
        }
        return previous();
    }

    protected abstract boolean isAtEnd();

    protected Symbol peek(int offset) {
        return getStream().lookAhead(offset);
    }

    protected Symbol peek() {
        return peek(0);
    }

    protected Symbol previous() {
        return peek(-1);
    }

    public abstract SymbolStream<Symbol> getStream();
}
