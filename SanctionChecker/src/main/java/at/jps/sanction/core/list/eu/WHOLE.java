/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren.
// Generiert: 2014.02.11 um 08:57:10 PM CET
//

package at.jps.sanction.core.list.eu;

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
 * Java-Klasse für anonymous complex type.
 * <p>
 * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ENTITY" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NAME" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="LASTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="FIRSTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="MIDDLENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="WHOLENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="GENDER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="TITLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="FUNCTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="BIRTH" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="PLACE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="CITIZEN" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="PASSPORT" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ADDRESS" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="ZIPCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="OTHER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="remark" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "entity" })
@XmlRootElement(name = "WHOLE")
public class WHOLE {

    /**
     * <p>
     * Java-Klasse für anonymous complex type.
     * <p>
     * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="NAME" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="LASTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="FIRSTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="MIDDLENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="WHOLENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="GENDER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="TITLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="FUNCTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="BIRTH" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="PLACE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="CITIZEN" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="PASSPORT" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ADDRESS" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="ZIPCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="OTHER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="remark" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "name", "birth", "citizen", "passport", "address" })
    public static class ENTITY {

        /**
         * <p>
         * Java-Klasse für anonymous complex type.
         * <p>
         * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="ZIPCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="OTHER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "number", "street", "zipcode", "city", "country", "other" })
        public static class ADDRESS {

            @XmlElement(name = "NUMBER")
            protected String number;
            @XmlElement(name = "STREET")
            protected String street;
            @XmlElement(name = "ZIPCODE")
            protected String zipcode;
            @XmlElement(name = "CITY")
            protected String city;
            @XmlElement(name = "COUNTRY")
            protected String country;
            @XmlElement(name = "OTHER")
            protected String other;
            @XmlAttribute(name = "Id")
            protected String id;
            @XmlAttribute(name = "Entity_id")
            protected String entityId;
            @XmlAttribute(name = "legal_basis")
            protected String legalBasis;
            @XmlAttribute(name = "reg_date")
            protected String regDate;
            @XmlAttribute(name = "pdf_link")
            protected String pdfLink;
            @XmlAttribute(name = "programme")
            protected String programme;

            /**
             * Ruft den Wert der city-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getCITY() {
                return city;
            }

            /**
             * Ruft den Wert der country-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getCOUNTRY() {
                return country;
            }

            /**
             * Ruft den Wert der entityId-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getEntityId() {
                return entityId;
            }

            /**
             * Ruft den Wert der id-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getId() {
                return id;
            }

            /**
             * Ruft den Wert der legalBasis-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getLegalBasis() {
                return legalBasis;
            }

            /**
             * Ruft den Wert der number-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getNUMBER() {
                return number;
            }

            /**
             * Ruft den Wert der other-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getOTHER() {
                return other;
            }

            /**
             * Ruft den Wert der pdfLink-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getPdfLink() {
                return pdfLink;
            }

            /**
             * Ruft den Wert der programme-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getProgramme() {
                return programme;
            }

            /**
             * Ruft den Wert der regDate-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getRegDate() {
                return regDate;
            }

            /**
             * Ruft den Wert der street-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getSTREET() {
                return street;
            }

            /**
             * Ruft den Wert der zipcode-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getZIPCODE() {
                return zipcode;
            }

            /**
             * Legt den Wert der city-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setCITY(final String value) {
                city = value;
            }

            /**
             * Legt den Wert der country-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setCOUNTRY(final String value) {
                country = value;
            }

            /**
             * Legt den Wert der entityId-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setEntityId(final String value) {
                entityId = value;
            }

            /**
             * Legt den Wert der id-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setId(final String value) {
                id = value;
            }

            /**
             * Legt den Wert der legalBasis-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setLegalBasis(final String value) {
                legalBasis = value;
            }

            /**
             * Legt den Wert der number-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setNUMBER(final String value) {
                number = value;
            }

            /**
             * Legt den Wert der other-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setOTHER(final String value) {
                other = value;
            }

            /**
             * Legt den Wert der pdfLink-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setPdfLink(final String value) {
                pdfLink = value;
            }

            /**
             * Legt den Wert der programme-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setProgramme(final String value) {
                programme = value;
            }

            /**
             * Legt den Wert der regDate-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setRegDate(final String value) {
                regDate = value;
            }

            /**
             * Legt den Wert der street-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setSTREET(final String value) {
                street = value;
            }

            /**
             * Legt den Wert der zipcode-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setZIPCODE(final String value) {
                zipcode = value;
            }

        }

        /**
         * <p>
         * Java-Klasse für anonymous complex type.
         * <p>
         * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="PLACE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "date", "place", "country" })
        public static class BIRTH {

            @XmlElement(name = "DATE")
            protected String date;
            @XmlElement(name = "PLACE")
            protected String place;
            @XmlElement(name = "COUNTRY")
            protected String country;
            @XmlAttribute(name = "Id")
            protected String id;
            @XmlAttribute(name = "Entity_id")
            protected String entityId;
            @XmlAttribute(name = "legal_basis")
            protected String legalBasis;
            @XmlAttribute(name = "reg_date")
            protected String regDate;
            @XmlAttribute(name = "pdf_link")
            protected String pdfLink;
            @XmlAttribute(name = "programme")
            protected String programme;

            /**
             * Ruft den Wert der country-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getCOUNTRY() {
                return country;
            }

            /**
             * Ruft den Wert der date-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getDATE() {
                return date;
            }

            /**
             * Ruft den Wert der entityId-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getEntityId() {
                return entityId;
            }

            /**
             * Ruft den Wert der id-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getId() {
                return id;
            }

            /**
             * Ruft den Wert der legalBasis-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getLegalBasis() {
                return legalBasis;
            }

            /**
             * Ruft den Wert der pdfLink-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getPdfLink() {
                return pdfLink;
            }

            /**
             * Ruft den Wert der place-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getPLACE() {
                return place;
            }

            /**
             * Ruft den Wert der programme-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getProgramme() {
                return programme;
            }

            /**
             * Ruft den Wert der regDate-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getRegDate() {
                return regDate;
            }

            /**
             * Legt den Wert der country-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setCOUNTRY(final String value) {
                country = value;
            }

            /**
             * Legt den Wert der date-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setDATE(final String value) {
                date = value;
            }

            /**
             * Legt den Wert der entityId-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setEntityId(final String value) {
                entityId = value;
            }

            /**
             * Legt den Wert der id-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setId(final String value) {
                id = value;
            }

            /**
             * Legt den Wert der legalBasis-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setLegalBasis(final String value) {
                legalBasis = value;
            }

            /**
             * Legt den Wert der pdfLink-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setPdfLink(final String value) {
                pdfLink = value;
            }

            /**
             * Legt den Wert der place-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setPLACE(final String value) {
                place = value;
            }

            /**
             * Legt den Wert der programme-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setProgramme(final String value) {
                programme = value;
            }

            /**
             * Legt den Wert der regDate-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setRegDate(final String value) {
                regDate = value;
            }

        }

        /**
         * <p>
         * Java-Klasse für anonymous complex type.
         * <p>
         * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "country" })
        public static class CITIZEN {

            @XmlElement(name = "COUNTRY")
            protected String country;
            @XmlAttribute(name = "Id")
            protected String id;
            @XmlAttribute(name = "Entity_id")
            protected String entityId;
            @XmlAttribute(name = "legal_basis")
            protected String legalBasis;
            @XmlAttribute(name = "reg_date")
            protected String regDate;
            @XmlAttribute(name = "pdf_link")
            protected String pdfLink;
            @XmlAttribute(name = "programme")
            protected String programme;

            /**
             * Ruft den Wert der country-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getCOUNTRY() {
                return country;
            }

            /**
             * Ruft den Wert der entityId-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getEntityId() {
                return entityId;
            }

            /**
             * Ruft den Wert der id-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getId() {
                return id;
            }

            /**
             * Ruft den Wert der legalBasis-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getLegalBasis() {
                return legalBasis;
            }

            /**
             * Ruft den Wert der pdfLink-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getPdfLink() {
                return pdfLink;
            }

            /**
             * Ruft den Wert der programme-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getProgramme() {
                return programme;
            }

            /**
             * Ruft den Wert der regDate-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getRegDate() {
                return regDate;
            }

            /**
             * Legt den Wert der country-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setCOUNTRY(final String value) {
                country = value;
            }

            /**
             * Legt den Wert der entityId-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setEntityId(final String value) {
                entityId = value;
            }

            /**
             * Legt den Wert der id-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setId(final String value) {
                id = value;
            }

            /**
             * Legt den Wert der legalBasis-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setLegalBasis(final String value) {
                legalBasis = value;
            }

            /**
             * Legt den Wert der pdfLink-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setPdfLink(final String value) {
                pdfLink = value;
            }

            /**
             * Legt den Wert der programme-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setProgramme(final String value) {
                programme = value;
            }

            /**
             * Legt den Wert der regDate-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setRegDate(final String value) {
                regDate = value;
            }

        }

        /**
         * <p>
         * Java-Klasse für anonymous complex type.
         * <p>
         * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="LASTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="FIRSTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="MIDDLENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="WHOLENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="GENDER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="TITLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="FUNCTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "lastname", "firstname", "middlename", "wholename", "gender", "title", "function", "language" })
        public static class NAME {

            @XmlElement(name = "LASTNAME")
            protected String lastname;
            @XmlElement(name = "FIRSTNAME")
            protected String firstname;
            @XmlElement(name = "MIDDLENAME")
            protected String middlename;
            @XmlElement(name = "WHOLENAME")
            protected String wholename;
            @XmlElement(name = "GENDER")
            protected String gender;
            @XmlElement(name = "TITLE")
            protected String title;
            @XmlElement(name = "FUNCTION")
            protected String function;
            @XmlElement(name = "LANGUAGE")
            protected String language;
            @XmlAttribute(name = "Id")
            protected String id;
            @XmlAttribute(name = "Entity_id")
            protected String entityId;
            @XmlAttribute(name = "legal_basis")
            protected String legalBasis;
            @XmlAttribute(name = "reg_date")
            protected String regDate;
            @XmlAttribute(name = "pdf_link")
            protected String pdfLink;
            @XmlAttribute(name = "programme")
            protected String programme;

            /**
             * Ruft den Wert der entityId-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getEntityId() {
                return entityId;
            }

            /**
             * Ruft den Wert der firstname-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getFIRSTNAME() {
                return firstname;
            }

            /**
             * Ruft den Wert der function-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getFUNCTION() {
                return function;
            }

            /**
             * Ruft den Wert der gender-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getGENDER() {
                return gender;
            }

            /**
             * Ruft den Wert der id-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getId() {
                return id;
            }

            /**
             * Ruft den Wert der language-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getLANGUAGE() {
                return language;
            }

            /**
             * Ruft den Wert der lastname-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getLASTNAME() {
                return lastname;
            }

            /**
             * Ruft den Wert der legalBasis-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getLegalBasis() {
                return legalBasis;
            }

            /**
             * Ruft den Wert der middlename-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getMIDDLENAME() {
                return middlename;
            }

            /**
             * Ruft den Wert der pdfLink-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getPdfLink() {
                return pdfLink;
            }

            /**
             * Ruft den Wert der programme-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getProgramme() {
                return programme;
            }

            /**
             * Ruft den Wert der regDate-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getRegDate() {
                return regDate;
            }

            /**
             * Ruft den Wert der title-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getTITLE() {
                return title;
            }

            /**
             * Ruft den Wert der wholename-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getWHOLENAME() {
                return wholename;
            }

            /**
             * Legt den Wert der entityId-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setEntityId(final String value) {
                entityId = value;
            }

            /**
             * Legt den Wert der firstname-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setFIRSTNAME(final String value) {
                firstname = value;
            }

            /**
             * Legt den Wert der function-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setFUNCTION(final String value) {
                function = value;
            }

            /**
             * Legt den Wert der gender-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setGENDER(final String value) {
                gender = value;
            }

            /**
             * Legt den Wert der id-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setId(final String value) {
                id = value;
            }

            /**
             * Legt den Wert der language-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setLANGUAGE(final String value) {
                language = value;
            }

            /**
             * Legt den Wert der lastname-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setLASTNAME(final String value) {
                lastname = value;
            }

            /**
             * Legt den Wert der legalBasis-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setLegalBasis(final String value) {
                legalBasis = value;
            }

            /**
             * Legt den Wert der middlename-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setMIDDLENAME(final String value) {
                middlename = value;
            }

            /**
             * Legt den Wert der pdfLink-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setPdfLink(final String value) {
                pdfLink = value;
            }

            /**
             * Legt den Wert der programme-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setProgramme(final String value) {
                programme = value;
            }

            /**
             * Legt den Wert der regDate-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setRegDate(final String value) {
                regDate = value;
            }

            /**
             * Legt den Wert der title-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setTITLE(final String value) {
                title = value;
            }

            /**
             * Legt den Wert der wholename-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setWHOLENAME(final String value) {
                wholename = value;
            }

        }

        /**
         * <p>
         * Java-Klasse für anonymous complex type.
         * <p>
         * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Entity_id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="legal_basis" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="reg_date" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="pdf_link" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="programme" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "number", "country" })
        public static class PASSPORT {

            @XmlElement(name = "NUMBER")
            protected String number;
            @XmlElement(name = "COUNTRY")
            protected String country;
            @XmlAttribute(name = "Id")
            protected String id;
            @XmlAttribute(name = "Entity_id")
            protected String entityId;
            @XmlAttribute(name = "legal_basis")
            protected String legalBasis;
            @XmlAttribute(name = "reg_date")
            protected String regDate;
            @XmlAttribute(name = "pdf_link")
            protected String pdfLink;
            @XmlAttribute(name = "programme")
            protected String programme;

            /**
             * Ruft den Wert der country-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getCOUNTRY() {
                return country;
            }

            /**
             * Ruft den Wert der entityId-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getEntityId() {
                return entityId;
            }

            /**
             * Ruft den Wert der id-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getId() {
                return id;
            }

            /**
             * Ruft den Wert der legalBasis-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getLegalBasis() {
                return legalBasis;
            }

            /**
             * Ruft den Wert der number-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getNUMBER() {
                return number;
            }

            /**
             * Ruft den Wert der pdfLink-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getPdfLink() {
                return pdfLink;
            }

            /**
             * Ruft den Wert der programme-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getProgramme() {
                return programme;
            }

            /**
             * Ruft den Wert der regDate-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getRegDate() {
                return regDate;
            }

            /**
             * Legt den Wert der country-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setCOUNTRY(final String value) {
                country = value;
            }

            /**
             * Legt den Wert der entityId-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setEntityId(final String value) {
                entityId = value;
            }

            /**
             * Legt den Wert der id-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setId(final String value) {
                id = value;
            }

            /**
             * Legt den Wert der legalBasis-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setLegalBasis(final String value) {
                legalBasis = value;
            }

            /**
             * Legt den Wert der number-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setNUMBER(final String value) {
                number = value;
            }

            /**
             * Legt den Wert der pdfLink-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setPdfLink(final String value) {
                pdfLink = value;
            }

            /**
             * Legt den Wert der programme-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setProgramme(final String value) {
                programme = value;
            }

            /**
             * Legt den Wert der regDate-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setRegDate(final String value) {
                regDate = value;
            }

        }

        @XmlElement(name = "NAME")
        protected List<WHOLE.ENTITY.NAME>     name;
        @XmlElement(name = "BIRTH")
        protected List<WHOLE.ENTITY.BIRTH>    birth;
        @XmlElement(name = "CITIZEN")
        protected List<WHOLE.ENTITY.CITIZEN>  citizen;
        @XmlElement(name = "PASSPORT")
        protected List<WHOLE.ENTITY.PASSPORT> passport;
        @XmlElement(name = "ADDRESS")
        protected List<WHOLE.ENTITY.ADDRESS>  address;
        @XmlAttribute(name = "Id")
        protected String                      id;
        @XmlAttribute(name = "Type")
        protected String                      type;

        @XmlAttribute(name = "legal_basis")
        protected String                      legalBasis;

        @XmlAttribute(name = "reg_date")
        protected String                      regDate;

        @XmlAttribute(name = "pdf_link")
        protected String                      pdfLink;

        @XmlAttribute(name = "programme")
        protected String                      programme;

        @XmlAttribute(name = "remark")
        protected String                      remark;

        /**
         * Gets the value of the address property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the address property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getADDRESS().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link WHOLE.ENTITY.ADDRESS }
         */
        public List<WHOLE.ENTITY.ADDRESS> getADDRESS() {
            if (address == null) {
                address = new ArrayList<WHOLE.ENTITY.ADDRESS>();
            }
            return address;
        }

        /**
         * Gets the value of the birth property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the birth property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getBIRTH().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link WHOLE.ENTITY.BIRTH }
         */
        public List<WHOLE.ENTITY.BIRTH> getBIRTH() {
            if (birth == null) {
                birth = new ArrayList<WHOLE.ENTITY.BIRTH>();
            }
            return birth;
        }

        /**
         * Gets the value of the citizen property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the citizen property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getCITIZEN().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link WHOLE.ENTITY.CITIZEN }
         */
        public List<WHOLE.ENTITY.CITIZEN> getCITIZEN() {
            if (citizen == null) {
                citizen = new ArrayList<WHOLE.ENTITY.CITIZEN>();
            }
            return citizen;
        }

        /**
         * Ruft den Wert der id-Eigenschaft ab.
         *
         * @return possible object is {@link String }
         */
        public String getId() {
            return id;
        }

        /**
         * Ruft den Wert der legalBasis-Eigenschaft ab.
         *
         * @return possible object is {@link String }
         */
        public String getLegalBasis() {
            return legalBasis;
        }

        /**
         * Gets the value of the name property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the name property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getNAME().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link WHOLE.ENTITY.NAME }
         */
        public List<WHOLE.ENTITY.NAME> getNAME() {
            if (name == null) {
                name = new ArrayList<WHOLE.ENTITY.NAME>();
            }
            return name;
        }

        /**
         * Gets the value of the passport property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the passport property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getPASSPORT().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link WHOLE.ENTITY.PASSPORT }
         */
        public List<WHOLE.ENTITY.PASSPORT> getPASSPORT() {
            if (passport == null) {
                passport = new ArrayList<WHOLE.ENTITY.PASSPORT>();
            }
            return passport;
        }

        /**
         * Ruft den Wert der pdfLink-Eigenschaft ab.
         *
         * @return possible object is {@link String }
         */
        public String getPdfLink() {
            return pdfLink;
        }

        /**
         * Ruft den Wert der programme-Eigenschaft ab.
         *
         * @return possible object is {@link String }
         */
        public String getProgramme() {
            return programme;
        }

        /**
         * Ruft den Wert der regDate-Eigenschaft ab.
         *
         * @return possible object is {@link String }
         */
        public String getRegDate() {
            return regDate;
        }

        /**
         * Ruft den Wert der remark-Eigenschaft ab.
         *
         * @return possible object is {@link String }
         */
        public String getRemark() {
            return remark;
        }

        /**
         * Ruft den Wert der type-Eigenschaft ab.
         *
         * @return possible object is {@link String }
         */
        public String getType() {
            return type;
        }

        /**
         * Legt den Wert der id-Eigenschaft fest.
         *
         * @param value
         *            allowed object is {@link String }
         */
        public void setId(final String value) {
            id = value;
        }

        /**
         * Legt den Wert der legalBasis-Eigenschaft fest.
         *
         * @param value
         *            allowed object is {@link String }
         */
        public void setLegalBasis(final String value) {
            legalBasis = value;
        }

        /**
         * Legt den Wert der pdfLink-Eigenschaft fest.
         *
         * @param value
         *            allowed object is {@link String }
         */
        public void setPdfLink(final String value) {
            pdfLink = value;
        }

        /**
         * Legt den Wert der programme-Eigenschaft fest.
         *
         * @param value
         *            allowed object is {@link String }
         */
        public void setProgramme(final String value) {
            programme = value;
        }

        /**
         * Legt den Wert der regDate-Eigenschaft fest.
         *
         * @param value
         *            allowed object is {@link String }
         */
        public void setRegDate(final String value) {
            regDate = value;
        }

        /**
         * Legt den Wert der remark-Eigenschaft fest.
         *
         * @param value
         *            allowed object is {@link String }
         */
        public void setRemark(final String value) {
            remark = value;
        }

        /**
         * Legt den Wert der type-Eigenschaft fest.
         *
         * @param value
         *            allowed object is {@link String }
         */
        public void setType(final String value) {
            type = value;
        }

    }

    @XmlElement(name = "ENTITY")
    protected List<WHOLE.ENTITY> entity;

    @XmlAttribute(name = "Date")
    protected String             date;

    /**
     * Ruft den Wert der date-Eigenschaft ab.
     *
     * @return possible object is {@link String }
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the value of the entity property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the entity property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getENTITY().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link WHOLE.ENTITY }
     */
    public List<WHOLE.ENTITY> getENTITY() {
        if (entity == null) {
            entity = new ArrayList<WHOLE.ENTITY>();
        }
        return entity;
    }

    /**
     * Legt den Wert der date-Eigenschaft fest.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setDate(final String value) {
        date = value;
    }

}
