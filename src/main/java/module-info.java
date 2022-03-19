module org.kodedevs.kode {
    requires info.picocli;

    opens org.kodedevs.kode.api.cli to info.picocli;
}
