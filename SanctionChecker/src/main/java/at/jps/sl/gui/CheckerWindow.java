package at.jps.sl.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import at.jps.sanction.core.Checker;
import at.jps.sanction.core.StreamManager;
import at.jps.sanction.model.queue.QueueEventListener;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

public class CheckerWindow {

    private JFrame     frmChecker;
    private JTextField textField_Input;
    private JTextField textField_Hit;
    private JTextField textField_noHit;
    private JTextField textField_max;
    private JTextField textField_hitRatio;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final CheckerWindow window = new CheckerWindow();
                    window.frmChecker.setVisible(true);
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public CheckerWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        final Preferences root = Preferences.userRoot();
        final Preferences node = root.node("/at/jps/swing/sl/checkerwindow");
        final int left = node.getInt("left", 100);
        final int top = node.getInt("top", 100);
        final int width = node.getInt("width", 800);
        final int height = node.getInt("height", 600);

        frmChecker = new JFrame();

        frmChecker.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                node.putInt("left", frmChecker.getX());
                node.putInt("top", frmChecker.getY());
                node.putInt("width", frmChecker.getWidth());
                node.putInt("height", frmChecker.getHeight());
            }
        });

        frmChecker.setTitle("Checker");
        frmChecker.setBounds(left, top, width, height);
        frmChecker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        frmChecker.getContentPane().add(panel, BorderLayout.SOUTH);
        final GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        panel.setLayout(gbl_panel);

        final JLabel lblNewLabel_3 = new JLabel("max work");
        final GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 0;
        panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

        textField_max = new JTextField();
        textField_max.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField_max.setEditable(false);
        final GridBagConstraints gbc_textField_max = new GridBagConstraints();
        gbc_textField_max.insets = new Insets(0, 0, 5, 0);
        gbc_textField_max.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_max.gridx = 1;
        gbc_textField_max.gridy = 0;
        panel.add(textField_max, gbc_textField_max);
        textField_max.setColumns(10);

        final JLabel lblNewLabel = new JLabel("inputqueue");
        final GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        panel.add(lblNewLabel, gbc_lblNewLabel);

        textField_Input = new JTextField();
        textField_Input.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField_Input.setEditable(false);
        final GridBagConstraints gbc_textField_Input = new GridBagConstraints();
        gbc_textField_Input.insets = new Insets(0, 0, 5, 0);
        gbc_textField_Input.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_Input.gridx = 1;
        gbc_textField_Input.gridy = 1;
        panel.add(textField_Input, gbc_textField_Input);
        textField_Input.setColumns(10);

        final JLabel lblNewLabel_1 = new JLabel("hit queue");
        final GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 2;
        panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

        textField_Hit = new JTextField();
        textField_Hit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField_Hit.setEditable(false);
        final GridBagConstraints gbc_textField_Hit = new GridBagConstraints();
        gbc_textField_Hit.insets = new Insets(0, 0, 5, 0);
        gbc_textField_Hit.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_Hit.gridx = 1;
        gbc_textField_Hit.gridy = 2;
        panel.add(textField_Hit, gbc_textField_Hit);
        textField_Hit.setColumns(10);

        final JLabel lblNewLabel_2 = new JLabel("nohit queue");
        final GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 3;
        panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

        textField_noHit = new JTextField();
        textField_noHit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField_noHit.setEditable(false);
        final GridBagConstraints gbc_textField_noHit = new GridBagConstraints();
        gbc_textField_noHit.insets = new Insets(0, 0, 5, 0);
        gbc_textField_noHit.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_noHit.gridx = 1;
        gbc_textField_noHit.gridy = 3;
        panel.add(textField_noHit, gbc_textField_noHit);
        textField_noHit.setColumns(10);

        final JLabel lblHitratio = new JLabel("hitRatio");
        final GridBagConstraints gbc_lblHitratio = new GridBagConstraints();
        gbc_lblHitratio.anchor = GridBagConstraints.EAST;
        gbc_lblHitratio.insets = new Insets(0, 0, 5, 5);
        gbc_lblHitratio.gridx = 0;
        gbc_lblHitratio.gridy = 4;
        panel.add(lblHitratio, gbc_lblHitratio);

        textField_hitRatio = new JTextField();
        textField_hitRatio.setFont(new Font("Tahoma", Font.BOLD, 18));
        textField_hitRatio.setEditable(false);
        final GridBagConstraints gbc_textField_hitRatio = new GridBagConstraints();
        gbc_textField_hitRatio.insets = new Insets(0, 0, 5, 0);
        gbc_textField_hitRatio.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_hitRatio.gridx = 1;
        gbc_textField_hitRatio.gridy = 4;
        panel.add(textField_hitRatio, gbc_textField_hitRatio);
        textField_hitRatio.setColumns(10);

        final JPanel panel_1 = new JPanel();
        panel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
        frmChecker.getContentPane().add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        final JPanel panel_graph1 = new JPanel();
        panel_graph1.setBackground(new Color(255, 255, 240));
        panel_1.add(panel_graph1);
        panel_graph1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_graph1.setLayout(new GridLayout(2, 1, 5, 5));

        final Chart2D chart1 = new Chart2D();
        chart1.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel_graph1.add(chart1);
        chart1.setBackground(new Color(255, 255, 240));
        chart1.setUseAntialiasing(true);

        final Chart2D chart2 = new Chart2D();
        chart2.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel_graph1.add(chart2);
        chart2.setUseAntialiasing(true);
        chart2.setBackground(new Color(255, 255, 240));

        final JPanel panel_graph2 = new JPanel();
        panel_graph2.setBorder(new LineBorder(new Color(0, 0, 0)));

        // frmChecker.getContentPane().add(panel_graph2, BorderLayout.CENTER);
        // panel_graph2.setLayout(new BorderLayout(10, 10));

        final ITrace2D maxTrace = new Trace2DLtd(200);
        maxTrace.setColor(Color.BLUE);
        maxTrace.setName("Total");
        maxTrace.setStroke(new BasicStroke(3));

        final ITrace2D workTrace = new Trace2DLtd(200);
        workTrace.setColor(Color.CYAN);
        workTrace.setName("Work");
        workTrace.setStroke(new BasicStroke(3));

        final ITrace2D hitTrace = new Trace2DLtd(200);
        hitTrace.setColor(Color.ORANGE);
        hitTrace.setName("Hit");
        hitTrace.setStroke(new BasicStroke(3));

        final ITrace2D nohitTrace = new Trace2DLtd(200);
        nohitTrace.setColor(Color.GREEN);
        nohitTrace.setName("NoHit");
        nohitTrace.setStroke(new BasicStroke(3));

        final ITrace2D errorTrace = new Trace2DLtd(200);
        errorTrace.setColor(Color.RED);
        errorTrace.setName("Error");
        errorTrace.setStroke(new BasicStroke(3));

        chart1.addTrace(maxTrace);
        chart1.addTrace(workTrace);
        chart2.addTrace(hitTrace);
        chart2.addTrace(nohitTrace);
        chart2.addTrace(errorTrace);

        final Checker checker = new Checker() {

            private final long m_starttime  = System.currentTimeMillis();

            int                hitCounter   = 0;
            int                nohitCounter = 0;

            void addTracePoint(final int counter, final ITrace2D trace) {
                trace.addPoint(((double) System.currentTimeMillis() - m_starttime), counter);
            }

            @Override
            protected void startupManagers() {

                final StreamManager sm = getStreamManagers().get(0);  // TODO: HACK !!!

                sm.addQueueListener("InputQueue", new QueueEventListener() {

                    int counter    = 0;
                    int maxCounter = 0;

                    @Override
                    public void messageRemoved() {
                        counter--;
                        addTracePoint(counter, workTrace);
                        addTracePoint(maxCounter, maxTrace);

                        textField_Input.setText(String.format("%d", counter));
                    }

                    @Override
                    public void messageAdded() {
                        counter++;
                        maxCounter++;
                        addTracePoint(counter, workTrace);
                        addTracePoint(maxCounter, maxTrace);

                        textField_Input.setText(String.format("d", counter));
                        textField_max.setText(String.format("%d", maxCounter));
                    }
                });

                sm.addQueueListener("HitQueue", new QueueEventListener() {

                    @Override
                    public void messageRemoved() {
                        hitCounter--;
                        addTracePoint(hitCounter, hitTrace);
                        textField_Hit.setText(String.format("%d", hitCounter));
                    }

                    @Override
                    public void messageAdded() {
                        hitCounter++;
                        addTracePoint(hitCounter, hitTrace);
                        textField_Hit.setText(String.format("%d", hitCounter));
                    }
                });

                sm.addQueueListener("NoHitQueue", new QueueEventListener() {

                    @Override
                    public void messageRemoved() {
                        nohitCounter--;
                        addTracePoint(nohitCounter, nohitTrace);
                        textField_noHit.setText(String.format("%d", nohitCounter));
                    }

                    @Override
                    public void messageAdded() {
                        nohitCounter++;
                        addTracePoint(nohitCounter, nohitTrace);
                        textField_noHit.setText(String.format("%d", nohitCounter));

                        textField_hitRatio.setText(String.format("%d%%", hitCounter / ((nohitCounter + hitCounter) / 100)));

                    }
                });

                sm.addQueueListener("DefectQueue", new QueueEventListener() {

                    int counter = 0;

                    @Override
                    public void messageRemoved() {
                        counter--;
                        addTracePoint(counter, errorTrace);
                        // textField_.setText(String.format("%07d", counter));
                    }

                    @Override
                    public void messageAdded() {
                        counter++;
                        addTracePoint(counter, errorTrace);
                        // textField_noHit.setText(String.format("%07d", counter));
                    }
                });

                super.startupManagers();
            }

        };

        checker.startup(null, true);
    }

}
