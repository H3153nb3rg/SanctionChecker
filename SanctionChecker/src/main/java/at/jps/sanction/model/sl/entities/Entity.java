package at.jps.sanction.model.sl.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 350588830084232520L;

    private List<String>      legalBasises;

    private String            comment;
    private String            issueDate;
    private String            type;                                                                     // Individual
                                                                                                        // /
                                                                                                        // Entity
                                                                                                        // /
                                                                                                        // Transport
                                                                                                        // /
                                                                                                        // Other
                                                                                                        // ...
    private String            id;
    private List<String>      informationUrls;

    private List<Name>        names;
    private List<Passport>    passports;
    private List<Address>     addresses;

    private String            entryType;                                                           // PEP
                                                                                                   // /
                                                                                                   // Sanctiontype

    public String getComment() {
        return comment;
    }

    public String getEntryType() {
        return entryType;
    }

    public String getId() {
        return id;
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

    public List<Name> getNames() {

        if (names == null) {
            names = new ArrayList<Name>();
        }

        return names;
    }

    public List<Passport> getPassports() {

        if (passports == null) {
            passports = new ArrayList<Passport>();
        }

        return passports;
    }

    public List<Address> getAddresses() {

        if (addresses == null) {
            addresses = new ArrayList<Address>();
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

    public void setId(final String id) {
        this.id = id;
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

    public void setNames(final List<Name> names) {
        this.names = names;
    }

    public void setPassports(final List<Passport> passports) {
        this.passports = passports;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
