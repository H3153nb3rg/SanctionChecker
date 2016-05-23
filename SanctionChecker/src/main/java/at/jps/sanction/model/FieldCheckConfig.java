package at.jps.sanction.model;

import java.util.List;

public class FieldCheckConfig {

    public final String  entityTypes[]      = { "Transport", "Individual", "Entity" };

    public final String  entityCategories[] = { "Name", "Address", "Birthday", "Country" };

    private String       fieldName;
    private boolean      fuzzy;
    private int          minTokenLen;
    private String       entityType;
    private String       entityCategory;
    private boolean      handleAsIBAN;
    private boolean      handleAsBIC;
    private List<String> watchlists;

    // single token % of match
    private int          minRelVal;
    private int          minAbsVal;
    // total ( % min(#tokens).length of max(#tokens).length )
    // private int minRelTokenVal;
    private int          fuzzyVal;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(final boolean fuzzy) {
        this.fuzzy = fuzzy;
    }

    public int getMinTokenLen() {
        return minTokenLen;
    }

    public void setMinTokenLen(final int tokenLen) {
        minTokenLen = tokenLen;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(final String enitityType) {
        entityType = enitityType;
    }

    public boolean isHandleAsIBAN() {
        return handleAsIBAN;
    }

    public void setHandleAsIBAN(final boolean handleAsIBAN) {
        this.handleAsIBAN = handleAsIBAN;
    }

    public boolean isHandleAsBIC() {
        return handleAsBIC;
    }

    public void setHandleAsBIC(final boolean handleAsBIC) {
        this.handleAsBIC = handleAsBIC;
    }

    public List<String> getWatchlists() {
        return watchlists;
    }

    public void setWatchlists(final List<String> watchlists) {
        this.watchlists = watchlists;
    }

    public String getEntityCategory() {
        return entityCategory;
    }

    public void setEntityCategory(String entityCategory) {
        this.entityCategory = entityCategory;
    }

    public int getFuzzyVal() {
        return fuzzyVal;
    }

    public void setFuzzyVal(int fuzzyVal) {
        this.fuzzyVal = fuzzyVal;
    }

    public int getMinRelVal() {
        return minRelVal;
    }

    public void setMinRelVal(int minRelVal) {
        this.minRelVal = minRelVal;
    }

    public int getMinAbsVal() {
        return minAbsVal;
    }

    public void setMinAbsVal(int minAbsVal) {
        this.minAbsVal = minAbsVal;
    }

}
