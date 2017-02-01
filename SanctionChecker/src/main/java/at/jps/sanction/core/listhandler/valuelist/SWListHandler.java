/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.listhandler.valuelist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.listhandler.BaseFileHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;

public class SWListHandler extends BaseFileHandler implements ValueListHandler {

    public final static String   LISTNAME = "SWList";

    static final Logger          logger   = LoggerFactory.getLogger(SWListHandler.class);

    private static SWListHandler instance;

    public static SWListHandler getInstance() {
        return instance;
    }

    private ArrayList<String> stopWords;

    @Override
    public String getListName() {
        return LISTNAME;
    }

    @Override
    public List<String> getValues() {
        if (stopWords == null) {
            stopWords = new ArrayList<>();
        }
        return stopWords;
    }

    @Override
    synchronized public void initialize() {

        instance = this;

        // final String filename = properties.getProperty(PropertyKeys.PROP_VALLIST_DEF + "." + name + ".filename");
        readList(getFilename());

        // archiveFile(filename, properties.getProperty(PropertyKeys.PROP_LISTHIST_DEF, "."), getListName());
    }

    private void sortByLength() {
        if (getValues().size() > 1) {
            Collections.sort(getValues(), new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {

                    int delta = o1.length() - o2.length();

                    if (delta == 0) {
                        delta = o1.compareTo(o2);
                    }
                    return delta;
                }
            });
        }
    }

    private void readList(final String filename) {
        logger.info("start reading " + LISTNAME + " file:" + filename);

        if (stopWords != null) {
            return;
        }

        try {
            final BufferedReader input = new BufferedReader(new FileReader(filename));
            try {
                String line = null;
                do {
                    line = input.readLine();
                    if (line != null) {

                        line = line.trim();

                        logger.debug("Stopword :" + line);

                        final String token = line.toUpperCase();
                        if (!getValues().contains(token)) {
                            getValues().add(token);
                        }

                    }
                } while (line != null);

                sortByLength();
            }
            finally {
                input.close();
            }
        }
        catch (final Exception x) {
            logger.error("parsing failed reading " + LISTNAME + " file:" + filename + " Exception: " + x.toString());
            logger.debug("Exception : ", x);
        }

        logger.info("finished reading " + LISTNAME + " file:" + filename);
        logger.info("finished reading Stopwords :" + getValues().size());
    }

}
