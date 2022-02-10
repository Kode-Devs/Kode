package org.kodedevs.core.nodes.expr;

import org.kodedevs.core.nodes.ASTNode;
import org.kodedevs.core.internal.token.Token;

public abstract class ExprNode extends ASTNode {
    public ExprNode(Token identifierToken, ASTNode... childNodes) {
        super(identifierToken, childNodes);
    }
}
