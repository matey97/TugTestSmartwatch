package es.uji.geotec.tugtest.records;

public class Record {
    private long timestamp;

    public Record(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
