package at.jps.sanction.model;

import java.util.ArrayList;
import java.util.List;

public class FieldCheckConfig {

    private String       fieldName;
    private boolean      fuzzy;

    // private String entityType;
    // private String entityCategory;
    private boolean      handleAsIBAN;
    private boolean      handleAsBIC;
    private boolean      handleAsISIN;
    private boolean      handleAsISOCountry;

    private boolean      checkIngoing;
    private boolean      checkOutgoing;

    private List<String> searchlists;

    // private int minTokenLen;
    // // single token % of match
    // private int minRelVal;
    // private int minAbsVal;
    // // total ( % min(#tokens).length of max(#tokens).length )
    // // private int minRelTokenVal;
    // private int fuzzyVal;

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

    // public int getMinTokenLen() {
    // return minTokenLen;
    // }
    //
    // public void setMinTokenLen(final int tokenLen) {
    // minTokenLen = tokenLen;
    // }

    // public String getEntityType() {
    // return entityType;
    // }
    //
    // public void setEntityType(final String enitityType) {
    // entityType = enitityType;
    // }

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

    public List<String> getSearchlists() {

        if (searchlists == null) {
            searchlists = new ArrayList<>();
        }
        return searchlists;

    }

    public void setSearchlists(final List<String> watchlists) {
        searchlists = watchlists;
    }

    public boolean isHandleAsISIN() {
        return handleAsISIN;
    }

    public void setHandleAsISIN(boolean handleAsISIN) {
        this.handleAsISIN = handleAsISIN;
    }

    public boolean isHandleAsISOCountry() {
        return handleAsISOCountry;
    }

    public void setHandleAsISOCountry(boolean handleISOCountry) {
        handleAsISOCountry = handleISOCountry;
    }

    public boolean isCheckIngoing() {
        return checkIngoing;
    }

    public void setCheckIngoing(boolean checkIngoing) {
        this.checkIngoing = checkIngoing;
    }

    public boolean isCheckOutgoing() {
        return checkOutgoing;
    }

    public void setCheckOutgoing(boolean checkOutgoing) {
        this.checkOutgoing = checkOutgoing;
    }

}
