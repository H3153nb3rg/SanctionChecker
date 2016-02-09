//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren.
// Generiert: 2015.08.18 um 10:13:18 PM CEST
//

package at.jps.sanction.domain.payment.sepa.pacs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="Ctry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdrLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "ctry", "adrLine" })
@XmlRootElement(name = "PstlAdr")
public class PstlAdr {

    @XmlElement(name = "Ctry")
    protected String ctry;
    @XmlElement(name = "AdrLine")
    protected String adrLine;

    /**
     * Ruft den Wert der adrLine-Eigenschaft ab.
     *
     * @return possible object is {@link String }
     */
    public String getAdrLine() {
        return adrLine;
    }

    /**
     * Ruft den Wert der ctry-Eigenschaft ab.
     *
     * @return possible object is {@link String }
     */
    public String getCtry() {
        return ctry;
    }

    /**
     * Legt den Wert der adrLine-Eigenschaft fest.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setAdrLine(final String value) {
        adrLine = value;
    }

    /**
     * Legt den Wert der ctry-Eigenschaft fest.
     *
     * @param value
     *            allowed object is {@link String }
     */
    public void setCtry(final String value) {
        ctry = value;
    }

}
