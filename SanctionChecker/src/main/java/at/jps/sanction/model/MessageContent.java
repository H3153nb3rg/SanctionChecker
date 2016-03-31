package at.jps.sanction.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class MessageContent extends BaseModel implements Serializable {

    /**
     *
     */
    private static final long             serialVersionUID = -4252872705897803406L;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private HashMap<String, String>       fieldsAndValues  = new HashMap<String, String>();

    @Transient
    private HashMap<String, List<String>> fieldsAndValueTokens;

    public MessageContent() {

    }

    public void putField(final String field, final String content) {
        fieldsAndValues.put(field, content);
    }

    public HashMap<String, String> getFieldsAndValues() {
        if (fieldsAndValues == null) {
            fieldsAndValues = new HashMap<String, String>();
        }
        return fieldsAndValues;
    }

    public void setFieldsAndValues(final HashMap<String, String> fieldsAndValues) {
        this.fieldsAndValues = fieldsAndValues;
    }

    public HashMap<String, List<String>> getFieldsAndValueTokens() {

        if (fieldsAndValueTokens == null) {
            fieldsAndValueTokens = new HashMap<String, List<String>>();
        }

        return fieldsAndValueTokens;
    }

    public void setFieldsAndValueTokens(final HashMap<String, List<String>> fieldsAndValueTokens) {
        this.fieldsAndValueTokens = fieldsAndValueTokens;
    }

    public List<String> getTokenizedField(final String fieldName) {
        return getFieldsAndValueTokens().get(fieldName);
    }

    public void setTokenizedField(final String fieldName, final List<String> tokens) {
        getFieldsAndValueTokens().put(fieldName, tokens);
    }

}
