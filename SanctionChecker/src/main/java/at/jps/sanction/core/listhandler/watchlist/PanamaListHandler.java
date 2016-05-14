package at.jps.sanction.core.listhandler.watchlist;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.listhandler.SanctionListHandlerImpl;
import at.jps.sanction.model.wl.entities.WL_Address;
import at.jps.sanction.model.wl.entities.WL_Attribute;
import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sanction.model.wl.entities.WL_Name;
import at.jps.sanction.model.wl.entities.WL_Relation;

public class PanamaListHandler extends SanctionListHandlerImpl {

    static final Logger logger = LoggerFactory.getLogger(PanamaListHandler.class);

    private String      filenameE;
    private String      filenameI;
    private String      filenameO;
    private String      filenameR;
    private String      filenameA;

    private HashMap<String, CSVRecord> readEntityFile(final String filename, final int idcol) {

        final HashMap<String, CSVRecord> entities = new HashMap<String, CSVRecord>();

        Reader in;
        try {
            in = new FileReader(filename);
            final Iterable<CSVRecord> records = CSVFormat.RFC4180.withSkipHeaderRecord().parse(in);

            for (final CSVRecord record : records) {

                System.out.println(filename + " #" + record.get(idcol));

                if (entities.get(record.get(idcol)) == null) {
                    entities.put(record.get(idcol), record);
                }
                else {
                    System.out.println("DUPE #" + record.get(idcol));
                }

                // final StringBuilder line = new StringBuilder();
                // for (int i = 0; i < record.size(); i++) {
                //
                // final String column = record.get(i);
                // line.append(column).append(" | ");

                // }
                // System.out.println(line.toString());
            }
            logger.info("finished reading " + LISTNAME + " file:" + filename + " Records: " + entities.size());
        }
        catch (final Exception e) {

            e.printStackTrace();
        }
        return entities;
    }

    private MultiValuedMap<String, WL_Relation> readRelationsFile(final String filename) {

        final int idcol = 0;
        final MultiValuedMap<String, WL_Relation> relations = new ArrayListValuedHashMap<String, WL_Relation>();

        Reader in;
        try {
            in = new FileReader(filename);
            final Iterable<CSVRecord> records = CSVFormat.RFC4180.withSkipHeaderRecord().parse(in);

            for (final CSVRecord record : records) {

                System.out.println(filename + " #" + record.get(idcol));

                final WL_Relation relation = new WL_Relation();

                relation.setWlid_from(record.get(0));
                relation.setWlid_to(record.get(2));
                relation.setDescription(record.get(1));

                relations.put(record.get(idcol), relation);

                // final StringBuilder line = new StringBuilder();
                // for (int i = 0; i < record.size(); i++) {
                //
                // final String column = record.get(i);
                // line.append(column).append(" | ");

                // }
                // System.out.println(line.toString());
            }
            logger.info("finished reading " + LISTNAME + " file:" + filename + " Records: " + relations.size());
        }
        catch (final Exception e) {

            e.printStackTrace();
        }
        return relations;
    }

    private WL_Entity addAddress(String id, WL_Entity entity, HashMap<String, CSVRecord> addresses) {

        if (addresses.containsKey(id)) {
            final CSVRecord record = addresses.get(id);

            final WL_Address address = new WL_Address();

            address.setLine(record.get(0));
            address.setCountryISO(record.get(3));
            address.setCountry(record.get(4));

            entity.getAddresses().add(address);
        }

        return entity;
    }

    private WL_Entity addrelations(String id, WL_Entity entity, MultiValuedMap<String, WL_Relation> relations) {

        if (relations.containsKey(id)) {
            for (final WL_Relation relation : relations.get(id)) {
                entity.addReleation(relation);
            }
        }

        return entity;
    }

    private void buildEntityList(HashMap<String, CSVRecord> entities, HashMap<String, CSVRecord> officers, HashMap<String, CSVRecord> inter, HashMap<String, CSVRecord> addresses, MultiValuedMap<String, WL_Relation> relations) {

        for (final Map.Entry<String, CSVRecord> record : entities.entrySet()) {

            final WL_Entity entity = new WL_Entity();
            entity.setWL_Id(record.getKey());

            addWLEntry(entity);

            entity.setEntryCategory(WL_Entity.EntryCategory.EMBARGO);
            entity.addLegalBasis(record.getValue().get(20));
            entity.setComment("Enitity " + record.getValue().get(17));
            entity.setIssueDate(record.getValue().get(8));
            entity.setEntityType(WL_Entity.EntityType.ENTITY);

            final WL_Name name = new WL_Name();

            name.setWholeName(record.getValue().get(0));
            name.setAka(false);

            entity.getNames().add(name);

            for (int i = 1; i < 3; i++) {
                final String aka = record.getValue().get(i);
                if ((aka != null) && (aka.length() > 1)) {
                    final WL_Name name2 = new WL_Name();

                    name2.setWholeName(aka);
                    name2.setAka(true);
                    entity.getNames().add(name2);
                }
            }

            final WL_Attribute attributes = new WL_Attribute();

            for (int i = 0; i < record.getValue().size(); i++) {
                attributes.addAttribute("Value " + i, record.getValue().get(i));
            }

            entity.setAttributes(attributes);

            // add Address
            addAddress(record.getKey(), entity, addresses);

            // add relations
            addrelations(record.getKey(), entity, relations);

        }

        for (final Map.Entry<String, CSVRecord> record : officers.entrySet()) {

            final WL_Entity entity = new WL_Entity();
            entity.setWL_Id(record.getKey());

            addWLEntry(entity);

            entity.setEntryCategory(WL_Entity.EntryCategory.EMBARGO);
            entity.addLegalBasis(record.getValue().get(6));
            entity.setComment("Officer " + record.getValue().get(6));
            // entity.setIssueDate(record.getValue().get(8));
            entity.setEntityType(WL_Entity.EntityType.ENTITY); // ?? TODO: might be INDIVIDUAL

            final WL_Name name = new WL_Name();

            name.setWholeName(record.getValue().get(0));
            name.setAka(false);

            entity.getNames().add(name);

            final WL_Attribute attributes = new WL_Attribute();

            for (int i = 0; i < record.getValue().size(); i++) {
                attributes.addAttribute("Value " + i, record.getValue().get(i));
            }

            entity.setAttributes(attributes);

            // add Address
            addAddress(record.getKey(), entity, addresses);

            // add relations
            addrelations(record.getKey(), entity, relations);

        }

        for (final Map.Entry<String, CSVRecord> record : inter.entrySet()) {

            final WL_Entity entity = new WL_Entity();
            entity.setWL_Id(record.getKey());

            addWLEntry(entity);

            entity.setEntryCategory(WL_Entity.EntryCategory.EMBARGO);
            entity.addLegalBasis(record.getValue().get(6));
            entity.setComment("Intermediate " + record.getValue().get(6));
            // entity.setIssueDate(record.getValue().get(8));
            entity.setEntityType(WL_Entity.EntityType.ENTITY); // ?? TODO: might be INDIVIDUAL

            final WL_Name name = new WL_Name();

            name.setWholeName(record.getValue().get(0));
            name.setAka(false);

            entity.getNames().add(name);

            final WL_Attribute attributes = new WL_Attribute();

            for (int i = 0; i < record.getValue().size(); i++) {
                attributes.addAttribute("Value " + i, record.getValue().get(i));
            }

            entity.setAttributes(attributes);

            // add Address
            addAddress(record.getKey(), entity, addresses);

            // add relations
            addrelations(record.getKey(), entity, relations);

        }

    }

    @Override
    public void initialize() {
        final HashMap<String, CSVRecord> entities = readEntityFile(getFilenameE(), 19);
        final HashMap<String, CSVRecord> officers = readEntityFile(getFilenameO(), 5);
        final HashMap<String, CSVRecord> inter = readEntityFile(getFilenameI(), 7);
        final HashMap<String, CSVRecord> addresses = readEntityFile(getFilenameA(), 5);

        final MultiValuedMap<String, WL_Relation> relations = readRelationsFile(getFilenameR());

        buildEntityList(entities, officers, inter, addresses, relations);

        System.out.println(" complete list loaded :" + getEntityList().size());

    }

    final static String LISTNAME = "PANAMALIST";

    // public ArrayList<CityInfo> getCityList() {
    //
    // if (cityList == null) {
    // cityList = new ArrayList<CityInfo>();
    // }
    // return cityList;
    // }
    //
    // public void setCityList(final ArrayList<CityInfo> cityList) {
    // this.cityList = cityList;
    // }

    public static void main(final String[] args) {

        final PanamaListHandler clh = new PanamaListHandler();

        clh.setFilenameE("C:\\Users\\johannes\\Sanctionlists\\panama\\Entities.csv");
        clh.setFilenameA("C:\\Users\\johannes\\Sanctionlists\\panama\\Addresses.csv");
        clh.setFilenameR("C:\\Users\\johannes\\Sanctionlists\\panama\\all_edges.csv");
        clh.setFilenameO("C:\\Users\\johannes\\Sanctionlists\\panama\\Officers.csv");
        clh.setFilenameI("C:\\Users\\johannes\\Sanctionlists\\panama\\Intermediaries.csv");

        clh.initialize();
    }

    public String getFilenameE() {
        return filenameE;
    }

    public void setFilenameE(String filenameE) {
        this.filenameE = filenameE;
    }

    public String getFilenameI() {
        return filenameI;
    }

    public void setFilenameI(String filenameI) {
        this.filenameI = filenameI;
    }

    public String getFilenameO() {
        return filenameO;
    }

    public void setFilenameO(String filenameO) {
        this.filenameO = filenameO;
    }

    public String getFilenameR() {
        return filenameR;
    }

    public void setFilenameR(String filenameR) {
        this.filenameR = filenameR;
    }

    public String getFilenameA() {
        return filenameA;
    }

    public void setFilenameA(String filenameA) {
        this.filenameA = filenameA;
    }

    public static Logger getLogger() {
        return logger;
    }

}
