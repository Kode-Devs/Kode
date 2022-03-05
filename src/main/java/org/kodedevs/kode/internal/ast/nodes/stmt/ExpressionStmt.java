package org.kodedevs.kode.internal.ast.nodes.stmt;

import org.kodedevs.kode.internal.runtime.RuntimeState;
import org.kodedevs.kode.internal.ast.nodes.ExprNode;
import org.kodedevs.kode.internal.ast.nodes.StmtNode;

public class ExpressionStmt implements StmtNode {

    private final ExprNode exprNode;

    public ExpressionStmt(ExprNode exprNode) {
        this.exprNode = exprNode;
    }

    @Override
    public Object evaluate(RuntimeState runtimeState) {
        return null;
    }
}
