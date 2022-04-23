/**
 * API Implementations
 *
 * @author arpan
 */
module org.kodedevs.kode {
    requires info.picocli;
    requires org.fusesource.jansi;
    requires org.apache.commons.configuration2;

    requires transitive java.scripting;

    opens org.kodedevs.kode.api.cli to info.picocli;

    provides javax.script.ScriptEngineFactory with
            org.kodedevs.kode.api.jsr223.KodeScriptEngineFactory;
}