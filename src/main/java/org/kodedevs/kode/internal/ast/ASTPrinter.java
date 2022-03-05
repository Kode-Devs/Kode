package org.kodedevs.kode.internal.ast;

import hu.webarticum.treeprinter.SimpleTreeNode;
import hu.webarticum.treeprinter.TreeNode;
import hu.webarticum.treeprinter.UnicodeMode;
import hu.webarticum.treeprinter.printer.traditional.TraditionalTreePrinter;
import org.kodedevs.kode.internal.ast.nodes.ASTNode;

public class ASTPrinter {

    private final TraditionalTreePrinter treePrinter = new TraditionalTreePrinter(true);

    public String stringify(ASTNode rootNode) {
        return treePrinter.stringify(buildTree(rootNode));
    }

    private TreeNode buildTree(ASTNode parent) {
        SimpleTreeNode node = new SimpleTreeNode(parent.getName());
        for (ASTNode child : parent.getChildren()) {
            node.addChild(buildTree(child));
        }
        return node;
    }

    public ASTPrinter withASCII() {
        UnicodeMode.setUnicodeAsDefault(false);
        return this;
    }
}
