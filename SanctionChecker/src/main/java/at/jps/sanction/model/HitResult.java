/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

import org.apache.commons.lang3.SystemUtils;

@Entity
@Inheritance
@DiscriminatorColumn(name = "HITRES_TYPE")
public class HitResult extends BaseModel implements Serializable, Comparable<HitResult> {

    /**
     *
     */
    private static final long serialVersionUID = 7257096991234760208L;

    private String            hitField;
    private String            hitDescripton;

    private int               absolutHit;
    private int               relativeHit;
    private int               phraseHit;

    private String            hitType;                                   // SL,PEP
    private String            entityType;                             // person / company / vessel / etc...

    @Override
    public int compareTo(final HitResult other) {
        int result = 0;

        if (getPhraseHit() < other.getPhraseHit()) {
            result = -1;
        }
        else if (getPhraseHit() == other.getPhraseHit()) {

            if (getAbsolutHit() < other.getAbsolutHit()) {
                result = -1;
            }
            else if (getAbsolutHit() == other.getAbsolutHit()) {

                if (getRelativeHit() < other.getRelativeHit()) {
                    result = -1;
                }
                else if (getRelativeHit() == other.getRelativeHit()) {
                    result = 0;
                }
                else {
                    result = 1;
                }

            }
            else {
                result = 1;
            }
        }
        else {
            result = 1;
        }

        return result;
    }

    public int getAbsolutHit() {
        return absolutHit;
    }

    public String getHitDescripton() {
        return hitDescripton;
    }

    public String getHitField() {
        return hitField;
    }

    public String getHitType() {
        return hitType;
    }

    public int getPhraseHit() {
        return phraseHit;
    }

    public int getRelativeHit() {
        return relativeHit;
    }

    public void setAbsolutHit(final int absolutHit) {
        this.absolutHit = absolutHit;
    }

    public void setHitDescripton(final String hitDescripton) {
        this.hitDescripton = hitDescripton;
    }

    public void setHitField(final String hit) {
        hitField = hit;
    }

    public void setHitType(final String hitType) {
        this.hitType = hitType;
    }

    public void setPhraseHit(final int phraseHit) {
        this.phraseHit = phraseHit;
    }

    public void setRelativeHit(final int relativeHit) {
        this.relativeHit = relativeHit;
    }

    @Override
    public String toString() {

        final StringBuilder content = new StringBuilder(512);

        content.append("Description:      ").append(getHitDescripton()).append(SystemUtils.LINE_SEPARATOR).append("Field : ").append(getHitField()).append(SystemUtils.LINE_SEPARATOR)
                .append("Absolute Value : ").append(getAbsolutHit()).append(SystemUtils.LINE_SEPARATOR).append("Relative Value : ").append(getRelativeHit()).append(SystemUtils.LINE_SEPARATOR)
                .append("Phrase Value   : ").append(getPhraseHit()).append(SystemUtils.LINE_SEPARATOR).append("HitType        : ").append(getHitType()).append(SystemUtils.LINE_SEPARATOR);

        return content.toString();
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    // public long getAnalysisresultId() {
    // return analysisresultId;
    // }
    //
    // public void setAnalysisresultId(long analysisresultId) {
    // this.analysisresultId = analysisresultId;
    // }

    // TBC
}
