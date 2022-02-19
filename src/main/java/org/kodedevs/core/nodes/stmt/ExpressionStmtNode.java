package org.kodedevs.core.nodes.stmt;

import org.kodedevs.core.internal.RuntimeState;
import org.kodedevs.core.nodes.ExprNode;
import org.kodedevs.core.nodes.StmtNode;

public class ExpressionStmtNode implements StmtNode {

    private final ExprNode exprNode;

    public ExpressionStmtNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }

    @Override
    public Object evaluate(RuntimeState runtimeState) {
        return null;
    }
}
