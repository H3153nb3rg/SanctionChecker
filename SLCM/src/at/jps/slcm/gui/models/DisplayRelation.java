package at.jps.slcm.gui.models;

import java.io.Serializable;

public class DisplayRelation implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 
     */

    private String            relation;
    private String            entity;
    private String            type;
    private String            wlid;

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWlid() {
        return wlid;
    }

    public void setWlid(String wlid) {
        this.wlid = wlid;
    }

}
