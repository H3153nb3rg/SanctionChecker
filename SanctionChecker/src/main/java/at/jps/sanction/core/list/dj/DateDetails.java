/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2012.09.25 at 10:47:25 AM MESZ
//

package at.jps.sanction.core.list.dj;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Date" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DateValue" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Day" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Month" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Year" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Dnotes" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="DateType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "date" })
@XmlRootElement(name = "DateDetails")
public class DateDetails {

    /**
     * <p>
     * Java class for anonymous complex type.
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="DateValue" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="Day" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Month" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Year" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Dnotes" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="DateType" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "dateValue" })
    public static class Date {

        /**
         * <p>
         * Java class for anonymous complex type.
         * <p>
         * The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="Day" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Month" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Year" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Dnotes" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class DateValue {

            @XmlAttribute(name = "Day")
            protected String day;
            @XmlAttribute(name = "Month")
            protected String month;
            @XmlAttribute(name = "Year")
            protected String year;
            @XmlAttribute(name = "Dnotes")
            protected String dnotes;

            /**
             * Gets the value of the day property.
             *
             * @return possible object is {@link String }
             */
            public String getDay() {
                return day;
            }

            /**
             * Gets the value of the dnotes property.
             *
             * @return possible object is {@link String }
             */
            public String getDnotes() {
                return dnotes;
            }

            /**
             * Gets the value of the month property.
             *
             * @return possible object is {@link String }
             */
            public String getMonth() {
                return month;
            }

            /**
             * Gets the value of the year property.
             *
             * @return possible object is {@link String }
             */
            public String getYear() {
                return year;
            }

            /**
             * Sets the value of the day property.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setDay(final String value) {
                day = value;
            }

            /**
             * Sets the value of the dnotes property.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setDnotes(final String value) {
                dnotes = value;
            }

            /**
             * Sets the value of the month property.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setMonth(final String value) {
                month = value;
            }

            /**
             * Sets the value of the year property.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setYear(final String value) {
                year = value;
            }

        }

        @XmlElement(name = "DateValue")
        protected List<DateDetails.Date.DateValue> dateValue;

        @XmlAttribute(name = "DateType")
        protected String dateType;

        /**
         * Gets the value of the dateType property.
         *
         * @return possible object is {@link String }
         */
        public String getDateType() {
            return dateType;
        }

        /**
         * Gets the value of the dateValue property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the dateValue property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getDateValue().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link DateDetails.Date.DateValue }
         */
        public List<DateDetails.Date.DateValue> getDateValue() {
            if (dateValue == null) {
                dateValue = new ArrayList<DateDetails.Date.DateValue>();
            }
            return dateValue;
        }

        /**
         * Sets the value of the dateType property.
         *
         * @param value
         *            allowed object is {@link String }
         */
        public void setDateType(final String value) {
            dateType = value;
        }

    }

    @XmlElement(name = "Date")
    protected List<DateDetails.Date> date;

    /**
     * Gets the value of the date property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the date property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getDate().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link DateDetails.Date }
     */
    public List<DateDetails.Date> getDate() {
        if (date == null) {
            date = new ArrayList<DateDetails.Date>();
        }
        return date;
    }

}
