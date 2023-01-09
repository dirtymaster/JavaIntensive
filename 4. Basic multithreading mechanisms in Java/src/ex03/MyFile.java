package ex03;

public class MyFile {
    private final int fileId;

    private final String url;

    public MyFile(int fieldId, String url) {
        this.fileId = fieldId;
        this.url = url;
    }

    public int getFileId() {
        return fileId;
    }

    public String getUrl() {
        return url;
    }
}
