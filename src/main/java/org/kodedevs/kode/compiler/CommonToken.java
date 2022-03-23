package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.Source;
import org.kodedevs.kode.common.Token;
import org.kodedevs.kode.common.TokenType;

public final class CommonToken implements Token {

    private final TokenType type;
    private final int startIdx, stopIdx;
    private final Source tokenSource;

    public CommonToken(TokenType type, int startIdx, int stopIdx, Source tokenSource) {
        this.type = type;
        this.startIdx = startIdx;
        this.stopIdx = stopIdx;
        this.tokenSource = tokenSource;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public int getStartIndex() {
        return startIdx;
    }

    @Override
    public int getStopIndex() {
        return stopIdx;
    }

    @Override
    public Source getTokenSource() {
        return tokenSource;
    }
}
