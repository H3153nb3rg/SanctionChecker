package at.jps.sl.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import at.jps.sanction.core.monitoring.jmx.JMXClient;

public class QueueStatusWindow {

    private JFrame            frmQueueStatus;
    private JTextField        textField;

    private JMXClient         jmxClient;

    static ApplicationContext context = null;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        String configFilename = "SanctionChecker.xml";
        boolean initialized = false;

        if (args.length < 0) {
            configFilename = args[0];
            try {
                context = new FileSystemXmlApplicationContext(configFilename);
                initialized = true;
            }
            catch (Exception x) {
                x.printStackTrace();
                System.out.println(x.toString());
            }
        }
        if (!initialized) {
            context = new ClassPathXmlApplicationContext("SanctionChecker.xml");
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    QueueStatusWindow window = new QueueStatusWindow();
                    window.frmQueueStatus.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public QueueStatusWindow() {
        initialize();
    }

    JPanel           panel  = new JPanel();

    List<JLabel>     labels = null;
    List<JTextField> fields = null;

    private void refreshFields(HashMap<String, Long> queues) {
        int i = 0;
        for (String queueName : queues.keySet()) {
            labels.get(i).setText(queueName);
            fields.get(i).setText(queues.get(queueName).toString());
            i++;
        }
    }

    private void addFields(final int nr) {

        labels = new ArrayList<JLabel>();
        fields = new ArrayList<JTextField>();

        for (int i = 0; i < nr; i++) {
            JLabel lblNewLabel = new JLabel("New label");
            GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
            gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
            gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel.gridx = 0;
            gbc_lblNewLabel.gridy = i;
            panel.add(lblNewLabel, gbc_lblNewLabel);

            labels.add(lblNewLabel);

            textField = new JTextField();
            textField.setEditable(false);
            GridBagConstraints gbc_textField = new GridBagConstraints();
            gbc_textField.fill = GridBagConstraints.HORIZONTAL;
            gbc_textField.insets = new Insets(0, 0, 5, 0);
            gbc_textField.gridx = 1;
            gbc_textField.gridy = i;
            panel.add(textField, gbc_textField);
            textField.setColumns(10);

            fields.add(textField);
        }

    }

    private HashMap<String, Long> getQueueSizes() {

        if (jmxClient == null) {

            jmxClient = (JMXClient) context.getBean("JMXClientAdapter");

            // jmxClient = new JMXClient();

            jmxClient.connect();
        }

        return jmxClient.getQueueSizes();

        /*
         * HashMap<String, Long> map = new HashMap<String, Long>(10); map.put("Queue1", new Long(123)); map.put("Queue2", new Long(345)); map.put("Queue3", new Long(234324)); return map;
         */
    }

    private void refreshView() {
        HashMap<String, Long> queues = getQueueSizes();

        if (queues.size() > 0) {
            if (labels == null) {
                addFields(queues.size());
            }

            refreshFields(queues);

        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frmQueueStatus = new JFrame();
        frmQueueStatus.setTitle("Queue Status");
        frmQueueStatus.setBounds(100, 100, 450, 300);
        frmQueueStatus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        frmQueueStatus.getContentPane().add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();

        gbl_panel.columnWidths = new int[] { 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        panel.setLayout(gbl_panel);

        int delay = 10000; // milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                refreshView();

            }
        };
        new Timer(delay, taskPerformer).start();

        /*
         * JLabel lblNewLabel = new JLabel("New label"); GridBagConstraints gbc_lblNewLabel = new GridBagConstraints(); gbc_lblNewLabel.anchor = GridBagConstraints.WEST; gbc_lblNewLabel.insets = new
         * Insets(0, 0, 5, 5); gbc_lblNewLabel.gridx = 0; gbc_lblNewLabel.gridy = 0; panel.add(lblNewLabel, gbc_lblNewLabel); labels.add(lblNewLabel); textField = new JTextField();
         * textField.setEditable(false); GridBagConstraints gbc_textField = new GridBagConstraints(); gbc_textField.fill = GridBagConstraints.HORIZONTAL; gbc_textField.insets = new Insets(0, 0, 5, 0);
         * gbc_textField.gridx = 1; gbc_textField.gridy = 0; panel.add(textField, gbc_textField); textField.setColumns(10); lblNewLabel = new JLabel("New label"); gbc_lblNewLabel_1 = new
         * GridBagConstraints(); gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST; gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5); gbc_lblNewLabel_1.gridx = 0; gbc_lblNewLabel_1.gridy = 1;
         * panel.add(lblNewLabel, gbc_lblNewLabel_1); labels.add(lblNewLabel); textField = new JTextField(); textField.setEditable(false); gbc_textField_1 = new GridBagConstraints();
         * gbc_textField_1.fill = GridBagConstraints.HORIZONTAL; gbc_textField_1.insets = new Insets(0, 0, 5, 0); gbc_textField_1.gridx = 1; gbc_textField_1.gridy = 1; panel.add(textField,
         * gbc_textField_1); textField.setColumns(10);
         */
    }

}
