package at.jps.slcm.gui.models;

import java.io.Serializable;

public class DisplayWordHit implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1524336456004178268L;
    private String            watchlist;
    private String            slid;
    private String            listentry;
    private String            field;
    private String            content;
    private String            value;

    private Long              id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(String watchList) {
        this.watchlist = watchList;
    }

    public String getSlid() {
        return slid;
    }

    public void setSlid(String slid) {
        this.slid = slid;
    }

    public String getListentry() {
        return listentry;
    }

    public void setListentry(String listentry) {
        this.listentry = listentry;
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

}
