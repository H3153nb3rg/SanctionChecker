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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.SystemUtils;

@Entity
public class AnalysisResult extends BaseModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -678664716203619959L;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MSG_ID", referencedColumnName = "ID")
    private Message           message;

    private String            exceptionInfo;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AR_ID", referencedColumnName = "ID", updatable = false)
    private List<HitResult>   hitlist;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AR_ID", referencedColumnName = "ID", updatable = false)
    private List<WordHitInfo> hitTokenInfoList;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AR_ID", referencedColumnName = "ID", updatable = false)
    private List<ProcessStep> processSteps;

    private String            comment          = "";
    private String            category         = "";

    private long              analysisStartTime;
    private long              analysisStopTime;

    private MessageStatus     analysisStatus;

    public AnalysisResult() {

    }

    public AnalysisResult(final Message message) {
        this.message = message;

    }

    public void addHitResult(final HitResult hitResult) {
        getHitList().add(hitResult);
    }

    public long getAnalysisStartTime() {
        return analysisStartTime;
    }

    public long getAnalysisStopTime() {
        return analysisStopTime;
    }

    public String getComment() {
        return comment;
    }

    public String getException() {
        return exceptionInfo;
    }

    public List<HitResult> getHitList() {
        if (hitlist == null) {
            hitlist = new ArrayList<HitResult>();
        }
        return hitlist;
    }

    public List<WordHitInfo> getHitTokensList() {
        if (hitTokenInfoList == null) {
            hitTokenInfoList = new ArrayList<WordHitInfo>();
        }
        return hitTokenInfoList;
    }

    public Message getMessage() {
        return message;
    }

    public void setAnalysisStartTime(final long analysisStartTime) {
        this.analysisStartTime = analysisStartTime;
    }

    public void setAnalysisStopTime(final long analysisStopTime) {
        this.analysisStopTime = analysisStopTime;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void addComment(final String comment) {
        this.comment += comment;
    }

    public void setException(final Exception exception) {
        exceptionInfo = exception.toString();
    }

    public void setHitTokensList(final List<WordHitInfo> hitTokensList) {
        hitTokenInfoList = hitTokensList;
    }

    public void setMessage(final Message message) {
        this.message = message;
    }

    @Override
    public String toString() {

        final StringBuilder stringbuilder = new StringBuilder();

        stringbuilder.append(message.toString()).append(SystemUtils.LINE_SEPARATOR).append("analysistime:").append(getAnalysisStopTime() - getAnalysisStartTime()).append(" ms")
                .append(SystemUtils.LINE_SEPARATOR);

        if ((hitTokenInfoList != null) && ((hitTokenInfoList.size()) > 0)) {
            stringbuilder.append("Tokenhits: ");
            for (final WordHitInfo token : hitTokenInfoList) {
                stringbuilder.append(token.toString()).append(',');
            }
            stringbuilder.append(SystemUtils.LINE_SEPARATOR);
        }

        if (!getProcessSteps().isEmpty()) {
            stringbuilder.append("----------").append(SystemUtils.LINE_SEPARATOR).append("steps:").append(SystemUtils.LINE_SEPARATOR);

            for (final ProcessStep step : getProcessSteps()) {
                stringbuilder.append(step.toString()).append(SystemUtils.LINE_SEPARATOR);
            }
            stringbuilder.append(SystemUtils.LINE_SEPARATOR);
        }

        if ((getCategory() != null) && (getCategory().length() > 0)) {
            stringbuilder.append("Category: ").append(getCategory()).append(SystemUtils.LINE_SEPARATOR);
        }

        if ((getComment() != null) && (getComment().length() > 0)) {
            stringbuilder.append("Comment: ").append(getComment()).append(SystemUtils.LINE_SEPARATOR);
        }

        return stringbuilder.toString();
    }

    public List<ProcessStep> getProcessSteps() {
        if (processSteps == null) {
            processSteps = new ArrayList<ProcessStep>();
        }
        return processSteps;
    }

    public void setProcessSteps(final List<ProcessStep> processSteps) {
        this.processSteps = processSteps;
    }

    public void addProcessStep(final ProcessStep processStep) {
        getProcessSteps().add(processStep);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public MessageStatus getAnalysisStatus() {
        return analysisStatus;
    }

    public void setAnalysisStatus(final MessageStatus analysisStatus) {
        this.analysisStatus = analysisStatus;
        if (getMessage() != null) {
            getMessage().setMessageProcessingStatus(analysisStatus);
        }
    }

}
