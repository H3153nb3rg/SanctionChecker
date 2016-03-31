//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren.
// Generiert: 2015.08.18 um 10:13:18 PM CEST
//

package at.jps.sanction.domain.payment.sepa.pacs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId"/>
 *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}PstlAdr"/>
 *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}Id"/>
 *         &lt;element name="FIToFICstmrCdtTrf">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="GrpHdr" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="MsgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="CreDtTm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="NbOfTxs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="IntrBkSttlmDt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="TtlIntrBkSttlmAmt" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="Ccy" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="SttlmInf" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="SttlmMtd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="ClrSys" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Prtry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="InstgAgt" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="CdtTrfTxInf" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ChrgBr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="PmtId" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="InstrId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="EndToEndId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="TxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="PmtTpInf" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="SvcLvl" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Cd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="IntrBkSttlmAmt" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="Ccy" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Dbtr" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}PstlAdr" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="DbtrAcct" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}Id" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="DbtrAgt" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="CdtrAgt" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Cdtr" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}PstlAdr" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="CdtrAcct" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}Id" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="RmtInf" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Ustrd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "finInstnIdOrPstlAdrOrId" })
@XmlRootElement(name = "Document")
public class Document {

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
     *         &lt;element name="GrpHdr" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="MsgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="CreDtTm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="NbOfTxs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="IntrBkSttlmDt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="TtlIntrBkSttlmAmt" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="Ccy" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="SttlmInf" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="SttlmMtd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="ClrSys" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="Prtry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="InstgAgt" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="CdtTrfTxInf" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ChrgBr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="PmtId" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="InstrId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="EndToEndId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="TxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="PmtTpInf" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="SvcLvl" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="Cd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="IntrBkSttlmAmt" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="Ccy" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Dbtr" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}PstlAdr" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="DbtrAcct" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}Id" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="DbtrAgt" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="CdtrAgt" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Cdtr" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}PstlAdr" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="CdtrAcct" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}Id" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="RmtInf" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Ustrd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
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
    @XmlType(name = "", propOrder = { "grpHdr", "cdtTrfTxInf" })
    public static class FIToFICstmrCdtTrf {

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
         *         &lt;element name="ChrgBr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="PmtId" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="InstrId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="EndToEndId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="TxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="PmtTpInf" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="SvcLvl" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="Cd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="IntrBkSttlmAmt" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="Ccy" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Dbtr" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}PstlAdr" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="DbtrAcct" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}Id" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="DbtrAgt" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="CdtrAgt" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Cdtr" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}PstlAdr" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="CdtrAcct" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}Id" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="RmtInf" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Ustrd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
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
        @XmlType(name = "", propOrder = { "chrgBr", "pmtId", "pmtTpInf", "intrBkSttlmAmt", "dbtr", "dbtrAcct", "dbtrAgt", "cdtrAgt", "cdtr", "cdtrAcct", "rmtInf" })
        public static class CdtTrfTxInf {

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
             *         &lt;element name="Nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}PstlAdr" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "nm", "pstlAdr" })
            public static class Cdtr {

                @XmlElement(name = "Nm")
                protected String        nm;
                @XmlElement(name = "PstlAdr")
                protected List<PstlAdr> pstlAdr;

                /**
                 * Ruft den Wert der nm-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getNm() {
                    return nm;
                }

                /**
                 * Gets the value of the pstlAdr property.
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the pstlAdr property.
                 * <p>
                 * For example, to add a new item, do as follows:
                 *
                 * <pre>
                 * getPstlAdr().add(newItem);
                 * </pre>
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link PstlAdr }
                 */
                public List<PstlAdr> getPstlAdr() {
                    if (pstlAdr == null) {
                        pstlAdr = new ArrayList<PstlAdr>();
                    }
                    return pstlAdr;
                }

                /**
                 * Legt den Wert der nm-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setNm(final String value) {
                    nm = value;
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
             *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}Id" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "id" })
            public static class CdtrAcct {

                @XmlElement(name = "Id")
                protected List<Id> id;

                /**
                 * Gets the value of the id property.
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the id property.
                 * <p>
                 * For example, to add a new item, do as follows:
                 *
                 * <pre>
                 * getId().add(newItem);
                 * </pre>
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link Id }
                 */
                public List<Id> getId() {
                    if (id == null) {
                        id = new ArrayList<Id>();
                    }
                    return id;
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
             *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "finInstnId" })
            public static class CdtrAgt {

                @XmlElement(name = "FinInstnId")
                protected List<FinInstnId> finInstnId;

                /**
                 * Gets the value of the finInstnId property.
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the finInstnId property.
                 * <p>
                 * For example, to add a new item, do as follows:
                 *
                 * <pre>
                 * getFinInstnId().add(newItem);
                 * </pre>
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link FinInstnId }
                 */
                public List<FinInstnId> getFinInstnId() {
                    if (finInstnId == null) {
                        finInstnId = new ArrayList<FinInstnId>();
                    }
                    return finInstnId;
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
             *         &lt;element name="Nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}PstlAdr" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "nm", "pstlAdr" })
            public static class Dbtr {

                @XmlElement(name = "Nm")
                protected String        nm;
                @XmlElement(name = "PstlAdr")
                protected List<PstlAdr> pstlAdr;

                /**
                 * Ruft den Wert der nm-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getNm() {
                    return nm;
                }

                /**
                 * Gets the value of the pstlAdr property.
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the pstlAdr property.
                 * <p>
                 * For example, to add a new item, do as follows:
                 *
                 * <pre>
                 * getPstlAdr().add(newItem);
                 * </pre>
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link PstlAdr }
                 */
                public List<PstlAdr> getPstlAdr() {
                    if (pstlAdr == null) {
                        pstlAdr = new ArrayList<PstlAdr>();
                    }
                    return pstlAdr;
                }

                /**
                 * Legt den Wert der nm-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setNm(final String value) {
                    nm = value;
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
             *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}Id" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "id" })
            public static class DbtrAcct {

                @XmlElement(name = "Id")
                protected List<Id> id;

                /**
                 * Gets the value of the id property.
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the id property.
                 * <p>
                 * For example, to add a new item, do as follows:
                 *
                 * <pre>
                 * getId().add(newItem);
                 * </pre>
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link Id }
                 */
                public List<Id> getId() {
                    if (id == null) {
                        id = new ArrayList<Id>();
                    }
                    return id;
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
             *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "finInstnId" })
            public static class DbtrAgt {

                @XmlElement(name = "FinInstnId")
                protected List<FinInstnId> finInstnId;

                /**
                 * Gets the value of the finInstnId property.
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the finInstnId property.
                 * <p>
                 * For example, to add a new item, do as follows:
                 *
                 * <pre>
                 * getFinInstnId().add(newItem);
                 * </pre>
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link FinInstnId }
                 */
                public List<FinInstnId> getFinInstnId() {
                    if (finInstnId == null) {
                        finInstnId = new ArrayList<FinInstnId>();
                    }
                    return finInstnId;
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
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="Ccy" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "value" })
            public static class IntrBkSttlmAmt {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "Ccy")
                protected String ccy;

                /**
                 * Ruft den Wert der ccy-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getCcy() {
                    return ccy;
                }

                /**
                 * Ruft den Wert der value-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Legt den Wert der ccy-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setCcy(final String value) {
                    ccy = value;
                }

                /**
                 * Legt den Wert der value-Eigenschaft fest.
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
             * Java-Klasse für anonymous complex type.
             * <p>
             * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="InstrId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="EndToEndId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="TxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "instrId", "endToEndId", "txId" })
            public static class PmtId {

                @XmlElement(name = "InstrId")
                protected String instrId;
                @XmlElement(name = "EndToEndId")
                protected String endToEndId;
                @XmlElement(name = "TxId")
                protected String txId;

                /**
                 * Ruft den Wert der endToEndId-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getEndToEndId() {
                    return endToEndId;
                }

                /**
                 * Ruft den Wert der instrId-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getInstrId() {
                    return instrId;
                }

                /**
                 * Ruft den Wert der txId-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getTxId() {
                    return txId;
                }

                /**
                 * Legt den Wert der endToEndId-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setEndToEndId(final String value) {
                    endToEndId = value;
                }

                /**
                 * Legt den Wert der instrId-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setInstrId(final String value) {
                    instrId = value;
                }

                /**
                 * Legt den Wert der txId-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setTxId(final String value) {
                    txId = value;
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
             *         &lt;element name="SvcLvl" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Cd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
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
            @XmlType(name = "", propOrder = { "svcLvl" })
            public static class PmtTpInf {

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
                 *         &lt;element name="Cd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = { "cd" })
                public static class SvcLvl {

                    @XmlElement(name = "Cd")
                    protected String cd;

                    /**
                     * Ruft den Wert der cd-Eigenschaft ab.
                     *
                     * @return possible object is {@link String }
                     */
                    public String getCd() {
                        return cd;
                    }

                    /**
                     * Legt den Wert der cd-Eigenschaft fest.
                     *
                     * @param value
                     *            allowed object is {@link String }
                     */
                    public void setCd(final String value) {
                        cd = value;
                    }

                }

                @XmlElement(name = "SvcLvl")
                protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtTpInf.SvcLvl> svcLvl;

                /**
                 * Gets the value of the svcLvl property.
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the svcLvl property.
                 * <p>
                 * For example, to add a new item, do as follows:
                 *
                 * <pre>
                 * getSvcLvl().add(newItem);
                 * </pre>
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtTpInf.SvcLvl }
                 */
                public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtTpInf.SvcLvl> getSvcLvl() {
                    if (svcLvl == null) {
                        svcLvl = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtTpInf.SvcLvl>();
                    }
                    return svcLvl;
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
             *         &lt;element name="Ustrd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "ustrd" })
            public static class RmtInf {

                @XmlElement(name = "Ustrd")
                protected String ustrd;

                /**
                 * Ruft den Wert der ustrd-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getUstrd() {
                    return ustrd;
                }

                /**
                 * Legt den Wert der ustrd-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setUstrd(final String value) {
                    ustrd = value;
                }

            }

            @XmlElement(name = "ChrgBr")
            protected String                                                      chrgBr;

            @XmlElement(name = "PmtId")
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtId>          pmtId;

            @XmlElement(name = "PmtTpInf")
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtTpInf>       pmtTpInf;

            @XmlElement(name = "IntrBkSttlmAmt", nillable = true)
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.IntrBkSttlmAmt> intrBkSttlmAmt;

            @XmlElement(name = "Dbtr")
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.Dbtr>           dbtr;

            @XmlElement(name = "DbtrAcct")
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.DbtrAcct>       dbtrAcct;

            @XmlElement(name = "DbtrAgt")
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.DbtrAgt>        dbtrAgt;

            @XmlElement(name = "CdtrAgt")
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.CdtrAgt>        cdtrAgt;

            @XmlElement(name = "Cdtr")
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.Cdtr>           cdtr;

            @XmlElement(name = "CdtrAcct")
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.CdtrAcct>       cdtrAcct;

            @XmlElement(name = "RmtInf")
            protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.RmtInf>         rmtInf;

            /**
             * Gets the value of the cdtr property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the cdtr property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getCdtr().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.Cdtr }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.Cdtr> getCdtr() {
                if (cdtr == null) {
                    cdtr = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.Cdtr>();
                }
                return cdtr;
            }

            /**
             * Gets the value of the cdtrAcct property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the cdtrAcct property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getCdtrAcct().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.CdtrAcct }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.CdtrAcct> getCdtrAcct() {
                if (cdtrAcct == null) {
                    cdtrAcct = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.CdtrAcct>();
                }
                return cdtrAcct;
            }

            /**
             * Gets the value of the cdtrAgt property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the cdtrAgt property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getCdtrAgt().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.CdtrAgt }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.CdtrAgt> getCdtrAgt() {
                if (cdtrAgt == null) {
                    cdtrAgt = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.CdtrAgt>();
                }
                return cdtrAgt;
            }

            /**
             * Ruft den Wert der chrgBr-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getChrgBr() {
                return chrgBr;
            }

            /**
             * Gets the value of the dbtr property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the dbtr property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getDbtr().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.Dbtr }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.Dbtr> getDbtr() {
                if (dbtr == null) {
                    dbtr = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.Dbtr>();
                }
                return dbtr;
            }

            /**
             * Gets the value of the dbtrAcct property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the dbtrAcct property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getDbtrAcct().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.DbtrAcct }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.DbtrAcct> getDbtrAcct() {
                if (dbtrAcct == null) {
                    dbtrAcct = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.DbtrAcct>();
                }
                return dbtrAcct;
            }

            /**
             * Gets the value of the dbtrAgt property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the dbtrAgt property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getDbtrAgt().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.DbtrAgt }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.DbtrAgt> getDbtrAgt() {
                if (dbtrAgt == null) {
                    dbtrAgt = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.DbtrAgt>();
                }
                return dbtrAgt;
            }

            /**
             * Gets the value of the intrBkSttlmAmt property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the intrBkSttlmAmt property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getIntrBkSttlmAmt().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.IntrBkSttlmAmt }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.IntrBkSttlmAmt> getIntrBkSttlmAmt() {
                if (intrBkSttlmAmt == null) {
                    intrBkSttlmAmt = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.IntrBkSttlmAmt>();
                }
                return intrBkSttlmAmt;
            }

            /**
             * Gets the value of the pmtId property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the pmtId property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getPmtId().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtId }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtId> getPmtId() {
                if (pmtId == null) {
                    pmtId = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtId>();
                }
                return pmtId;
            }

            /**
             * Gets the value of the pmtTpInf property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the pmtTpInf property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getPmtTpInf().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtTpInf }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtTpInf> getPmtTpInf() {
                if (pmtTpInf == null) {
                    pmtTpInf = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.PmtTpInf>();
                }
                return pmtTpInf;
            }

            /**
             * Gets the value of the rmtInf property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the rmtInf property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getRmtInf().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf.RmtInf }
             */
            public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.RmtInf> getRmtInf() {
                if (rmtInf == null) {
                    rmtInf = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf.RmtInf>();
                }
                return rmtInf;
            }

            /**
             * Legt den Wert der chrgBr-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setChrgBr(final String value) {
                chrgBr = value;
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
         *         &lt;element name="MsgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="CreDtTm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="NbOfTxs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="IntrBkSttlmDt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="TtlIntrBkSttlmAmt" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="Ccy" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="SttlmInf" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="SttlmMtd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="ClrSys" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="Prtry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="InstgAgt" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
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
        @XmlType(name = "", propOrder = { "msgId", "creDtTm", "nbOfTxs", "intrBkSttlmDt", "ttlIntrBkSttlmAmt", "sttlmInf", "instgAgt" })
        public static class GrpHdr {

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
             *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02}FinInstnId" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "finInstnId" })
            public static class InstgAgt {

                @XmlElement(name = "FinInstnId")
                protected List<FinInstnId> finInstnId;

                /**
                 * Gets the value of the finInstnId property.
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the finInstnId property.
                 * <p>
                 * For example, to add a new item, do as follows:
                 *
                 * <pre>
                 * getFinInstnId().add(newItem);
                 * </pre>
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link FinInstnId }
                 */
                public List<FinInstnId> getFinInstnId() {
                    if (finInstnId == null) {
                        finInstnId = new ArrayList<FinInstnId>();
                    }
                    return finInstnId;
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
             *         &lt;element name="SttlmMtd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="ClrSys" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Prtry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
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
            @XmlType(name = "", propOrder = { "sttlmMtd", "clrSys" })
            public static class SttlmInf {

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
                 *         &lt;element name="Prtry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = { "prtry" })
                public static class ClrSys {

                    @XmlElement(name = "Prtry")
                    protected String prtry;

                    /**
                     * Ruft den Wert der prtry-Eigenschaft ab.
                     *
                     * @return possible object is {@link String }
                     */
                    public String getPrtry() {
                        return prtry;
                    }

                    /**
                     * Legt den Wert der prtry-Eigenschaft fest.
                     *
                     * @param value
                     *            allowed object is {@link String }
                     */
                    public void setPrtry(final String value) {
                        prtry = value;
                    }

                }

                @XmlElement(name = "SttlmMtd")
                protected String                                                  sttlmMtd;

                @XmlElement(name = "ClrSys")
                protected List<Document.FIToFICstmrCdtTrf.GrpHdr.SttlmInf.ClrSys> clrSys;

                /**
                 * Gets the value of the clrSys property.
                 * <p>
                 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the clrSys property.
                 * <p>
                 * For example, to add a new item, do as follows:
                 *
                 * <pre>
                 * getClrSys().add(newItem);
                 * </pre>
                 * <p>
                 * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.GrpHdr.SttlmInf.ClrSys }
                 */
                public List<Document.FIToFICstmrCdtTrf.GrpHdr.SttlmInf.ClrSys> getClrSys() {
                    if (clrSys == null) {
                        clrSys = new ArrayList<Document.FIToFICstmrCdtTrf.GrpHdr.SttlmInf.ClrSys>();
                    }
                    return clrSys;
                }

                /**
                 * Ruft den Wert der sttlmMtd-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getSttlmMtd() {
                    return sttlmMtd;
                }

                /**
                 * Legt den Wert der sttlmMtd-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setSttlmMtd(final String value) {
                    sttlmMtd = value;
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
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="Ccy" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "value" })
            public static class TtlIntrBkSttlmAmt {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "Ccy")
                protected String ccy;

                /**
                 * Ruft den Wert der ccy-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getCcy() {
                    return ccy;
                }

                /**
                 * Ruft den Wert der value-Eigenschaft ab.
                 *
                 * @return possible object is {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Legt den Wert der ccy-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setCcy(final String value) {
                    ccy = value;
                }

                /**
                 * Legt den Wert der value-Eigenschaft fest.
                 *
                 * @param value
                 *            allowed object is {@link String }
                 */
                public void setValue(final String value) {
                    this.value = value;
                }

            }

            @XmlElement(name = "MsgId")
            protected String                                                    msgId;
            @XmlElement(name = "CreDtTm")
            protected String                                                    creDtTm;
            @XmlElement(name = "NbOfTxs")
            protected String                                                    nbOfTxs;
            @XmlElement(name = "IntrBkSttlmDt")
            protected String                                                    intrBkSttlmDt;

            @XmlElement(name = "TtlIntrBkSttlmAmt", nillable = true)
            protected List<Document.FIToFICstmrCdtTrf.GrpHdr.TtlIntrBkSttlmAmt> ttlIntrBkSttlmAmt;

            @XmlElement(name = "SttlmInf")
            protected List<Document.FIToFICstmrCdtTrf.GrpHdr.SttlmInf>          sttlmInf;

            @XmlElement(name = "InstgAgt")
            protected List<Document.FIToFICstmrCdtTrf.GrpHdr.InstgAgt>          instgAgt;

            /**
             * Ruft den Wert der creDtTm-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getCreDtTm() {
                return creDtTm;
            }

            /**
             * Gets the value of the instgAgt property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the instgAgt property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getInstgAgt().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.GrpHdr.InstgAgt }
             */
            public List<Document.FIToFICstmrCdtTrf.GrpHdr.InstgAgt> getInstgAgt() {
                if (instgAgt == null) {
                    instgAgt = new ArrayList<Document.FIToFICstmrCdtTrf.GrpHdr.InstgAgt>();
                }
                return instgAgt;
            }

            /**
             * Ruft den Wert der intrBkSttlmDt-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getIntrBkSttlmDt() {
                return intrBkSttlmDt;
            }

            /**
             * Ruft den Wert der msgId-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getMsgId() {
                return msgId;
            }

            /**
             * Ruft den Wert der nbOfTxs-Eigenschaft ab.
             *
             * @return possible object is {@link String }
             */
            public String getNbOfTxs() {
                return nbOfTxs;
            }

            /**
             * Gets the value of the sttlmInf property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the sttlmInf property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getSttlmInf().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.GrpHdr.SttlmInf }
             */
            public List<Document.FIToFICstmrCdtTrf.GrpHdr.SttlmInf> getSttlmInf() {
                if (sttlmInf == null) {
                    sttlmInf = new ArrayList<Document.FIToFICstmrCdtTrf.GrpHdr.SttlmInf>();
                }
                return sttlmInf;
            }

            /**
             * Gets the value of the ttlIntrBkSttlmAmt property.
             * <p>
             * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why
             * there is not a <CODE>set</CODE> method for the ttlIntrBkSttlmAmt property.
             * <p>
             * For example, to add a new item, do as follows:
             *
             * <pre>
             * getTtlIntrBkSttlmAmt().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.GrpHdr.TtlIntrBkSttlmAmt }
             */
            public List<Document.FIToFICstmrCdtTrf.GrpHdr.TtlIntrBkSttlmAmt> getTtlIntrBkSttlmAmt() {
                if (ttlIntrBkSttlmAmt == null) {
                    ttlIntrBkSttlmAmt = new ArrayList<Document.FIToFICstmrCdtTrf.GrpHdr.TtlIntrBkSttlmAmt>();
                }
                return ttlIntrBkSttlmAmt;
            }

            /**
             * Legt den Wert der creDtTm-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setCreDtTm(final String value) {
                creDtTm = value;
            }

            /**
             * Legt den Wert der intrBkSttlmDt-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setIntrBkSttlmDt(final String value) {
                intrBkSttlmDt = value;
            }

            /**
             * Legt den Wert der msgId-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setMsgId(final String value) {
                msgId = value;
            }

            /**
             * Legt den Wert der nbOfTxs-Eigenschaft fest.
             *
             * @param value
             *            allowed object is {@link String }
             */
            public void setNbOfTxs(final String value) {
                nbOfTxs = value;
            }

        }

        @XmlElement(name = "GrpHdr")
        protected List<Document.FIToFICstmrCdtTrf.GrpHdr>      grpHdr;

        @XmlElement(name = "CdtTrfTxInf")
        protected List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf> cdtTrfTxInf;

        /**
         * Gets the value of the cdtTrfTxInf property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the cdtTrfTxInf property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getCdtTrfTxInf().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.CdtTrfTxInf }
         */
        public List<Document.FIToFICstmrCdtTrf.CdtTrfTxInf> getCdtTrfTxInf() {
            if (cdtTrfTxInf == null) {
                cdtTrfTxInf = new ArrayList<Document.FIToFICstmrCdtTrf.CdtTrfTxInf>();
            }
            return cdtTrfTxInf;
        }

        /**
         * Gets the value of the grpHdr property.
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
         * is not a <CODE>set</CODE> method for the grpHdr property.
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getGrpHdr().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list {@link Document.FIToFICstmrCdtTrf.GrpHdr }
         */
        public List<Document.FIToFICstmrCdtTrf.GrpHdr> getGrpHdr() {
            if (grpHdr == null) {
                grpHdr = new ArrayList<Document.FIToFICstmrCdtTrf.GrpHdr>();
            }
            return grpHdr;
        }

    }

    @XmlElements({ @XmlElement(name = "FinInstnId", type = FinInstnId.class), @XmlElement(name = "PstlAdr", type = PstlAdr.class), @XmlElement(name = "Id", type = Id.class),
            @XmlElement(name = "FIToFICstmrCdtTrf", type = Document.FIToFICstmrCdtTrf.class) })
    protected List<Object> finInstnIdOrPstlAdrOrId;

    /**
     * Gets the value of the finInstnIdOrPstlAdrOrId property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
     * not a <CODE>set</CODE> method for the finInstnIdOrPstlAdrOrId property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getFinInstnIdOrPstlAdrOrId().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FinInstnId } {@link PstlAdr } {@link Id } {@link Document.FIToFICstmrCdtTrf }
     */
    public List<Object> getFinInstnIdOrPstlAdrOrId() {
        if (finInstnIdOrPstlAdrOrId == null) {
            finInstnIdOrPstlAdrOrId = new ArrayList<Object>();
        }
        return finInstnIdOrPstlAdrOrId;
    }

}
