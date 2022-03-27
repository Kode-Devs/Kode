package org.kodedevs.kode.common;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Metadata {
    private static String projectName;
    private static String projectVersion;

    // Ensure there is no object for Metadata.class
    private Metadata() {
    }

    static {
        loadProperties();
    }

    private static void loadProperties() {
        final String versionProperties = "org/kodedevs/kode/metadata.properties";
        final InputStream in = Metadata.class.getClassLoader().getResourceAsStream(versionProperties);
        try {
            if (in == null) {
                throw new FileNotFoundException("Metadata file not found");
            }
            Properties properties = new Properties();
            properties.load(in);
            projectName = properties.getProperty("project.name");
            projectVersion = properties.getProperty("project.version");
        } catch (Exception ex) {
            throw new RuntimeException("There was a problem loading required metadata information.", ex);
        }
    }

    public static String getApplicationName() {
        return "Kode";
    }

    public static String getExecutableName() {
        return Optional.ofNullable(projectName).orElse("<exec>");
    }

    public static String getApplicationVersion() {
        return Optional.ofNullable(projectVersion).orElse("unknown");
    }
}
