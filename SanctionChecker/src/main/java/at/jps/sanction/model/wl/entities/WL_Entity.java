package at.jps.sanction.model.wl.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class WL_Entity implements Serializable {

    public enum EntryCategory {
        PEP("PEP"), EMBARGO("EMBARGO"), OTHER("OTHER");

        private final String text;

        private EntryCategory(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

    }

    public enum EntityType {
        INDIVIDUAL("INDIVIDUAL"), ENTITY("ENTITY"), TRANSPORT("TRANSPORT"), OTHER("OTHER");

        private final String text;

        private EntityType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

    }

    /**
     *
     */
    private static final long serialVersionUID = 350588830084232520L;

    private List<String>      legalBasises;
    // to
    // add
    // date
    // to
    // LB
    // !!!!

    private String            listName;
    private String            comment;
    private String            issueDate;

    private WL_BirthInfo       birthday;                                // TODO: we should make a map out of it for generic ref of multiple dates

    private EntityType        entityType;
    // Individual
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
    private WL_Attribute      attributes;

    private EntryCategory     entityCategory;
    // PEP
    // /
    // Sanctiontype

    private List<WL_Relation> relations;

    public String getComment() {
        return comment;
    }

    public EntryCategory getEntryCategory() {
        return entityCategory;
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

    public EntityType getEntityType() {
        return entityType;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setEntryCategory(final EntryCategory entityCategory) {
        this.entityCategory = entityCategory;
    }

    public void setWL_Id(final String id) {
        wl_id = id;
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

    public void setEntityType(final EntityType type) {
        entityType = type;
    }

    public List<WL_Relation> getRelations() {

        if (relations == null) {
            relations = new ArrayList<WL_Relation>();
        }

        return relations;
    }

    public void addReleation(final String wl_id, final String type) {

        final WL_Relation relation = new WL_Relation();
        relation.setWlid_from(getWL_Id());
        relation.setWlid_to(wl_id);
        relation.setDescription(type);

        addReleation(relation);
    }

    public void addReleation(final WL_Relation relation) {
        getRelations().add(relation);
    }

    public String getListName() {
        return listName;
    }

    public void setListName(final String listName) {
        this.listName = listName;
    }

    public WL_Attribute getAttributes() {
        return attributes;
    }

    public void setAttributes(final WL_Attribute attributes) {
        this.attributes = attributes;
    }

    public WL_BirthInfo getBirthday() {
        return birthday;
    }

    public void setBirthday(WL_BirthInfo birthday) {
        this.birthday = birthday;
    }

}
