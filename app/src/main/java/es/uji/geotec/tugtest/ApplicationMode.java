package es.uji.geotec.tugtest;

public enum ApplicationMode {
    TUG("start-execution", "stop-execution"),
    COLLECTION("start-collection", "stop-collection");

    public String start, stop;
    ApplicationMode(String start, String stop) {
        this.start = start;
        this.stop = stop;
    }
}
