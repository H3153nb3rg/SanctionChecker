//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2016.02.01 at 09:15:55 PM CET
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
import javax.xml.bind.annotation.XmlValue;

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
 *         &lt;element name="Reference" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="SinceDay" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="SinceMonth" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="SinceYear" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ToYear" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ToMonth" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ToDay" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "reference" })
@XmlRootElement(name = "SanctionsReferences")
public class SanctionsReferences {

    @XmlElement(name = "Reference", nillable = true)
    protected List<SanctionsReferences.Reference> reference;

    /**
     * Gets the value of the reference property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the reference property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getReference().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link SanctionsReferences.Reference }
     */
    public List<SanctionsReferences.Reference> getReference() {
        if (reference == null) {
            reference = new ArrayList<SanctionsReferences.Reference>();
        }
        return this.reference;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="SinceDay" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="SinceMonth" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="SinceYear" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="ToYear" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="ToMonth" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="ToDay" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "value" })
    public static class Reference {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "SinceDay")
        protected String sinceDay;
        @XmlAttribute(name = "SinceMonth")
        protected String sinceMonth;
        @XmlAttribute(name = "SinceYear")
        protected String sinceYear;
        @XmlAttribute(name = "ToYear")
        protected String toYear;
        @XmlAttribute(name = "ToMonth")
        protected String toMonth;
        @XmlAttribute(name = "ToDay")
        protected String toDay;

        /**
         * Gets the value of the value property.
         * 
         * @return possible object is {@link String }
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *            allowed object is {@link String }
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the sinceDay property.
         * 
         * @return possible object is {@link String }
         */
        public String getSinceDay() {
            return sinceDay;
        }

        /**
         * Sets the value of the sinceDay property.
         * 
         * @param value
         *            allowed object is {@link String }
         */
        public void setSinceDay(String value) {
            this.sinceDay = value;
        }

        /**
         * Gets the value of the sinceMonth property.
         * 
         * @return possible object is {@link String }
         */
        public String getSinceMonth() {
            return sinceMonth;
        }

        /**
         * Sets the value of the sinceMonth property.
         * 
         * @param value
         *            allowed object is {@link String }
         */
        public void setSinceMonth(String value) {
            this.sinceMonth = value;
        }

        /**
         * Gets the value of the sinceYear property.
         * 
         * @return possible object is {@link String }
         */
        public String getSinceYear() {
            return sinceYear;
        }

        /**
         * Sets the value of the sinceYear property.
         * 
         * @param value
         *            allowed object is {@link String }
         */
        public void setSinceYear(String value) {
            this.sinceYear = value;
        }

        /**
         * Gets the value of the toYear property.
         * 
         * @return possible object is {@link String }
         */
        public String getToYear() {
            return toYear;
        }

        /**
         * Sets the value of the toYear property.
         * 
         * @param value
         *            allowed object is {@link String }
         */
        public void setToYear(String value) {
            this.toYear = value;
        }

        /**
         * Gets the value of the toMonth property.
         * 
         * @return possible object is {@link String }
         */
        public String getToMonth() {
            return toMonth;
        }

        /**
         * Sets the value of the toMonth property.
         * 
         * @param value
         *            allowed object is {@link String }
         */
        public void setToMonth(String value) {
            this.toMonth = value;
        }

        /**
         * Gets the value of the toDay property.
         * 
         * @return possible object is {@link String }
         */
        public String getToDay() {
            return toDay;
        }

        /**
         * Sets the value of the toDay property.
         * 
         * @param value
         *            allowed object is {@link String }
         */
        public void setToDay(String value) {
            this.toDay = value;
        }

    }

}
