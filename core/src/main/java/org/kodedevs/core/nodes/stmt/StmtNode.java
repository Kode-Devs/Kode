package org.kodedevs.core.nodes.stmt;

import org.kodedevs.core.internal.ASTNode;
import org.kodedevs.core.internal.token.Token;

public abstract class StmtNode extends ASTNode {

    public StmtNode(Token identifierToken, ASTNode... childNodes) {
        super(identifierToken, childNodes);
    }
}
