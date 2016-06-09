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
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.list.dj.Associate;
import at.jps.sanction.core.list.dj.DateDetails;
import at.jps.sanction.core.list.dj.DateDetails.Date.DateValue;
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
import at.jps.sanction.core.util.country.CountryCodeConverter;
import at.jps.sanction.model.wl.entities.WL_Address;
import at.jps.sanction.model.wl.entities.WL_Attribute;
import at.jps.sanction.model.wl.entities.WL_BirthInfo;
import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sanction.model.wl.entities.WL_Name;
import at.jps.sanction.model.wl.entities.WL_Passport;

public class DJListHandler extends SanctionListHandlerImpl {

    private static String           LISTNAME = "DowJones";
    static final Logger             logger   = LoggerFactory.getLogger(DJListHandler.class);

    // private List<String> descr1ToUse;

    private String                  loadDescription1;
    private String                  loadDescription2;
    private HashMap<String, String> monthTxt;

    {
        monthTxt = new HashMap<String, String>();

        monthTxt.put("Jan", "01");
        monthTxt.put("Feb", "02");
        monthTxt.put("Mar", "03");
        monthTxt.put("Apr", "04");
        monthTxt.put("May", "05");
        monthTxt.put("Jun", "06");
        monthTxt.put("Jul", "07");
        monthTxt.put("Aug", "08");
        monthTxt.put("Sep", "09");
        monthTxt.put("Oct", "10");
        monthTxt.put("Nov", "11");
        monthTxt.put("Dez", "12");

    }

    public static void main(final String[] args) {

        final DJListHandler djh = new DJListHandler();

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

    private void addSourceDescription(final WL_Entity entity, final List<SourceDescription> sourceDescriptions) {
        for (final SourceDescription sourceDescription : sourceDescriptions) {
            for (final Source source : sourceDescription.getSource()) {
                if (source.getName().startsWith("http")) {
                    entity.addInformationUrl(source.getName());
                }
                else {
                    entity.addLegalBasis(source.getName());
                }
            }
        }
    }

    private void addSanctionReferenzes(final PFA pfa, final WL_Entity entity, final List<SanctionsReferences> sanctionreferences) {
        for (final SanctionsReferences sanctionReference : sanctionreferences) {

            for (final SanctionsReferences.Reference reference : sanctionReference.getReference()) {

                String entry = getSanctionreference(pfa, reference.getValue());

                if ((reference.getSinceDay() != null) && (reference.getSinceDay().length() > 0)) {
                    entry += " (" + reference.getSinceDay() + "." + reference.getSinceMonth() + "." + reference.getSinceYear() + ")";
                }

                entity.addLegalBasis(entry); // todo
            }
        }
    }

    private String addDescriptions(final PFA pfa, final WL_Entity entity, final List<Descriptions> descriptions) {  // DOR: at the moment onnly for descr1 filtering!!

        String id = "";

        for (final Descriptions descrs : descriptions) {
            for (final Description descr : descrs.getDescription()) {
                id = descr.getDescription1();
                String descr1 = getDescription1(pfa, id);

                descr1 += " | " + getDescription2(pfa, descr.getDescription1(), descr.getDescription2());

                descr1 += " | " + getDescription3(pfa, descr.getDescription3());

                // entity.setEntityType(descr1); // PEP / RCA / SIP ??

                WL_Attribute wla = entity.getAttributes();

                if (wla == null) {
                    wla = new WL_Attribute();

                    entity.setAttributes(wla);
                }

                wla.addAttribute("Description", descr1);

                // System.out.println(" description1: " + descr1);

                break;
            }
        }
        return id;
    }

    private void addName(final WL_Entity entity, final List<NameDetails> nameDatails) {
        for (final NameDetails nameDetails : nameDatails) {
            for (final NameDetails.Name pfaname : nameDetails.getName()) {

                for (final NameDetails.Name.NameValue nameValue : pfaname.getNameValue()) {
                    final WL_Name name = new WL_Name();

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

    private void addAddress(final PFA pfa, final WL_Entity entity, final List<PFA.Records.Person.Address> addresses) {
        for (final PFA.Records.Person.Address pfaAddress : addresses) {
            final WL_Address address = new WL_Address();
            entity.getAddresses().add(address);

            final String ISOCtry = CountryCodeConverter.convert(4, 1, getCountryName(pfa, pfaAddress.getAddressCountry()));
            // System.out.println("ISOCTRY: " + ISOCtry);

            address.setCountry(ISOCtry);
            address.setCountryISO(ISOCtry);

            address.setPlace(pfaAddress.getAddressCity());
            address.setLine(pfaAddress.getAddressLine());
        }
    }

    private void addCompanyAddress(final PFA pfa, final WL_Entity entity, final List<PFA.Records.Entity.CompanyDetails> companyDetails) {
        for (final PFA.Records.Entity.CompanyDetails cd : companyDetails) {
            final WL_Address address = new WL_Address();
            entity.getAddresses().add(address);

            final String ISOCtry = CountryCodeConverter.convert(4, 1, getCountryName(pfa, cd.getAddressCountry()));
            // System.out.println("ISOCTRY: " + ISOCtry);

            address.setCountry(ISOCtry);
            address.setCountryISO(ISOCtry);

            address.setPlace(cd.getAddressCity());
            address.setLine(cd.getAddressLine());
        }
    }

    private void addBirthInfo(final PFA pfa, final WL_Entity entity, List<DateDetails> dateDetails, List<PFA.Records.Person.BirthPlace> birthPlaces) {
        for (final DateDetails dt : dateDetails) {
            for (final DateDetails.Date d : dt.getDate()) {
                if (d.getDateType().equalsIgnoreCase("Date of Birth")) {
                    // steht so drin !?

                    for (final DateValue dv : d.getDateValue()) {
                        final WL_BirthInfo birthInfo = new WL_BirthInfo();

                        birthInfo.setDay(dv.getDay());
                        birthInfo.setMonth(monthTxt.get(dv.getMonth()));
                        birthInfo.setYear(dv.getYear());

                        entity.setBirthday(birthInfo);

                        for (final PFA.Records.Person.BirthPlace birthplace : birthPlaces) {
                            for (final PFA.Records.Person.BirthPlace.Place place : birthplace.getPlace()) {

                                String countryName = place.getName();

                                final int pos = countryName.lastIndexOf(",");

                                if (pos > -1) {
                                    countryName = countryName.substring(pos + 1);
                                    birthInfo.setPlace(place.getName().substring(0, pos));
                                }

                                final String ISOCtry = CountryCodeConverter.convert(4, 1, countryName);
                                if (ISOCtry != null) {
                                    birthInfo.setCountry(ISOCtry);
                                }

                                System.out.println("place:" + birthInfo.getPlace() + " - " + birthInfo.getCountry());
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    private void addIDs(final WL_Entity entity, final List<IDNumberTypes> numbers) {
        for (final IDNumberTypes ids : numbers) {
            for (final IDNumberTypes.ID id : ids.getID()) {
                for (final IDNumberTypes.ID.IDValue value : id.getIDValue()) {

                    final WL_Passport passport = new WL_Passport();
                    passport.setType(id.getIDType());
                    passport.setNumber(value.getValue());

                    entity.getPassports().add(passport);
                }
            }
        }
    }

    private String getDescription1(final PFA pfa, final String descriptionId1) {
        String value = "";
        for (final PFA.Description1List list1 : pfa.getDescription1List()) {
            for (final Description1List.Description1Name name1 : list1.getDescription1Name()) {
                if (name1.getDescription1Id().equals(descriptionId1)) {
                    name1.getRecordType();
                    value = name1.getValue();
                    return value;
                }
            }
        }
        return value;
    }

    private String getDescription2(final PFA pfa, final String descriptionId1, final String descriptionId2) {
        String value = "";
        for (final PFA.Description2List list2 : pfa.getDescription2List()) {
            for (final Description2List.Description2Name name2 : list2.getDescription2Name()) {
                if ((name2.getDescription1Id().equals(descriptionId1)) && (name2.getDescription2Id().equals(descriptionId2))) {
                    value = name2.getValue();
                    return value;
                }
            }
        }
        return value;
    }

    private String getDescription3(final PFA pfa, final String descriptionId3) {
        String value = "";
        for (final PFA.Description3List list3 : pfa.getDescription3List()) {
            for (final Description3List.Description3Name name3 : list3.getDescription3Name()) {
                if (name3.getDescription3Id().equals(descriptionId3)) {
                    value = name3.getValue();
                    return value;
                }
            }
        }
        return value;
    }

    private String getSanctionreference(final PFA pfa, final String code) {
        String srlName = "";
        for (final PFA.SanctionsReferencesList srl : pfa.getSanctionsReferencesList()) {
            for (final PFA.SanctionsReferencesList.ReferenceName srln : srl.getReferenceName()) {
                if (srln.getCode().equals(code)) {
                    srlName = srln.getName() + " [" + srln.getStatus() + "]";
                    break;
                }
            }
        }
        return srlName;
    }

    private String getCountryName(final PFA pfa, final String countryCode) {
        String countryName = null;

        for (final PFA.CountryList c : pfa.getCountryList()) {
            for (final PFA.CountryList.CountryName cn : c.getCountryName()) {

                if (cn.getCode().equals(countryCode)) {
                    countryName = cn.getName();
                    break;
                }
            }
        }
        return countryName;
    }

    private String getAssociationType(final PFA pfa, final String code) {
        String type = "";

        for (final PFA.RelationshipList rl : pfa.getRelationshipList()) {
            for (final Relationship r : rl.getRelationship()) {
                if (r.getCode().equals(code)) {
                    type = r.getName();
                    break;
                }
            }
        }

        return type;
    }

    void getAssociations(final PFA pfa, final List<Associate> associates, final String id) {

        final WL_Entity entity = getEntityById(id);

        if (entity != null) {

            for (final Associate asso : associates) {

                entity.addReleation(asso.getId(), getAssociationType(pfa, asso.getCode()));
                asso.getEx();
            }
        }

    }

    void getAssociations(final PFA pfa) {
        for (final PFA.Associations assosiations : pfa.getAssociations()) {
            for (final PFA.Associations.SpecialEntity specialEntity : assosiations.getSpecialEntity()) {
                getAssociations(pfa, specialEntity.getAssociate(), specialEntity.getId());
            }

            for (final PFA.Associations.PublicFigure publicfigure : assosiations.getPublicFigure()) {
                getAssociations(pfa, publicfigure.getAssociate(), publicfigure.getId());
            }
        }
    }

    public void buildEntityList(final PFA pfa) {

        for (final PFA.Records records : pfa.getRecords()) {

            for (final PFA.Records.Person pfaPerson : records.getPerson()) {

                // add description filter !!

                if ((pfaPerson.getActiveStatus() != null) && (pfaPerson.getActiveStatus().equalsIgnoreCase("Active"))) {
                    final WL_Entity entity = new WL_Entity();

                    final String descr1 = addDescriptions(pfa, entity, pfaPerson.getDescriptions());

                    if ((loadDescription1 == null) || (loadDescription1.length() == 0) || loadDescription1.contains(descr1)) {

                        entity.setEntryCategory(getListCategory().equalsIgnoreCase("PEP") ? WL_Entity.EntryCategory.PEP : WL_Entity.EntryCategory.EMBARGO);

                        entity.setEntityType(WL_Entity.EntityType.INDIVIDUAL);
                        entity.setWL_Id(pfaPerson.getId());
                        entity.setIssueDate(pfaPerson.getDate());
                        entity.setComment(pfaPerson.getProfileNotes());

                        addWLEntry(entity);

                        addBirthInfo(pfa, entity, pfaPerson.getDateDetails(), pfaPerson.getBirthPlace());

                        addSanctionReferenzes(pfa, entity, pfaPerson.getSanctionsReferences());

                        addSourceDescription(entity, pfaPerson.getSourceDescription());
                        addName(entity, pfaPerson.getNameDetails());
                        addAddress(pfa, entity, pfaPerson.getAddress());

                        addIDs(entity, pfaPerson.getIDNumberTypes());
                    }
                }
            }

            for (final PFA.Records.Entity pfaEntity : records.getEntity()) {

                if ((pfaEntity.getActiveStatus() != null) && (pfaEntity.getActiveStatus().equalsIgnoreCase("Active"))) {

                    final WL_Entity entity = new WL_Entity();

                    final String descr1 = addDescriptions(pfa, entity, pfaEntity.getDescriptions());

                    if ((loadDescription1 == null) || (loadDescription1.length() == 0) || loadDescription1.contains(descr1)) {

                        entity.setEntryCategory(getListCategory().equalsIgnoreCase("PEP") ? WL_Entity.EntryCategory.PEP : WL_Entity.EntryCategory.EMBARGO);

                        entity.setEntityType(WL_Entity.EntityType.ENTITY);
                        entity.setWL_Id(pfaEntity.getId());
                        entity.setIssueDate(pfaEntity.getDate());
                        entity.setComment(pfaEntity.getProfileNotes());

                        addWLEntry(entity);

                        addCompanyAddress(pfa, entity, pfaEntity.getCompanyDetails());

                        addSanctionReferenzes(pfa, entity, pfaEntity.getSanctionsReferences());

                        addSourceDescription(entity, pfaEntity.getSourceDescription());
                        addName(entity, pfaEntity.getNameDetails());

                        addIDs(entity, pfaEntity.getIDNumberTypes());

                        if ((pfaEntity.getVesselDetails() != null) && (pfaEntity.getVesselDetails().size() > 0)) {
                            entity.setEntityType(WL_Entity.EntityType.TRANSPORT);

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

        final PFA pfa = readList(getFilename());

        archiveFile(getFilename(), getHistPath(), getListName());

        // final StringTokenizer tokenizer = new StringTokenizer(getLoadDescription1(), ",");
        //
        // descr1ToUse = new ArrayList<String>();
        // while (tokenizer.hasMoreTokens()) {
        // descr1ToUse.add(tokenizer.nextToken());
        // }

        try {
            // !! 1. Step
            if (pfa != null) {
                buildEntityList(pfa);

                // 3. step build associations
                getAssociations(pfa);
            }
        }
        catch (final Exception e) {
            logger.error("parsing list failed!!!!");
            if (logger.isDebugEnabled()) {
                logger.debug("Exception : ", e);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("-------------------");
            logger.info("Entities loaded: " + getEntityList().size());
            logger.info("-------------------");
        }
    }

    public String getLoadDescription1() {
        return loadDescription1;
    }

    public void setLoadDescription1(final String loadDescription1) {
        this.loadDescription1 = loadDescription1;
    }

    public String getLoadDescription2() {
        return loadDescription2;
    }

    public void setLoadDescription2(final String loadDescription2) {
        this.loadDescription2 = loadDescription2;
    }

}
