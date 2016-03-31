package at.jps.sanction.model.wl.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

@Entity
public class WL_Attribute implements Serializable {

    /**
     *
     */
    private static final long   serialVersionUID = 4875084218586018944L;

    private Map<String, String> attributes;

    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    public void setAttributes(final HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(final String key, final String value) {
        getAttributes().put(key, value);
    }

    public String getAttribute(final String key) {
        return getAttributes().get(key);
    }
}
