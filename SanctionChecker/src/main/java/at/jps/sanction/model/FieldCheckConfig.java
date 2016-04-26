package at.jps.sanction.model;

import java.util.List;

public class FieldCheckConfig {

    public final String  entityTypes[]      = { "Transport", "Individual", "Entity" };

    public final String  entityCategories[] = { "Name", "Address", "Birthday", "Country" };

    private String       fieldName;
    private boolean      fuzzy;
    private int          tokenLen;
    private String       entityType;
    private String       entityCategory;
    private boolean      handleAsIBAN;
    private boolean      handleAsBIC;
    private List<String> watchlists;

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

    public int getTokenLen() {
        return tokenLen;
    }

    public void setTokenLen(final int tokenLen) {
        this.tokenLen = tokenLen;
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

}
