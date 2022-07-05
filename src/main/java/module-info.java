/**
 * The Kode Programming Language
 *
 * @author arpan
 */
module org.kodedevs.kode {
    requires info.picocli;
    requires com.install4j.runtime;

    requires transitive java.scripting;

    opens org.kodedevs.kode.cli to info.picocli;

    provides javax.script.ScriptEngineFactory with
            org.kodedevs.kode.jsr223.KodeScriptEngineFactory;
}