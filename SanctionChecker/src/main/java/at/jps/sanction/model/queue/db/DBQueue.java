/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model.queue.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.util.persistance.DBHelper;
import at.jps.sanction.model.BaseModel;
import at.jps.sanction.model.queue.AbstractQueue;

public abstract class DBQueue<X extends BaseModel> extends AbstractQueue<X> {

    static final Logger logger = LoggerFactory.getLogger(DBQueue.class);

    public DBQueue() {
        super();
    }

    protected void initialize() {

    }

    @Override
    public void close() {
    }

    @Override
    public boolean addMessage(final X message) {

        try {
            DBHelper.saveToDB(message);
            // for notification only !!
            super.addMessage(message);

            return true;
        }
        catch (Exception e) {
            logger.error(getName() + ": DB Error" + e.toString());
            if (logger.isDebugEnabled()) logger.debug("Exception: ", e);

            return false;
        }

    }

    @Override
    public X getNextMessage(boolean wait) {

        return null;
    }

    @Override
    public void clear() {
        // DBHelper.deleteFromDB();
    }

    @Override
    public long size() {
        // TODO: get size of relevant objects
        return 0;
    }

    @Override
    public long getRemainingCapacity() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return size() > 0;
    }

}
