/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.listhandler.reflist;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.listhandler.BaseFileHandler;
import at.jps.sanction.model.listhandler.ReferenceListHandler;

public class CurrencyListHandler extends BaseFileHandler implements ReferenceListHandler {

    private final static String        LISTNAME = "CurrencyList";

    static final Logger                logger   = LoggerFactory.getLogger(CurrencyListHandler.class);

    private static CurrencyListHandler instance;

    public static CurrencyListHandler getInstance() {
        return instance;
    }

    private Properties currencies;

    @Override
    public String getListName() {
        return LISTNAME;
    }

    @Override
    public Properties getValues() {
        if (currencies == null) {
            currencies = new Properties();
        }
        return currencies;
    }

    @Override
    public void initialize() {

        instance = this;
        readList(getFilename());

    }

    private void readList(final String filename) {
        logger.info("start reading " + LISTNAME + " file:" + filename);

        try {
            currencies = getValues();

            final File file = new File(filename);

            if (file.exists()) {
                currencies.load(new FileInputStream(file));
            }
            else {
                logger.warn("parsing failed (nofile?) file: " + filename);
            }

        }
        catch (final Exception x) {
            logger.error("parsing failed reading Country file: " + filename + " Exception: " + x.toString());
            logger.debug("Exception : ", x);
        }

        logger.info("finished reading " + LISTNAME + " file:" + filename);
        logger.info("finished reading " + LISTNAME + " :" + getValues().size());
    }

}
