package org.kodedevs.core.nodes.expr;

import org.kodedevs.core.internal.RuntimeState;
import org.kodedevs.core.internal.token.Token;
import org.kodedevs.core.nodes.ExprNode;

public class UnaryExprNode implements ExprNode {

    // Private Fields
    private final Token operator;
    private final ExprNode rightNode;

    public UnaryExprNode(Token operator, ExprNode right) {
        this.operator = operator;
        this.rightNode = right;
    }

    @Override
    public Object evaluate(RuntimeState runtimeState) {
        Object right = rightNode.evaluate(runtimeState);

        return switch (operator.tokenType()) {
            default -> null;
        };
    }
}
