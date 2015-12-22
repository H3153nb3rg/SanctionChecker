/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.jps.sanction.domain.swift.SwiftMessageParser;
import at.jps.sanction.domain.swift.SwiftMessageParser.MessageBlock;
import at.jps.sanction.model.Message;

public class OmniTCReader {

    public static ArrayList<String> getRefIdList(final String filename) {
        final ArrayList<String> refIds = new ArrayList<String>();

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(new File(filename)));
            for (String line; (line = br.readLine()) != null;) {

                refIds.add(line.trim());

            }
            // line is not visible here.
        }
        catch (final Exception x) {
            System.err.println("RefId file: [" + filename + "] parsing error");
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (final IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return refIds;
    }

    public static void main(final String[] args) throws IOException {

        // ArrayList<String> refIds = getRefIdList(args[1]);

        final OmniTCSwiftParser parser = new OmniTCSwiftParser();

        final File file = new File(args[0]);

        parser.parse(file);

        System.out.println("found: " + parser.getMessages().size() + " Messages  --------------");

        final ArrayList<String> tokens = new ArrayList<String>();

        boolean found = false;
        int foundctr = 0;
        for (final Message message : parser.getMessages()) {

            final List<MessageBlock> messageDetails = SwiftMessageParser.parseMessage(message.getRawContent());

            found = false;
            for (final MessageBlock messageBlock : messageDetails) {
                if (found == false) {
                    for (final String key : messageBlock.getFields().keySet()) {
                        if (found == false) {
                            if (key.trim().equals("20") || key.trim().equals("50K") || key.trim().equals("70")) {

                                if (found == false) {
                                    if (((key.trim().equals("20"))) || (key.trim().equals("50K") || (key.trim().equals("70") && !found))) {
                                        final String token = messageBlock.getFields().get(key);
                                        String cleanedToken = token;
                                        if (key.trim().equals("50K") || key.trim().equals("70")) {
                                            cleanedToken = token.replaceAll("[0-9]", "").replace("/", "");
                                            if (!cleanedToken.trim().equalsIgnoreCase("xx")) {
                                                tokens.add(cleanedToken);
                                            }
                                        }
                                        System.out.println(key + " : " + cleanedToken);
                                    }
                                    else {
                                        if (key.trim().equals("20")) {
                                            System.out.println(key + " : OK");
                                            found = true;
                                            foundctr++;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // System.out.println(message.toString());
            System.out.println("--------------");

            // System.out.println("Message: " + messageDetails.);
        }
        System.out.println("not found: " + foundctr);

        // if (args.length > 2) {
        // writeOutFile(args[2], parser.getMessages(), refIds);
        // }
        //
        if (args.length >= 2) {
            writeOutCSVFile(args[1], tokens);
        }

    }

    private static void writeOutCSVFile(final String filename, final ArrayList<String> tokens) {

        final String LISTNAME = "INDTST";

        final SimpleDateFormat dfhistvon = new SimpleDateFormat("yyyyMMddHHmmssS");
        final String histvon = dfhistvon.format(Calendar.getInstance().getTime()).substring(0, 16);

        final String newline = System.getProperty("line.separator");

        BufferedWriter writerNAMES = null;
        BufferedWriter writerMAIN = null;

        try {
            final File outFileNAMES = new File(filename + "_NAMES.csv");
            final File outFileMAIN = new File(filename + "_MAIN.csv");

            writerNAMES = new BufferedWriter(new FileWriter(outFileNAMES));
            writerMAIN = new BufferedWriter(new FileWriter(outFileMAIN));

            int id = 1000;
            for (final String token : tokens) {

                final String ids = String.format("%06d", id);
                final String names = ids + ";" + LISTNAME + ";" + ids + ";-1;;;;" + token + ";" + token + ";;;;" + histvon + ";9999";
                final String main = LISTNAME + ";" + ids + ";-1;P;Omnicision;;;OMNI;" + histvon + ";Omnicision Token;" + histvon + ";9999;;N;;";

                writerNAMES.write(names);
                writerNAMES.write(newline);
                writerMAIN.write(main);
                writerMAIN.write(newline);

                id++;

            }

        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                // Close the writer regardless of what happens...
                writerNAMES.close();
                writerMAIN.close();
            }
            catch (final Exception e) {
            }
        }

    }

    @SuppressWarnings("unused")
    private static void writeOutFile(final String filename, final ArrayList<Message> messages, final ArrayList<String> refIds) {

        final String newline = System.getProperty("line.separator");

        BufferedWriter writer = null;
        try {
            final File outFile = new File(filename);

            writer = new BufferedWriter(new FileWriter(outFile));

            boolean found = false;

            for (final Message message : messages) {

                final List<MessageBlock> messageDetails = SwiftMessageParser.parseMessage(message.getRawContent());

                found = false;
                for (final MessageBlock messageBlock : messageDetails) {
                    if (found == false) {
                        for (final String key : messageBlock.getFields().keySet()) {
                            if (found == false) {
                                if (key.trim().equals("20") || key.trim().equals("50K") || key.trim().equals("70")) {

                                    if (found == false) {
                                        if (((key.trim().equals("20"))) || (key.trim().equals("50K") && !found)) {
                                            writer.write(key + " : " + messageBlock.getFields().get(key));
                                            writer.write(newline);
                                        }
                                        else {
                                            if (key.trim().equals("20")) {
                                                // System.out.println(key +
                                                // " : OK");
                                                found = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!found) {
                    writer.write("--------------");
                    writer.write(newline);
                }

            }

            // for (Message message : messages) {
            //
            // List<MessageBlock> messageDetails = SwiftMessageParser
            // .parseMessage(message.getContent());
            //
            // for (MessageBlock messageBlock : messageDetails) {
            // for (String key : messageBlock.getFields().keySet()) {
            //
            // if (key.trim().equals("20") || key.trim().equals("50K")) {
            // writer.write(key + " : "
            // + messageBlock.getFields().get(key));
            // writer.write(newline);
            // }
            // }
            // }
            //
            // writer.write("--------------");
            // writer.write(newline);
            // }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            }
            catch (final Exception e) {
            }
        }

    }

}
