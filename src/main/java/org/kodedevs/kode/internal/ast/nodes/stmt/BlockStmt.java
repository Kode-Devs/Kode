package org.kodedevs.kode.internal.ast.nodes.stmt;

import org.kodedevs.kode.internal.runtime.RuntimeState;
import org.kodedevs.kode.internal.ast.nodes.StmtNode;

import java.util.List;

public class BlockStmt implements StmtNode {

    private final List<StmtNode> statements;

    public BlockStmt(List<StmtNode> statements) {
        this.statements = statements;
    }

    @Override
    public Object evaluate(RuntimeState runtimeState) {
        return null;
    }
}
