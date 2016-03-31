package at.jps.sanction.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "HITRES_TYPE")
public class WordHitInfo extends BaseModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1924136493123423017L;

    private String            token;
    private String            fieldName;
    private String            fieldText;
    private int               value;

    // private long analysisresultId;

    public WordHitInfo() {
        super();
    }

    public WordHitInfo(final String token, final String fieldName, final String fieldText, final int value) {
        super();

        this.value = value;
        this.token = token;
        this.fieldText = fieldText;
        this.fieldName = fieldName;

    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldText() {
        return fieldText;
    }

    public void setFieldText(final String fieldText) {
        this.fieldText = fieldText;
    }

    @Override
    public boolean equals(final Object obj) {

        boolean eq = false;

        if (obj != null) {
            if (((WordHitInfo) obj).getToken() != null) {
                eq = getToken().equals(((WordHitInfo) obj).getToken());
                if ((eq) && (((WordHitInfo) obj).getFieldName() != null)) {
                    eq = getFieldName().equals(((WordHitInfo) obj).getFieldName());
                    if ((eq) && (((WordHitInfo) obj).getFieldText() != null)) {
                        eq = getFieldText().equals(((WordHitInfo) obj).getFieldText());
                    }
                }
            }
        }
        return eq;
    }

    @Override
    public String toString() {
        final StringBuilder stringbuilder = new StringBuilder();

        stringbuilder.append("Token: ").append(getToken()).append(" Value: " + getValue()).append(" Fieldname: ").append(getFieldName()).append(" FieldText: " + getFieldText());

        return stringbuilder.toString();
    }

    // public long getAnalysisresultId() {
    // return analysisresultId;
    // }
    //
    // public void setAnalysisresultId(long analysisresultId) {
    // this.analysisresultId = analysisresultId;
    // }

}
