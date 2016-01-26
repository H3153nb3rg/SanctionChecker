package at.jps.slcm.gui.model;

import java.io.Serializable;

public class DisplayResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7079189748717216533L;
    private String            watchList;
    private String            wlid;
    private String            field;
    private String            value;
    private String            descr;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWatchList() {
        return watchList;
    }

    public void setWatchList(String watchList) {
        this.watchList = watchList;
    }

    public String getWlid() {
        return wlid;
    }

    public void setWlid(String wlid) {
        this.wlid = wlid;
    }

    public String getField() {
        return field;
    }

    public void setField(String token) {
        this.field = token;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

}
