package at.jps.slcm.gui.models;

import java.io.Serializable;

public class DisplayReferenceListRecord implements Serializable {

    /**
     *
     */

    /**
     *
     */
    private static final long serialVersionUID = -3129270680503146963L;
    /**
     *
     */
    private String            value;
    private String            key;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
