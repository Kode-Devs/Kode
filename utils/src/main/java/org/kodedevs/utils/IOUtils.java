package org.kodedevs.utils;

import org.fusesource.jansi.AnsiConsole;

import java.io.InputStream;
import java.io.PrintStream;

public class IOUtils {
    public final PrintStream out = AnsiConsole.out();
    public final PrintStream err = AnsiConsole.err();
    public final InputStream in = System.in;

    /**
     * Try to find the width of the console for this process.
     * A value of 0 is returned if the width can not be determined.
     */
    public int getTerminalWidth() {
        return AnsiConsole.getTerminalWidth();
    }
}
