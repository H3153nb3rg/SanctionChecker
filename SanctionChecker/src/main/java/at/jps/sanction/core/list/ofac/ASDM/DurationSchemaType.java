//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.07.15 at 02:50:54 PM MESZ
//

package at.jps.sanction.core.list.ofac.ASDM;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * <p>
 * Java class for DurationSchemaType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DurationSchemaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Years">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>nonNegativeInteger">
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Months">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>nonNegativeInteger">
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Days">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>nonNegativeInteger">
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Approximate" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DurationSchemaType", propOrder = { "years", "months", "days" })
public class DurationSchemaType {

    /**
     * <p>
     * Java class for anonymous complex type.
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>nonNegativeInteger">
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "value" })
    public static class Days {

        @XmlValue
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger            value;
        @XmlAttribute(name = "DeltaAction")
        protected DeltaActionSchemaType deltaAction;

        /**
         * Gets the value of the deltaAction property.
         *
         * @return possible object is {@link DeltaActionSchemaType }
         */
        public DeltaActionSchemaType getDeltaAction() {
            return deltaAction;
        }

        /**
         * Gets the value of the value property.
         *
         * @return possible object is {@link BigInteger }
         */
        public BigInteger getValue() {
            return value;
        }

        /**
         * Sets the value of the deltaAction property.
         *
         * @param value
         *            allowed object is {@link DeltaActionSchemaType }
         */
        public void setDeltaAction(final DeltaActionSchemaType value) {
            deltaAction = value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value
         *            allowed object is {@link BigInteger }
         */
        public void setValue(final BigInteger value) {
            this.value = value;
        }

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
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>nonNegativeInteger">
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "value" })
    public static class Months {

        @XmlValue
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger            value;
        @XmlAttribute(name = "DeltaAction")
        protected DeltaActionSchemaType deltaAction;

        /**
         * Gets the value of the deltaAction property.
         *
         * @return possible object is {@link DeltaActionSchemaType }
         */
        public DeltaActionSchemaType getDeltaAction() {
            return deltaAction;
        }

        /**
         * Gets the value of the value property.
         *
         * @return possible object is {@link BigInteger }
         */
        public BigInteger getValue() {
            return value;
        }

        /**
         * Sets the value of the deltaAction property.
         *
         * @param value
         *            allowed object is {@link DeltaActionSchemaType }
         */
        public void setDeltaAction(final DeltaActionSchemaType value) {
            deltaAction = value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value
         *            allowed object is {@link BigInteger }
         */
        public void setValue(final BigInteger value) {
            this.value = value;
        }

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
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>nonNegativeInteger">
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "value" })
    public static class Years {

        @XmlValue
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger            value;
        @XmlAttribute(name = "DeltaAction")
        protected DeltaActionSchemaType deltaAction;

        /**
         * Gets the value of the deltaAction property.
         *
         * @return possible object is {@link DeltaActionSchemaType }
         */
        public DeltaActionSchemaType getDeltaAction() {
            return deltaAction;
        }

        /**
         * Gets the value of the value property.
         *
         * @return possible object is {@link BigInteger }
         */
        public BigInteger getValue() {
            return value;
        }

        /**
         * Sets the value of the deltaAction property.
         *
         * @param value
         *            allowed object is {@link DeltaActionSchemaType }
         */
        public void setDeltaAction(final DeltaActionSchemaType value) {
            deltaAction = value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value
         *            allowed object is {@link BigInteger }
         */
        public void setValue(final BigInteger value) {
            this.value = value;
        }

    }

    @XmlElement(name = "Years", required = true)
    protected DurationSchemaType.Years  years;
    @XmlElement(name = "Months", required = true)
    protected DurationSchemaType.Months months;

    @XmlElement(name = "Days", required = true)
    protected DurationSchemaType.Days   days;

    @XmlAttribute(name = "Approximate", required = true)
    protected boolean                   approximate;

    @XmlAttribute(name = "DeltaAction")
    protected DeltaActionSchemaType     deltaAction;

    /**
     * Gets the value of the days property.
     *
     * @return possible object is {@link DurationSchemaType.Days }
     */
    public DurationSchemaType.Days getDays() {
        return days;
    }

    /**
     * Gets the value of the deltaAction property.
     *
     * @return possible object is {@link DeltaActionSchemaType }
     */
    public DeltaActionSchemaType getDeltaAction() {
        return deltaAction;
    }

    /**
     * Gets the value of the months property.
     *
     * @return possible object is {@link DurationSchemaType.Months }
     */
    public DurationSchemaType.Months getMonths() {
        return months;
    }

    /**
     * Gets the value of the years property.
     *
     * @return possible object is {@link DurationSchemaType.Years }
     */
    public DurationSchemaType.Years getYears() {
        return years;
    }

    /**
     * Gets the value of the approximate property.
     */
    public boolean isApproximate() {
        return approximate;
    }

    /**
     * Sets the value of the approximate property.
     */
    public void setApproximate(final boolean value) {
        approximate = value;
    }

    /**
     * Sets the value of the days property.
     *
     * @param value
     *            allowed object is {@link DurationSchemaType.Days }
     */
    public void setDays(final DurationSchemaType.Days value) {
        days = value;
    }

    /**
     * Sets the value of the deltaAction property.
     *
     * @param value
     *            allowed object is {@link DeltaActionSchemaType }
     */
    public void setDeltaAction(final DeltaActionSchemaType value) {
        deltaAction = value;
    }

    /**
     * Sets the value of the months property.
     *
     * @param value
     *            allowed object is {@link DurationSchemaType.Months }
     */
    public void setMonths(final DurationSchemaType.Months value) {
        months = value;
    }

    /**
     * Sets the value of the years property.
     *
     * @param value
     *            allowed object is {@link DurationSchemaType.Years }
     */
    public void setYears(final DurationSchemaType.Years value) {
        years = value;
    }

}
