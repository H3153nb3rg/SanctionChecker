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

import at.jps.sanction.core.list.dj.Associate;
import at.jps.sanction.core.list.dj.Descriptions;
import at.jps.sanction.core.list.dj.Descriptions.Description;
import at.jps.sanction.core.list.dj.IDNumberTypes;
import at.jps.sanction.core.list.dj.NameDetails;
import at.jps.sanction.core.list.dj.PFA;
import at.jps.sanction.core.list.dj.PFA.Description1List;
import at.jps.sanction.core.list.dj.PFA.Description2List;
import at.jps.sanction.core.list.dj.PFA.Description3List;
import at.jps.sanction.core.list.dj.PFA.RelationshipList.Relationship;
import at.jps.sanction.core.list.dj.SanctionsReferences;
import at.jps.sanction.core.list.dj.SourceDescription;
import at.jps.sanction.core.list.dj.SourceDescription.Source;
import at.jps.sanction.core.listhandler.SanctionListHandlerImpl;
import at.jps.sanction.model.sl.entities.WL_Address;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sanction.model.sl.entities.WL_Name;
import at.jps.sanction.model.sl.entities.WL_Passport;

public class DJListHandler extends SanctionListHandlerImpl {

    private static String LISTNAME = "DowJones";
    static final Logger   logger   = LoggerFactory.getLogger(DJListHandler.class);

    private List<String>  descr1ToUse;

    private String        loadDescription1;
    private String        loadDescription2;

    public static void main(final String[] args) {

        DJListHandler djh = new DJListHandler();

        final PFA pfa = djh.readList("C:/Users/johannes/workspaces/SanctionList/SLHandler/src/lists/ST_xml_1039_001_201511230002_f.xml");

        djh.buildEntityList(pfa);

        System.out.println("size: " + djh.getEntityList().size());

    }

    public PFA readList(final String filename) {
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

    private void addSourceDescription(WL_Entity entity, List<SourceDescription> sourceDescriptions) {
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

    private void addSanctionReferenzes(PFA pfa, WL_Entity entity, List<SanctionsReferences> sanctionreferences) {
        for (SanctionsReferences sanctionReference : sanctionreferences) {

            for (SanctionsReferences.Reference reference : sanctionReference.getReference()) {

                String entry = getSanctionreference(pfa, reference.getValue());

                if ((reference.getSinceDay() != null) && (reference.getSinceDay().length() > 0)) {
                    entry += " (" + reference.getSinceDay() + "." + reference.getSinceMonth() + "." + reference.getSinceYear() + ")";
                }

                entity.addLegalBasis(entry); // todo
            }
        }
    }

    private String addDescriptions(PFA pfa, WL_Entity entity, List<Descriptions> descriptions) {  // DOR: at the moment onnly for descr1 filtering!!

        String id = "";

        for (Descriptions descrs : descriptions) {
            for (Description descr : descrs.getDescription()) {
                id = descr.getDescription1();
                String descr1 = getDescription1(pfa, id);

                descr1 += " | " + getDescription2(pfa, descr.getDescription1(), descr.getDescription2());

                descr1 += " | " + getDescription3(pfa, descr.getDescription3());

                entity.setEntryType(descr1); // PEP / RCA / SIP ??

                break;
            }
        }
        return id;
    }

    private void addName(WL_Entity entity, List<NameDetails> nameDatails) {
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

    private void addAddress(WL_Entity entity, List<PFA.Records.Person.Address> addresses) {
        for (PFA.Records.Person.Address pfaAddress : addresses) {
            WL_Address address = new WL_Address();
            entity.getAddresses().add(address);

            address.setCountry(pfaAddress.getAddressCountry());
            address.setPlace(pfaAddress.getAddressCity());
            address.setLine(pfaAddress.getAddressLine());
        }
    }

    private void addCompanyAddress(WL_Entity entity, List<PFA.Records.Entity.CompanyDetails> companyDetails) {
        for (PFA.Records.Entity.CompanyDetails cd : companyDetails) {
            WL_Address address = new WL_Address();
            entity.getAddresses().add(address);

            address.setCountry(cd.getAddressCountry());
            address.setPlace(cd.getAddressCity());
            address.setLine(cd.getAddressLine());
        }
    }

    private void addIDs(WL_Entity entity, List<IDNumberTypes> numbers) {
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

    private String getDescription1(PFA pfa, final String descriptionId1) {
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

    private String getDescription2(PFA pfa, final String descriptionId1, final String descriptionId2) {
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

    private String getDescription3(PFA pfa, final String descriptionId3) {
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

    private String getSanctionreference(PFA pfa, final String code) {
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

    private String getAssociationType(PFA pfa, final String code) {
        String type = "";

        for (PFA.RelationshipList rl : pfa.getRelationshipList()) {
            for (Relationship r : rl.getRelationship()) {
                if (r.getCode().equals(code)) {
                    type = r.getName();
                    break;
                }
            }
        }

        return type;
    }

    void getAssociations(PFA pfa, List<Associate> associates, final String id) {

        WL_Entity entity = getEntityById(id);

        if (entity != null) {

            for (Associate asso : associates) {

                entity.addReleation(asso.getId(), getAssociationType(pfa, asso.getCode()));
                asso.getEx();
            }
        }

    }

    void getAssociations(PFA pfa) {
        for (PFA.Associations assosiations : pfa.getAssociations()) {
            for (PFA.Associations.SpecialEntity specialEntity : assosiations.getSpecialEntity()) {
                getAssociations(pfa, specialEntity.getAssociate(), specialEntity.getId());
            }

            for (PFA.Associations.PublicFigure publicfigure : assosiations.getPublicFigure()) {
                getAssociations(pfa, publicfigure.getAssociate(), publicfigure.getId());
            }
        }
    }

    public void buildEntityList(PFA pfa) {

        for (PFA.Records records : pfa.getRecords()) {

            for (PFA.Records.Person pfaPerson : records.getPerson()) {

                // add description filter !!

                if ((pfaPerson.getActiveStatus() != null) && (pfaPerson.getActiveStatus().equalsIgnoreCase("Active"))) {
                    WL_Entity entity = new WL_Entity();

                    entity.setType("Individual");
                    entity.setWL_Id(pfaPerson.getId());
                    entity.setIssueDate(pfaPerson.getDate());

                    String descr1 = addDescriptions(pfa, entity, pfaPerson.getDescriptions());

                    if ((loadDescription1 == null) || (loadDescription1.length() == 0) || loadDescription1.contains(descr1)) {
                        addWLEntry(entity);

                        addSanctionReferenzes(pfa, entity, pfaPerson.getSanctionsReferences());

                        addSourceDescription(entity, pfaPerson.getSourceDescription());
                        addName(entity, pfaPerson.getNameDetails());
                        addAddress(entity, pfaPerson.getAddress());

                        addIDs(entity, pfaPerson.getIDNumberTypes());
                    }
                }
            }

            for (PFA.Records.Entity pfaEntity : records.getEntity()) {

                if ((pfaEntity.getActiveStatus() != null) && (pfaEntity.getActiveStatus().equalsIgnoreCase("Active"))) {

                    WL_Entity entity = new WL_Entity();

                    entity.setType("Entity");
                    entity.setWL_Id(pfaEntity.getId());
                    entity.setIssueDate(pfaEntity.getDate());

                    String descr1 = addDescriptions(pfa, entity, pfaEntity.getDescriptions());

                    if ((loadDescription1 == null) || (loadDescription1.length() == 0) || loadDescription1.contains(descr1)) {

                        addWLEntry(entity);

                        addCompanyAddress(entity, pfaEntity.getCompanyDetails());

                        addSanctionReferenzes(pfa, entity, pfaEntity.getSanctionsReferences());

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
    }

    public void writeList(final String filename, final PFA pfa) {

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

    // @Override
    // public List<String> getStopwordList() {
    // logger.error("Stopword list for (" + LISTNAME + ") not yet implemented!");
    // return null;
    // }

    @Override
    public void initialize() {
        super.initialize();

        PFA pfa = readList(getFilename());

        archiveFile(getFilename(), getHistPath(), getListName());

        final StringTokenizer tokenizer = new StringTokenizer(getLoadDescription1(), ",");

        descr1ToUse = new ArrayList<String>();
        while (tokenizer.hasMoreTokens()) {
            descr1ToUse.add(tokenizer.nextToken());
        }

        // !! 1. Step
        if (pfa != null) {
            buildEntityList(pfa);

            // 3. step build associations
            getAssociations(pfa);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("-------------------");
            logger.debug("Entities loaded: " + getEntityList().size());
            logger.debug("-------------------");
        }
    }

    public String getLoadDescription1() {
        return loadDescription1;
    }

    public void setLoadDescription1(String loadDescription1) {
        this.loadDescription1 = loadDescription1;
    }

    public String getLoadDescription2() {
        return loadDescription2;
    }

    public void setLoadDescription2(String loadDescription2) {
        this.loadDescription2 = loadDescription2;
    }

}
