package at.jps.sanction.core.util.persistance;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.model.AnalysisResult;

public class DBHelper {

    private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);

    public static void saveNewAnalysisResult(EntityManager em, final AnalysisResult analsysisResult) {
        try {
            // Begin a new local transaction so that we can persist a new entity
            em.getTransaction().begin();

            em.persist(analsysisResult);
            // Commit the transaction, which will cause the entity to
            // be stored in the database
            em.getTransaction().commit();
        }
        catch (Exception x) {
            if (logger.isErrorEnabled()) {
                logger.error("persisting message FAILED : " + analsysisResult.getMessage().getId());
                logger.error("Exception ", x);
            }
        }

    }

}
