package at.jps.sanction.model.sl.entities;

import java.io.Serializable;

import javax.persistence.Entity;

import at.jps.sanction.model.BaseModel;

@Entity
public class WL_Address extends BaseModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7950849489280039686L;

    private String            place;
    private String            line;
    private String            country;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
