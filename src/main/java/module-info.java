module org.kodedevs.kode {
    requires info.picocli;
    requires org.fusesource.jansi;

    opens org.kodedevs.kode.api.cli to info.picocli;
}
