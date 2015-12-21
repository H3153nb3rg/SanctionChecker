package at.jps.sanction.core.listhandler;

public class BaseFileHandler {

    private String histPath;
    private String filename;
    private String url;
    private String listName;

    public void close() {
        // NOOP
    }

    public String getHistPath() {
        return histPath;
    }

    public void setHistPath(String histPath) {
        this.histPath = histPath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

}
