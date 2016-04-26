/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.io.file.FileParserImpl;
import at.jps.sanction.model.Message;

public class PersonFileParser extends FileParserImpl {

    static final Logger logger = LoggerFactory.getLogger(PersonFileParser.class);

    @Override
    public boolean parse(final File file) {
        boolean parsedOK = false;
        logger.info("start parsing file:" + file.getAbsoluteFile());

        try {
            final BufferedReader input = new BufferedReader(new FileReader(file));
            try {
                String line = null; // not declared within while loop
                while ((line = input.readLine()) != null) {

                    final Message message = new PersonMessage(line);
                    prepareMessage(message);
                }
            }
            finally {
                input.close();
            }
            parsedOK = true;
        }
        catch (final Exception x) {
            logger.error("parsing failed:" + file.getAbsoluteFile());
            logger.debug("Exception: ", x);
        }

        logger.info("finished parsing file:" + file.getAbsoluteFile());
        return parsedOK;
    }

}
