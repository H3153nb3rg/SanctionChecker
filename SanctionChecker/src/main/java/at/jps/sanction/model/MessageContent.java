package at.jps.sanction.model;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class MessageContent extends BaseModel implements Serializable {

    /**
     * 
     */
    private static final long       serialVersionUID = -4252872705897803406L;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private HashMap<String, String> fieldsAndValues  = new HashMap<String, String>();

    public MessageContent() {

    }

    public void putField(final String field, final String content) {
        fieldsAndValues.put(field, content);
    }

    public HashMap<String, String> getFieldsAndValues() {
        return fieldsAndValues;
    }

    public void setFieldsAndValues(HashMap<String, String> fieldsAndValues) {
        this.fieldsAndValues = fieldsAndValues;
    }

}
