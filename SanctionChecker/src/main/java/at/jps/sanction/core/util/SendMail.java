/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {

    private final String from;
    private String[]     to;
    private String[]     cc;
    private final String subject;
    private final String text;
    private final String attachment;
    private final String server;

    public SendMail(final String from, final String to, final String cc, final String subject, final String text, final String server, final String pathToAttachment) {

        final List<String> toList = tokenize(to);
        final List<String> ccList = tokenize(cc);

        this.to = null;
        this.cc = null;

        if (to != null) {
            this.to = new String[toList.size()];
            for (int i = 0; i < toList.size(); i++) {
                this.to[i] = toList.get(i);
            }
        }

        if (cc != null) {
            this.cc = new String[ccList.size()];
            for (int i = 0; i < ccList.size(); i++) {
                this.cc[i] = ccList.get(i);
            }
        }

        this.from = from;
        this.subject = subject;
        this.text = text;
        attachment = pathToAttachment;
        this.server = server;
    }

    public void send() {

        final Properties props = new Properties();
        props.put("mail.smtp.host", server);
        props.put("mail.smtp.port", "25");

        final Session mailSession = Session.getDefaultInstance(props);
        final Message message = new MimeMessage(mailSession);

        InternetAddress fromAddress = null;

        try {
            fromAddress = new InternetAddress(from);

            if (to.length > 0) {
                final InternetAddress toAddresses[] = new InternetAddress[to.length];
                for (int i = 0; i < to.length; i++) {
                    toAddresses[i] = new InternetAddress(to[i]);
                }
                message.setRecipients(RecipientType.TO, toAddresses);
            }

            if (cc.length > 0) {
                final InternetAddress ccAddresses[] = new InternetAddress[cc.length];

                for (int i = 0; i < cc.length; i++) {
                    ccAddresses[i] = new InternetAddress(cc[i]);
                }
                message.setRecipients(RecipientType.CC, ccAddresses);
            }

            message.setFrom(fromAddress);

            message.setSubject(subject);

            // create and fill the first message part
            final MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(text);

            MimeBodyPart mbp2 = null;

            if ((attachment != null) && (attachment.length() > 1)) {
                // create the second message part
                mbp2 = new MimeBodyPart();

                // attach the file to the message
                final FileDataSource fds = new FileDataSource(attachment);
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());
            }

            // create the Multipart and add its parts to it
            final Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            if (mbp2 != null) {
                mp.addBodyPart(mbp2);
            }

            // add the Multipart to the message
            message.setContent(mp);

            // set the Date: header
            message.setSentDate(new Date());

            message.addHeader("X-Priority", "1");
            // message.AddHeaderField("X-Priority","1");
            message.addHeader("X-MSMail-Priority", "High");
            message.addHeader("Importance", "High");

            // send the message
            Transport.send(message);

        }
        catch (final Exception e) {
            e.printStackTrace();
        }

    }

    private List<String> tokenize(String line) {
        if (line == null) {
            line = "";
        }
        final StringTokenizer tokenizer = new StringTokenizer(line, ",;");
        final List<String> list = new ArrayList<String>();

        while (tokenizer.hasMoreTokens()) {
            final String element = tokenizer.nextToken();
            list.add(element);
        }
        return list;
    }
}
