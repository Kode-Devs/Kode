package org.kodedevs.utils;

import org.fusesource.jansi.AnsiConsole;

import java.io.*;

public class IOUtils extends UtilityClass {

    public static final PrintStream out = AnsiConsole.out();
    public static final PrintStream err = AnsiConsole.err();
    public static final InputStream in = System.in;

    /**
     * Try to find the width of the console for this process.
     * A value of 0 is returned if the width can not be determined.
     */
    public static int getTerminalWidth() {
        return AnsiConsole.getTerminalWidth();
    }
}
