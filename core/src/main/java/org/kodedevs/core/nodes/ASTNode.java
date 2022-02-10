package org.kodedevs.core.nodes;

import hu.webarticum.treeprinter.SimpleTreeNode;
import hu.webarticum.treeprinter.printer.traditional.TraditionalTreePrinter;
import org.kodedevs.core.internal.Interpreter;
import org.kodedevs.core.internal.Resolver;
import org.kodedevs.core.internal.token.Token;
import org.kodedevs.injection.Depends;
import org.kodedevs.utils.IOUtils;

public abstract class ASTNode {

    private final SimpleTreeNode treeNode;
    private final Token identifierToken;

    public ASTNode(Token identifierToken, ASTNode... childNodes) {
        this.identifierToken = identifierToken;

        // Print Tree
        treeNode = new SimpleTreeNode(this.toString());
        for(ASTNode child : childNodes) {
            treeNode.addChild(child.treeNode);
        }
    }

    public void resolve(Resolver resolver) {
        // Do Nothing
    }

    public abstract Object evaluate(Interpreter interpreter);

    // ----------------------------------------------------------------------------------------------- utility fns

    public final void printTree() {
        new TraditionalTreePrinter().print(treeNode, Depends.on(IOUtils.class).out);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
