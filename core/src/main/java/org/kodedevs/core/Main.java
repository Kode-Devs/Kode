package org.kodedevs.core;

import org.kodedevs.core.internal.parser.ASTGenerator;
import org.kodedevs.injection.Depends;

public class Main {

    public static void main(String[] args) {
        Depends.on(Main.class).runREPL();
    }

    public void runREPL() {
        ASTGenerator astGenerator = new ASTGenerator("5+6*7+22/7;1*3*4;");
    }
}
