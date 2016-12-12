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
import at.jps.sanction.core.util.country.CountryCodeConverter;
import at.jps.sanction.model.wl.entities.SL_Entry;
import at.jps.sanction.model.wl.entities.WL_BirthInfo;
import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sanction.model.wl.entities.WL_Entity.EntityType;
import at.jps.sanction.model.wl.entities.WL_Entity.EntryCategory;
import at.jps.sanction.model.wl.entities.WL_Name;
import at.jps.sanction.model.wl.entities.WL_Passport;

public class EUListHandler extends SanctionListHandlerImpl {

    private final static String LISTNAME = "EUList";

    private static final Logger logger   = LoggerFactory.getLogger(EUListHandler.class);

    private static WHOLE        whole;

    @Override
    public List<SL_Entry> buildSearchListEntries(final String topicName, WL_Entity entity) {
        List<SL_Entry> entries = null;
        switch (topicName) {
            case "EmbargoPersonNames":
                if (entity.getEntityType().equals(EntityType.INDIVIDUAL) && entity.getEntryCategory().equals(EntryCategory.EMBARGO)) {

                    entries = new ArrayList<>();

                    for (final WL_Name name : entity.getNames()) {
                        final SL_Entry entry = new SL_Entry();
                        entry.setSearchValue(name.getWholeName());
                        entries.add(entry);
                    }
                }
                break;
            case "PepPersonNames":
                if (entity.getEntityType().equals(EntityType.INDIVIDUAL) && (!entity.getEntryCategory().equals(EntryCategory.EMBARGO))) {

                    entries = new ArrayList<>();

                    for (final WL_Name name : entity.getNames()) {
                        final SL_Entry entry = new SL_Entry();
                        entry.setSearchValue(name.getWholeName());
                        entries.add(entry);
                    }
                }
                break;
            case "EmbargoEntityNames":
                if ((entity.getEntityType().equals(EntityType.ENTITY) || entity.getEntityType().equals(EntityType.OTHER)) && entity.getEntryCategory().equals(EntryCategory.EMBARGO)) {

                    entries = new ArrayList<>();

                    for (final WL_Name name : entity.getNames()) {
                        final SL_Entry entry = new SL_Entry();
                        entry.setSearchValue(name.getWholeName());
                        entries.add(entry);
                    }
                }
                break;
            case "EmbargoVesselNames":
                if (entity.getEntityType().equals(EntityType.TRANSPORT) && entity.getEntryCategory().equals(EntryCategory.EMBARGO)) {

                    entries = new ArrayList<>();

                    for (final WL_Name name : entity.getNames()) {
                        final SL_Entry entry = new SL_Entry();
                        entry.setSearchValue(name.getWholeName());
                        entries.add(entry);
                    }
                }
                break;
            case "EmbargoPassportNumbers":
                if (entity.getEntityType().equals(EntityType.INDIVIDUAL) && entity.getEntryCategory().equals(EntryCategory.EMBARGO)) {

                    entries = new ArrayList<>();

                    for (final WL_Passport passport : entity.getPassports()) {
                        final SL_Entry entry = new SL_Entry();
                        if (passport.getType().equals("Passport No.")) {
                            entry.setSearchValue(passport.getNumber());
                            entries.add(entry);
                        }
                    }
                }
                break;
            case "PepPassportNumbers":
                if (entity.getEntityType().equals(EntityType.INDIVIDUAL) && (!entity.getEntryCategory().equals(EntryCategory.EMBARGO))) {

                    entries = new ArrayList<>();

                    for (final WL_Passport passport : entity.getPassports()) {
                        final SL_Entry entry = new SL_Entry();
                        if (passport.getType().equals("Passport No.")) {
                            entry.setSearchValue(passport.getNumber());
                            entries.add(entry);
                        }
                    }
                }
                break;

            default:
                break;
        }
        return entries;
    }

    public void buildEntityList(final WHOLE whole) {

        if (whole == null) {
            return;
        }

        if (whole != null) {

            synchronized (whole) {

                // TODO: complete loading should be implemented !!

                // new SimpleDateFormat("yyyy-MM-dd");

                for (final WHOLE.ENTITY wentity : whole.getENTITY()) {
                    // StringBuilder line = new StringBuilder();
                    // line.append("Name : ");

                    final WL_Entity entity = new WL_Entity();

                    entity.setWL_Id(wentity.getId());

                    if (wentity.getType().equalsIgnoreCase("P")) {
                        entity.setEntityType(WL_Entity.EntityType.INDIVIDUAL);
                    }
                    else if (wentity.getType().equalsIgnoreCase("E")) {
                        entity.setEntityType(WL_Entity.EntityType.ENTITY);
                    }
                    else {
                        entity.setEntityType(WL_Entity.EntityType.OTHER);
                    }

                    entity.addLegalBasis(wentity.getLegalBasis());
                    entity.addInformationUrl(wentity.getPdfLink());
                    entity.setComment(wentity.getRemark());
                    entity.setIssueDate(wentity.getRegDate());

                    entity.setEntryCategory(WL_Entity.EntryCategory.EMBARGO);

                    for (final WHOLE.ENTITY.BIRTH birth : wentity.getBIRTH()) {
                        final WL_BirthInfo birthInfo = new WL_BirthInfo();

                        final String ISOCtry = CountryCodeConverter.convert(2, 1, birth.getCOUNTRY());

                        if (ISOCtry != null) {
                            birthInfo.setCountry(ISOCtry);
                        }
                        birthInfo.setPlace(birth.getPLACE());

                        try {
                            // it is like 1924-02-21...
                            birthInfo.setYear(birth.getDATE().substring(0, 4));
                            birthInfo.setMonth(birth.getDATE().substring(5, 7));
                            birthInfo.setDay(birth.getDATE().substring(8));

                            entity.setBirthday(birthInfo);
                        }
                        catch (final Exception x) {
                            System.err.println("Birthday parsing failed! : " + birth.getDATE());
                            logger.error("Birthday parsing failed! : " + birth.getDATE());
                        }
                        // Date date;
                        // try {
                        // date = sdf.parse(birth.getDATE());
                        //
                        // birthInfo.setYear(date.getYear() + "");
                        // birthInfo.setMonth(date.getMonth() + "");
                        // birthInfo.setDay(date.getDay() + "");
                        //
                        // }
                        // catch (final ParseException e) {
                        // logger.error("Birthday parsing failed [" + birth.getDATE() + "] :" + e.toString());
                        // logger.debug("Exception : ", e);
                        // }

                        break;
                    }

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

                        final WL_Name name = new WL_Name();
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

                        final WL_Passport passport = new WL_Passport();

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

                    addWLEntry(entity);
                }

            }
        }
    }

    public WHOLE readList(final String filename) {
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
            logger.error("JAXB Problem (" + getListName() + ")- reading failed: " + x.toString());
            logger.debug("Exception : ", x);
        }
        return whole;
    }

    public void writeList(final String filename, final WHOLE whole) {

        try {
            final JAXBContext ctx = JAXBContext.newInstance(WHOLE.class);
            final Marshaller marshaller = ctx.createMarshaller();

            marshaller.marshal(whole, new File(filename));
        }
        catch (final Exception x) {
            logger.error("JAXB Problem (" + getListName() + ")- storing failed: " + x.toString());
            logger.debug("Exception : ", x);
        }
    }

    @Override
    public void initialize() {
        super.initialize();

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

        try {
            buildEntityList(readList(filename));
        }
        catch (final Exception e) {
            logger.error("parsing list failed!!!!");
            if (logger.isDebugEnabled()) {
                logger.debug("Exception : ", e);
            }
        }
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

        if (logger.isInfoEnabled()) {
            logger.info("-------------------");
            logger.info("Entities loaded: " + getEntityList().size());
            logger.info("-------------------");
        }

    }
}
