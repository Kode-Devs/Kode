package org.kodedevs.kode.meta;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Metadata {

    private static void loadProperties() {
        final String versionProperties = "META-INF/product-info.json";
        final InputStream in = Metadata.class.getClassLoader().getResourceAsStream(versionProperties);
        try {
            if (in == null) {
                throw new FileNotFoundException("Metadata information not found");
            }
        } catch (Exception ex) {
            throw new RuntimeException("There was a problem loading required metadata information.", ex);
        }
    }


}
