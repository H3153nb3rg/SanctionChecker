/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.queue.db;

import at.jps.sanction.core.queue.memory.MemoryOutputQueue;
import at.jps.sanction.core.util.persistance.DBHelper;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.MessageStatus;
import at.jps.sanction.model.queue.db.DBQueue;

public class DBOutputQueue extends DBQueue<AnalysisResult> {

    final private static int  DB_CACHE_SIZE = 1000;

    private MessageStatus     ourRecordId;

    private String            messageStatusFlag;

    private MemoryOutputQueue internalCache;

    public DBOutputQueue() {
        super();
    }

    @Override
    public boolean addMessage(AnalysisResult message) {

        message.setAnalysisStatus(getOurRecordId());

        DBHelper.saveToDB(message);

        return super.addMessage(message);
    }

    @Override
    public AnalysisResult getNextMessage(boolean wait) {

        if (getInternalCache().isEmpty()) {
            for (AnalysisResult message : DBHelper.getNextAnalysisResults(getOurRecordId(), DB_CACHE_SIZE)) {
                getInternalCache().addMessage(message);
            }
        }

        AnalysisResult message = getInternalCache().getNextMessage(wait);

        return message;
    }

    public MessageStatus getOurRecordId() {
        return ourRecordId;
    }

    public String getMessageStatusFlag() {
        return messageStatusFlag;
    }

    public void setMessageStatusFlag(String messageStatusFlag) {
        this.messageStatusFlag = messageStatusFlag;

        switch (messageStatusFlag) {
            case "D":
                ourRecordId = MessageStatus.DELETED;
                break;
            case "H":
                ourRecordId = MessageStatus.HIT;
                break;
            case "I":
                ourRecordId = MessageStatus.INCONSPICIOUS;
                break;
            case "B":
                ourRecordId = MessageStatus.BLOCK;
                break;
            case "C":
                ourRecordId = MessageStatus.CHECK;
                break;
            case "N":
                ourRecordId = MessageStatus.NEW;
                break;
            case "R":
                ourRecordId = MessageStatus.RELEASE;
                break;
            default:
                ourRecordId = MessageStatus.NEW;
        }
    }

    public MemoryOutputQueue getInternalCache() {

        if (internalCache == null) {
            internalCache = new MemoryOutputQueue("DBCache", DB_CACHE_SIZE);
        }

        return internalCache;
    }

    public void setInternalCache(MemoryOutputQueue internalCache) {
        this.internalCache = internalCache;
    }

}
