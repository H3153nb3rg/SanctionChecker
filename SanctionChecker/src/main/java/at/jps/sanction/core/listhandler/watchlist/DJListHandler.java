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
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.list.dj.Descriptions;
import at.jps.sanction.core.list.dj.Descriptions.Description;
import at.jps.sanction.core.list.dj.IDNumberTypes;
import at.jps.sanction.core.list.dj.NameDetails;
import at.jps.sanction.core.list.dj.PFA;
import at.jps.sanction.core.list.dj.PFA.Description1List;
import at.jps.sanction.core.list.dj.PFA.Description2List;
import at.jps.sanction.core.list.dj.PFA.Description3List;
import at.jps.sanction.core.list.dj.SanctionsReferences;
import at.jps.sanction.core.list.dj.SourceDescription;
import at.jps.sanction.core.list.dj.SourceDescription.Source;
import at.jps.sanction.core.listhandler.SanctionListHandlerImpl;
import at.jps.sanction.model.sl.entities.WL_Address;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sanction.model.sl.entities.WL_Name;
import at.jps.sanction.model.sl.entities.WL_Passport;

public class DJListHandler extends SanctionListHandlerImpl {

    private static String          LISTNAME = "DowJones";
    static final Logger            logger   = LoggerFactory.getLogger(DJListHandler.class);

    static private List<WL_Entity> entityList;

    private List<String>           descr1ToUse;

    private String                 loadDescription;

    public static void main(final String[] args) {

        final PFA pfa = readList("C:/Users/johannes/workspaces/SanctionList/SLHandler/src/lists/ST_xml_1039_001_201511230002_f.xml");

        buildEntityList(pfa);

        System.out.println("size: " + entityList.size());

    }

    public static PFA readList(final String filename) {
        PFA pfa = null;
        Reader reader = null;
        try {

            final JAXBContext ctx = JAXBContext.newInstance(PFA.class);

            // Unmarshaller um = ctx.createUnmarshaller();
            // PFA pfa = (PFA) um.unmarshal(new File(properties
            // .getProperty("list.inputfile")));

            final Unmarshaller unmarshaller = ctx.createUnmarshaller();
            // unmarshaller.setProperty(Unmarshaller., "UTF-8");

            final InputStream inputStream = new FileInputStream(filename);
            reader = new InputStreamReader(inputStream, "UTF-8");

            pfa = (PFA) unmarshaller.unmarshal(reader);
        }
        catch (final Exception x) {
            logger.error("JAXB Problem (" + LISTNAME + ")- reading failed: " + x.toString());
        }
        return pfa;
    }

    private static void addSourceDescription(WL_Entity entity, List<SourceDescription> sourceDescriptions) {
        for (SourceDescription sourceDescription : sourceDescriptions) {
            for (Source source : sourceDescription.getSource()) {
                if (source.getName().startsWith("http")) {
                    entity.addInformationUrl(source.getName());
                }
                else {
                    entity.addLegalBasis(source.getName());
                }
            }
        }
    }

    private static void addSanctionReferenzes(PFA pfa, WL_Entity entity, List<SanctionsReferences> sanctionreferences) {
        for (SanctionsReferences sanctionReference : sanctionreferences) {

            for (SanctionsReferences.Reference reference : sanctionReference.getReference()) {

                entity.addLegalBasis(getSanctionreference(pfa, reference.getValue()) + " (" + reference.getSinceDay() + "." + reference.getSinceMonth() + "." + reference.getSinceYear() + ")"); // todo
                                                                                                                                                                                                 // date
                                                                                                                                                                                                 // formatter
                                                                                                                                                                                                 // !!
            }
        }
    }

    private static void addDescriptions(PFA pfa, WL_Entity entity, List<Descriptions> descriptions) {

        for (Descriptions descrs : descriptions) {
            for (Description descr : descrs.getDescription()) {
                String descr1 = descr.getDescription1();

                descr1 = getDescription1(pfa, descr.getDescription1());

                entity.setEntryType(descr1); // PEP / RCA / SIP ??

                // String descr2 = descr.getDescription2(); // ??

                // descr2 = getDescription2(pfa, descr.getDescription1(), descr.getDescription2());

                // String descr3 = descr.getDescription3();
            }
        }
    }

    private static void addName(WL_Entity entity, List<NameDetails> nameDatails) {
        for (NameDetails nameDetails : nameDatails) {
            for (NameDetails.Name pfaname : nameDetails.getName()) {

                for (NameDetails.Name.NameValue nameValue : pfaname.getNameValue()) {
                    WL_Name name = new WL_Name();

                    entity.getNames().add(name);

                    String wholeName = nameValue.getEntityName();

                    if ((wholeName == null) || (wholeName.length() < 1)) {
                        wholeName = nameValue.getFirstName() + " " + (nameValue.getMiddleName() != null ? nameValue.getMiddleName() : " ") + " " + nameValue.getSurname();
                    }

                    // name.setDescription(nameValue.)

                    name.setWholeName(wholeName);

                    name.setFirstName(nameValue.getFirstName());
                    name.setMiddleName(nameValue.getMiddleName());
                    name.setLastName(nameValue.getSurname());

                    name.setAka(pfaname.getNameType().contentEquals("Also Known As"));
                    name.setWaka(false);
                }
            }
        }

    }

    private static void addAddress(WL_Entity entity, List<PFA.Records.Person.Address> addresses) {
        for (PFA.Records.Person.Address pfaAddress : addresses) {
            WL_Address address = new WL_Address();
            entity.getAddresses().add(address);

            address.setCountry(pfaAddress.getAddressCountry());
            address.setPlace(pfaAddress.getAddressCity());
            address.setLine(pfaAddress.getAddressLine());
        }
    }

    private static void addIDs(WL_Entity entity, List<IDNumberTypes> numbers) {
        for (IDNumberTypes ids : numbers) {
            for (IDNumberTypes.ID id : ids.getID()) {
                for (IDNumberTypes.ID.IDValue value : id.getIDValue()) {

                    WL_Passport passport = new WL_Passport();
                    passport.setType(id.getIDType());
                    passport.setNumber(value.getValue());

                    entity.getPassports().add(passport);

                }
            }
        }
    }

    static String getDescription1(PFA pfa, final String descriptionId1) {
        String value = "";
        for (PFA.Description1List list1 : pfa.getDescription1List()) {
            for (Description1List.Description1Name name1 : list1.getDescription1Name()) {
                if (name1.getDescription1Id().equals(descriptionId1)) {
                    name1.getRecordType();
                    value = name1.getValue();
                    return value;
                }
            }
        }
        return value;
    }

    static String getDescription2(PFA pfa, final String descriptionId1, final String descriptionId2) {
        String value = "";
        for (PFA.Description2List list2 : pfa.getDescription2List()) {
            for (Description2List.Description2Name name2 : list2.getDescription2Name()) {
                if ((name2.getDescription1Id().equals(descriptionId1)) && (name2.getDescription2Id().equals(descriptionId2))) {
                    value = name2.getValue();
                    return value;
                }
            }
        }
        return value;
    }

    static String getDescription3(PFA pfa, final String descriptionId3) {
        String value = "";
        for (PFA.Description3List list3 : pfa.getDescription3List()) {
            for (Description3List.Description3Name name3 : list3.getDescription3Name()) {
                if (name3.getDescription3Id().equals(descriptionId3)) {
                    value = name3.getValue();
                    return value;
                }
            }
        }
        return value;
    }

    static private String getSanctionreference(PFA pfa, final String code) {
        String srlName = "";
        for (PFA.SanctionsReferencesList srl : pfa.getSanctionsReferencesList()) {
            for (PFA.SanctionsReferencesList.ReferenceName srln : srl.getReferenceName()) {
                if (srln.getCode().equals(code)) {
                    srlName = srln.getName() + " [" + srln.getStatus() + "]";
                    break;
                }
            }
        }
        return srlName;
    }

    public static void buildEntityList(PFA pfa) {
        entityList = new ArrayList<WL_Entity>();

        for (PFA.Records records : pfa.getRecords()) {

            for (PFA.Records.Person pfaPerson : records.getPerson()) {

                if (pfaPerson.getActiveStatus().equalsIgnoreCase("Active")) {
                    WL_Entity entity = new WL_Entity();

                    entity.setType("Individual");
                    entity.setWL_Id(pfaPerson.getId());
                    entity.setIssueDate(pfaPerson.getDate());

                    entityList.add(entity);

                    addSanctionReferenzes(pfa, entity, pfaPerson.getSanctionsReferences());

                    addDescriptions(pfa, entity, pfaPerson.getDescriptions());
                    addSourceDescription(entity, pfaPerson.getSourceDescription());
                    addName(entity, pfaPerson.getNameDetails());
                    addAddress(entity, pfaPerson.getAddress());

                    addIDs(entity, pfaPerson.getIDNumberTypes());
                }
            }

            for (PFA.Records.Entity pfaEntity : records.getEntity()) {

                if (pfaEntity.getActiveStatus().equalsIgnoreCase("Active")) {

                    WL_Entity entity = new WL_Entity();

                    entity.setType("Entity");
                    entity.setWL_Id(pfaEntity.getId());
                    entity.setIssueDate(pfaEntity.getDate());

                    entityList.add(entity);

                    addSanctionReferenzes(pfa, entity, pfaEntity.getSanctionsReferences());

                    addDescriptions(pfa, entity, pfaEntity.getDescriptions());
                    addSourceDescription(entity, pfaEntity.getSourceDescription());
                    addName(entity, pfaEntity.getNameDetails());

                    addIDs(entity, pfaEntity.getIDNumberTypes());

                    if ((pfaEntity.getVesselDetails() != null) && (pfaEntity.getVesselDetails().size() > 0)) {
                        entity.setType("Transport");

                        // TODO: Implement VesselDetails !!

                        // for( PFA.Records.Entity.VesselDetails vd: pfaEntity.getVesselDetails())
                        // {
                        // vd.getVesselType()
                        // }
                    }
                }
            }
        }

    }

    public static void writeList(final String filename, final PFA pfa) {

        try {
            final JAXBContext ctx = JAXBContext.newInstance(PFA.class);
            final Marshaller marshaller = ctx.createMarshaller();

            marshaller.marshal(pfa, new File(filename));
        }
        catch (final Exception x) {
            logger.error("JAXB Problem (" + LISTNAME + ")- storing failed: " + x.toString());
            logger.debug("Exception : ", x);
        }
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
    public List<String> getStopwordList() {
        logger.error("Stopword list for (" + LISTNAME + ") not yet implemented!");
        return null;
    }

    @Override
    public void initialize() {
        super.initialize();

        // final String filename = properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".filename");
        PFA pfa = readList(getFilename());

        archiveFile(getFilename(), getHistPath(), getListName());

        // String descr1 = // properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".LoadDescription1");

        final StringTokenizer tokenizer = new StringTokenizer(getLoadDescription(), ",");

        descr1ToUse = new ArrayList<String>();
        while (tokenizer.hasMoreTokens()) {
            descr1ToUse.add(tokenizer.nextToken());
        }

        buildEntityList(pfa);

    }

    public String getLoadDescription() {
        return loadDescription;
    }

    public void setLoadDescription(String loadDescription) {
        this.loadDescription = loadDescription;
    }

}
