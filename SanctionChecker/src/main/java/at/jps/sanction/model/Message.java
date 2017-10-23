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
import java.util.UUID;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Transient;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.TxRunnable;
import com.avaje.ebean.config.ServerConfig;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MSG_TYPE", discriminatorType = DiscriminatorType.STRING, length = 5)
@DiscriminatorValue("PLAIN")
public class Message extends BaseModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6731885812749283091L;

    // private String rawContent;
    @Lob
    private byte[]            rawContent;

    private String            uuid;

    private long              inTime;
    private long              outTime;

    private MessageStatus     messageProcessingStatus;

    // TODO: content should also be persisted
    @Transient
    private MessageContent    messageContent;

    public Message() {
        getUUID();
        setMessageProcessingStatus(MessageStatus.NEW);
    }

    public Message(final String text) {
        setRawContent(text);
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public String getRawContent() {
        return new String(rawContent);
    }

    public byte[] getRawContentAsByteArray() {
        return rawContent;
    }

    public String getUUID() {

        if (uuid == null) {
            uuid = generateUUID();
        }

        return uuid;
    }

    public long getInTime() {
        return inTime;
    }

    public long getOutTime() {
        return outTime;
    }

    public void setRawContent(final String content) {
        rawContent = content.getBytes();
    }

    public void setRawContent(final byte[] content) {
        rawContent = content;
    }

    public void setId(final String id) {
        uuid = id;
    }

    public void setInTime(final long inTime) {
        this.inTime = inTime;
    }

    public void setOutTime(final long outTime) {
        this.outTime = outTime;
    }

    @Override
    public String toString() {

        final StringBuilder msg = new StringBuilder();

        msg.append("ID: ").append(getUUID()).append(System.lineSeparator());

        // parsed details are available:
        if (messageContent != null) {

            msg.append("----").append(System.lineSeparator());

            for (final String field : messageContent.getFieldsAndValues().keySet()) {
                final String content = messageContent.getFieldsAndValues().get(field);

                msg.append(field).append(" : ").append(content).append(System.lineSeparator());
            }

        }
        else {
            msg.append("Content: ").append(getRawContent()).append(System.lineSeparator());
        }

        return msg.toString();
    }

    public static void main(final String[] args) {

        // if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1;packages=at.jps.sanction.core.model.**")) {
        // System.out.println("avaje-ebeanorm-agent not found in classpath - not dynamically loaded");
        // }

        final ServerConfig config = new ServerConfig();
        config.setName("embargo");

        // load configuration from ebean.properties
        // using "pg" as the server name
        config.loadFromProperties();

        final EbeanServer server = EbeanServerFactory.create(config);

        // EbeanServer server = Ebean.getServer("embargo");

        server.execute(new TxRunnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    final Message msg = new Message();
                    msg.setInTime(System.currentTimeMillis());
                    msg.setRawContent("Content" + i);

                    server.save(msg);
                }

            }
        });

        /*
         * EntityManagerFactory factory; factory = Persistence.createEntityManagerFactory("embargo"); EntityManager em = factory.createEntityManager(); // Begin a new local transaction so that we can
         * persist a new entity em.getTransaction().begin(); // read the existing entries TypedQuery<Message> q = em.createQuery("select m from Message m order by m.id", Message.class); // Persons
         * should be empty // do we have entries? boolean createNewEntries = (q.getResultList().size() == 0); // No, so lets create new entries if (createNewEntries) { for (int i = 0; i < 40; i++) {
         * Message message = new Message(); message.setContent("This is a Message " + i); message.getId(); em.persist(message); } } else { for (Message message : q.getResultList()) {
         * System.out.println(message); } Query q1 = em.createQuery("delete from Message"); q1.executeUpdate(); } // Commit the transaction, which will cause the entity to // be stored in the database
         * em.getTransaction().commit();
         */
    }

    public MessageContent getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(final MessageContent messageContent) {
        this.messageContent = messageContent;
    }

    public MessageStatus getMessageProcessingStatus() {
        return messageProcessingStatus;
    }

    public void setMessageProcessingStatus(final MessageStatus messageProcessingStatus) {
        this.messageProcessingStatus = messageProcessingStatus;
    }
}
