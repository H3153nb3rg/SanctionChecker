package at.jps.sanction.core.io.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sanction.model.wl.entities.WL_Name;
import at.jps.sanction.model.wl.entities.WL_Passport;
import at.jps.sanction.model.wl.entities.WL_Relation;

public class DBHelper {

    static final Logger logger = LoggerFactory.getLogger(DBHelper.class);

    Connection          connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(final Connection connection) {
        this.connection = connection;
    }

    // WL Readers

    public boolean read_WL_Entity(final String wl_id, final WL_Entity entity, final boolean transitive) {

        boolean ok = false;

        try {
            final String sql = "SELECT id,created,WL_ID,WL_COMMENT,WL_TYPE,WL_ISSUEDATE,WL_LISTNAME from WL_ENTITY where WL_ID=" + wl_id;

            final Statement query = getConnection().createStatement();

            // stmt = getConnection().prepareStatement(sql);
            // stmt.setString(1, entity.getWL_Id());

            final ResultSet rs = query.executeQuery(sql);
            if (rs.next()) {
                rs.getInt(1);

                if (transitive) {
                    // TODO: details
                    if (entity != null) {

                    }
                }
                ok = true;
            }
            query.close();

        }
        catch (final Exception x) {
            logger.error("DB Operation failed!");
            logger.error("Exception:" + x.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("Exception:", x);
            }
        }
        return ok;
    }

    public boolean delete_WL_Entity(final String wl_id) {

        boolean ok = false;

        try {
            final String sql = "delete from WL_ENTITY where WL_ID='" + wl_id + "'";

            final Statement query = getConnection().createStatement();

            // stmt = getConnection().prepareStatement(sql);
            // stmt.setString(1, entity.getWL_Id());

            ok = query.execute(sql);
            query.close();

        }
        catch (final Exception x) {
            logger.error("DB Operation failed!");
            logger.error("Exception:" + x.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("Exception:", x);
            }
        }
        return ok;
    }

    // WL * writers

    public boolean save_WL_Entity(final WL_Entity entity, final boolean transitive) {
        boolean ok = false;

        // for now we delete if existing...

        final boolean exists = read_WL_Entity(entity.getWL_Id(), null, false);

        if (exists) {
            // delete existing !!!!
            delete_WL_Entity(entity.getWL_Id());

        }

        PreparedStatement stmt = null;
        try {
            final String sql = "INSERT INTO WL_ENTITY (id,created,WL_ID,WL_COMMENT,WL_TYPE,WL_ISSUEDATE,WL_LISTNAME) VALUES ( ?,?,?,?,?,?,?)";

            final int entityId = getUniqueDBId("WL_ENT");

            stmt = getConnection().prepareStatement(sql);

            stmt.setInt(1, entityId);
            stmt.setString(2, getCurrentTimeAsString());
            stmt.setString(3, entity.getWL_Id());
            stmt.setString(4, entity.getComment());
            stmt.setString(5, entity.getEntityType().getText());
            stmt.setString(6, entity.getIssueDate());
            stmt.setString(7, entity.getListName());

            stmt.executeUpdate();
            stmt.close();
            ok = true;

            if (logger.isDebugEnabled()) {
                logger.debug("WL_ENITITY inserted:" + entity.getWL_Id());
            }

            if (transitive) {
                save_Relation(entity, entityId);
                save_LegalBasis(entity, entityId);
                save_Name(entity, entityId);
                save_Document(entity, entityId);
            }
        }
        catch (final Exception x) {
            logger.error("DB Operation failed!");
            logger.error("Exception:" + x.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("Exception:", x);
            }
        }
        return ok;
    }

    public boolean save_LegalBasis(final WL_Entity entity, final int id) {
        boolean ok = false;

        PreparedStatement stmt = null;
        try {
            final String sql = "INSERT INTO WL_LEGALBASIS (id,created,WL_LEGALBASIS,ENTITYID) VALUES ( ?,?,?,?)";

            stmt = getConnection().prepareStatement(sql);

            for (final String lb : entity.getLegalBasises()) {

                final int lbId = getUniqueDBId("WL_LB");

                stmt.setInt(1, lbId);
                stmt.setString(2, getCurrentTimeAsString());
                stmt.setString(3, lb);
                stmt.setInt(4, id);

                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();
            ok = true;
        }
        catch (final Exception x) {
            logger.error("DB Operation failed!");
            logger.error("Exception:" + x.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("Exception:", x);
            }
        }
        return ok;
    }

    public boolean save_Document(final WL_Entity entity, final int id) {
        boolean ok = false;

        PreparedStatement stmt = null;
        try {
            final String sql = "INSERT INTO WL_DOCUMENT (id,created,WL_NUMBER,WL_ISSUEDATE,WL_TYPE,WL_COUNTRY,ENTITYID) VALUES ( ?,?,?,?,?,?,?)";

            stmt = getConnection().prepareStatement(sql);

            for (final WL_Passport pass : entity.getPassports()) {

                final int docId = getUniqueDBId("WL_DOC");

                stmt.setInt(1, docId);
                stmt.setString(2, getCurrentTimeAsString());
                stmt.setString(3, pass.getNumber());
                stmt.setString(4, pass.getIssueDate());
                stmt.setString(5, pass.getType());
                stmt.setString(6, pass.getCountry());
                stmt.setInt(7, id);

                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();
            ok = true;
        }
        catch (final Exception x) {
            logger.error("DB Operation failed!");
            logger.error("Exception:" + x.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("Exception:", x);
            }
        }
        return ok;
    }

    public boolean save_Relation(final WL_Entity entity, final int id) {
        boolean ok = false;

        PreparedStatement stmt = null;
        try {
            final String sql = "INSERT INTO WL_RELATION (id,created,WL_RELTYPE,ENTITYID,WL_REL_WL_ID) VALUES ( ?,?,?,?,?)";

            stmt = getConnection().prepareStatement(sql);

            for (final WL_Relation relation : entity.getRelations()) {

                final int relId = getUniqueDBId("WL_REL");

                stmt.setInt(1, relId);
                stmt.setString(2, getCurrentTimeAsString());
                stmt.setString(3, relation.getDescription());
                stmt.setString(4, relation.getWlid_from());
                stmt.setString(5, relation.getWlid_to());

                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();
            ok = true;
        }
        catch (final Exception x) {
            logger.error("DB Operation failed!");
            logger.error("Exception:" + x.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("Exception:", x);
            }
        }
        return ok;
    }

    public boolean save_Name(final WL_Entity entity, final int id) {
        boolean ok = false;

        PreparedStatement stmt = null;
        try {
            final String sql = "INSERT INTO WL_NAME (id,created,WL_FIRSTNAME,WL_MIDDLENAME,WL_LASTNAME,WL_WHOLENAME,WL_DESC,WL_AKA,WL_WAKA,ENTITYID) VALUES ( ?,?,?,?,?,?,?,?,?,?)";

            stmt = getConnection().prepareStatement(sql);

            for (final WL_Name name : entity.getNames()) {

                final int nameId = getUniqueDBId("WL_NAME");

                stmt.setInt(1, nameId);
                stmt.setString(2, getCurrentTimeAsString());
                stmt.setString(3, name.getFirstName());
                stmt.setString(4, name.getMiddleName());
                stmt.setString(5, name.getLastName());
                stmt.setString(6, name.getWholeName());
                stmt.setString(7, name.getDescription());
                stmt.setString(8, name.isAka() ? "Y" : "N");
                stmt.setString(9, name.isWaka() ? "Y" : "N");
                stmt.setInt(10, id);

                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();
            ok = true;
        }
        catch (final Exception x) {
            logger.error("DB Operation failed!");
            logger.error("Exception:" + x.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("Exception:", x);
            }
        }
        return ok;
    }

    final SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSS");

    String getCurrentTimeAsString() {
        return sdf.format(System.currentTimeMillis()).substring(0, 16);
    }

    int getUniqueDBId(final String sequenceName) {
        int id = -1;

        try {
            final Statement query = getConnection().createStatement();

            // stmt.setString(1, sequenceName);
            // stmt.registerOutParameter(1, Types.INTEGER);

            final ResultSet rs = query.executeQuery("select getUniqueId('" + sequenceName + "') id from dual;");
            if (rs.next()) {
                id = rs.getInt(1);
            }

            query.close();
        }
        catch (final Exception x) {
            logger.error("DB Operation failed!");
            logger.error("Exception:" + x.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("Exception:", x);
            }
        }
        return id;
    }

}
