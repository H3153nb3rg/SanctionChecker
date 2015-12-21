//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.07.15 at 02:50:54 PM MESZ
//

package at.jps.sanction.core.list.ofac.ASDM;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for IdentitySchemaType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="IdentitySchemaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.un.org/sanctions/1.0}Comment" minOccurs="0"/>
 *         &lt;element name="Alias" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.un.org/sanctions/1.0}Comment" minOccurs="0"/>
 *                   &lt;element ref="{http://www.un.org/sanctions/1.0}DatePeriod" minOccurs="0"/>
 *                   &lt;element name="DocumentedName" type="{http://www.un.org/sanctions/1.0}DocumentedNameSchemaType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="FixedRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="AliasTypeID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="Primary" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="LowQuality" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="NamePartGroups">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MasterNamePartGroup" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="NamePartGroup" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                                     &lt;attribute name="NamePartTypeID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                                     &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.un.org/sanctions/1.0}IDRegDocumentReference" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="FixedRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Primary" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="False" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentitySchemaType", propOrder = { "comment", "alias", "namePartGroups", "idRegDocumentReference" })
public class IdentitySchemaType {

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
     *         &lt;element ref="{http://www.un.org/sanctions/1.0}Comment" minOccurs="0"/>
     *         &lt;element ref="{http://www.un.org/sanctions/1.0}DatePeriod" minOccurs="0"/>
     *         &lt;element name="DocumentedName" type="{http://www.un.org/sanctions/1.0}DocumentedNameSchemaType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *       &lt;attribute name="FixedRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="AliasTypeID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="Primary" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="LowQuality" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "comment", "datePeriod", "documentedName" })
    public static class Alias {

        @XmlElement(name = "Comment")
        protected Comment                        comment;
        @XmlElement(name = "DatePeriod")
        protected DatePeriod                     datePeriod;
        @XmlElement(name = "DocumentedName", required = true)
        protected List<DocumentedNameSchemaType> documentedName;
        @XmlAttribute(name = "FixedRef", required = true)
        protected String                         fixedRef;
        @XmlAttribute(name = "AliasTypeID", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger                     aliasTypeID;
        @XmlAttribute(name = "Primary", required = true)
        protected boolean                        primary;
        @XmlAttribute(name = "LowQuality", required = true)
        protected boolean                        lowQuality;
        @XmlAttribute(name = "DeltaAction")
        protected DeltaActionSchemaType          deltaAction;

        /**
         * Gets the value of the aliasTypeID property.
         *
         * @return possible object is {@link BigInteger }
         */
        public BigInteger getAliasTypeID() {
            return aliasTypeID;
        }

        /**
         * Gets the value of the comment property.
         *
         * @return possible object is {@link Comment }
         */
        public Comment getComment() {
            return comment;
        }

        /**
         * Gets the value of the datePeriod property.
         *
         * @return possible object is {@link DatePeriod }
         */
        public DatePeriod getDatePeriod() {
            return datePeriod;
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
         * Gets the value of the documentedName property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the documentedName property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getDocumentedName().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link DocumentedNameSchemaType }
         */
        public List<DocumentedNameSchemaType> getDocumentedName() {
            if (documentedName == null) {
                documentedName = new ArrayList<DocumentedNameSchemaType>();
            }
            return documentedName;
        }

        /**
         * Gets the value of the fixedRef property.
         *
         * @return possible object is {@link String }
         */
        public String getFixedRef() {
            return fixedRef;
        }

        /**
         * Gets the value of the lowQuality property.
         */
        public boolean isLowQuality() {
            return lowQuality;
        }

        /**
         * Gets the value of the primary property.
         */
        public boolean isPrimary() {
            return primary;
        }

        /**
         * Sets the value of the aliasTypeID property.
         *
         * @param value
         *            allowed object is {@link BigInteger }
         */
        public void setAliasTypeID(final BigInteger value) {
            aliasTypeID = value;
        }

        /**
         * Sets the value of the comment property.
         *
         * @param value
         *            allowed object is {@link Comment }
         */
        public void setComment(final Comment value) {
            comment = value;
        }

        /**
         * Sets the value of the datePeriod property.
         *
         * @param value
         *            allowed object is {@link DatePeriod }
         */
        public void setDatePeriod(final DatePeriod value) {
            datePeriod = value;
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
         * Sets the value of the fixedRef property.
         *
         * @param value
         *            allowed object is {@link String }
         */
        public void setFixedRef(final String value) {
            fixedRef = value;
        }

        /**
         * Sets the value of the lowQuality property.
         */
        public void setLowQuality(final boolean value) {
            lowQuality = value;
        }

        /**
         * Sets the value of the primary property.
         */
        public void setPrimary(final boolean value) {
            primary = value;
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
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="MasterNamePartGroup" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="NamePartGroup" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                           &lt;attribute name="NamePartTypeID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                           &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "masterNamePartGroup" })
    public static class NamePartGroups {

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
         *         &lt;element name="NamePartGroup" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *                 &lt;attribute name="NamePartTypeID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "namePartGroup" })
        public static class MasterNamePartGroup {

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
             *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
             *       &lt;attribute name="NamePartTypeID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
             *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class NamePartGroup {

                @XmlAttribute(name = "ID", required = true)
                @XmlSchemaType(name = "nonNegativeInteger")
                protected BigInteger            id;
                @XmlAttribute(name = "NamePartTypeID", required = true)
                @XmlSchemaType(name = "nonNegativeInteger")
                protected BigInteger            namePartTypeID;
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
                 * Gets the value of the id property.
                 *
                 * @return possible object is {@link BigInteger }
                 */
                public BigInteger getID() {
                    return id;
                }

                /**
                 * Gets the value of the namePartTypeID property.
                 *
                 * @return possible object is {@link BigInteger }
                 */
                public BigInteger getNamePartTypeID() {
                    return namePartTypeID;
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
                 * Sets the value of the id property.
                 *
                 * @param value
                 *            allowed object is {@link BigInteger }
                 */
                public void setID(final BigInteger value) {
                    id = value;
                }

                /**
                 * Sets the value of the namePartTypeID property.
                 *
                 * @param value
                 *            allowed object is {@link BigInteger }
                 */
                public void setNamePartTypeID(final BigInteger value) {
                    namePartTypeID = value;
                }

            }

            @XmlElement(name = "NamePartGroup", required = true)
            protected List<IdentitySchemaType.NamePartGroups.MasterNamePartGroup.NamePartGroup> namePartGroup;

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
             * Gets the value of the namePartGroup property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the namePartGroup property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getNamePartGroup().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link IdentitySchemaType.NamePartGroups.MasterNamePartGroup.NamePartGroup }
             */
            public List<IdentitySchemaType.NamePartGroups.MasterNamePartGroup.NamePartGroup> getNamePartGroup() {
                if (namePartGroup == null) {
                    namePartGroup = new ArrayList<IdentitySchemaType.NamePartGroups.MasterNamePartGroup.NamePartGroup>();
                }
                return namePartGroup;
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

        }

        @XmlElement(name = "MasterNamePartGroup", required = true)
        protected List<IdentitySchemaType.NamePartGroups.MasterNamePartGroup> masterNamePartGroup;

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
         * Gets the value of the masterNamePartGroup property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the masterNamePartGroup property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getMasterNamePartGroup().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link IdentitySchemaType.NamePartGroups.MasterNamePartGroup }
         */
        public List<IdentitySchemaType.NamePartGroups.MasterNamePartGroup> getMasterNamePartGroup() {
            if (masterNamePartGroup == null) {
                masterNamePartGroup = new ArrayList<IdentitySchemaType.NamePartGroups.MasterNamePartGroup>();
            }
            return masterNamePartGroup;
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

    }

    @XmlElement(name = "Comment")
    protected Comment                           comment;
    @XmlElement(name = "Alias", required = true)
    protected List<IdentitySchemaType.Alias>    alias;
    @XmlElement(name = "NamePartGroups", required = true)
    protected IdentitySchemaType.NamePartGroups namePartGroups;
    @XmlElement(name = "IDRegDocumentReference")
    protected List<IDRegDocumentReference>      idRegDocumentReference;
    @XmlAttribute(name = "ID", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger                        id;
    @XmlAttribute(name = "FixedRef", required = true)
    protected String                            fixedRef;
    @XmlAttribute(name = "Primary", required = true)
    protected boolean                           primary;

    @XmlAttribute(name = "False", required = true)
    protected boolean _false;

    @XmlAttribute(name = "DeltaAction")
    protected DeltaActionSchemaType deltaAction;

    /**
     * Gets the value of the alias property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the alias property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getAlias().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link IdentitySchemaType.Alias }
     */
    public List<IdentitySchemaType.Alias> getAlias() {
        if (alias == null) {
            alias = new ArrayList<IdentitySchemaType.Alias>();
        }
        return alias;
    }

    /**
     * Gets the value of the comment property.
     *
     * @return possible object is {@link Comment }
     */
    public Comment getComment() {
        return comment;
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
     * Gets the value of the fixedRef property.
     *
     * @return possible object is {@link String }
     */
    public String getFixedRef() {
        return fixedRef;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getID() {
        return id;
    }

    /**
     * Gets the value of the idRegDocumentReference property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the idRegDocumentReference property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getIDRegDocumentReference().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link IDRegDocumentReference }
     */
    public List<IDRegDocumentReference> getIDRegDocumentReference() {
        if (idRegDocumentReference == null) {
            idRegDocumentReference = new ArrayList<IDRegDocumentReference>();
        }
        return idRegDocumentReference;
    }

    /**
     * Gets the value of the namePartGroups property.
     *
     * @return possible object is {@link IdentitySchemaType.NamePartGroups }
     */
    public IdentitySchemaType.NamePartGroups getNamePartGroups() {
        return namePartGroups;
    }

    /**
     * Gets the value of the false property.
     */
    public boolean isFalse() {
        return _false;
    }

    /**
     * Gets the value of the primary property.
     */
    public boolean isPrimary() {
        return primary;
    }

    /**
     * Sets the value of the comment property.
     *
     * @param value
     *            allowed object is {@link Comment }
     */
    public void setComment(final Comment value) {
        comment = value;
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
     * Sets the value of the false property.
     */
    public void setFalse(final boolean value) {
        _false = value;
    }

    /**
     * Sets the value of the fixedRef property.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setFixedRef(final String value) {
        fixedRef = value;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     */
    public void setID(final BigInteger value) {
        id = value;
    }

    /**
     * Sets the value of the namePartGroups property.
     *
     * @param value
     *            allowed object is {@link IdentitySchemaType.NamePartGroups }
     */
    public void setNamePartGroups(final IdentitySchemaType.NamePartGroups value) {
        namePartGroups = value;
    }

    /**
     * Sets the value of the primary property.
     */
    public void setPrimary(final boolean value) {
        primary = value;
    }

}
