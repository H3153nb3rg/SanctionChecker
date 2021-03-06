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
import javax.xml.bind.annotation.XmlValue;

/**
 * <p>
 * Java class for IDRegDocumentSchemaType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="IDRegDocumentSchemaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.un.org/sanctions/1.0}Comment" minOccurs="0"/>
 *         &lt;element name="IDRegistrationNo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="IssuingAuthority" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DocumentDate" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.un.org/sanctions/1.0}DatePeriod"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="IDRegDocDateTypeID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="IDRegDocumentMention" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="IDRegDocumentID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="ReferenceType" use="required" type="{http://www.un.org/sanctions/1.0}ReferenceSchemaType" />
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.un.org/sanctions/1.0}FeatureVersionReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DocumentedNameReference" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="DocumentedNameID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.un.org/sanctions/1.0}ProfileRelationshipReference" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="IDRegDocTypeID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="IdentityID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="IssuedBy-CountryID" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="IssuedIn-LocationID" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="ValidityID" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IDRegDocumentSchemaType", propOrder = { "comment", "idRegistrationNo", "issuingAuthority", "documentDate", "idRegDocumentMention", "featureVersionReference",
        "documentedNameReference", "profileRelationshipReference" })
public class IDRegDocumentSchemaType {

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
     *         &lt;element ref="{http://www.un.org/sanctions/1.0}DatePeriod"/>
     *       &lt;/sequence>
     *       &lt;attribute name="IDRegDocDateTypeID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "datePeriod" })
    public static class DocumentDate {

        @XmlElement(name = "DatePeriod", required = true)
        protected DatePeriod            datePeriod;
        @XmlAttribute(name = "IDRegDocDateTypeID", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger            idRegDocDateTypeID;
        @XmlAttribute(name = "DeltaAction")
        protected DeltaActionSchemaType deltaAction;

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
         * Gets the value of the idRegDocDateTypeID property.
         *
         * @return possible object is {@link BigInteger }
         */
        public BigInteger getIDRegDocDateTypeID() {
            return idRegDocDateTypeID;
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
         * Sets the value of the idRegDocDateTypeID property.
         *
         * @param value
         *            allowed object is {@link BigInteger }
         */
        public void setIDRegDocDateTypeID(final BigInteger value) {
            idRegDocDateTypeID = value;
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
     *       &lt;attribute name="DocumentedNameID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DocumentedNameReference {

        @XmlAttribute(name = "DocumentedNameID", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger            documentedNameID;
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
         * Gets the value of the documentedNameID property.
         *
         * @return possible object is {@link BigInteger }
         */
        public BigInteger getDocumentedNameID() {
            return documentedNameID;
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
         * Sets the value of the documentedNameID property.
         *
         * @param value
         *            allowed object is {@link BigInteger }
         */
        public void setDocumentedNameID(final BigInteger value) {
            documentedNameID = value;
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
     *       &lt;attribute name="IDRegDocumentID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="ReferenceType" use="required" type="{http://www.un.org/sanctions/1.0}ReferenceSchemaType" />
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class IDRegDocumentMention {

        @XmlAttribute(name = "IDRegDocumentID", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger            idRegDocumentID;
        @XmlAttribute(name = "ReferenceType", required = true)
        protected ReferenceSchemaType   referenceType;
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
         * Gets the value of the idRegDocumentID property.
         *
         * @return possible object is {@link BigInteger }
         */
        public BigInteger getIDRegDocumentID() {
            return idRegDocumentID;
        }

        /**
         * Gets the value of the referenceType property.
         *
         * @return possible object is {@link ReferenceSchemaType }
         */
        public ReferenceSchemaType getReferenceType() {
            return referenceType;
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
         * Sets the value of the idRegDocumentID property.
         *
         * @param value
         *            allowed object is {@link BigInteger }
         */
        public void setIDRegDocumentID(final BigInteger value) {
            idRegDocumentID = value;
        }

        /**
         * Sets the value of the referenceType property.
         *
         * @param value
         *            allowed object is {@link ReferenceSchemaType }
         */
        public void setReferenceType(final ReferenceSchemaType value) {
            referenceType = value;
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
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "value" })
    public static class IDRegistrationNo {

        @XmlValue
        protected String                value;
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
         * @return possible object is {@link String }
         */
        public String getValue() {
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
         *            allowed object is {@link String }
         */
        public void setValue(final String value) {
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
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "value" })
    public static class IssuingAuthority {

        @XmlValue
        protected String                value;
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
         * @return possible object is {@link String }
         */
        public String getValue() {
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
         *            allowed object is {@link String }
         */
        public void setValue(final String value) {
            this.value = value;
        }

    }

    @XmlElement(name = "Comment")
    protected Comment                                               comment;
    @XmlElement(name = "IDRegistrationNo")
    protected IDRegDocumentSchemaType.IDRegistrationNo              idRegistrationNo;
    @XmlElement(name = "IssuingAuthority")
    protected IDRegDocumentSchemaType.IssuingAuthority              issuingAuthority;
    @XmlElement(name = "DocumentDate")
    protected List<IDRegDocumentSchemaType.DocumentDate>            documentDate;
    @XmlElement(name = "IDRegDocumentMention")
    protected List<IDRegDocumentSchemaType.IDRegDocumentMention>    idRegDocumentMention;
    @XmlElement(name = "FeatureVersionReference")
    protected List<FeatureVersionReference>                         featureVersionReference;
    @XmlElement(name = "DocumentedNameReference")
    protected List<IDRegDocumentSchemaType.DocumentedNameReference> documentedNameReference;
    @XmlElement(name = "ProfileRelationshipReference")
    protected List<ProfileRelationshipReference>                    profileRelationshipReference;
    @XmlAttribute(name = "ID", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger                                            id;
    @XmlAttribute(name = "IDRegDocTypeID", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger                                            idRegDocTypeID;

    @XmlAttribute(name = "IdentityID", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger                                            identityID;

    @XmlAttribute(name = "IssuedBy-CountryID")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger                                            issuedByCountryID;

    @XmlAttribute(name = "IssuedIn-LocationID")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger                                            issuedInLocationID;

    @XmlAttribute(name = "ValidityID")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger                                            validityID;

    @XmlAttribute(name = "DeltaAction")
    protected DeltaActionSchemaType                                 deltaAction;

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
     * Gets the value of the documentDate property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the documentDate property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getDocumentDate().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link IDRegDocumentSchemaType.DocumentDate }
     */
    public List<IDRegDocumentSchemaType.DocumentDate> getDocumentDate() {
        if (documentDate == null) {
            documentDate = new ArrayList<IDRegDocumentSchemaType.DocumentDate>();
        }
        return documentDate;
    }

    /**
     * Gets the value of the documentedNameReference property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the documentedNameReference property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getDocumentedNameReference().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link IDRegDocumentSchemaType.DocumentedNameReference }
     */
    public List<IDRegDocumentSchemaType.DocumentedNameReference> getDocumentedNameReference() {
        if (documentedNameReference == null) {
            documentedNameReference = new ArrayList<IDRegDocumentSchemaType.DocumentedNameReference>();
        }
        return documentedNameReference;
    }

    /**
     * Gets the value of the featureVersionReference property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the featureVersionReference property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getFeatureVersionReference().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FeatureVersionReference }
     */
    public List<FeatureVersionReference> getFeatureVersionReference() {
        if (featureVersionReference == null) {
            featureVersionReference = new ArrayList<FeatureVersionReference>();
        }
        return featureVersionReference;
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
     * Gets the value of the identityID property.
     *
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getIdentityID() {
        return identityID;
    }

    /**
     * Gets the value of the idRegDocTypeID property.
     *
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getIDRegDocTypeID() {
        return idRegDocTypeID;
    }

    /**
     * Gets the value of the idRegDocumentMention property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the idRegDocumentMention property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getIDRegDocumentMention().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link IDRegDocumentSchemaType.IDRegDocumentMention }
     */
    public List<IDRegDocumentSchemaType.IDRegDocumentMention> getIDRegDocumentMention() {
        if (idRegDocumentMention == null) {
            idRegDocumentMention = new ArrayList<IDRegDocumentSchemaType.IDRegDocumentMention>();
        }
        return idRegDocumentMention;
    }

    /**
     * Gets the value of the idRegistrationNo property.
     *
     * @return possible object is {@link IDRegDocumentSchemaType.IDRegistrationNo }
     */
    public IDRegDocumentSchemaType.IDRegistrationNo getIDRegistrationNo() {
        return idRegistrationNo;
    }

    /**
     * Gets the value of the issuedByCountryID property.
     *
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getIssuedByCountryID() {
        return issuedByCountryID;
    }

    /**
     * Gets the value of the issuedInLocationID property.
     *
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getIssuedInLocationID() {
        return issuedInLocationID;
    }

    /**
     * Gets the value of the issuingAuthority property.
     *
     * @return possible object is {@link IDRegDocumentSchemaType.IssuingAuthority }
     */
    public IDRegDocumentSchemaType.IssuingAuthority getIssuingAuthority() {
        return issuingAuthority;
    }

    /**
     * Gets the value of the profileRelationshipReference property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the profileRelationshipReference property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getProfileRelationshipReference().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ProfileRelationshipReference }
     */
    public List<ProfileRelationshipReference> getProfileRelationshipReference() {
        if (profileRelationshipReference == null) {
            profileRelationshipReference = new ArrayList<ProfileRelationshipReference>();
        }
        return profileRelationshipReference;
    }

    /**
     * Gets the value of the validityID property.
     *
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getValidityID() {
        return validityID;
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
     * Sets the value of the id property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     */
    public void setID(final BigInteger value) {
        id = value;
    }

    /**
     * Sets the value of the identityID property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     */
    public void setIdentityID(final BigInteger value) {
        identityID = value;
    }

    /**
     * Sets the value of the idRegDocTypeID property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     */
    public void setIDRegDocTypeID(final BigInteger value) {
        idRegDocTypeID = value;
    }

    /**
     * Sets the value of the idRegistrationNo property.
     *
     * @param value
     *            allowed object is {@link IDRegDocumentSchemaType.IDRegistrationNo }
     */
    public void setIDRegistrationNo(final IDRegDocumentSchemaType.IDRegistrationNo value) {
        idRegistrationNo = value;
    }

    /**
     * Sets the value of the issuedByCountryID property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     */
    public void setIssuedByCountryID(final BigInteger value) {
        issuedByCountryID = value;
    }

    /**
     * Sets the value of the issuedInLocationID property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     */
    public void setIssuedInLocationID(final BigInteger value) {
        issuedInLocationID = value;
    }

    /**
     * Sets the value of the issuingAuthority property.
     *
     * @param value
     *            allowed object is {@link IDRegDocumentSchemaType.IssuingAuthority }
     */
    public void setIssuingAuthority(final IDRegDocumentSchemaType.IssuingAuthority value) {
        issuingAuthority = value;
    }

    /**
     * Sets the value of the validityID property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     */
    public void setValidityID(final BigInteger value) {
        validityID = value;
    }

}
