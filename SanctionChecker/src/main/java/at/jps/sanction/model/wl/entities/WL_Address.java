package at.jps.sanction.model.wl.entities;

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
    private String            countryISO;

    public String getPlace() {
        return place;
    }

    public void setPlace(final String place) {
        this.place = place;
    }

    public String getLine() {
        return line;
    }

    public void setLine(final String line) {
        this.line = line;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getCountryISO() {
        return countryISO;
    }

    public void setCountryISO(String countryISO) {
        this.countryISO = countryISO;
    }

}
