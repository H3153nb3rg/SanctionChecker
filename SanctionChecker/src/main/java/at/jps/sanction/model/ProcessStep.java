package at.jps.sanction.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ProcessStep extends BaseModel implements Serializable {

    /**
     * 
     */
    private static final long  serialVersionUID = -7291348147778669956L;

    public final static String DEFAULT_USER     = "SYSTEM";

    private long               timestamp;
    private String             actor;
    private String             remark;

    // private long analysisresultId;

    public ProcessStep() {
        actor = DEFAULT_USER;
        timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    // public long getAnalysisresultId() {
    // return analysisresultId;
    // }
    //
    // public void setAnalysisresultId(long analysisresultId) {
    // this.analysisresultId = analysisresultId;
    // }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("User: ").append(getActor()).append("descr: ").append(getRemark()).append("time: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp)));  // TODO:
                                                                                                                                                                                        // Timestamp
                                                                                                                                                                                        // formatting is
                                                                                                                                                                                        // shitty
                                                                                                                                                                                        // implemented!!
        return sb.toString();

    }

}
