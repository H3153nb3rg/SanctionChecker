package at.jps.slcm.gui.models;

import java.io.Serializable;

public class DisplayOptiTxListRecord implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -194750501656467678L;

    /**
     *
     */
    private String            field;
    private String            content;
    private String            watchlist;
    private String            wlid;
    private String            status;

    private Long              id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(String watchlist) {
        this.watchlist = watchlist;
    }

    public String getWlid() {
        return wlid;
    }

    public void setWlid(String wlid) {
        this.wlid = wlid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
