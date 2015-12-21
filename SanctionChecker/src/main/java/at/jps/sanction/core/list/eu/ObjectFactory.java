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

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the at.jps.sl.models.eu package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model groups. Factory methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.jps.sl.models.eu
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NewDataSet }
     */
    public NewDataSet createNewDataSet() {
        return new NewDataSet();
    }

    /**
     * Create an instance of {@link WHOLE }
     */
    public WHOLE createWHOLE() {
        return new WHOLE();
    }

    /**
     * Create an instance of {@link WHOLE.ENTITY }
     */
    public WHOLE.ENTITY createWHOLEENTITY() {
        return new WHOLE.ENTITY();
    }

    /**
     * Create an instance of {@link WHOLE.ENTITY.ADDRESS }
     */
    public WHOLE.ENTITY.ADDRESS createWHOLEENTITYADDRESS() {
        return new WHOLE.ENTITY.ADDRESS();
    }

    /**
     * Create an instance of {@link WHOLE.ENTITY.BIRTH }
     */
    public WHOLE.ENTITY.BIRTH createWHOLEENTITYBIRTH() {
        return new WHOLE.ENTITY.BIRTH();
    }

    /**
     * Create an instance of {@link WHOLE.ENTITY.CITIZEN }
     */
    public WHOLE.ENTITY.CITIZEN createWHOLEENTITYCITIZEN() {
        return new WHOLE.ENTITY.CITIZEN();
    }

    /**
     * Create an instance of {@link WHOLE.ENTITY.NAME }
     */
    public WHOLE.ENTITY.NAME createWHOLEENTITYNAME() {
        return new WHOLE.ENTITY.NAME();
    }

    /**
     * Create an instance of {@link WHOLE.ENTITY.PASSPORT }
     */
    public WHOLE.ENTITY.PASSPORT createWHOLEENTITYPASSPORT() {
        return new WHOLE.ENTITY.PASSPORT();
    }

}
