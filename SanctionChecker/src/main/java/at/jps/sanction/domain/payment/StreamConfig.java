package at.jps.sanction.domain.payment;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StreamConfig implements at.jps.sanction.core.StreamConfig {

    private int               minTokenLen = 2;
    private int               minRelVal   = 79;
    private int               minAbsVal   = 79;
    private double            fuzzyVal    = 20;

    private ArrayList<String> fields2Check;
    private ArrayList<String> fields2BIC;
    private ArrayList<String> fuzzyFields;

    public void setBICFields(String fields) {
        if (fields != null) {
            fields2BIC = new ArrayList<String>();

            final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

            while (tokenizer.hasMoreTokens()) {
                fields2BIC.add(tokenizer.nextToken());
            }
        }
    }

    public void setCheckFields(String fields) {
        if (fields != null) {
            fields2Check = new ArrayList<String>();

            final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

            while (tokenizer.hasMoreTokens()) {
                fields2Check.add(tokenizer.nextToken());
            }
        }
    }

    public void setFuzzyFields(String fields) {
        if (fields != null) {
            fuzzyFields = new ArrayList<String>();

            final StringTokenizer tokenizer = new StringTokenizer(fields, ",");

            while (tokenizer.hasMoreTokens()) {
                fuzzyFields.add(tokenizer.nextToken());
            }
        }
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

    public void setFuzzyVal(double fuzzyVal) {
        this.fuzzyVal = fuzzyVal / 100;
    }

    public void setMinTokenLen(int minTokenLen) {
        this.minTokenLen = minTokenLen;
    }

    public void setMinRelVal(int minRelVal) {
        this.minRelVal = minRelVal;
    }

    public void setMinAbsVal(int minAbsVal) {
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

    /*
     * (non-Javadoc)
     * @see at.jps.sanction.domain.swift.StreamConfig1#getFuzzyFields()
     */
    @Override
    public ArrayList<String> getFuzzyFields() {
        return fuzzyFields;
    }

}
