/**
 * API Implementations
 *
 * @author arpan
 */
module org.kodedevs.kode {
    requires info.picocli;

    requires transitive java.scripting;

    opens org.kodedevs.kode.api.cli to info.picocli;

    provides javax.script.ScriptEngineFactory with
            org.kodedevs.kode.api.jsr223.KodeScriptEngineFactory;
}