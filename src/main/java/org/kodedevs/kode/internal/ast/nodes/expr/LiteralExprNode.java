package org.kodedevs.kode.internal.ast.nodes.expr;

import org.kodedevs.kode.internal.runtime.RuntimeState;
import org.kodedevs.kode.internal.parser.Token;
import org.kodedevs.kode.internal.ast.nodes.ExprNode;

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
