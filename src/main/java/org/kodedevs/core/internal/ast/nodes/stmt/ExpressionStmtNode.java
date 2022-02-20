package org.kodedevs.core.internal.ast.nodes.stmt;

import org.kodedevs.core.internal.RuntimeState;
import org.kodedevs.core.internal.ast.nodes.ExprNode;
import org.kodedevs.core.internal.ast.nodes.StmtNode;

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
