package at.jps.sanction.model.worker;

import java.util.HashMap;
import java.util.List;

public class MessageFields {

    private HashMap<String, String>       fieldsAndValues;
    private HashMap<String, List<String>> fieldsAndValueTokens;

    public HashMap<String, String> getFieldsAndValues() {
        if (fieldsAndValues == null) {
            fieldsAndValues = new HashMap<String, String>();
        }
        return fieldsAndValues;
    }

    public void setFieldsAndValues(HashMap<String, String> fieldsAndValues) {
        this.fieldsAndValues = fieldsAndValues;
    }

    public HashMap<String, List<String>> getFieldsAndValueTokens() {

        if (fieldsAndValueTokens == null) {
            fieldsAndValueTokens = new HashMap<String, List<String>>();
        }

        return fieldsAndValueTokens;
    }

    public void setFieldsAndValueTokens(HashMap<String, List<String>> fieldsAndValueTokens) {

        this.fieldsAndValueTokens = fieldsAndValueTokens;
    }

    public List<String> getTokenizedField(final String fieldName) {
        return getFieldsAndValueTokens().get(fieldName);
    }

    public void setTokenizedField(final String fieldName, List<String> tokens) {
        getFieldsAndValueTokens().put(fieldName, tokens);
    }
}
