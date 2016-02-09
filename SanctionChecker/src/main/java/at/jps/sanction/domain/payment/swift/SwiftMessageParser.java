/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.payment.swift;

// see http://www-01.ibm.com/support/knowledgecenter/SSBTEG_4.3.0/com.ibm.wbia_adapters.doc/doc/swift/swift72.htm%23HDRI1023624

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SwiftMessageParser {
    public static class MessageBlock {
        String                          swiftTxMsgBlock;
        String                          id;

        Hashtable<String, String>       fields;
        Hashtable<String, List<String>> tokenizedfields = new Hashtable<String, List<String>>();

        MessageBlock(final String tx) {

            // remove last -
            if (tx.endsWith("-")) {
                swiftTxMsgBlock = tx.substring(0, tx.length() - 1);
            }
            else {
                swiftTxMsgBlock = tx;
            }

            parseMsgBlock();
        }

        public Hashtable<String, String> getFields() {

            if (fields == null) {
                fields = new Hashtable<String, String>();
            }

            return fields;
        }

        public List<String> getTokenizedfields(final String field) {
            return tokenizedfields.get(field);
        }

        /*
         * ${1:F01TBNKUS00AXXX5356135117}{2:IUC1TBNKUS00XXXXN}{4: :20:5fdd433812b3be5d :23B:CRED :32A:150609USD1, :33B:USD1, :50K:/1506095211 Oficina general de la radiotelevision libia :53A:HSBCGB2L
         * :54A:CRESCHZZ :56A:CHASUS33 :57A:DEUTDEFF :59A:DEUTDEFF :71A:SHA -}
         */
        void parseMsgBlock() {

            String field = null;
            int startPos = swiftTxMsgBlock.indexOf(":");
            id = swiftTxMsgBlock.substring(0, startPos);

            int endPos = -1;
            while (true) {
                startPos = swiftTxMsgBlock.indexOf(":", startPos + 1);

                if (startPos < 0) {
                    break;
                }

                endPos = swiftTxMsgBlock.indexOf(":", startPos + 1);

                if (endPos < 0) {
                    break;
                    // endPos = swiftTxMsgBlock.length()-1;
                }

                field = swiftTxMsgBlock.substring(startPos + 1, endPos);
                // System.out.println("Field " + field);

                final int contentPosEnd = swiftTxMsgBlock.indexOf(":", endPos + 1);

                final String content = (contentPosEnd > -1) ? swiftTxMsgBlock.substring(endPos + 1, contentPosEnd) : swiftTxMsgBlock.substring(endPos + 1);
                // System.out.println("Content " + content);

                // TODO: Linebreak token duplication !!!
                // TODO: ----> this relies in the fact that "." is handled as deadcharacter later on!!

                // getFields().put(field, content.trim().replaceAll("[\\\n|\\\r]", ".").replaceAll("\\s+", " "));
                getFields().put(field, content.trim().replaceAll("\\s+", " "));

                startPos = endPos + 1;
            }

        }

        void printFields() {
            if (fields != null) {
                for (final String key : fields.keySet()) {
                    System.out.println(key + " : " + fields.get(key));
                }
            }
        }

        public void setTokenizedfields(final String field, final List<String> tokenizedfields) {
            this.tokenizedfields.put(field, tokenizedfields);
        }

    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        // TODO Auto-generated method stub
        final String tx = "{1:F01EFGBGRAAA5356135117}{2:OUC10831130531EFGBGRAA   X12341234560510170831N}{4::20:0000000000000046 :21R:GT01530963709665 :30:050302 :21:GT01530963709665                         :32B:EUR12345,                  :57A:SABRRUMM                   :59:/00570200105                        :XX: Treffer auf INDIKT (00570200105)                   :71A:SHA                        -}";

        // problem in omni test
        final String tx2 = "{1:F01TBNKUS00AXXX5356135117}{2:IUC1TBNKUS00XXXXN}{4::20:88d35c9a4aa73db9:23B:CRED:32A:150609USD1,:33B:USD1,:50K:/15060917542Dummy-Name OrganisationA Simple Address IMO 7904580:53A:HSBCGB2L:54A:CRESCHZZ:56A:CHASUS33:57A:DEUTDEFF:59A:DEUTDEFF:71A:SHA-}";
        final List<MessageBlock> msgBlocks = parseMessage(tx);

        for (final MessageBlock mb : msgBlocks) {
            mb.printFields();
        }

        System.out.println("-------------------------");

        final List<MessageBlock> msgBlocks2 = parseMessage(tx2);

        for (final MessageBlock mb : msgBlocks2) {
            mb.printFields();
        }

    }

    public static List<MessageBlock> parseMessage(final String message) {
        int startPos = -1;
        int endPos = -1;

        final ArrayList<MessageBlock> msgBlocks = new ArrayList<MessageBlock>();

        while (true) {
            startPos = message.indexOf("{", startPos + 1);
            if (startPos < 0) {
                break;
            }
            endPos = message.indexOf("}", startPos + 1);
            msgBlocks.add(new MessageBlock(message.substring(startPos + 1, endPos)));
        }
        return msgBlocks;
    }

}
