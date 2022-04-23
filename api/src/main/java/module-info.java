/**
 * API Implementations
 *
 * @author arpan
 */
module org.kodedevs.kode.api {
    requires info.picocli;
    requires org.fusesource.jansi;
    requires org.kodedevs.kode.common;

    requires transitive java.scripting;

    opens org.kodedevs.kode.api.cli to info.picocli;

    provides javax.script.ScriptEngineFactory with
            org.kodedevs.kode.api.jsr223.KodeScriptEngineFactory;
}