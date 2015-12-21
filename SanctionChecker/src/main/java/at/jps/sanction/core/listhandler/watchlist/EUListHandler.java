/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.listhandler.watchlist;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.list.eu.WHOLE;
import at.jps.sanction.core.listhandler.SanctionListHandlerImpl;
import at.jps.sanction.model.sl.entities.Entity;
import at.jps.sanction.model.sl.entities.Name;
import at.jps.sanction.model.sl.entities.Passport;

public class EUListHandler extends SanctionListHandlerImpl {

    private final static String  LISTNAME = "EUList";

    private static final Logger  logger   = LoggerFactory.getLogger(EUListHandler.class);

    private static EUListHandler instance;

    private static WHOLE         whole;

    static private List<Entity>  entityList;

    public static void buildEntityList(final WHOLE whole) {

        if (whole == null) {
            return;
        }

        if (whole != null) {

            synchronized (whole) {

                if (entityList == null) {
                    entityList = new ArrayList<Entity>();

                    // TODO: complete loading should be implemented

                    for (final WHOLE.ENTITY wentity : whole.getENTITY()) {
                        // StringBuilder line = new StringBuilder();
                        // line.append("Name : ");

                        final Entity entity = new Entity();
                        entityList.add(entity);

                        entity.setId(wentity.getId());

                        if (wentity.getType().equalsIgnoreCase("P")) {
                            entity.setType("Individual");
                        }
                        else if (wentity.getType().equalsIgnoreCase("E")) {
                            entity.setType("Entity");
                        }
                        else {
                            entity.setType(wentity.getType());
                        }

                        entity.setLegalBasis(wentity.getLegalBasis());
                        entity.setInformationUrl(wentity.getPdfLink());
                        entity.setComment(wentity.getRemark());
                        entity.setIssueDate(wentity.getRegDate());

                        for (final WHOLE.ENTITY.NAME wname : wentity.getNAME()) {
                            // line.append(",").append(
                            // name.getFIRSTNAME() + " - " +
                            // name.getMIDDLENAME()
                            // + " - " + name.getLASTNAME() + " wn: "
                            // + name.getWHOLENAME());

                            String wnamet = wname.getWHOLENAME();

                            if ((wnamet == null) || (wnamet.length() < 1)) {
                                wnamet = wname.getFIRSTNAME() + " " + wname.getMIDDLENAME() + " " + wname.getLASTNAME();
                            }

                            final Name name = new Name();
                            name.setWholeName(wnamet);
                            name.setFirstName(wname.getFIRSTNAME());
                            name.setMiddleName(wname.getMIDDLENAME());
                            name.setLastName(wname.getLASTNAME());
                            name.setAka(false); // TODO: this is not sooo cool
                            name.setWaka(false);

                            if (!entity.getNames().contains(name)) {
                                entity.getNames().add(name);
                            }

                        }

                        for (final WHOLE.ENTITY.PASSPORT wpass : wentity.getPASSPORT()) {

                            final Passport passport = new Passport();

                            passport.setCountry(wpass.getCOUNTRY());
                            passport.setIssueDate(wpass.getNUMBER());
                            passport.setNumber(wpass.getNUMBER());

                            entity.getPassports().add(passport);
                        }

                        // line.append("Address : ");
                        // for (WHOLE.ENTITY.ADDRESS address :
                        // entity.getADDRESS())
                        // {
                        // line.append(",").append(
                        // address.getSTREET() + " - " + address.getCITY()
                        // + " - " + address.getCOUNTRY());
                        // }

                        // System.out.println(entity.getId() + ":" +
                        // line.toString());
                    }

                }
            }
        }
    }

    public static EUListHandler getInstance() {
        return instance;
    }

    public static WHOLE readList(final String filename) {
        whole = null;
        Reader reader = null;
        try {

            final JAXBContext ctx = JAXBContext.newInstance(WHOLE.class);

            final Unmarshaller unmarshaller = ctx.createUnmarshaller();

            final InputStream inputStream = new FileInputStream(filename);
            reader = new InputStreamReader(inputStream, "UTF-8");

            whole = (WHOLE) unmarshaller.unmarshal(reader);

        }
        catch (final Exception x) {
            logger.error("JAXB Problem (" + LISTNAME + ")- reading failed: " + x.toString());
            logger.debug("Exception : ", x);
        }
        return whole;
    }

    public static void writeList(final String filename, final WHOLE whole) {

        try {
            final JAXBContext ctx = JAXBContext.newInstance(WHOLE.class);
            final Marshaller marshaller = ctx.createMarshaller();

            marshaller.marshal(whole, new File(filename));
        }
        catch (final Exception x) {
            logger.error("JAXB Problem (" + LISTNAME + ")- storing failed: " + x.toString());
            logger.debug("Exception : ", x);
        }
    }

    @Override
    public List<Entity> getEntityList() {

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
        //
        // final String url = properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".url");
        String filename = getFilename();
        if (getUrl() != null) {

            try {

                // final boolean proxyOn = Boolean.parseBoolean(properties.getProperty(PropertyKeys.PROP_USEPROXY_DEF, "true"));

                filename = downloadFile(getUrl(), isUseSysProxy());
                if (filename == null) {
                    filename = getFilename(); // properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".filename");
                }

            }
            catch (final Exception e) {
                logger.error("Download (" + LISTNAME + ") - from URL: " + getUrl() + " failed!");
                logger.debug("Exception : ", e);
            }
        }

        buildEntityList(readList(filename));

        archiveFile(filename, getHistPath(), getListName());

        // try {
        // WHOLE whole = loadlist(filename);
        //
        // if (whole != null) {
        //
        // for (WHOLE.ENTITY entity : whole.getENTITY()) {
        // StringBuilder line = new StringBuilder();
        // line.append("Name : ");
        //
        // for (WHOLE.ENTITY.NAME name : entity.getNAME()) {
        // line.append(",").append(
        // name.getFIRSTNAME() + " - "
        // + name.getMIDDLENAME() + " - "
        // + name.getLASTNAME() + " wn: "
        // + name.getWHOLENAME());
        //
        // if (name.getWHOLENAME() != null) {
        // nameList.put(name.getWHOLENAME(), entity);
        // }
        //
        // }
        //
        // line.append("Address : ");
        // for (WHOLE.ENTITY.ADDRESS address : entity.getADDRESS()) {
        // line.append(",").append(
        // address.getSTREET() + " - " + address.getCITY()
        // + " - " + address.getCOUNTRY());
        // }
        //
        // // System.out.println(entity.getId() + ":" +
        // // line.toString());
        // }
        // }
        // } catch (Exception x) {
        // logger.error("Error loading list " + LISTNAME + " from " + filename);
        // }
    }
}
