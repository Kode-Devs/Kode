package org.kodedevs.core.internal.ast.nodes.expr;

import org.kodedevs.core.internal.RuntimeState;
import org.kodedevs.core.internal.parser.Token;
import org.kodedevs.core.internal.ast.nodes.ExprNode;

public class BinaryExprNode implements ExprNode {

    // Private Fields
    private final ExprNode leftNode;
    private final Token operator;
    private final ExprNode rightNode;

    public BinaryExprNode(ExprNode left, Token operator, ExprNode right) {
        this.leftNode = left;
        this.operator = operator;
        this.rightNode = right;
    }

    @Override
    public Object evaluate(RuntimeState runtimeState) {
        Object left = leftNode.evaluate(runtimeState);
        Object right = rightNode.evaluate(runtimeState);

        return switch (operator.tokenType()) {
            default -> null;
        };
    }
}
