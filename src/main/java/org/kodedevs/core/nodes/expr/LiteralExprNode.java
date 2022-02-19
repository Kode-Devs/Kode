package org.kodedevs.core.nodes.expr;

import org.kodedevs.core.internal.RuntimeState;
import org.kodedevs.core.internal.parser.Token;
import org.kodedevs.core.nodes.ExprNode;

public class LiteralExprNode implements ExprNode {

    // Private Fields
    private final Token literal;

    public LiteralExprNode(Token literal) {
        this.literal = literal;
    }

    @Override
    public Object evaluate(RuntimeState runtimeState) {
        return null;
    }
}