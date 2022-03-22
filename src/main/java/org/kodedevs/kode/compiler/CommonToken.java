package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.ISource;
import org.kodedevs.kode.common.IToken;
import org.kodedevs.kode.common.TokenType;

public final class CommonToken implements IToken {

    private final TokenType type;
    private final int startIdx, stopIdx;
    private final ISource tokenSource;

    public CommonToken(TokenType type, int startIdx, int stopIdx, ISource tokenSource) {
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
    public ISource getTokenSource() {
        return tokenSource;
    }
}
