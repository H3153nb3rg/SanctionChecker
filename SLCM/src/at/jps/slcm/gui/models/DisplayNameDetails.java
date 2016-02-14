package at.jps.slcm.gui.models;

import java.io.Serializable;

public class DisplayNameDetails implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8711878219037513245L;
    private String            firstname;
    private String            middleName;
    private String            surname;
    private String            wholename;
    private String            aka;

    private Long              id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getWholename() {
        return wholename;
    }

    public void setWholename(String wholename) {
        this.wholename = wholename;
    }

    public String getAka() {
        return aka;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

}
