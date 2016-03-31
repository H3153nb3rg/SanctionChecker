package at.jps.sanction.model.wl.entities;

import java.io.Serializable;

import javax.persistence.Entity;

import at.jps.sanction.model.BaseModel;

@Entity
public class WL_Passport extends BaseModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6738991421324273322L;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    private String number;
    private String issueDate;
    private String type;
    private String country;

    public String getCountry() {
        return country;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getNumber() {
        return number;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public void setIssueDate(final String issueDate) {
        this.issueDate = issueDate;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
