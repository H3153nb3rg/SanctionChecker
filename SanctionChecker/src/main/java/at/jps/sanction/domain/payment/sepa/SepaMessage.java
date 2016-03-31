/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.payment.sepa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import at.jps.sanction.model.Message;

@Entity
@DiscriminatorValue("SEPA")
public class SepaMessage extends Message {

    private static final long serialVersionUID = -4325196787145588987L;

    private String            businessId;

    public SepaMessage() {
        super();
    }

    public SepaMessage(final String text) {
        super();
        setRawContent(text);
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(final String businessId) {
        this.businessId = businessId;
    }

}
