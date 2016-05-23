package at.jps.sanction.model;

import java.io.Serializable;

import javax.persistence.Entity;

import at.jps.sanction.core.util.TimeStamp;

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

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(final String actor) {
        this.actor = actor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
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
        final StringBuilder sb = new StringBuilder();

        sb.append("User: ").append(getActor()).append(" | ").append("descr: ").append(getRemark()).append(" | ").append("time: ").append(TimeStamp.formatAsReadably(timestamp));
        return sb.toString();

    }

}
