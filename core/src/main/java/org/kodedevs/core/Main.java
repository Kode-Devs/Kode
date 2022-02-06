package org.kodedevs.core;

import org.kodedevs.cdi.Depends;
import org.kodedevs.utils.IOUtils;

public class Main {

    private final IOUtils ioUtils = Depends.on(IOUtils.class);

    public static void main(String[] args) {
        Depends.on(Main.class).runREPL();
    }

    public void runREPL() {
        ioUtils.out.println("\u001b[34mHello \u001b[32mWorld!!!");
    }
}
