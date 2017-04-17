/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

import at.jps.sanction.model.Message;

@Entity
@Inheritance
public class SanctionMessage extends Message {

    private static final long serialVersionUID = 4055207318443230327L;

    private String            businessId;

    public SanctionMessage() {
        super();
    }

    public SanctionMessage(final String text) {
        super();
        setRawContent(text);
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(final String businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        final StringBuilder msg = new StringBuilder();

        if (getBusinessId() != null) {
            msg.append("Business ID: ").append(getBusinessId()).append(System.lineSeparator());
        }
        msg.append(super.toString());

        return msg.toString();
    }

}
