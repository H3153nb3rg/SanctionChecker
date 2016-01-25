package at.jps.sanction.model.sl.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class WL_Entity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 350588830084232520L;

    private List<String>      legalBasises;                            // to add date to LB !!!!

    private String            comment;
    private String            issueDate;
    private String            type;                                                                                                                                                                           // Individual
                                                                                                                                                                                                              // /
                                                                                                                                                                                                              // Entity
                                                                                                                                                                                                              // /
                                                                                                                                                                                                              // Transport
                                                                                                                                                                                                              // /
                                                                                                                                                                                                              // Other
                                                                                                                                                                                                              // ...
    private String            wl_id;
    private List<String>      informationUrls;

    private List<WL_Name>     names;
    private List<WL_Passport> passports;
    private List<WL_Address>  addresses;

    private String            entryType;                                                                                                                                                  // PEP
                                                                                                                                                                                          // /
                                                                                                                                                                                          // Sanctiontype

    public String getComment() {
        return comment;
    }

    public String getEntryType() {
        return entryType;
    }

    public String getWL_Id() {
        return wl_id;
    }

    @Deprecated
    public String getInformationUrl() {
        return (getInformationUrls().size() > 0) ? getInformationUrls().get(0) : null;
    }

    public String getIssueDate() {
        return issueDate;
    }

    @Deprecated
    public String getLegalBasis() {
        return (getLegalBasises().size() > 0) ? getLegalBasises().get(0) : null;
    }

    public List<String> getInformationUrls() {

        if (informationUrls == null) {
            informationUrls = new ArrayList<String>();
        }

        return informationUrls;
    }

    public List<String> getLegalBasises() {

        if (legalBasises == null) {
            legalBasises = new ArrayList<String>();
        }

        return legalBasises;
    }

    public List<WL_Name> getNames() {

        if (names == null) {
            names = new ArrayList<WL_Name>();
        }

        return names;
    }

    public List<WL_Passport> getPassports() {

        if (passports == null) {
            passports = new ArrayList<WL_Passport>();
        }

        return passports;
    }

    public List<WL_Address> getAddresses() {

        if (addresses == null) {
            addresses = new ArrayList<WL_Address>();
        }

        return addresses;
    }

    public String getType() {
        return type;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setEntryType(final String entryType) {
        this.entryType = entryType;
    }

    public void setWL_Id(final String id) {
        this.wl_id = id;
    }

    @Deprecated
    public void setInformationUrl(final String informationUrl) {
        addInformationUrl(informationUrl);
    }

    public void addInformationUrl(final String informationUrl) {
        getInformationUrls().add(informationUrl);
    }

    public void setIssueDate(final String issueDate) {
        this.issueDate = issueDate;
    }

    @Deprecated
    public void setLegalBasis(final String legalBasis) {
        getLegalBasises().add(legalBasis);
    }

    public void addLegalBasis(final String legalBasis) {
        getLegalBasises().add(legalBasis);
    }

    public void setNames(final List<WL_Name> names) {
        this.names = names;
    }

    public void setPassports(final List<WL_Passport> passports) {
        this.passports = passports;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
