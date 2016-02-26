package at.jps.slcm.gui.models;

import java.io.Serializable;

public class DisplayValueListRecord implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -194750501656467678L;

    /**
     *
     */
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

}
