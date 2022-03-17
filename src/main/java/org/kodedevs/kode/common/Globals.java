package org.kodedevs.kode.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Globals {

    // Ensure there is no object for Globals.class
    private Globals() {
    }

    public static final String APPLICATION_NAME = "Kode";
    public static final String EXECUTABLE_NAME;
    public static final String APPLICATION_VERSION;

    static {
        Properties props = new Properties();

        ClassLoader classLoader = Globals.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                props.load(inputStream);
            }
        } catch (IOException ignored) {
        }

        EXECUTABLE_NAME = props.getProperty("project.name", "<exec>");
        APPLICATION_VERSION = props.getProperty("project.version", "unknown");
    }
}
