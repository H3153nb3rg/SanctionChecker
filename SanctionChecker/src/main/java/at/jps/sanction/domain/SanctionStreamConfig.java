package at.jps.sanction.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;

import at.jps.sanction.model.FieldCheckConfig;
import at.jps.sanction.model.queue.Queue;

public class SanctionStreamConfig implements at.jps.sanction.core.StreamConfig {

    private int                             minTokenLen = 2;
    private int                             minRelVal   = 79;
    private int                             minAbsVal   = 79;
    private double                          fuzzyVal    = 20;

    private ArrayList<String>               fields2Check;
    private ArrayList<String>               fields2IBAN;
    private ArrayList<String>               fields2BIC;
    private ArrayList<String>               fuzzyFields;

    private List<FieldCheckConfig>          fieldsToCheck;
    private LinkedHashMap<String, Queue<?>> queues;

    public void initialize() {

        assert getFields2Check() != null : "no fields specified - see configuration";
        assert getQueues() != null : "no queues specified - see configuration";

        for (final FieldCheckConfig fcc : getFieldsToCheck()) {
            setCheckFields(fcc.getFieldName());
            setFuzzyFields(fcc.getFieldName());  // TODO: ???
            if (fcc.isHandleAsBIC()) {
                setBICFields(fcc.getFieldName());
            }
        }
    }

    public void setBICFields(final String fields) {
        if (fields != null) {
            fields2BIC = new ArrayList<String>();
        }
        // fields.split(",").

        final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

        while (tokenizer.hasMoreTokens()) {
            fields2BIC.add(tokenizer.nextToken());
        }
    }

    public void setIBANFields(final String fields) {
        if (fields != null) {
            fields2IBAN = new ArrayList<String>();
        }
        // fields.split(",").

        final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

        while (tokenizer.hasMoreTokens()) {
            fields2IBAN.add(tokenizer.nextToken());
        }
    }

    public void setCheckFields(final String fields) {
        if (fields != null) {
            fields2Check = new ArrayList<String>();
        }
        final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

        while (tokenizer.hasMoreTokens()) {
            fields2Check.add(tokenizer.nextToken());
        }
    }

    public void setFuzzyFields(final String fields) {
        if (fields != null) {
            fuzzyFields = new ArrayList<String>();
        }
        final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

        while (tokenizer.hasMoreTokens()) {
            fuzzyFields.add(tokenizer.nextToken());
        }
    }

    @Override
    public boolean isFieldToCheck(final String fieldName, final String listName, final String entityType, final String entityCategory, final boolean reverseContains) {
        boolean check = false;
        FieldCheckConfig ffcc = null;
        for (final FieldCheckConfig fcc : getFieldsToCheck()) {

            if (reverseContains) {
                if (fieldName.contains(fcc.getFieldName())) {
                    check = fcc.getWatchlists().contains(listName);   // TODO: search for default!!!
                    ffcc = fcc;
                }
            }
            else {
                if (fcc.getFieldName().contains(fieldName)) {
                    check = fcc.getWatchlists().contains(listName);
                    ffcc = fcc;
                }
            }
        }
        if (((ffcc != null) && (check)) && (entityType != null)) {
            check = ((ffcc.getEntityType() == null) || (ffcc.getEntityType().isEmpty())) || (ffcc.getEntityType().contains(entityType));
        }

        if (((ffcc != null) && (check)) && (entityCategory != null)) {
            check = ((ffcc.getEntityCategory() == null) || (ffcc.getEntityCategory().isEmpty())) || (ffcc.getEntityCategory().contains(entityCategory));
        }

        return check;
    }

    @Override
    public boolean isField2BICTranslate(final String fieldName, final boolean reverseContains) {
        boolean check = false;
        for (final FieldCheckConfig fcc : getFieldsToCheck()) {
            if (reverseContains) {
                if (fieldName.contains(fcc.getFieldName())) {
                    check = fcc.isHandleAsBIC();
                }
            }
            else {
                if (fcc.getFieldName().contains(fieldName)) {
                    check = fcc.isHandleAsBIC();
                }
            }
        }
        return check;
    }

    @Override
    public boolean isField2IBANCheck(final String fieldName, final boolean reverseContains) {
        boolean check = false;
        for (final FieldCheckConfig fcc : getFieldsToCheck()) {
            if (reverseContains) {
                if (fieldName.contains(fcc.getFieldName())) {
                    check = fcc.isHandleAsIBAN();
                }
            }
            else {
                if (fcc.getFieldName().contains(fieldName)) {
                    check = fcc.isHandleAsIBAN();
                }
            }
        }
        return check;
    }

    @Override
    public boolean isField2Fuzzy(final String fieldName, final boolean reverseContains) {
        boolean check = false;
        for (final FieldCheckConfig fcc : getFieldsToCheck()) {
            if (reverseContains) {
                if (fieldName.contains(fcc.getFieldName())) {
                    check = fcc.isFuzzy();
                }
            }
            else {
                if (fcc.getFieldName().contains(fieldName)) {
                    check = fcc.isFuzzy();
                }
            }
        }
        return check;
    }

    @Override
    public int getMinAbsVal() {
        return minAbsVal;
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getMinRelVal()
     */
    @Override
    public int getMinRelVal() {
        return minRelVal;
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getMinTokenLen()
     */
    @Override
    public int getMinTokenLen() {
        return minTokenLen;
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getFuzzyValue()
     */
    @Override
    public double getFuzzyValue() {
        return getFuzzyVal();
    }

    public void setFuzzyVal(final double fuzzyVal) {
        this.fuzzyVal = fuzzyVal / 100;
    }

    public void setMinTokenLen(final int minTokenLen) {
        this.minTokenLen = minTokenLen;
    }

    public void setMinRelVal(final int minRelVal) {
        this.minRelVal = minRelVal;
    }

    public void setMinAbsVal(final int minAbsVal) {
        this.minAbsVal = minAbsVal;
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getFuzzyVal()
     */
    @Override
    public double getFuzzyVal() {
        return fuzzyVal;
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getFields2Check()
     */
    @Override
    public ArrayList<String> getFields2Check() {
        return fields2Check;
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getFields2BIC()
     */
    @Override
    public ArrayList<String> getFields2BIC() {
        return fields2BIC;
    }

    @Override
    public ArrayList<String> getFields2IBAN() {
        return fields2IBAN;
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getFuzzyFields()
     */
    @Override
    public ArrayList<String> getFuzzyFields() {
        return fuzzyFields;
    }

    @Override
    public LinkedHashMap<String, Queue<?>> getQueues() {
        return queues;
    }

    public void setQueues(final LinkedHashMap<String, Queue<?>> queues) {
        this.queues = queues;
    }

    public List<FieldCheckConfig> getFieldsToCheck() {
        return fieldsToCheck;
    }

    public void setFieldsToCheck(final List<FieldCheckConfig> fieldsToCheck) {
        this.fieldsToCheck = fieldsToCheck;
    }

}
