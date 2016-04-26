package at.jps.slcm.gui.util;

public class SessionInfo {

    final public static String SESSION_INFO = "SESSION_INFO";

    private String             activeStream;
    private String             userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActiveStream() {
        return activeStream;
    }

    public void setActiveStream(String activeStream) {
        this.activeStream = activeStream;
    }

}
