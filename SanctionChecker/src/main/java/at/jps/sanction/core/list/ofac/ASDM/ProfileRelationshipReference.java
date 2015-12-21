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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;attribute name="ProfileRelationshipID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="DeltaAction" type="{http://www.un.org/sanctions/1.0}DeltaActionSchemaType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ProfileRelationshipReference")
public class ProfileRelationshipReference {

    @XmlAttribute(name = "ProfileRelationshipID", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger            profileRelationshipID;
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
     * Gets the value of the profileRelationshipID property.
     *
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getProfileRelationshipID() {
        return profileRelationshipID;
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
     * Sets the value of the profileRelationshipID property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     */
    public void setProfileRelationshipID(final BigInteger value) {
        profileRelationshipID = value;
    }

}
