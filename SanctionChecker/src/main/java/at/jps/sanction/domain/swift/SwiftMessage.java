/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.swift;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import at.jps.sanction.model.Message;

@Entity
@DiscriminatorValue("SWIFT")
public class SwiftMessage extends Message {

    private static final long serialVersionUID = -7056887127357257079L;

    private String            businessId;

    public SwiftMessage() {
        super();
    }

    public SwiftMessage(final String text) {
        super();
        setRawContent(text);
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    // public static void main(String[] args) {
    //
    // EntityManagerFactory factory;
    //
    // factory = Persistence.createEntityManagerFactory("embargo");
    //
    // EntityManager em = factory.createEntityManager();
    //
    // // Begin a new local transaction so that we can persist a new entity
    // em.getTransaction().begin();
    //
    // // read the existing entries
    // TypedQuery<Message> q = em.createQuery("select m from Message m order by m.id", Message.class);
    // // Persons should be empty
    //
    // // do we have entries?
    // boolean createNewEntries = (q.getResultList().size() == 0);
    //
    // // No, so lets create new entries
    // if (createNewEntries) {
    // for (int i = 0; i < 40; i++) {
    // Message message = new SwiftMessage();
    // message.setContent("This is a Message " + i);
    // message.getId();
    // em.persist(message);
    // }
    // }
    // else {
    // for (Message message : q.getResultList()) {
    // System.out.println(message);
    // }
    // Query q1 = em.createQuery("delete from Message");
    // q1.executeUpdate();
    //
    // }
    //
    // // Commit the transaction, which will cause the entity to
    // // be stored in the database
    // em.getTransaction().commit();
    //
    // }

}
