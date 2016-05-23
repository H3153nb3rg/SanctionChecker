package at.jps.sanction.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import at.jps.sanction.model.FieldCheckConfig;
import at.jps.sanction.model.queue.Queue;

public class SanctionStreamConfig implements at.jps.sanction.core.StreamConfig {

    final private static int                default_minTokenLen = 2;
    final private static int                default_minRelVal   = 79;
    final private static int                default_minAbsVal   = 79;
    final private static double             default_fuzzyVal    = 20;

    final private static String             default_fieldName   = "default";

    private ArrayList<String>               fields2Check;
    private ArrayList<String>               fields2IBAN;
    private ArrayList<String>               fields2BIC;
    private ArrayList<String>               fuzzyFields;

    private LinkedHashMap<String, Integer>  minRelVals;
    private LinkedHashMap<String, Integer>  minAbsVals;
    private LinkedHashMap<String, Integer>  minTokenLens;
    private LinkedHashMap<String, Double>   fuzzyVals;

    private List<FieldCheckConfig>          fieldsToCheck;
    private LinkedHashMap<String, Queue<?>> queues;

    public void initialize() {

        assert getFields2Check() != null : "no fields specified - see configuration";
        assert getQueues() != null : "no queues specified - see configuration";

        for (final FieldCheckConfig fcc : getFieldsToCheck()) {

            setCheckFields(fcc);

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

    public void setCheckFields(FieldCheckConfig fcc) {

        final String fields = fcc.getFieldName();

        if (fields != null) {
            fields2Check = new ArrayList<String>();
        }
        final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

        while (tokenizer.hasMoreTokens()) {
            final String fieldname = tokenizer.nextToken();
            fields2Check.add(fieldname);

            // add values too!!!!
            if (!getMinAbsVals().containsKey(fieldname)) {
                getMinAbsVals().put(fieldname, fcc.getMinAbsVal());
            }
            if (!getMinRelVals().containsKey(fieldname)) {
                getMinRelVals().put(fieldname, fcc.getMinRelVal());
            }
            if (!getMinTokenLens().containsKey(fieldname)) {
                getMinTokenLens().put(fieldname, fcc.getMinTokenLen());
            }
            if (!getFuzzyVals().containsKey(fieldname)) {
                getFuzzyVals().put(fieldname, (double) (fcc.getFuzzyVal() / 100));
            }

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
                    check = fcc.getWatchlists().contains(listName);   // TODO: search for default field if no other specified !!!
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
            check = ((ffcc.getEntityType() == null) || (ffcc.getEntityType().isEmpty())) || (ffcc.getEntityType().toLowerCase().contains(entityType.toLowerCase()));
        }

        if (((ffcc != null) && (check)) && (entityCategory != null)) {
            check = ((ffcc.getEntityCategory() == null) || (ffcc.getEntityCategory().isEmpty())) || (ffcc.getEntityCategory().toLowerCase().contains(entityCategory.toLowerCase()));
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
    public int getMinAbsVal(final String fieldName, boolean reverseContains) {

        final Integer mav = getMinAbsVals().get(getFieldNameFuzzy(getFuzzyVals().keySet(), fieldName, reverseContains));

        return (mav != null ? mav : default_minAbsVal);
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getMinRelVal()
     */
    @Override
    public int getMinRelVal(final String fieldName, boolean reverseContains) {

        final Integer mrv = getMinRelVals().get(getFieldNameFuzzy(getFuzzyVals().keySet(), fieldName, reverseContains));

        return (mrv != null ? mrv : default_minRelVal);
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getMinTokenLen()
     */
    @Override
    public int getMinTokenLen(final String fieldName, boolean reverseContains) {

        final Integer mtl = getMinTokenLens().get(getFieldNameFuzzy(getFuzzyVals().keySet(), fieldName, reverseContains));

        return (mtl != null ? mtl : default_minTokenLen);
    }

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getFuzzyValue()
     */
    @Override
    public double getFuzzyValue(final String fieldName, boolean reverseContains) {

        final Double mfv = getFuzzyVals().get(getFieldNameFuzzy(getFuzzyVals().keySet(), fieldName, reverseContains));

        return (mfv != null ? mfv : default_fuzzyVal);
    }

    /**
     * for swift we look for : fieldname IN definition reverseContains = false
     * for SEPA we look for definition IN fieldname !! reverseContains = true
     */
    private String getFieldNameFuzzy(final Set<String> fieldnames, final String fieldname, boolean reverseContains) {
        String fieldkey = default_fieldName;

        for (final String fieldNameKey : fieldnames) {

            if (reverseContains) {
                if (fieldname.contains(fieldNameKey)) {
                    fieldkey = fieldname;
                }
            }
            else {
                if (fieldNameKey.contains(fieldname)) {
                    fieldkey = fieldNameKey;
                }
            }
        }

        return fieldkey;
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

    // public int getMinTokenLen() {
    // return minTokenLen;
    // }
    //
    // public int getMinRelVal() {
    // return minRelVal;
    // }
    //
    // public int getMinAbsVal() {
    // return minAbsVal;
    // }
    //
    // public double getFuzzyVal() {
    // return fuzzyVal;
    // }

    public LinkedHashMap<String, Integer> getMinRelVals() {

        if (minRelVals == null) {
            minRelVals = new LinkedHashMap<String, Integer>();
        }
        return minRelVals;
    }

    public LinkedHashMap<String, Integer> getMinAbsVals() {
        if (minAbsVals == null) {
            minAbsVals = new LinkedHashMap<String, Integer>();
        }
        return minAbsVals;
    }

    public LinkedHashMap<String, Integer> getMinTokenLens() {
        if (minTokenLens == null) {
            minTokenLens = new LinkedHashMap<String, Integer>();
        }
        return minTokenLens;
    }

    public LinkedHashMap<String, Double> getFuzzyVals() {
        if (fuzzyVals == null) {
            fuzzyVals = new LinkedHashMap<String, Double>();
        }
        return fuzzyVals;
    }

    @Override
    public int getMinimumTokenLen() {
        int minLen = Integer.MAX_VALUE;
        for (final String key : getMinTokenLens().keySet()) {
            final int value = getMinTokenLens().get(key);
            if (getMinTokenLens().get(key) < minLen) {
                minLen = value;
            }
        }
        return minLen;
    }

}
