package at.jps.sanction.test;

import java.io.IOException;

import org.apache.commons.lang3.SerializationUtils;

import com.leansoft.bigqueue.BigQueueImpl;

import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.Message;

public class QueueTest {

    public static void main(final String[] args) {
        queueTest1();

    }

    public static void queueTest1() {

        try {
            final BigQueueImpl messageQueue1 = new BigQueueImpl("E:/tmp/", "TESTQ1");

            AnalysisResult ana = new AnalysisResult(new Message());

            messageQueue1.enqueue(SerializationUtils.serialize(ana));

            System.out.println("Queuesize1: " + messageQueue1.size());

            final BigQueueImpl messageQueue2 = new BigQueueImpl("E:/tmp/", "TESTQ2");

            Message msg = new Message();

            messageQueue2.enqueue(SerializationUtils.serialize(msg));

            System.out.println("Queuesize2: " + messageQueue2.size());

            for (int i = 0; i < 100; i++) {
                msg = new Message();

                messageQueue2.enqueue(SerializationUtils.serialize(msg));

                ana = new AnalysisResult(new Message());

                ana.setException(new Exception());
                ana.getHitList();

                messageQueue1.enqueue(SerializationUtils.serialize(ana));
            }

            System.out.println("Queuesize1: " + messageQueue1.size());
            System.out.println("Queuesize2: " + messageQueue2.size());

            messageQueue1.close();
            messageQueue2.close();

        }
        catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
