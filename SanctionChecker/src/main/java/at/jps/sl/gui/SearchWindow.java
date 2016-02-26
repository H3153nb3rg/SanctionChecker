package at.jps.sl.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sl.gui.model.watchlist.SearchTableModelHandler;
import at.jps.sl.gui.util.GUIConfigHolder;

public class SearchWindow extends JFrame {

    /**
     * 
     */
    private static final long      serialVersionUID = 2034573157046769253L;
    private JPanel                 contentPane;
    private JTextField             textField;
    private JTable                 table_ListEntries;

    private static GUIConfigHolder config;

    // private static SearchWindow searchWindow = null;

    class ValueTablesRenderer extends DefaultTableCellRenderer {
        /**
         *
         */

        Color                     odd              = new Color(247, 253, 255);
        Color                     even             = new java.awt.Color(255, 255, 255);

        private static final long serialVersionUID = 3097445954446635745L;

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
            final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setBorder(new CompoundBorder(new EmptyBorder(new Insets(1, 4, 1, 4)), getBorder()));

            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(odd);
                }
                else {
                    c.setBackground(even);
                }

            }

            return c;
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

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

        config = (GUIConfigHolder) context.getBean("GUIConfig");

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // searchWindow =
                    new SearchWindow(config);
                    // frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SearchWindow(GUIConfigHolder configuration) {

        config = configuration;

        initializeGUI();
        setVisible(true);
    }

    private void initializeGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final Preferences root = Preferences.userRoot();
        final Preferences node = root.node("/at/jps/swing/sl/searchwindow");
        final int left = node.getInt("left", 100);
        final int top = node.getInt("top", 100);
        final int width = node.getInt("width", 800);
        final int height = node.getInt("height", 600);

        setBounds(left, top, width, height);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                node.putInt("left", getX());
                node.putInt("top", getY());
                node.putInt("width", getWidth());
                node.putInt("height", getHeight());
            }
        });

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmClose = new JMenuItem("Close");
        mntmClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(SearchWindow.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        mnFile.add(mntmClose);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        JMenuItem mntmabout = new JMenuItem("About");
        mnHelp.add(mntmabout);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        JPanel panel_watchlistsearch = new JPanel();
        tabbedPane.addTab("Watchlist Search", null, panel_watchlistsearch, null);
        panel_watchlistsearch.setLayout(new BorderLayout(0, 0));

        JPanel panel_search = new JPanel();
        panel_watchlistsearch.add(panel_search, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Searchpattern");
        panel_search.add(lblNewLabel);

        textField = new JTextField();
        panel_search.add(textField);
        textField.setColumns(20);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final String searchPattern = textField.getText();

                if ((searchPattern != null) && (searchPattern.trim().length() >= 2)) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            doSearch(searchPattern);
                        }
                    });
                }
            }

        });
        panel_search.add(btnSearch);

        JPanel panel_result = new JPanel();
        panel_result.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_watchlistsearch.add(panel_result, BorderLayout.CENTER);
        panel_result.setLayout(new BorderLayout(0, 0));

        table_ListEntries = new JTable();
        table_ListEntries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_ListEntries.setAutoCreateRowSorter(true);
        table_ListEntries.setFillsViewportHeight(true);

        panel_result.add(new JScrollPane(table_ListEntries), BorderLayout.CENTER);

        for (String key : config.getValueLists().keySet()) {
            JPanel panel_valueList = new JPanel();
            tabbedPane.addTab(key, null, panel_valueList, null);
            panel_valueList.setLayout(new BorderLayout(0, 0));

            ValueListHandler valListhandler = config.getValueLists().get(key);

            JTable table_valueList = new JTable();
            table_valueList.setDefaultRenderer(String.class, new ValueTablesRenderer());
            table_valueList.setModel(SearchTableModelHandler.generateValueListTableModel(valListhandler));
            table_valueList.setAutoCreateRowSorter(true);
            table_valueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table_valueList.setFillsViewportHeight(true);

            panel_valueList.add(new JScrollPane(table_valueList));
            addSelectionListener(table_valueList, valListhandler.getListName());

        }

        for (String key : config.getReferenceLists().keySet()) {
            JPanel panel_referenzList = new JPanel();
            tabbedPane.addTab(key, null, panel_referenzList, null);
            panel_referenzList.setLayout(new BorderLayout(0, 0));

            JTable table_valueList = new JTable();

            ReferenceListHandler refListhandler = config.getReferenceLists().get(key);

            table_valueList.setDefaultRenderer(String.class, new ValueTablesRenderer());
            table_valueList.setModel(SearchTableModelHandler.generateReferenceListTableModel(refListhandler));
            table_valueList.setAutoCreateRowSorter(true);
            table_valueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table_valueList.setFillsViewportHeight(true);

            panel_referenzList.add(new JScrollPane(table_valueList));
            addSelectionListener(table_valueList, refListhandler.getListName());

        }

        // show opti 1 & 2
        JPanel panel_TxNoHitoptiList = new JPanel();
        tabbedPane.addTab("TxNoHitOptiList", null, panel_TxNoHitoptiList, null);
        panel_TxNoHitoptiList.setLayout(new BorderLayout(0, 0));

        final JTable table_TxNoHitOptiList = new JTable();

        table_TxNoHitOptiList.setDefaultRenderer(String.class, new ValueTablesRenderer());
        table_TxNoHitOptiList.setModel(SearchTableModelHandler.generateOptiListTableModel(config.getTxNoHitOptimizationListHandler().getValues()));
        table_TxNoHitOptiList.setAutoCreateRowSorter(true);
        table_TxNoHitOptiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_TxNoHitOptiList.setFillsViewportHeight(true);

        panel_TxNoHitoptiList.add(new JScrollPane(table_TxNoHitOptiList));
        addSelectionListener(table_TxNoHitOptiList, "TX No Hit Records");

        JPanel panel_TxHitOptiList = new JPanel();
        tabbedPane.addTab("TxHitOptiList", null, panel_TxHitOptiList, null);
        panel_TxHitOptiList.setLayout(new BorderLayout(0, 0));

        final JTable table_TxHitOptiList = new JTable();

        table_TxHitOptiList.setDefaultRenderer(String.class, new ValueTablesRenderer());
        table_TxHitOptiList.setModel(SearchTableModelHandler.generateOptiListTableModel(config.getTxHitOptimizationListHandler().getValues()));
        table_TxHitOptiList.setAutoCreateRowSorter(true);
        table_TxHitOptiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_TxHitOptiList.setFillsViewportHeight(true);

        panel_TxHitOptiList.add(new JScrollPane(table_TxHitOptiList));
        addSelectionListener(table_TxHitOptiList, "TX Hit Records");

        // show no hit
        JPanel panel_nohitList = new JPanel();
        tabbedPane.addTab("No Word Hits", null, panel_nohitList, null);
        panel_nohitList.setLayout(new BorderLayout(0, 0));

        JTable table_nohitList = new JTable();

        table_nohitList.setDefaultRenderer(String.class, new ValueTablesRenderer());
        table_nohitList.setModel(SearchTableModelHandler.generateNoHitListTableModel(config.getNoWordHitListHandler()));
        table_nohitList.setAutoCreateRowSorter(true);
        table_nohitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_nohitList.setFillsViewportHeight(true);

        panel_nohitList.add(new JScrollPane(table_nohitList));
        addSelectionListener(table_nohitList, "No Hits");

    }

    private void addSelectionListener(final JTable table, final String caption) {
        final ListSelectionModel selectionModelTX = table.getSelectionModel();

        selectionModelTX.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if ((!e.getValueIsAdjusting()) && (table.getSelectedRow() >= 0)) {
                    int resultRow = table.convertRowIndexToModel(table.getSelectedRow());

                    String[] labels = new String[table.getModel().getColumnCount()];
                    JComponent[] fields = new JComponent[table.getModel().getColumnCount()];

                    for (int i = 0; i < table.getModel().getColumnCount(); i++) {
                        labels[i] = table.getModel().getColumnName(i);
                        fields[i] = new JTextField((String) table.getModel().getValueAt(resultRow, i));
                    }

                    showDetailsDialog(caption, labels, fields);
                }
            }
        });

    }

    private void showDetailsDialog(String caption, String[] labels, JComponent[] components) {
        try {
            JComponent labelsAndFields = DetailsDialog.getTwoColumnLayout(labels, components);

            DetailsDialog dialog = new DetailsDialog(caption, labelsAndFields);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doSearch(final String searchPattern) {

        table_ListEntries.setModel(SearchTableModelHandler.doSearch(config.getWatchLists(), searchPattern));

        final int columnWidthMsg[] = { 100, 90, 300, 90 };

        for (int i = 0; i < columnWidthMsg.length - 1; i++) {
            final TableColumn column = table_ListEntries.getColumnModel().getColumn(i);
            column.setMinWidth(columnWidthMsg[i]);
            // if (i != 2) column.setMaxWidth(columnWidthMsg[i]);
            column.setPreferredWidth(columnWidthMsg[i]);
        }

    }
}
