package at.jps.sanction.model.wl.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class WL_BirthInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4082107225907761606L;
    /**
     *
     */

    private String            day;
    private String            month;
    private String            year;
    private String            country;
    private String            place;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
