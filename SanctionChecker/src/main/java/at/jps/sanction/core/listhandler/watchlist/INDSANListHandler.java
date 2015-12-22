/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.listhandler.watchlist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.listhandler.SanctionListHandlerImpl;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sanction.model.sl.entities.WL_Name;

public class INDSANListHandler extends SanctionListHandlerImpl {

    public final static String       LISTNAME = "INDSANList";

    static final Logger              logger   = LoggerFactory.getLogger(INDSANListHandler.class);

    private static INDSANListHandler instance;

    static private List<WL_Entity>   entityList;

    public static INDSANListHandler getInstance() {
        return instance;
    }

    public static void main(final String[] args) {

        final INDSANListHandler handler = new INDSANListHandler();

        handler.readList("E:/Workspace/SanctionList/SLHandler/src/lists/INDSAN.csv");
        System.out.println("size: " + handler.getEntityList().size());

        handler.printEntries();

    }

    @Override
    public List<WL_Entity> getEntityList() {

        return entityList;
    }

    @Override
    public String getListName() {
        return LISTNAME;
    }

    @Override
    public void initialize() {
        super.initialize();

        instance = this;

        // String filename = properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".filename");
        // final String url = properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".url");
        String filename = getFilename();
        if (getUrl() != null) {
            try {

                // String test = properties.getProperty(PropertyKeys.PROP_USEPROXY_DEF);
                // System.out.println(test + " : " + Boolean.getBoolean(properties.getProperty(PropertyKeys.PROP_USEPROXY_DEF, "true")) + " " + Boolean.getBoolean(test));

                // final boolean proxyOn = Boolean.parseBoolean(properties.getProperty(PropertyKeys.PROP_USEPROXY_DEF, "true"));

                filename = downloadFile(getUrl(), isUseSysProxy());
                if (filename == null) {
                    filename = getFilename(); // properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".filename");
                }

            }
            catch (final Exception e) {
                logger.error("Download (" + LISTNAME + ") - from URL: " + getUrl() + " failed!");
                if (logger.isDebugEnabled()) {
                    logger.debug("Exception : ", e);
                }
            }
        }

        readList(filename);

        archiveFile(filename, getHistPath(), getListName());

    }

    public void printEntries() {
        if (getEntityList() != null) {
            for (final WL_Entity entity : getEntityList()) {
                System.out.println("ID: " + entity.getWL_Id());
                System.out.println("name: " + entity.getNames().get(0).getLastName());
                System.out.println("Legal Basis: " + entity.getLegalBasis());
                System.out.println("--------------");
            }
        }
    }

    private void readList(final String filename) {
        logger.info("start reading " + LISTNAME + " file:" + filename);

        entityList = new ArrayList<WL_Entity>();
        try {
            final BufferedReader input = new BufferedReader(new FileReader(filename));
            try {
                String line = null;
                do {
                    line = input.readLine();
                    if (line != null) {

                        if (logger.isDebugEnabled()) {
                            logger.debug(getListName() + ": " + line);
                        }

                        final StringTokenizer tokenizer = new StringTokenizer(line, ";");
                        final WL_Entity entity = new WL_Entity();
                        while (tokenizer.hasMoreTokens()) {

                            entity.setEntryType("SL");
                            entity.setType("Individual");
                            entity.setWL_Id(tokenizer.nextToken());

                            final WL_Name name = new WL_Name();
                            name.setLastName(tokenizer.nextToken());
                            name.setWholeName(name.getLastName());

                            entity.getNames().add(name);

                            entity.setLegalBasis(tokenizer.nextToken());
                            entityList.add(entity);
                        }
                    }
                } while (line != null);

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
        logger.info("finished reading records :" + entityList.size());
    }

}
