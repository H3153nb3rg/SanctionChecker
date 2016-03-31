package at.jps.sanction.model;

import java.sql.Timestamp;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * Base domain object with Id, version, whenCreated and whenUpdated.
 * <p>
 * Extending Model to enable the 'active record' style.
 * <p>
 * whenCreated and whenUpdated are generally useful for maintaining external search services (like elasticsearch) and audit.
 */
@MappedSuperclass
public abstract class BaseModel extends Model {

    @Id
    Long      id;

    @Version
    Long      version;

    @CreatedTimestamp
    Timestamp whenCreated;

    @UpdatedTimestamp
    Timestamp whenUpdated;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }

    public Timestamp getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(final Timestamp whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Timestamp getWhenUpdated() {
        return whenUpdated;
    }

    public void setWhenUpdated(final Timestamp whenUpdated) {
        this.whenUpdated = whenUpdated;
    }

}
