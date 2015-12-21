package at.jps.sanction.model.sl.entities;

import java.io.Serializable;
import java.util.List;

public class Name implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3439411802913057378L;

    private String            firstName;
    private String            middleName;
    private String            LastName;
    private String            wholeName;

    private String            description;

    private boolean           aka;
    private boolean           waka;

    private List<String>      tokenizedNames;                                                                                                                                                                                                                                                                                                                                                  // Helper

    public void addToWholeName(final String wholeName) {
        if (wholeName != null) {
            // if (wholeName.equals("null"))
            // {
            // System.out.println("mist");
            // }
            if (this.wholeName == null) {
                this.wholeName = wholeName;
            }
            else {

                if (!this.wholeName.contains(wholeName)) {
                    this.wholeName += " " + wholeName;
                }
            }
        }
    }

    public String getDescription() {
        return description;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public List<String> getTokenizedNames() {
        return tokenizedNames;
    }

    public String getWholeName() {
        return wholeName;
    }

    public boolean isAka() {
        return aka;
    }

    public boolean isWaka() {
        return waka;
    }

    public void setAka(final boolean aka) {
        this.aka = aka;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        LastName = lastName;
    }

    public void setTokenizedNames(final List<String> tokenizedNames) {
        this.tokenizedNames = tokenizedNames;
    }

    public void setWaka(final boolean waka) {
        this.waka = waka;
    }

    public void setWholeName(final String wholeName) {
        this.wholeName = wholeName;
    }

    @Override
    public boolean equals(Object other) {

        boolean equal = false;

        if ((other != null)) {

            equal = (((getFirstName() != null && ((Name) other).getFirstName() != null)) && (getFirstName().equals(((Name) other).getFirstName())))
                    || ((getFirstName() == null && ((Name) other).getFirstName() == null));
            if (equal) {
                equal = (((getLastName() != null && ((Name) other).getLastName() != null)) && (getLastName().equals(((Name) other).getLastName())))
                        || ((getLastName() == null && ((Name) other).getLastName() == null));
            }
            if (equal) {
                equal = (((getMiddleName() != null && ((Name) other).getMiddleName() != null)) && (getMiddleName().equals(((Name) other).getMiddleName())))
                        || ((getMiddleName() == null && ((Name) other).getMiddleName() == null));
            }
            if (equal) {
                equal = (((getWholeName() != null && ((Name) other).getWholeName() != null)) && (getWholeName().equals(((Name) other).getWholeName())))
                        || ((getWholeName() == null && ((Name) other).getWholeName() == null));
            }

            if (equal) {
                equal = (((getDescription() != null && ((Name) other).getDescription() != null)) && (getDescription().equals(((Name) other).getDescription())))
                        || ((getDescription() == null && ((Name) other).getDescription() == null));
            }
        }
        return equal;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
