package at.jps.sanction.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.util.CSVFileReader;
import at.jps.sanction.model.FieldCheckConfig;
import at.jps.sanction.model.queue.Queue;

public class SanctionStreamConfig implements at.jps.sanction.core.StreamConfig {

    static final Logger                                    logger            = LoggerFactory.getLogger(SanctionStreamConfig.class);

    // final private static int default_minTokenLen = 2;
    // final private static int default_minRelVal = 79;
    // final private static int default_minAbsVal = 79;
    // final private static double default_fuzzyVal = 20;

    final private static String                            default_fieldName = "default";

    private ArrayList<String>                              fields2Check;
    private ArrayList<String>                              fields2IBAN;
    private ArrayList<String>                              fields2BIC;
    private ArrayList<String>                              fuzzyFields;

    // private LinkedHashMap<String, Integer> minRelVals;
    // private LinkedHashMap<String, Integer> minAbsVals;
    // private LinkedHashMap<String, Integer> minTokenLens;
    // private LinkedHashMap<String, Double> fuzzyVals;
    //
    // private List<FieldCheckConfig> fieldsToCheck; // <--------------
    // tobe

    private Map<String, HashMap<String, FieldCheckConfig>> fieldsPerMessageTypes;

    private LinkedHashMap<String, Queue<?>>                queues;

    private String                                         fieldConfigFileName;

    public void initialize() {

        assert getFieldConfigFileName() != null : "no field mapping file specified - see configuration";

        try {
            initializeFieldsCheckConfig();
        }
        catch (final Exception x) {

            x.printStackTrace(System.out);

            logger.error("Field mapping initialization failed! ", x);
        }

        // assert getFields2Check() != null : "no fields specified - see configuration";
        // assert getQueues() != null : "no queues specified - see configuration";
        //
        // for (final FieldCheckConfig fcc : getFieldsToCheck()) {
        //
        // setCheckFields(fcc);
        //
        // setFuzzyFields(fcc.getFieldName()); // TODO: ???
        //
        // if (fcc.isHandleAsBIC()) {
        // setBICFields(fcc.getFieldName());
        // }
        // }
    }

    private void initializeFieldsCheckConfig() {

        final List<String[]> fieldCheckSettings = CSVFileReader.readCSVFile(getFieldConfigFileName(), ",");

        int csvLine = 1;

        final List<String> watchListSearchLists = new ArrayList<>();

        for (final String[] field : fieldCheckSettings) {  // skip 2 LINES !!!!!!

            if (csvLine == 2) {

                // get watchlist / Searchlist combo Info
                int index = 0;
                for (final String header : field) {

                    // leading cols are static use !
                    if (index > 7) {

                        watchListSearchLists.add(header); // dont split for now

                        // final String wl[] = header.split("/");
                        //
                        // if (!watchListSearchLists.containsKey(wl[0])) {
                        // watchListSearchLists.put(wl[0], new ArrayList<String>());
                        // }
                        //
                        // final List<String> sl = watchListSearchLists.get(wl[0]);
                        //
                        // sl.add(wl[1]);

                    }
                    index++;
                }

            }
            else {

                if (csvLine > 2) {

                    final String messsageType = field[0];

                    // boolean incoming = field[3].toUpperCase().equals("X");
                    // boolean outgoing = field[4].toUpperCase().equals("X");

                    // I + O is in the SAME row but we need 2 records ....

                    if (!getFieldsPerMessageTypes().containsKey(messsageType)) {
                        getFieldsPerMessageTypes().put(messsageType, new LinkedHashMap<>());
                    }

                    final HashMap<String, FieldCheckConfig> fieldsPerMessageType = getFieldsPerMessageTypes().get(messsageType);

                    for (int io = 3; io < 5; io++) {

                        final boolean mtio = field[io].toUpperCase().equals("X");

                        final String fieldName = ((((io % 2) != 0) ? "I" : "O") + field[1]).toUpperCase();

                        if (!fieldsPerMessageType.containsKey(fieldName)) {
                            fieldsPerMessageType.put(fieldName, new FieldCheckConfig());
                        }

                        final FieldCheckConfig fieldConfig = fieldsPerMessageType.get(fieldName);

                        if ((io % 2) != 0) {
                            fieldConfig.setCheckIngoing(mtio);
                        }
                        else {
                            fieldConfig.setCheckOutgoing(mtio);
                        }

                        // static fields

                        fieldConfig.setHandleAsBIC(field[5].toUpperCase().equals("X"));
                        fieldConfig.setHandleAsIBAN(field[6].toUpperCase().equals("X"));
                        fieldConfig.setHandleAsISIN(field[7].toUpperCase().equals("X"));
                        fieldConfig.setHandleAsISOCountry(field[8].toUpperCase().equals("X"));

                        for (int ix = 9; ix < field.length; ix++) {

                            if (field[ix] != null) {
                                if (field[ix].equals("1") || field[ix].equals("2")) {

                                    final String searchListName = watchListSearchLists.get(ix - 8);

                                    if (searchListName != null) {
                                        fieldConfig.getSearchlists().add(searchListName);
                                    }
                                    else {
                                        System.err.println("searchListName not found: " + searchListName);
                                        logger.error("searchListName not found: " + searchListName);
                                    }

                                    fieldConfig.setFuzzy(field[ix].equals("2"));

                                }
                            }
                        }
                    }

                    for (int i = 0; i < field.length; i++) {
                        System.out.print(field[i] + ",");
                    }
                    System.out.println();

                }
            }
            csvLine++;

        }

        // for debugging only
        for (final String mt : getFieldsPerMessageTypes().keySet()) {
            System.out.println(mt);
            final HashMap<String, FieldCheckConfig> fieldsPerMessageType = getFieldsPerMessageTypes().get(mt);

            for (final String field : fieldsPerMessageType.keySet()) {
                System.out.print(field + ",");
                fieldsPerMessageType.get(field);
            }
            System.out.println();
        }

    }

    public void setBICFields(final String fields) {
        if (fields != null) {
            fields2BIC = new ArrayList<>();
        }
        // fields.split(",").

        final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

        while (tokenizer.hasMoreTokens()) {
            fields2BIC.add(tokenizer.nextToken());
        }
    }

    public void setIBANFields(final String fields) {
        if (fields != null) {
            fields2IBAN = new ArrayList<>();
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
            fields2Check = new ArrayList<>();
        }
        final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

        while (tokenizer.hasMoreTokens()) {
            final String fieldname = tokenizer.nextToken();
            fields2Check.add(fieldname);

            // // add values too!!!!
            // if (!getMinAbsVals().containsKey(fieldname)) {
            // getMinAbsVals().put(fieldname, fcc.getMinAbsVal());
            // }
            // if (!getMinRelVals().containsKey(fieldname)) {
            // getMinRelVals().put(fieldname, fcc.getMinRelVal());
            // }
            // if (!getMinTokenLens().containsKey(fieldname)) {
            // getMinTokenLens().put(fieldname, fcc.getMinTokenLen());
            // }
            // if (!getFuzzyVals().containsKey(fieldname)) {
            // getFuzzyVals().put(fieldname, (double) (fcc.getFuzzyVal() / 100));
            // }

        }
    }

    public void setFuzzyFields(final String fields) {
        if (fields != null) {
            fuzzyFields = new ArrayList<>();
        }
        final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

        while (tokenizer.hasMoreTokens()) {
            fuzzyFields.add(tokenizer.nextToken());
        }
    }

    private FieldCheckConfig getFieldsConfigPerMT(final String fieldName, final String messageType) {
        FieldCheckConfig fcc = null;

        final HashMap<String, FieldCheckConfig> fields = getFieldsPerMessageTypes().get(messageType);

        // get specific
        if (fields != null) {
            fcc = fields.get(fieldName.toUpperCase());
        }

        // get default

        // get reverse
        //

        return fcc;
    }

    @Override
    public boolean isFieldToCheck(final String fieldName, final String searchindex, final String watchList, final String messageType, final boolean reverseContains) {
        boolean check = false;
        FieldCheckConfig fcc = null;

        if (!reverseContains) {
            fcc = getFieldsConfigPerMT(messageType.substring(0, 1) + fieldName, messageType.substring(1));// !!!! first digit is i/o .... //TODO: this is a hack IO is tweaked in
        }                                                                                                                    // field / type...

        if (logger.isDebugEnabled()) {
            if (fcc == null) {
                logger.debug("undefined field: " + messageType.substring(1) + " / " + fieldName);
            }
        }

        if (fcc != null) {
            check = fcc.getSearchlists().contains(watchList + "/" + searchindex);
        }

        // for (final FieldCheckConfig fcc : getFieldsToCheck()) {
        //
        // if (reverseContains) {
        // if (fieldName.contains(fcc.getFieldName())) {
        // check = fcc.getSearchlists().contains(searchindex); // TODO: search for default field if no other specified !!!
        // }
        // }
        // else {
        // if (fcc.getFieldName().contains(fieldName)) {
        // check = fcc.getSearchlists().contains(searchindex);
        // }
        // }
        // }

        // if (((ffcc != null) && (check)) && (entityType != null)) {
        // check = ((ffcc.getEntityType() == null) || (ffcc.getEntityType().isEmpty())) || (ffcc.getEntityType().toLowerCase().contains(entityType.toLowerCase()));
        // }
        //
        // if (((ffcc != null) && (check)) && (entityCategory != null)) {
        // check = ((ffcc.getEntityCategory() == null) || (ffcc.getEntityCategory().isEmpty())) || (ffcc.getEntityCategory().toLowerCase().contains(entityCategory.toLowerCase()));
        // }

        return check;
    }

    @Override
    public boolean isField2BICTranslate(final String fieldName, final String messageType, final boolean reverseContains) {
        boolean check = false;

        final FieldCheckConfig fcc = getFieldsConfigPerMT(messageType.substring(0, 1) + fieldName, messageType.substring(1));// !!!! first digit is i/o ....

        if (fcc != null) {
            check = fcc.isHandleAsBIC();
        }

        // for (final FieldCheckConfig fcc : getFieldsToCheck()) {
        // if (reverseContains) {
        // if (fieldName.contains(fcc.getFieldName())) {
        // check = fcc.isHandleAsBIC();
        // }
        // }
        // else {
        // if (fcc.getFieldName().contains(fieldName)) {
        // check = fcc.isHandleAsBIC();
        // }
        // }
        // }
        return check;
    }

    @Override
    public boolean isField2IBANCheck(final String fieldName, final String messageType, final boolean reverseContains) {
        boolean check = false;

        final FieldCheckConfig fcc = getFieldsConfigPerMT(messageType.substring(0, 1) + fieldName, messageType.substring(1));// !!!! first digit is i/o ....

        if (fcc != null) {
            check = fcc.isHandleAsIBAN();
        }

        //
        //
        // for (final FieldCheckConfig fcc : getFieldsToCheck()) {
        // if (reverseContains) {
        // if (fieldName.contains(fcc.getFieldName())) {
        // check = fcc.isHandleAsIBAN();
        // }
        // }
        // else {
        // if (fcc.getFieldName().contains(fieldName)) {
        // check = fcc.isHandleAsIBAN();
        // }
        // }
        // }
        return check;
    }

    @Override
    public boolean isField2ISINCheck(final String fieldName, final String messageType, boolean reverseContains) {

        boolean check = false;

        final FieldCheckConfig fcc = getFieldsConfigPerMT(messageType.substring(0, 1) + fieldName, messageType.substring(1));// !!!! first digit is i/o ....

        if (fcc != null) {
            check = fcc.isHandleAsISIN();
        }

        return check;
    }

    @Override
    public boolean isField2Fuzzy(final String fieldName, final String messageType, final boolean reverseContains) {
        boolean check = false;

        final FieldCheckConfig fcc = getFieldsConfigPerMT(messageType.substring(0, 1) + fieldName, messageType.substring(1));// !!!! first digit is i/o ....

        if (fcc != null) {
            check = fcc.isFuzzy();
        }

        // for (final FieldCheckConfig fcc : getFieldsToCheck()) {
        //
        // if (reverseContains) {
        // if (fieldName.contains(fcc.getFieldName())) {
        // check = fcc.isFuzzy();
        // }
        // }
        // else {
        // if (fcc.getFieldName().contains(fieldName)) {
        // check = fcc.isFuzzy();
        // }
        // }
        // }
        return check;
    }

    // @Override
    // public int getMinAbsVal(final String fieldName, boolean reverseContains) {
    //
    // final Integer mav = getMinAbsVals().get(getFieldNameFuzzy(getFuzzyVals().keySet(), fieldName, reverseContains));
    //
    // return (mav != null ? mav : default_minAbsVal);
    // }
    //
    // /*
    // * (non-Javadoc)
    // * @see at.jps.sanction.domain.swift.StreamConfig1#getMinRelVal()
    // */
    // @Override
    // public int getMinRelVal(final String fieldName, boolean reverseContains) {
    //
    // final Integer mrv = getMinRelVals().get(getFieldNameFuzzy(getFuzzyVals().keySet(), fieldName, reverseContains));
    //
    // return (mrv != null ? mrv : default_minRelVal);
    // }
    //
    // /*
    // * (non-Javadoc)
    // * @see at.jps.sanction.domain.swift.StreamConfig1#getMinTokenLen()
    // */
    // @Override
    // public int getMinTokenLen(final String fieldName, boolean reverseContains) {
    //
    // final Integer mtl = getMinTokenLens().get(getFieldNameFuzzy(getFuzzyVals().keySet(), fieldName, reverseContains));
    //
    // return (mtl != null ? mtl : default_minTokenLen);
    // }
    //
    // /*
    // * (non-Javadoc)
    // * @see at.jps.sanction.domain.swift.StreamConfig1#getFuzzyValue()
    // */
    // @Override
    // public double getFuzzyValue(final String fieldName, boolean reverseContains) {
    //
    // final Double mfv = getFuzzyVals().get(getFieldNameFuzzy(getFuzzyVals().keySet(), fieldName, reverseContains));
    //
    // return (mfv != null ? mfv : default_fuzzyVal);
    // }

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

    // public List<FieldCheckConfig> getFieldsToCheck() {
    // return fieldsToCheck;
    // }
    //
    // public void setFieldsToCheck(final List<FieldCheckConfig> fieldsToCheck) {
    // this.fieldsToCheck = fieldsToCheck;
    // }

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

    // public LinkedHashMap<String, Integer> getMinRelVals() {
    //
    // if (minRelVals == null) {
    // minRelVals = new LinkedHashMap<>();
    // }
    // return minRelVals;
    // }
    //
    // public LinkedHashMap<String, Integer> getMinAbsVals() {
    // if (minAbsVals == null) {
    // minAbsVals = new LinkedHashMap<>();
    // }
    // return minAbsVals;
    // }
    //
    // public LinkedHashMap<String, Integer> getMinTokenLens() {
    // if (minTokenLens == null) {
    // minTokenLens = new LinkedHashMap<>();
    // }
    // return minTokenLens;
    // }
    //
    // public LinkedHashMap<String, Double> getFuzzyVals() {
    // if (fuzzyVals == null) {
    // fuzzyVals = new LinkedHashMap<>();
    // }
    // return fuzzyVals;
    // }

    // @Override
    // public int getMinimumTokenLen() {
    // int minLen = Integer.MAX_VALUE;
    // for (final String key : getMinTokenLens().keySet()) {
    // final int value = getMinTokenLens().get(key);
    // if (getMinTokenLens().get(key) < minLen) {
    // minLen = value;
    // }
    // }
    // return minLen;
    // }

    public String getFieldConfigFileName() {
        return fieldConfigFileName;
    }

    public void setFieldConfigFileName(String fieldConfigFileName) {
        this.fieldConfigFileName = fieldConfigFileName;
    }

    public Map<String, HashMap<String, FieldCheckConfig>> getFieldsPerMessageTypes() {

        if (fieldsPerMessageTypes == null) {
            fieldsPerMessageTypes = new LinkedHashMap<>();
        }

        return fieldsPerMessageTypes;
    }

    public void setFieldsPerMessageTypes(Map<String, HashMap<String, FieldCheckConfig>> fieldsPerMessageTypes) {
        this.fieldsPerMessageTypes = fieldsPerMessageTypes;
    }

}
