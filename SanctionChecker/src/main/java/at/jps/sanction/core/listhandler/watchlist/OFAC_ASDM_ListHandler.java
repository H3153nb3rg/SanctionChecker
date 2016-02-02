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
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.list.ofac.SdnList;
import at.jps.sanction.core.list.ofac.ASDM.DistinctPartySchemaType;
import at.jps.sanction.core.list.ofac.ASDM.DistinctPartySchemaType.Profile;
import at.jps.sanction.core.list.ofac.ASDM.DocumentedNameSchemaType;
import at.jps.sanction.core.list.ofac.ASDM.IdentitySchemaType;
import at.jps.sanction.core.list.ofac.ASDM.IdentitySchemaType.Alias;
import at.jps.sanction.core.list.ofac.ASDM.ProfileRelationshipSchemaType;
import at.jps.sanction.core.list.ofac.ASDM.ReferenceValueSetsSchemaType;
import at.jps.sanction.core.list.ofac.ASDM.ReferenceValueSetsSchemaType.AliasTypeValues.AliasType;
import at.jps.sanction.core.list.ofac.ASDM.ReferenceValueSetsSchemaType.LegalBasisValues.LegalBasis;
import at.jps.sanction.core.list.ofac.ASDM.ReferenceValueSetsSchemaType.NamePartTypeValues.NamePartType;
import at.jps.sanction.core.list.ofac.ASDM.ReferenceValueSetsSchemaType.PartySubTypeValues.PartySubType;
import at.jps.sanction.core.list.ofac.ASDM.ReferenceValueSetsSchemaType.PartyTypeValues.PartyType;
import at.jps.sanction.core.list.ofac.ASDM.Sanctions;
import at.jps.sanction.core.list.ofac.ASDM.SanctionsEntrySchemaType;
import at.jps.sanction.core.list.ofac.ASDM.SanctionsEntrySchemaType.EntryEvent;
import at.jps.sanction.core.listhandler.SanctionListHandlerImpl;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sanction.model.sl.entities.WL_Name;

public class OFAC_ASDM_ListHandler extends SanctionListHandlerImpl {

    private final static String LISTNAME  = "OFACList";
    static final Logger         logger    = LoggerFactory.getLogger(OFAC_ASDM_ListHandler.class);

    Sanctions                   sanctions = null;

    String getAliasType(final BigInteger aliasType) {
        String aliastype = "";

        for (final AliasType atv : sanctions.getReferenceValueSets().getAliasTypeValues().getAliasType()) {

            if (atv.getID().equals(aliasType)) {
                aliastype = atv.getValue();
                break;
            }
        }

        return aliastype;
    }

    String getLegalBasisValue(final BigInteger id) {
        String lbtxt = "";

        for (final LegalBasis lb : sanctions.getReferenceValueSets().getLegalBasisValues().getLegalBasis()) {
            if (lb.getID().equals(id)) {
                lbtxt = lb.getLegalBasisShortRef();
                break;
            }
        }

        return lbtxt;
    }

    String getNamePartType(final BigInteger id) {
        String np = "";
        for (final NamePartType npt : sanctions.getReferenceValueSets().getNamePartTypeValues().getNamePartType()) {

            if (npt.getID().equals(id)) {
                np = npt.getValue();
                break;
            }
        }
        return np;
    }

    String getNameTypeDesc(final IdentitySchemaType ide, final BigInteger id) {
        final String ids = "";

        for (final IdentitySchemaType.NamePartGroups.MasterNamePartGroup mnpg : ide.getNamePartGroups().getMasterNamePartGroup()) {

            for (final IdentitySchemaType.NamePartGroups.MasterNamePartGroup.NamePartGroup npg : mnpg.getNamePartGroup()) {

                // npg.getNamePartTypeID() for global Info

                if (npg.getID().equals(id)) {
                    return getNamePartType(npg.getNamePartTypeID());
                }
            }
        }

        return ids;
    }

    String getPartyType(final BigInteger partyTypeId) {

        String partyTypeDesc = "";

        for (final PartyType partyType : sanctions.getReferenceValueSets().getPartyTypeValues().getPartyType()) {
            if (partyType.getID().equals(partyTypeId)) {
                partyTypeDesc = partyType.getValue();
            }
        }
        return partyTypeDesc;

    }

    String getPartyTypeFromSubType(final BigInteger SubtypeId) {
        String partyTypeDesc = "";

        for (final PartySubType pst : sanctions.getReferenceValueSets().getPartySubTypeValues().getPartySubType()) {

            if (pst.getID().equals(SubtypeId)) {
                partyTypeDesc = getPartyType(pst.getPartyTypeID());
                break;
            }
        }

        return partyTypeDesc;
    }

    String getPartySubType(final BigInteger aliasType) {
        String aliastype = "";

        for (final PartySubType pst : sanctions.getReferenceValueSets().getPartySubTypeValues().getPartySubType()) {

            if (pst.getID().equals(aliasType)) {
                aliastype = pst.getValue();
                break;
            }
        }

        return aliastype;
    }

    SanctionsEntrySchemaType getSanctionEntryForProfil(final BigInteger profileID) {
        SanctionsEntrySchemaType rsest = null;
        for (final SanctionsEntrySchemaType sest : sanctions.getSanctionsEntries().getSanctionsEntry()) {
            if (sest.getProfileID().equals(profileID)) {
                rsest = sest;
                break;
            }
        }

        return rsest;
    }

    public static void main(final String[] args) {

        OFAC_ASDM_ListHandler ofac_handler = new OFAC_ASDM_ListHandler();

        final Sanctions sanctions = ofac_handler.readList("E:/Workspace/SanctionList/SLHandler/src/lists/ofac-sdn_advanced.xml");
        System.out.println("size: " + sanctions.getSanctionsEntries().getSanctionsEntry().size());

        ofac_handler.printEntries();
    }

    void printEntries() {
        for (final DistinctPartySchemaType dpst : sanctions.getDistinctParties().getDistinctParty()) {

            for (final Profile prof : dpst.getProfile()) {
                String lbt = "( ";
                final SanctionsEntrySchemaType st = getSanctionEntryForProfil(prof.getID());
                for (final EntryEvent ee : st.getEntryEvent()) {
                    lbt += getLegalBasisValue(ee.getLegalBasisID()) + ";";

                }
                lbt += ")";
                // for (SanctionsMeasure sm : st.getSanctionsMeasure())
                // {
                // sm.
                // }

                for (final IdentitySchemaType ide : prof.getIdentity()) {
                    for (final Alias alias : ide.getAlias()) {
                        System.out.println("-----");
                        for (final DocumentedNameSchemaType dnst : alias.getDocumentedName()) {
                            for (final DocumentedNameSchemaType.DocumentedNamePart dnp : dnst.getDocumentedNamePart()) {

                                System.out.println("Profile id: " + prof.getID() + " Party SubType: " + getPartySubType(prof.getPartySubTypeID()) + " PartyType: "
                                        + getPartyTypeFromSubType(prof.getPartySubTypeID()) + " AliasType:" + getAliasType(alias.getAliasTypeID()) + " Primary: "
                                        + (alias.isPrimary() ? "true" : "false") + " LQ: " + (alias.isLowQuality() ? "true" : "false  ")
                                        + getNameTypeDesc(ide, dnp.getNamePartValue().getNamePartGroupID()) + " = " + dnp.getNamePartValue().getValue() + " " + lbt);
                            }
                        }
                    }
                }
            }
        }
    }

    public Sanctions readList(final String filename) {

        logger.info("start reading " + getListName() + " file:" + filename);

        Reader reader = null;
        try {

            final JAXBContext ctx = JAXBContext.newInstance(Sanctions.class);

            // Unmarshaller um = ctx.createUnmarshaller();
            // PFA pfa = (PFA) um.unmarshal(new File(properties
            // .getProperty("list.inputfile")));

            final Unmarshaller unmarshaller = ctx.createUnmarshaller();
            // unmarshaller.setProperty(Unmarshaller., "UTF-8");

            final InputStream inputStream = new FileInputStream(filename);
            reader = new InputStreamReader(inputStream, "UTF-8");

            // sdnList = (SdnList) unmarshaller.unmarshal(reader);
            sanctions = (Sanctions) unmarshaller.unmarshal(reader);

            logger.info("finished reading " + getListName() + " file:" + filename);
            logger.info(getListName() + "Records read: " + sanctions.getSanctionsEntries().getSanctionsEntry().size());

            final String datum = String.format("%04d%02d%02d", sanctions.getDateOfIssue().getYear().getValue().intValue(), sanctions.getDateOfIssue().getMonth().getValue().intValue(),
                    sanctions.getDateOfIssue().getDay().getValue().intValue());

            logger.info("Date of Issue: " + getListName() + " : " + datum);

        }
        catch (final Exception x) {
            logger.error("JAXB Problem (" + getListName() + ")- reading failed: " + x.toString());
            logger.debug("Exception : ", x);
        }

        return sanctions;
    }

    public void writeList(final String filename, final SdnList sdnList) {

        try {
            final JAXBContext ctx = JAXBContext.newInstance(SdnList.class);
            final Marshaller marshaller = ctx.createMarshaller();

            marshaller.marshal(sdnList, new File(filename));
        }
        catch (final Exception x) {
            logger.error("JAXB Problem (" + getListName() + ")- storing failed: " + x.toString());
            logger.debug("Exception : ", x);
        }
    }

    private String getRelationshipType(final Sanctions sanctions, BigInteger typid) {
        String typeText = "";
        for (ReferenceValueSetsSchemaType.RelationTypeValues.RelationType rt : sanctions.getReferenceValueSets().getRelationTypeValues().getRelationType()) {
            if (rt.getID().equals(typid)) {
                typeText = rt.getValue();
                break;
            }
        }
        return typeText;
    }

    private void addRelations(final Sanctions sanctions, WL_Entity entity) {

        for (ProfileRelationshipSchemaType prt : sanctions.getProfileRelationships().getProfileRelationship()) {

            if (prt.getFromProfileID().toString().equals(entity.getWL_Id())) {

                entity.addReleation(prt.getToProfileID().toString(), getRelationshipType(sanctions, prt.getRelationTypeID()));

            }
        }
    }

    private void buildEntityList(final Sanctions sanctions) {

        for (final DistinctPartySchemaType dpst : sanctions.getDistinctParties().getDistinctParty()) {

            for (final Profile prof : dpst.getProfile()) {

                final WL_Entity entity = new WL_Entity();

                entity.setWL_Id(prof.getID().toString());
                entity.setType(getPartyTypeFromSubType(prof.getPartySubTypeID()));

                addWLEntry(entity);

                addRelations(sanctions, entity);

                // String lbt = "( ";
                final SanctionsEntrySchemaType st = getSanctionEntryForProfil(prof.getID());
                for (final EntryEvent ee : st.getEntryEvent()) {

                    // lbt += getLegalBasisValue(ee.getLegalBasisID()) + ";";

                    entity.addLegalBasis(getLegalBasisValue(ee.getLegalBasisID()));

                }
                // lbt += ")";

                // entity.setLegalBasis(lbt);

                for (final IdentitySchemaType ide : prof.getIdentity()) {
                    for (final Alias alias : ide.getAlias()) {

                        final WL_Name name = new WL_Name();

                        name.setAka(!alias.isPrimary());
                        name.setWaka(alias.isLowQuality());

                        if ((!alias.isLowQuality()) || (isLoadWeak())) {
                            for (final DocumentedNameSchemaType dnst : alias.getDocumentedName()) {
                                for (final DocumentedNameSchemaType.DocumentedNamePart dnp : dnst.getDocumentedNamePart()) {

                                    // TODO: check for waka / vessels etc...

                                    name.setDescription(getNameTypeDesc(ide, dnp.getNamePartValue().getNamePartGroupID()));

                                    if (name.getDescription().equals("Last Name")) {
                                        name.setLastName(dnp.getNamePartValue().getValue());
                                    }
                                    else if (name.getDescription().equals("First Name")) {
                                        name.setFirstName(dnp.getNamePartValue().getValue());
                                    }
                                    else if (name.getDescription().equals("Middle Name")) {
                                        name.setMiddleName(dnp.getNamePartValue().getValue());
                                    }

                                    name.addToWholeName(dnp.getNamePartValue().getValue());

                                }
                            }

                            if (!entity.getNames().contains(name)) {
                                entity.getNames().add(name);
                            }
                        }
                        else {
                            logger.debug("Skipped WAKA for Entity: ", entity.getWL_Id());
                        }
                        // System.out.println("Name: " + name.getWholeName());
                    }
                    // System.out.println("-------------");
                }
            }
        }

    }

    @Override
    public void initialize() {
        super.initialize();

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

                // loadWeak = Boolean.parseBoolean(properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".loadWeak", "false"));
                // loadnonPrimary = Boolean.parseBoolean(properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + name + ".loadNonPrimary", "true"));

            }
            catch (final Exception e) {
                logger.error("Download (" + LISTNAME + ") - from URL: " + getUrl() + " failed!");
                if (logger.isDebugEnabled()) {
                    logger.debug("Exception : ", e);
                }
            }
        }

        buildEntityList(readList(filename));

        archiveFile(filename, getHistPath(), getListName());

        sanctions = null; // !! list is GCed

    }

}
