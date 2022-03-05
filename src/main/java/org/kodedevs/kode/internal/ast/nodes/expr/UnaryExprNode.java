package org.kodedevs.kode.internal.ast.nodes.expr;

import org.kodedevs.kode.internal.runtime.RuntimeState;
import org.kodedevs.kode.internal.parser.Token;
import org.kodedevs.kode.internal.ast.nodes.ExprNode;

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
