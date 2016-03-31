package at.jps.sanction.core.util.persistance;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaje.ebean.Ebean;

import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.BaseModel;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageStatus;

public class DBHelper {

    private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);

    public static void saveToDB(final BaseModel model) {

        try {
            model.save();
        }
        catch (final Throwable t) {

            logger.error("DB save failed - " + model.getClass());
            logger.debug("DB save failed - ", t);
        }

    }

    public static Message getNextMessage() {

        Message message = null;
        try {
            message = Ebean.find(Message.class).where().eq("messageProcessingStatus", MessageStatus.NEW).order().asc("inTime").setMaxRows(1).findList().get(0);
        }
        catch (final Exception x) {
            logger.error("DB fetch Message failed ");
            logger.debug("DB fetch Message - ", x);
        }
        return message;

    }

    public static List<AnalysisResult> getNextAnalysisResults(final MessageStatus status, final int maxRecords) {

        List<AnalysisResult> messages = null;
        try {
            messages = Ebean.find(AnalysisResult.class).where().eq("messageProcessingStatus", status).order().asc("analysisStartTime").setMaxRows(maxRecords).findList();
        }
        catch (final Exception x) {
            logger.error("DB fetch Message failed ");
            logger.debug("DB fetch Message - ", x);
        }
        return messages;

    }

    public static <T extends BaseModel> T getNext(final Class<T> klass) {

        return Ebean.find(klass).findList().get(0);

    }

    public static void saveMessage(final Message message) {

        message.save();
        logger.debug("Message - " + message.getUUID() + " persisted");
    }

    public static void saveNewAnalysisResult(final AnalysisResult analsysisResult) {

        // analsysisResult.getMessage().update();
        analsysisResult.save();
        logger.debug("AnalysisResult - " + analsysisResult.getMessage().getUUID() + " persisted");

    }

    // public static void saveNewAnalysisResult(EntityManager em, final AnalysisResult analsysisResult) {
    // try {
    // // Begin a new local transaction so that we can persist a new entity
    // em.getTransaction().begin();
    //
    // em.persist(analsysisResult);
    // // Commit the transaction, which will cause the entity to
    // // be stored in the database
    // em.getTransaction().commit();
    // }
    // catch (Exception x) {
    // if (logger.isErrorEnabled()) {
    // logger.error("persisting message FAILED : " + analsysisResult.getMessage().getId());
    // logger.error("Exception ", x);
    // }
    // }
    //
    // }

}
