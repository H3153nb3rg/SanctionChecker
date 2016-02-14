package at.jps.slcm.gui.models;

import java.io.Serializable;

public class DisplayEntitySearchDetails implements Serializable {

    private static final long serialVersionUID = -7049422713375447144L;

    private String            wlname;
    private String            wlid;
    private String            entry;
    private String            remark;

    private Long              id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getWlname() {
        return wlname;
    }

    public void setWlname(String wlname) {
        this.wlname = wlname;
    }

    public String getWlid() {
        return wlid;
    }

    public void setWlid(String wlid) {
        this.wlid = wlid;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
