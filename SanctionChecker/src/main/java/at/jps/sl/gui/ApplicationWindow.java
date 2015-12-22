package at.jps.sl.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.avaje.agentloader.AgentLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import at.jps.sanction.core.EntityManagementConfig;
import at.jps.sanction.domain.SanctionListHitResult;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.ProcessStep;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sl.gui.core.TableColumnAdjuster;
import at.jps.sl.gui.util.GUIConfigHolder;
import at.jps.sl.gui.util.URLHelper;

public class ApplicationWindow {

    class ResultSideRenderer extends DefaultTableCellRenderer {
        /**
         *
         */
        private static final long serialVersionUID = 3097445954446635745L;

        Color                     red              = new java.awt.Color(212, 83, 121);
        Color                     green            = new java.awt.Color(186, 212, 83);
        Color                     yelo             = new java.awt.Color(237, 231, 152);
        Color                     ora              = new java.awt.Color(194, 152, 237);

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
            final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setBorder(new CompoundBorder(new EmptyBorder(new Insets(1, 4, 1, 4)), getBorder()));

            if (column == 4) {
                String hintType = (String) table.getValueAt(row, column);
                Color color = c.getBackground();
                if (hintType.equals(OptimizationRecord.OPTI_STATUS_NEW)) {
                    color = ora;
                }
                else if (hintType.equals(" ")) {
                    color = red;
                }
                else if (hintType.equals(OptimizationRecord.OPTI_STATUS_PENDING)) {
                    color = yelo;
                }
                else if (hintType.equals(OptimizationRecord.OPTI_STATUS_CONFIRMED)) {
                    color = green;
                }
                c.setBackground(color);
            }
            else {
                if (!isSelected) {
                    c.setBackground(table.getBackground());
                }
            }
            return c;
        }
    }

    class TXSideRenderer extends DefaultTableCellRenderer {
        /**
         *
         */
        private static final long serialVersionUID = 3097445954446635745L;

        Color                     defaultColor     = new java.awt.Color(255, 150, 72);

        // Color white = new java.awt.Color(255, 255, 255);

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
            final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // if hitfiled

            if ((column == 2) && (guiAdapter.getHitTypeRows() != null)) {
                final String hitType = guiAdapter.getHitTypeRows().get(row);
                if (hitType != null) {
                    Color color = guiAdapter.getFieldColors().get(hitType);

                    if (color == null) {
                        color = guiAdapter.getFieldColors().get("UNCHECKED");
                    }

                    if (color == null) {
                        color = defaultColor;
                    }

                    c.setBackground(color);
                }
            }
            else {
                if (!isSelected) {
                    c.setBackground(table.getBackground());
                }
            }

            setBorder(new CompoundBorder(new EmptyBorder(new Insets(1, 4, 1, 4)), getBorder()));

            return c;
        }
    }

    TableColumnAdjuster       tca;
    private JFrame            frmCaseManagement;
    private JTable            tableTXHits;
    private JTable            tableTXNoHits;
    private JTable            tableResults;
    private JTable            tableWordHits;

    private JTable            table_EntityNameDetails;

    private JTabbedPane       tabbedPane;

    private JTextField        textField_AnalysisTime;
    private JTextPane         textPane_Comment;
    private JTextPane         textPane_LegalBack;
    private JTextField        textField_Type;
    private JTextField        textField_ListDescription;
    private JTextPane         textPane_Remark;
    private JButton           btn_ExternalUrl;
    private JComboBox<String> comboBox_Category;

    private JButton           btnNextButton;
    private JButton           btnPrevButton;

    private boolean           recursionProhibitorTX     = false;
    private boolean           recursionProhibitorResult = false;
    // private boolean recursionProhibitorToken = false;

    private SearchWindow      searchWindow;

    private JMenuItem         mntmAddToStopwords;
    private JMenuItem         mntmAddToNon;
    private JMenuItem         mntmAddToIA;
    private JMenuItem         mntmAddToNoHit;

    private AdapterHelper     guiAdapter;

    private boolean           hitsViewActive            = true;
    private boolean           resultsViewActive         = true;
    private boolean           autolearnMode             = true;

    private String            selectedToken             = "";
    private String            selectedFieldContent      = "";

    /**
     * Create the application.
     */
    public ApplicationWindow(GUIConfigHolder config) {

        guiAdapter = new AdapterHelper();
        guiAdapter.initialize(config);

        initializeGUI();
    }

    private void checkAndGetNextMessage(final boolean next) {
        // boolean exists = false;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // get it from a queue ( what else)....

                // HITS
                if (hitsViewActive) {

                    if ((next && guiAdapter.getHitBuffer().hasNextMessage()) || (!next && guiAdapter.getHitBuffer().hasPrevMessage())) {
                        guiAdapter.setCurrentMessage((next ? guiAdapter.getHitBuffer().getNextMessage() : guiAdapter.getHitBuffer().getPrevMessage()));

                        if (guiAdapter.getCurrentMessage() != null) {
                            // exists = true;
                            tableTXHits.setModel(guiAdapter.getMessageTableModel(guiAdapter.getCurrentMessage().getMessage()));
                            tableResults.setModel(guiAdapter.getAnalysisResultTableModel(guiAdapter.getCurrentMessage()));
                            tableWordHits.setModel(guiAdapter.getAnalysisWordListTableModel(guiAdapter.getCurrentMessage()));

                            final int columnWidthMsg[] = { 50, 50, 50, 90 };

                            for (int i = 0; i < 2; i++) {
                                final TableColumn column = tableTXHits.getColumnModel().getColumn(i);
                                column.setMinWidth(columnWidthMsg[i]);
                                column.setMaxWidth(columnWidthMsg[i]);
                                column.setPreferredWidth(columnWidthMsg[i]);
                            }

                            final int columnWidthHits[] = { 100, 130, 50, 120, 22, 200 };

                            for (int i = 0; i < columnWidthHits.length; i++) {
                                final TableColumn column = tableResults.getColumnModel().getColumn(i);
                                column.setMinWidth(columnWidthHits[i]);
                                if (i < columnWidthHits.length - 1) column.setMaxWidth(columnWidthHits[i]);
                                column.setPreferredWidth(columnWidthHits[i]);
                            }

                            final int columnWidthWordHits[] = { 100, 130, 150, 90 };

                            for (int i = 0; i < columnWidthWordHits.length; i++) {
                                final TableColumn column = tableWordHits.getColumnModel().getColumn(i);
                                column.setMinWidth(columnWidthWordHits[i]);
                                column.setMaxWidth(columnWidthWordHits[i]);
                                column.setPreferredWidth(columnWidthWordHits[i]);
                            }

                            updateMessageDetails();

                            tableResults.setRowSelectionInterval(0, 0);
                        }
                    }
                }
                else { // NOHITS
                    if ((next && guiAdapter.getNoHitBuffer().hasNextMessage()) || (!next && guiAdapter.getNoHitBuffer().hasPrevMessage())) {

                        guiAdapter.setCurrentMessage((next ? guiAdapter.getNoHitBuffer().getNextMessage() : guiAdapter.getNoHitBuffer().getPrevMessage()));

                        if (guiAdapter.getCurrentMessage() != null) {
                            // exists = true;
                            tableTXNoHits.setModel(guiAdapter.getMessageTableModel(guiAdapter.getCurrentMessage().getMessage()));

                            //
                            // Configures table's column width.
                            //

                            for (int i = 0; i < 2; i++) {
                                final TableColumn column = tableTXNoHits.getColumnModel().getColumn(i);
                                column.setMinWidth(50);
                                column.setMaxWidth(50);
                                column.setPreferredWidth(50);
                            }
                        }
                    }
                }
            }
        });

        // return exists;

    }

    private boolean checkNextMessage(final boolean next) {

        final boolean hasMsg =

        ((hitsViewActive && ((next && guiAdapter.getHitBuffer().hasNextMessage()) || (!next && guiAdapter.getHitBuffer().hasPrevMessage())))
                || (!hitsViewActive && ((next && guiAdapter.getNoHitBuffer().hasNextMessage()) || (!next && guiAdapter.getNoHitBuffer().hasPrevMessage()))));

        return hasMsg;
    }

    /**
     * Initialize the contents of the frame.
     */
    void initializeGUI() {

        if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1;packages=at.jps.sanction.core.model.**")) {
            System.out.println("avaje-ebeanorm-agent not found in classpath - not dynamically loaded");
        }

        final Preferences root = Preferences.userRoot();
        final Preferences node = root.node("/at/jps/swing/sl/mainwindow");
        final int left = node.getInt("left", 100);
        final int top = node.getInt("top", 100);
        final int width = node.getInt("width", 800);
        final int height = node.getInt("height", 600);

        frmCaseManagement = new JFrame();
        frmCaseManagement.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                node.putInt("left", frmCaseManagement.getX());
                node.putInt("top", frmCaseManagement.getY());
                node.putInt("width", frmCaseManagement.getWidth());
                node.putInt("height", frmCaseManagement.getHeight());
            }
        });
        frmCaseManagement.setTitle("Case Management for " + guiAdapter.getConfig().getName());
        frmCaseManagement.setBounds(left, top, width, height);
        frmCaseManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel();
        frmCaseManagement.getContentPane().add(panel, BorderLayout.SOUTH);

        final JCheckBox chckbxAutoLearnMode = new JCheckBox("auto learn mode");
        chckbxAutoLearnMode.setSelected(autolearnMode);
        chckbxAutoLearnMode.setToolTipText("enable for mystic meta analysis ");
        panel.add(chckbxAutoLearnMode);

        chckbxAutoLearnMode.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {

                autolearnMode = chckbxAutoLearnMode.isSelected();

            }
        });

        final JButton btnHitButton = new JButton("Hit");
        btnHitButton.setToolTipText("mark as Hit");
        panel.add(btnHitButton);

        btnHitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {

                markAsHitTransaction();

            }
        });

        final JButton btnNoHitButton = new JButton("No Hit");
        btnNoHitButton.setToolTipText("mark as NO hit");
        panel.add(btnNoHitButton);

        btnNoHitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {

                markAsNonHitTransaction();

            }
        });

        btnNextButton = new JButton("Next");
        btnNextButton.setToolTipText("show next Transaction");
        btnPrevButton = new JButton("Prev");
        btnPrevButton.setToolTipText("show previous Transaction");
        btnPrevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {

                doNextMessage(false);

            }
        });

        panel.add(btnPrevButton);

        btnNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {

                doNextMessage(true);
            }
        });
        panel.add(btnNextButton);

        tabbedPane = new JTabbedPane(SwingConstants.TOP);
        tabbedPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        frmCaseManagement.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        final JPanel panel_noHit = new JPanel();
        panel_noHit.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_noHit.setLayout(new BorderLayout(0, 0));

        tableTXNoHits = new JTable();
        tableTXNoHits.setFillsViewportHeight(true);
        panel_noHit.add(new JScrollPane(tableTXNoHits), BorderLayout.CENTER);

        // DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
        //
        // Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        // @Override
        // public Component getTableCellRendererComponent(JTable table,
        // Object value, boolean isSelected, boolean hasFocus,
        // int row, int column) {
        // super.getTableCellRendererComponent(table, value, isSelected,
        // hasFocus,
        // row, column);
        // setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));
        // return this;
        // }
        //
        // };

        // tableNoHits.setDefaultRenderer(String.class, renderer);

        tableTXNoHits.setDefaultRenderer(String.class, new ResultSideRenderer());

        // this.tableTXNoHits.setIntercellSpacing(new Dimension(10, 0));

        JPanel transactionHandlingPanel = new JPanel();
        transactionHandlingPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        final JSplitPane txSplitPane = new JSplitPane();
        txSplitPane.setContinuousLayout(true);

        tabbedPane.addTab("Transactions", null, txSplitPane, "Show TX and Hits");
        // tabbedPane.setEnabledAt(0, false);
        tabbedPane.addTab("No Hit Txs", null, panel_noHit, "Show TX without Hits");

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                // System.out.println("Tab: " + tabbedPane.getSelectedIndex());

                hitsViewActive = tabbedPane.getSelectedIndex() < 1;

                btnHitButton.setEnabled(hitsViewActive);
                btnNoHitButton.setEnabled(hitsViewActive);

                btnNextButton.setEnabled(checkNextMessage(true));
                btnPrevButton.setEnabled(checkNextMessage(false));

            }
        });

        final JPanel panel_tableTXHits = new JPanel();
        panel_tableTXHits.setBorder(new EmptyBorder(5, 5, 5, 5));
        txSplitPane.setLeftComponent(panel_tableTXHits);
        panel_tableTXHits.setLayout(new BorderLayout(0, 0));

        tableTXHits = new JTable();
        // tableTXHits.addFocusListener(new FocusAdapter() {
        // @Override
        // public void focusGained(FocusEvent e) {
        // super.focusGained(e);
        // System.out.println("got focus");
        // }
        //
        // @Override
        // public void focusLost(FocusEvent e) {
        // super.focusLost(e);
        // System.out.println("focus lost");
        // }
        // });
        tableTXHits.setFillsViewportHeight(true);
        tableTXHits.setBackground(new Color(255, 255, 240));
        tableTXHits.setToolTipText("Transaction");
        panel_tableTXHits.add(new JScrollPane(tableTXHits), BorderLayout.CENTER);
        panel_tableTXHits.add(transactionHandlingPanel, BorderLayout.SOUTH);
        GridBagLayout gbl_transactionHandlingPanel = new GridBagLayout();
        gbl_transactionHandlingPanel.columnWidths = new int[] { 615, 0 };
        gbl_transactionHandlingPanel.rowHeights = new int[] { 14, 20, 0, 0, 0, 0, 0 };
        gbl_transactionHandlingPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_transactionHandlingPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        transactionHandlingPanel.setLayout(gbl_transactionHandlingPanel);

        JLabel lblNewLabel = new JLabel("TX Analysis Time: ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
        gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        transactionHandlingPanel.add(lblNewLabel, gbc_lblNewLabel);

        textField_AnalysisTime = new JTextField();
        lblNewLabel.setLabelFor(textField_AnalysisTime);
        textField_AnalysisTime.setEditable(false);
        textField_AnalysisTime.setText("");
        GridBagConstraints gbc_textField_legal = new GridBagConstraints();
        gbc_textField_legal.insets = new Insets(0, 0, 5, 0);
        gbc_textField_legal.anchor = GridBagConstraints.NORTH;
        gbc_textField_legal.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_legal.gridx = 0;
        gbc_textField_legal.gridy = 1;
        transactionHandlingPanel.add(textField_AnalysisTime, gbc_textField_legal);
        textField_AnalysisTime.setColumns(10);

        JLabel lblCathegory = new JLabel("Category");
        lblCathegory.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblCathegory = new GridBagConstraints();
        gbc_lblCathegory.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblCathegory.anchor = GridBagConstraints.NORTH;
        gbc_lblCathegory.insets = new Insets(0, 0, 5, 0);
        gbc_lblCathegory.gridx = 0;
        gbc_lblCathegory.gridy = 2;
        transactionHandlingPanel.add(lblCathegory, gbc_lblCathegory);

        final String[] categories = { "", "Iran", "Irak", "Ukraine", "Nix besonderes...", "keine Ursache" };
        final DefaultComboBoxModel<String> categoryModel = new DefaultComboBoxModel<String>(categories);

        comboBox_Category = new JComboBox<String>();
        comboBox_Category.setEditable(true);
        lblCathegory.setLabelFor(comboBox_Category);
        comboBox_Category.setToolTipText("select Category");
        comboBox_Category.setModel(categoryModel);

        // comboBox.setModel(new ComboBoxModel<String>() {
        //
        // @Override
        // public int getSize() {
        //
        // return categories.length;
        // }
        //
        // @Override
        // public String getElementAt(int index) {
        // // TODO Auto-generated method stub
        // return categories[index];
        // }
        //
        // @Override
        // public void addListDataListener(ListDataListener l) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // @Override
        // public void removeListDataListener(ListDataListener l) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // @Override
        // public void setSelectedItem(Object anItem) {
        //
        // }
        //
        // @Override
        // public Object getSelectedItem() {
        // // TODO Auto-generated method stub
        // return null;
        // }
        //
        // });

        GridBagConstraints gbc_comboBox_Category = new GridBagConstraints();
        gbc_comboBox_Category.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox_Category.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_Category.gridx = 0;
        gbc_comboBox_Category.gridy = 3;
        transactionHandlingPanel.add(comboBox_Category, gbc_comboBox_Category);

        JLabel lblNewLabel_1 = new JLabel("Comment");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTH;
        gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 4;
        transactionHandlingPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

        textPane_Comment = new JTextPane();
        lblNewLabel_1.setLabelFor(textPane_Comment);
        textPane_Comment.setText(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        textPane_Comment.setToolTipText("enter Comment");
        GridBagConstraints gbc_textPane_Comment = new GridBagConstraints();
        gbc_textPane_Comment.fill = GridBagConstraints.BOTH;
        gbc_textPane_Comment.gridx = 0;
        gbc_textPane_Comment.gridy = 5;
        transactionHandlingPanel.add(textPane_Comment, gbc_textPane_Comment);

        JButton btnPostpone = new JButton("Postpone");
        btnPostpone.setToolTipText("machma sp\u00E4ter");
        GridBagConstraints gbc_btnPostpone = new GridBagConstraints();
        gbc_btnPostpone.gridx = 0;
        gbc_btnPostpone.gridy = 6;
        transactionHandlingPanel.add(btnPostpone, gbc_btnPostpone);

        btnPostpone.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {

                postponeTransaction();

            }
        });

        tableTXHits.setDefaultRenderer(String.class, new TXSideRenderer());
        tableTXHits.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final JPanel panel_tableResults = new JPanel();
        panel_tableResults.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_tableResults.setLayout(new BorderLayout(0, 0));

        final JPanel panel_tableWordHits = new JPanel();
        panel_tableWordHits.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_tableWordHits.setLayout(new BorderLayout(0, 0));

        final JTabbedPane tabbedPane_Results = new JTabbedPane(SwingConstants.TOP);
        tabbedPane_Results.setBorder(new EmptyBorder(5, 5, 5, 5));
        tabbedPane_Results.addTab("Details", null, panel_tableResults, "Hit Details");
        tabbedPane_Results.addTab("Word Hits", null, panel_tableWordHits, "Word Hits");

        tabbedPane_Results.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                resultsViewActive = tabbedPane_Results.getSelectedIndex() < 1;

            }
        });

        txSplitPane.setRightComponent(tabbedPane_Results);

        tableWordHits = new JTable();
        tableWordHits.setFillsViewportHeight(true);
        tableWordHits.setBackground(new Color(245, 240, 230));
        tableWordHits.setToolTipText("Word´Hits");
        panel_tableWordHits.add(new JScrollPane(tableWordHits), BorderLayout.CENTER);

        tableWordHits.setDefaultRenderer(String.class, new ResultSideRenderer());
        tableWordHits.setAutoCreateRowSorter(true);
        tableWordHits.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPopupMenu popupMenu = new JPopupMenu();
        addWordHitPopup(tableWordHits, popupMenu);

        mntmAddToStopwords = new JMenuItem("add to Stopwords");
        popupMenu.add(mntmAddToStopwords);

        mntmAddToNon = new JMenuItem("add to non single hits");
        popupMenu.add(mntmAddToNon);

        mntmAddToIA = new JMenuItem("add to Index Exclusion");
        popupMenu.add(mntmAddToIA);

        mntmAddToNoHit = new JMenuItem("add to No Hit List");
        popupMenu.add(mntmAddToNoHit);

        mntmAddToStopwords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAddTokenToStopword();
            }
        });

        mntmAddToNon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAddTokenToNSWH();
            }
        });

        mntmAddToIA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAddTokenToIA();
            }
        });

        mntmAddToNoHit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAddTokenToNoHit();
            }
        });

        tableResults = new JTable();
        tableResults.setFillsViewportHeight(true);
        tableResults.setBackground(new Color(255, 250, 240));
        tableResults.setToolTipText("Results");
        // panel_tableResults.add(new JScrollPane(tableResults), BorderLayout.CENTER);

        tableResults.setDefaultRenderer(String.class, new ResultSideRenderer());
        tableResults.setAutoCreateRowSorter(true);
        tableResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel panel_SanctionInfo = new JPanel();
        panel_SanctionInfo.setBorder(new EmptyBorder(5, 5, 5, 5));
        // panel_table Results.add(panel_SanctionInfo, BorderLayout.SOUTH);

        final JSplitPane vertSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        vertSplitPane.setResizeWeight(0.9);
        vertSplitPane.setContinuousLayout(true);
        vertSplitPane.setTopComponent(new JScrollPane(tableResults));
        vertSplitPane.setBottomComponent(panel_SanctionInfo);

        panel_tableResults.add(vertSplitPane, BorderLayout.CENTER);

        panel_SanctionInfo.setLayout(new BorderLayout(0, 0));

        JTabbedPane tabbedPane_Sanction = new JTabbedPane(JTabbedPane.TOP);
        panel_SanctionInfo.add(tabbedPane_Sanction);

        JPanel panel_Sanction1 = new JPanel();
        panel_Sanction1.setBorder(new EmptyBorder(5, 5, 5, 5));
        tabbedPane_Sanction.addTab("General", null, panel_Sanction1, null);
        GridBagLayout gbl_panel_Sanction1 = new GridBagLayout();
        gbl_panel_Sanction1.columnWidths = new int[] { 0, 0 };
        gbl_panel_Sanction1.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_panel_Sanction1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_panel_Sanction1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
        panel_Sanction1.setLayout(gbl_panel_Sanction1);

        JLabel lbListDescription = new JLabel("List Description");
        lbListDescription.setHorizontalAlignment(SwingConstants.LEFT);

        GridBagConstraints gbc_lbListDescription = new GridBagConstraints();
        gbc_lbListDescription.fill = GridBagConstraints.HORIZONTAL;
        gbc_lbListDescription.anchor = GridBagConstraints.NORTH;
        gbc_lbListDescription.insets = new Insets(0, 0, 5, 0);
        gbc_lbListDescription.gridx = 0;
        gbc_lbListDescription.gridy = 0;
        panel_Sanction1.add(lbListDescription, gbc_lbListDescription);

        GridBagConstraints gbc_textField_ListDescription = new GridBagConstraints();
        gbc_textField_ListDescription.insets = new Insets(0, 0, 5, 0);
        gbc_textField_ListDescription.fill = GridBagConstraints.BOTH;
        gbc_textField_ListDescription.gridx = 0;
        gbc_textField_ListDescription.gridy = 1;

        textField_ListDescription = new JTextField();
        textField_ListDescription.setText("");
        textField_ListDescription.setEditable(false);

        panel_Sanction1.add(textField_ListDescription, gbc_textField_ListDescription);

        JLabel lblLegalBackground = new JLabel("Legal Background");
        lblLegalBackground.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblLegalBackground = new GridBagConstraints();
        gbc_lblLegalBackground.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblLegalBackground.anchor = GridBagConstraints.NORTH;
        gbc_lblLegalBackground.insets = new Insets(0, 0, 5, 0);
        gbc_lblLegalBackground.gridx = 0;
        gbc_lblLegalBackground.gridy = 2;
        panel_Sanction1.add(lblLegalBackground, gbc_lblLegalBackground);

        GridBagConstraints gbc_textField_legal1 = new GridBagConstraints();
        gbc_textField_legal1.insets = new Insets(0, 0, 5, 0);
        gbc_textField_legal1.fill = GridBagConstraints.BOTH;
        gbc_textField_legal1.gridx = 0;
        gbc_textField_legal1.gridy = 3;

        textPane_LegalBack = new JTextPane();
        textPane_LegalBack.setText("");
        textPane_LegalBack.setEditable(false);
        // textPane_legal.setColumns(10);

        panel_Sanction1.add(textPane_LegalBack, gbc_textField_legal1);

        JLabel lbType = new JLabel("Entity Type");
        lbType.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblType = new GridBagConstraints();
        gbc_lblType.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblType.anchor = GridBagConstraints.NORTH;
        gbc_lblType.insets = new Insets(0, 0, 5, 0);
        gbc_lblType.gridx = 0;
        gbc_lblType.gridy = 4;
        panel_Sanction1.add(lbType, gbc_lblType);

        GridBagConstraints gbc_textField_Type = new GridBagConstraints();
        gbc_textField_Type.insets = new Insets(0, 0, 5, 0);
        gbc_textField_Type.fill = GridBagConstraints.BOTH;
        gbc_textField_Type.gridx = 0;
        gbc_textField_Type.gridy = 5;

        textField_Type = new JTextField();
        textField_Type.setText("");
        textField_Type.setEditable(false);

        panel_Sanction1.add(textField_Type, gbc_textField_Type);

        JLabel lblRemark = new JLabel("Remark");
        lblRemark.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblRemark = new GridBagConstraints();
        gbc_lblRemark.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblRemark.anchor = GridBagConstraints.NORTH;
        gbc_lblRemark.insets = new Insets(0, 0, 5, 0);
        gbc_lblRemark.gridx = 0;
        gbc_lblRemark.gridy = 6;
        panel_Sanction1.add(lblRemark, gbc_lblRemark);

        textPane_Remark = new JTextPane();
        textPane_Remark.setToolTipText("Remark");
        textPane_Remark.setText(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        GridBagConstraints gbc_textPane = new GridBagConstraints();
        gbc_textPane.insets = new Insets(0, 0, 5, 0);
        gbc_textPane.fill = GridBagConstraints.BOTH;
        gbc_textPane.gridx = 0;
        gbc_textPane.gridy = 7;
        panel_Sanction1.add(textPane_Remark, gbc_textPane);

        JPanel panel_Sanction2 = new JPanel();
        panel_Sanction2.setBorder(new EmptyBorder(5, 5, 5, 5));
        tabbedPane_Sanction.addTab("NameDetails", null, panel_Sanction2, null);
        panel_Sanction2.setLayout(new BorderLayout(0, 0));

        table_EntityNameDetails = new JTable();
        table_EntityNameDetails.setDefaultRenderer(String.class, new ResultSideRenderer());
        table_EntityNameDetails.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tca = new TableColumnAdjuster(table_EntityNameDetails);
        // tca.adjustColumns();

        table_EntityNameDetails.setBackground(tableResults.getBackground());
        panel_Sanction2.add(new JScrollPane(table_EntityNameDetails));

        btn_ExternalUrl = new JButton("external URL");
        btn_ExternalUrl.setHorizontalAlignment(SwingConstants.LEFT);

        GridBagConstraints gbc_ExternalUrl = new GridBagConstraints();
        gbc_ExternalUrl.gridx = 0;
        gbc_ExternalUrl.gridy = 8;

        panel_Sanction1.add(btn_ExternalUrl, gbc_ExternalUrl);

        JMenuBar menuBar = new JMenuBar();
        frmCaseManagement.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmClose = new JMenuItem("Close...");
        mntmClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose();
            }
        });
        mnFile.add(mntmClose);

        JMenu mnTools = new JMenu("Tools");
        menuBar.add(mnTools);

        JMenuItem mntmSearch = new JMenuItem("Search...");
        mntmSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                doSearch();
            }
        });
        mnTools.add(mntmSearch);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        JMenuItem mntmAbout = new JMenuItem("About...");
        mntmAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAbout();
            }
        });
        mnHelp.add(mntmAbout);

        btn_ExternalUrl.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {

                if (guiAdapter.getFocussedHitResult() instanceof SanctionListHitResult) {
                    // try
                    {
                        JPopupMenu urlMenu = new JPopupMenu();  // should be moved to refresh method and only SHOWN here

                        urlMenu.removeAll();

                        SanctionListHitResult slhr = (SanctionListHitResult) guiAdapter.getFocussedHitResult();

                        WL_Entity entity = guiAdapter.getSanctionListEntityDetails(slhr.getHitListName(), slhr.getHitId());

                        if (entity != null) {
                            for (String url : entity.getInformationUrls()) {
                                final JMenuItem mi = new JMenuItem(url);
                                urlMenu.add(mi);

                                mi.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            URLHelper.openWebpage(new URL(mi.getText()));
                                        }
                                        catch (MalformedURLException x) {
                                            System.err.println("External URL cannot be handled");
                                        }

                                    }
                                });

                            }
                        }
                        urlMenu.show(btn_ExternalUrl, 10, 10);

                        // URLHelper.openWebpage(new URL(((SanctionListHitResult) guiAdapter.getFocussedHitResult()).getHitExternalUrl()));
                    }
                    // catch (MalformedURLException e) {
                    // System.err.println("External URL cannot be handled");
                    // }
                }
            }
        });

        // panel_Sanction1.add(btn_ExternalUrl, gbc_btnPostpone);

        // GridBagConstraints gbc_textField_legal = new GridBagConstraints();
        // gbc_textField_legal.fill = GridBagConstraints.HORIZONTAL;
        // gbc_textField_legal.gridx = 0;
        // gbc_textField_legal.gridy = 1;
        // panel_Sanction1.add(textField_legal, gbc_textField_legal);

        final ListSelectionModel selectionModelResults = tableResults.getSelectionModel();

        selectionModelResults.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {

                // handle Result selection change !!
                if (((!e.getValueIsAdjusting()) && (tableResults.getSelectedRow() >= 0)) && recursionProhibitorResult == false) {
                    // System.out.println(e.getFirstIndex() + ": " + hitRowFields.get(e.getFirstIndex()));

                    // String field = resultRowFields.get(tableResults.getSelectedRow());

                    String field = guiAdapter.getResultRowFields().get(tableResults.convertRowIndexToModel(tableResults.getSelectedRow()));

                    if (field != null) {

                        recursionProhibitorResult = true;

                        int txHitTableRow = guiAdapter.getHitFieldRows().get(field);

                        // tableResults.convertRowIndexToView(tableResults.getSelectedRow());
                        // tableResults.convertRowIndexToModel(tableResults.getSelectedRow());

                        // System.out.println("Row:" + e.getLastIndex() + " Field : " + resultRowFields.get(tableResults.getSelectedRow()) + " --> " + txHitTableRow);
                        // System.out.println("E: " + e);
                        // System.out.println("T: " + tableResults.getSelectedRow() + " C: " + tableResults.convertRowIndexToModel(tableResults.getSelectedRow()) + " F: " + field);

                        tableResults.setToolTipText((String) tableResults.getValueAt(tableResults.convertRowIndexToModel(tableResults.getSelectedRow()), 5));

                        tableTXHits.setRowSelectionInterval(txHitTableRow, txHitTableRow);

                        updateSanctionDetails(tableResults.convertRowIndexToModel(tableResults.getSelectedRow()));

                        recursionProhibitorResult = false;
                    }
                }

            }
        });

        final ListSelectionModel selectionModelTX = tableTXHits.getSelectionModel();

        selectionModelTX.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {

                // handle Result selection change !!
                if (((!e.getValueIsAdjusting()) && (tableTXHits.getSelectedRow() >= 0)) && recursionProhibitorTX == false) {
                    // System.out.println(e.getFirstIndex() + ": " + hitRowFields.get(e.getFirstIndex()));

                    recursionProhibitorTX = true;

                    int resultTableRow = -1;

                    String field = guiAdapter.getHitRowFields().get(tableTXHits.getSelectedRow());

                    // find row on result side

                    for (Map.Entry<Integer, String> entry : guiAdapter.getResultRowFields().entrySet()) {
                        if (entry.getValue().equals(field)) {
                            resultTableRow = entry.getKey();
                            break;
                        }
                    }

                    if (resultTableRow > -1) {

                        resultTableRow = tableResults.convertRowIndexToView(resultTableRow);

                        tableResults.setRowSelectionInterval(resultTableRow, resultTableRow);
                    }

                    // System.out.println("Row:" + e.getLastIndex() + " Field : " + resultRowFields.get(tableResults.getSelectedRow()) + " --> " + txHitTableRow);
                    // System.out.println("E: " + e);
                    // System.out.println("T: " + tableResults.getSelectedRow());
                    // System.out.println("F: " + field + " --> R: " + resultTableRow);

                    // updateSanctionDetails(tableResults.getSelectedRow());

                    // update also word hits
                    for (Map.Entry<Integer, String> entry : guiAdapter.getTokenRowFields().entrySet()) {
                        if (entry.getValue().equals(field)) {
                            resultTableRow = entry.getKey();
                            break;
                        }
                    }
                    if (resultTableRow > -1) {
                        tableWordHits.setRowSelectionInterval(resultTableRow, resultTableRow);
                    }
                    recursionProhibitorTX = false;
                }

            }
        });

        // final ListSelectionModel selectionModelResultsTokens = tableWordHits.getSelectionModel();
        //
        // selectionModelResultsTokens.addListSelectionListener(new ListSelectionListener() {
        // @Override
        // public void valueChanged(final ListSelectionEvent e) {
        //
        // // handle Result selection change !!
        // if (((!e.getValueIsAdjusting()) && (tableWordHits.getSelectedRow() >= 0)) && recursionProhibitorToken == false) {
        //
        // recursionProhibitorToken = true;
        //
        // String field = resultRowFields.get(tableWordHits.convertRowIndexToModel(tableWordHits.getSelectedRow()));
        //
        // if (field != null) {
        // int txHitTableRow = hitFieldRows.get(field);
        //
        // // tableResults.convertRowIndexToView(tableResults.getSelectedRow());
        // // tableResults.convertRowIndexToModel(tableResults.getSelectedRow());
        //
        // // System.out.println("Row:" + e.getLastIndex() + " Field : " + resultRowFields.get(tableResults.getSelectedRow()) + " --> " + txHitTableRow);
        // // System.out.println("E: " + e);
        // // System.out.println("T: " + tableResults.getSelectedRow() + " C: " + tableResults.convertRowIndexToModel(tableResults.getSelectedRow()));
        //
        // tableTXHits.setRowSelectionInterval(txHitTableRow, txHitTableRow);
        //
        // // updateSanctionDetails(tableResults.getSelectedRow());
        // }
        //
        // recursionProhibitorToken = false;
        // }
        //
        // }
        // });
        //
        // // splitPane.setDividerLocation(0.5);
        //
        // // get first Message if any
        //
        checkAndGetNextMessage(true);

    }

    private void addProcessStep(final String text) {
        ProcessStep processStep = new ProcessStep();
        processStep.setRemark(text);
        guiAdapter.getCurrentMessage().addProcessStep(processStep);

    }

    protected void postponeTransaction() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Postpone Transaction ?", "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {

            guiAdapter.getCurrentMessage().setComment(textPane_Comment.getText());

            addProcessStep("Backlogged");

            guiAdapter.addToBacklogQueue(guiAdapter.getCurrentMessage());

        }
    }

    protected void markAsHitTransaction() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Mark Transaction as Hit?", "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {

            guiAdapter.getCurrentMessage().setComment(textPane_Comment.getText());

            if (autolearnMode) {

                guiAdapter.addToPostProcessHitQueue(guiAdapter.getCurrentMessage());
                addProcessStep("Autolearned Hit");
            }

            addProcessStep("Confirmed Hit");
            guiAdapter.addToFinalHitQueue(guiAdapter.getCurrentMessage());

            doNextMessage(true);
        }
    }

    protected void markAsNonHitTransaction() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Mark Transaction as NON Hit?", "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {

            guiAdapter.getCurrentMessage().setComment(textPane_Comment.getText());

            if (autolearnMode) {
                guiAdapter.addToPostProcessNoHitQueue(guiAdapter.getCurrentMessage());
                addProcessStep("Autolearned Miss");
            }

            addProcessStep("Confirmed Miss");

            guiAdapter.addToFinalNoHitQueue(guiAdapter.getCurrentMessage());

            doNextMessage(true);
        }
    }

    void updateMessageDetails() {

        textField_AnalysisTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(guiAdapter.getCurrentMessage().getAnalysisStopTime())));
        textPane_Comment.setText(guiAdapter.getCurrentMessage().getComment());
        if (guiAdapter.getCurrentMessage().getCategory() != null) {
            comboBox_Category.setSelectedItem(guiAdapter.getCurrentMessage().getCategory());
        }

    }

    public static String newline = System.getProperty("line.separator");

    void updateSanctionDetails(int rowId) {
        // TODO: gather SactionInfos

        guiAdapter.setFocussedHitResult(guiAdapter.getCurrentMessage().getHitList().get((guiAdapter.getCurrentMessage().getHitList().size() - 1) - rowId));

        if (guiAdapter.getFocussedHitResult() instanceof SanctionListHitResult) {

            SanctionListHitResult slhr = (SanctionListHitResult) guiAdapter.getFocussedHitResult();

            WL_Entity entity = guiAdapter.getSanctionListEntityDetails(slhr.getHitListName(), slhr.getHitId());

            if (entity != null) {
                textField_Type.setText((entity.getType() != null ? entity.getType() : " ") + (entity.getEntryType() != null ? " | " + entity.getEntryType() : " "));

                StringBuilder lbs = new StringBuilder();
                for (String lb : entity.getLegalBasises()) {
                    lbs.append(lb).append(newline);
                }
                textPane_LegalBack.setText(lbs.toString());
            }
            else {
                textField_Type.setText(slhr.getEntityType());
                textPane_LegalBack.setText(slhr.getHitLegalBasis());
            }

            textField_ListDescription.setText(guiAdapter.getSanctionListDescription(slhr.getHitListName()));

            textPane_Remark.setText(slhr.getHitRemark() != null ? slhr.getHitRemark() : " ");

            btn_ExternalUrl.setEnabled(slhr.getHitExternalUrl() != null);
            btn_ExternalUrl.setToolTipText(slhr.getHitExternalUrl() != null ? slhr.getHitExternalUrl() : "");

            btn_ExternalUrl.setEnabled(slhr.getHitExternalUrl() != null);
            btn_ExternalUrl.setToolTipText(slhr.getHitExternalUrl() != null ? slhr.getHitExternalUrl() : "");

            // details
            if (entity != null) {
                table_EntityNameDetails.setModel(guiAdapter.getEntityDetailsNamesTableModel(entity));

                tca.adjustColumns();
            }

        }
        else {
            // TODO: other dingbats

            btn_ExternalUrl.setEnabled(false);
        }
    }

    void doSearch() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                searchWindow = new SearchWindow(guiAdapter.getConfig());

            }
        });

    }

    void doNextMessage(final boolean next) {
        checkAndGetNextMessage(next);

        btnNextButton.setEnabled(checkNextMessage(true));
        btnPrevButton.setEnabled(checkNextMessage(false));

    }

    void doClose() {

    }

    void doAbout() {

    }

    void doAddTokenToStopword() {
        if ((selectedToken != null) && (selectedToken.length() > 1)) guiAdapter.addStopWord(selectedToken);
    }

    void doAddTokenToNSWH() {
        if ((selectedToken != null) && (selectedToken.length() > 1)) guiAdapter.addNSWH(selectedToken);
    }

    void doAddTokenToIA() {
        if ((selectedToken != null) && (selectedToken.length() > 1)) guiAdapter.addIA(selectedToken);
    }

    void doAddTokenToNoHit() {
        if (((selectedToken != null) && (selectedToken.length() > 1)) && ((selectedFieldContent != null) && (selectedFieldContent.length() > 1)))

            guiAdapter.addNoHit(selectedFieldContent, selectedToken);
    }

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {

        String configFilename = "SanctionChecker.xml";
        boolean initialized = false;
        ApplicationContext context = null;
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

        // important to do this here !!
        final EntityManagementConfig emmCfg = (EntityManagementConfig) context.getBean("EntityManagement");

        GUIConfigHolder config = (GUIConfigHolder) context.getBean("GUIConfig");

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final ApplicationWindow window = new ApplicationWindow(config);

                    window.initializeGUI();
                    window.frmCaseManagement.setVisible(true);
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addWordHitPopup(Component component, final JPopupMenu popupWordHits) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            private void showMenu(MouseEvent e) {

                int row = tableWordHits.rowAtPoint(new Point(e.getX(), e.getY()));  // SwingUtilities.convertPoint(tableWordHits,

                selectedToken = (String) tableWordHits.getValueAt(row, 2); // TODO: this is crumpy !!
                selectedFieldContent = (String) tableWordHits.getValueAt(row, 4);// TODO: this is crumpy !!

                // System.out.println("RowId: " + row + " = " + text);
                if (selectedToken != null) {
                    mntmAddToStopwords.setText("add '" + selectedToken + "' to Stopwords");
                    mntmAddToNon.setText("add '" + selectedToken + "' to phrase only");
                    mntmAddToIA.setText("add '" + selectedToken + "' to Index Exclusion");
                    if (selectedFieldContent != null) {
                        boolean tokensAreGleich = selectedFieldContent.equals(selectedToken);
                        if (!tokensAreGleich) {
                            mntmAddToNoHit.setText("'" + selectedToken + "' does NEVER match '" + selectedFieldContent + "'");
                        }
                        mntmAddToNoHit.setVisible(!tokensAreGleich);
                    }
                }

                popupWordHits.show(e.getComponent(), e.getX(), e.getY());

            }
        });
    }
}
