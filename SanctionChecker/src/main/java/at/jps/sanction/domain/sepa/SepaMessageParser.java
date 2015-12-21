/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.sepa;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class SepaMessageParser {

    // final String[] supportedTypes = { "pacs.008", "pacs.003" };

    // private static JAXBContext ctx = null;
    //
    // private static Unmarshaller unmarshaller = null;

    static final Logger                          logger        = LoggerFactory.getLogger(SepaMessageParser.class);

    private static DocumentBuilderFactory        factory;
    private static DocumentBuilder               builder;

    private static XPathFactory                  xPathfactory;
    private static XPath                         xpath;

    private static HashMap<String, List<String>> fieldsPerType = new HashMap<String, List<String>>();

    static {

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            xPathfactory = XPathFactory.newInstance();
            xpath = xPathfactory.newXPath();

        }
        catch (ParserConfigurationException e) {
            logger.error("Error initializing XPATH Parser", e);
        }
    }

    private static List<String> getFieldsForType(final String type) {
        if (fieldsPerType.get(type) == null) {
            // try to read property file;
            parsePropertyFile(type);
        }

        return fieldsPerType.get(type);
    }

    static void xpathtext() throws Exception {

        String message = "<?xml version=\"1.0\" encoding=\"utf-8\" ?> <inventory>     <!--Test is test comment-->        <book year=\"2000\">         <title>Snow Crash</title>         <author>Neal Stephenson</author>         <publisher>Spectra</publisher>         <isbn>0553380958</isbn>         <price>14.95</price>     </book>     <book year=\"2005\">         <title>Burning Tower</title>         <author>Larry Niven</author>         <author>Jerry Pournelle</author>         <publisher>Pocket</publisher>         <isbn>0743416910</isbn>         <price>5.99</price>     </book>     <book year=\"1995\">         <title>Zodiac</title>         <author>Neal Stephenson</author>         <publisher>Spectra</publisher>         <isbn>0553573862</isbn>         <price>7.50</price>     </book> </inventory>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(message)));
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        System.out.println("n//1) Get book titles written after 2001");         // 1) Get book titles written after 2001
        XPathExpression expr = xpath.compile("//book[@year>2001]/title/text()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
        System.out.println("n//2) Get book titles written before 2001");         // 2) Get book titles written before 2001
        expr = xpath.compile("//book[@year<2001]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
        System.out.println("n//3) Get book titles cheaper than 8 dollars");         // 3) Get book titles cheaper than 8 dollars
        expr = xpath.compile("//book[price<8]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
        System.out.println("n//4) Get book titles costlier than 8 dollars");         // 4) Get book titles costlier than 8 dollars
        expr = xpath.compile("//book[price>8]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
        System.out.println("n//5) Get book titles added in first node");         // 5) Get book titles added in first node
        expr = xpath.compile("//book[1]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
        System.out.println("n//6) Get book title added in last node");         // 6) Get book title added in last node
        expr = xpath.compile("//book[last()]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
        System.out.println("n//7) Get all writers");         // 7) Get all writers
        expr = xpath.compile("//book/author/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
        System.out.println("n//8) Count all books titles ");         // 8) Count all books titles
        expr = xpath.compile("count(//book/title)");
        result = expr.evaluate(doc, XPathConstants.NUMBER);
        Double count = (Double) result;
        System.out.println(count.intValue());
        System.out.println("n//9) Get book titles with writer name start with Neal");         // 9) Get book titles with writer name start with Neal
        expr = xpath.compile("//book[starts-with(author,'Neal')]");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getChildNodes().item(1)// node // <title>
                    // is on // first // index
                    .getTextContent());
        }
        System.out.println("n//10) Get book titles with writer name containing Niven");         // 10) Get book titles with writer name containing Niven
        expr = xpath.compile("//book[contains(author,'Niven')]");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getChildNodes().item(1)// node // <title> // is on // first // index
                    .getTextContent());
        }           // 11) Get book titles written by Neal Stephenson
        System.out.println("//11) Get book titles written by Neal Stephenson");
        expr = xpath.compile("//book[author='Neal Stephenson']/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }           // 12) Get count of book titles written by Neal Stephenson
        System.out.println("n//12) Get count of book titles written by Neal Stephenson");
        expr = xpath.compile("count(//book[author='Neal Stephenson'])");
        result = expr.evaluate(doc, XPathConstants.NUMBER);
        count = (Double) result;
        System.out.println(count.intValue());
        System.out.println("n//13) Reading comment node ");         // 13) Reading comment node
        expr = xpath.compile("//inventory/comment()");
        result = expr.evaluate(doc, XPathConstants.STRING);
        String comment = (String) result;
        System.out.println(comment);

    }

    public static HashMap<String, String> parseMessage(final String message, final List<String> supportedTypes) {

        final HashMap<String, String> fields = new HashMap<String, String>();

        if (factory == null) {
            logger.error("XPath problem - not initialized - message not parsable!");
        }
        else {
            List<String> fieldsToGet = null;

            for (final String msgType : supportedTypes) {
                if (message.contains(msgType)) {

                    if (logger.isDebugEnabled()) logger.debug("parsing using SEPA Msgtype: " + msgType);

                    fieldsToGet = getFieldsForType(msgType);

                    break;
                }
            }

            try {
                if (fieldsToGet != null) {

                    org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(message)));

                    for (String field : fieldsToGet) {
                        // final XPathExpression expr = xpath.compile("/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/PmtId/TxId");
                        XPathExpression expr = xpath.compile(field);

                        String content = (String) expr.evaluate(doc, XPathConstants.STRING);
                        if ((content != null) && (content.trim().length() > 0)) {

                            fields.put(field, content);
                            if (logger.isDebugEnabled()) {
                                logger.debug(field + " -> " + content.trim());
                            }
                            else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug(field + " -> NIX DA !!");
                                }
                            }
                        }
                    }
                }
                else {
                    logger.error("MsgType problem - no fields defined!");
                }

                // if (ctx == null) {
                // ctx = JAXBContext.newInstance(Document.class);
                // }
                // if (unmarshaller == null) {
                // unmarshaller = ctx.createUnmarshaller();
                // }
                //
                // final Document sepamessage = (Document) unmarshaller.unmarshal(new StringReader(message));
                //
                //
                // System.out.println(sepamessage);

                //
                //
                // DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                // // factory.setNamespaceAware(true);
                // DocumentBuilder builder = factory.newDocumentBuilder();
                //
                // // "E:\\Workspace\\SanctionList\\SLHandler\\src\\testdata\\EMB\\SEPA\\14320073104F0100.DAT"
                //
                // InputSource is = new InputSource(new StringReader(message));
                //
                // org.w3c.dom.Document doc = builder.parse(is);
                // // doc = builder.parse(new File("E:\\Workspace\\SanctionList\\SLHandler\\src\\testdata\\EMB\\SEPA\\14320073104F0100.DAT"));
                //
                // // doc = builder.parse(new File("E:\\Workspace\\SanctionList\\SLHandler\\src\\lists\\global.xml"));
                //
                // XPathFactory xPathfactory = XPathFactory.newInstance();
                // XPath xpath = xPathfactory.newXPath();
                // // XPathExpression expr = xpath.compile("/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/PmtId/TxId/"); // text()
                // XPathExpression expr = xpath.compile("//CdtTrfTxInf"); // text()
                //
                // NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                // for (int i = 0; i < nodes.getLength(); i++) {
                // System.out.println(nodes.item(i).getNodeValue());
                //
                // }
                //
                // String name = (String) expr.evaluate(doc, XPathConstants.STRING);
                //
                // System.out.println(name);
                //
                // Object obj = xpath.evaluate("FIToFICstmrCdtTrf/CdtTrfTxInf/PmtId/TxId", new InputSource(new StringReader(message)));

                // System.out.println(obj);

            }
            catch (final Exception x) {
                logger.error("Parserproblem :" + message);
                logger.debug("Exception : ", x);
            }
        }
        return fields;
    }

    private static void parsePropertyFile(final String type) {

        final Properties properties = new Properties();

        final String filename = type + ".properties";

        try {
            properties.load(new FileInputStream(filename));
            logger.info("properties loaded from file: " + filename);
        }
        catch (final Exception e) {
            logger.error("properties failed to load from file: " + filename);
            logger.debug("Exception: ", e);

            System.out.println("properties failed to load from: " + filename);
        }
        if (properties.isEmpty()) {
            try {
                properties.load(SepaMessageParser.class.getClassLoader().getResourceAsStream(filename));
                logger.info("properties loaded from classpath: " + filename);
            }
            catch (final IOException e) {
                logger.error("properties failed to load from classpath: " + filename);
                logger.debug("Exception: ", e);
            }
        }

        if (!properties.isEmpty()) {

            final List<String> fields = new ArrayList<String>();

            @SuppressWarnings("rawtypes")
            final Enumeration e = properties.propertyNames();

            while (e.hasMoreElements()) {
                final String key = (String) e.nextElement();
                fields.add(properties.getProperty(key));
            }

            fieldsPerType.put(type, fields);

        }
    }

    /**
     * @param args
     */
    public static void main(final String[] args) {

        final List<String> supportedTypes = new ArrayList<String>();
        supportedTypes.add("pacs.008");

        final String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
                + "<Document xsi:schemaLocation=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02 pacs.008.001.02.xsd\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><FIToFICstmrCdtTrf><GrpHdr><MsgId>14320073104F0100</MsgId><CreDtTm>2014-03-20T07:31:04</CreDtTm><NbOfTxs>1</NbOfTxs><TtlIntrBkSttlmAmt Ccy=\"EUR\">59.24</TtlIntrBkSttlmAmt><IntrBkSttlmDt>2014-03-21</IntrBkSttlmDt><SttlmInf><SttlmMtd>CLRG</SttlmMtd><ClrSys><Prtry>ST2</Prtry></ClrSys></SttlmInf><InstgAgt><FinInstnId><BIC>BACXCZPP</BIC></FinInstnId></InstgAgt></GrpHdr><CdtTrfTxInf><PmtId><InstrId>4077103696</InstrId><EndToEndId>NOTPROVIDED</EndToEndId><TxId>14318083722E0802</TxId></PmtId><PmtTpInf><SvcLvl><Cd>SEPA</Cd></SvcLvl></PmtTpInf><IntrBkSttlmAmt Ccy=\"EUR\">59.24</IntrBkSttlmAmt><ChrgBr>SLEV</ChrgBr><Dbtr><Nm>INDUSTRIAL CZ, SPOL. S R.O.</Nm><PstlAdr><Ctry>CZ</Ctry><AdrLine>K HUTIM 1040/419800 PRAHA 9</AdrLine></PstlAdr></Dbtr><DbtrAcct><Id><IBAN>CZ6727000000001002340329</IBAN></Id></DbtrAcct><DbtrAgt><FinInstnId><BIC>BACXCZPPXXX</BIC></FinInstnId></DbtrAgt><CdtrAgt><FinInstnId><BIC>DRESDEFF200</BIC></FinInstnId></CdtrAgt><Cdtr><Nm>DICHTOMATIK</Nm><PstlAdr><Ctry>DE</Ctry></PstlAdr></Cdtr><CdtrAcct><Id><IBAN>DE10200800000301510500</IBAN></Id></CdtrAcct><RmtInf><Ustrd>6400506354</Ustrd></RmtInf></CdtTrfTxInf></FIToFICstmrCdtTrf></Document>\r\n";

        parseMessage(msg, supportedTypes);

    }

}
