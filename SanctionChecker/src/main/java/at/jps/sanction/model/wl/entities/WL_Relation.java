package at.jps.sanction.model.wl.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class WL_Relation implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8640130075171102664L;

    String                    wlid_from;
    String                    wlid_to;
    String                    description;
    String                    expired;

    public String getWlid_from() {
        return wlid_from;
    }

    public void setWlid_from(String wlid_from) {
        this.wlid_from = wlid_from;
    }

    public String getWlid_to() {
        return wlid_to;
    }

    public void setWlid_to(String wlid_to) {
        this.wlid_to = wlid_to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

}
