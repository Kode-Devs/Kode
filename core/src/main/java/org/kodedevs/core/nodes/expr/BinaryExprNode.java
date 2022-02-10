package org.kodedevs.core.nodes.expr;

import org.kodedevs.core.internal.Interpreter;
import org.kodedevs.core.internal.token.Token;

public class BinaryExprNode extends ExprNode {

    // Private Fields
    private final ExprNode leftNode;
    private final Token operator;
    private final ExprNode rightNode;

    public BinaryExprNode(ExprNode left, Token operator, ExprNode right) {
        super(operator, left, right);
        this.leftNode = left;
        this.operator = operator;
        this.rightNode = right;
    }

    @Override
    public Object evaluate(Interpreter interpreter) {
        Object left = leftNode.evaluate(interpreter);
        Object right = rightNode.evaluate(interpreter);

        return switch (operator.tokenType()) {
            case TOKEN_PLUS, TOKEN_MINUS, TOKEN_STAR, TOKEN_SLASH -> left;
            default -> null;
        };
    }
}
