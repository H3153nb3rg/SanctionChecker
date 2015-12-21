package at.jps.sl.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DetailsDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = -2034472533894307267L;
    private final JPanel      contentPanel     = new JPanel();

    /**
     * Launch the application.
     */

    public static void main(String[] args) {

        JComponent[] components = { new JTextField(15), new JTextField(10), new JTextField(8), new JSpinner(new SpinnerNumberModel(1, 0, 10, 1)),
                new JSpinner(new SpinnerNumberModel(9.95, 0d, 100d, .01)), new JSpinner(new SpinnerNumberModel(9.95, 0d, 1000d, .01)), new JSpinner(new SpinnerNumberModel(9.95, 0d, 100d, .01)),
                new JSpinner(new SpinnerNumberModel(9.95, 0d, 1000d, .01)), new JSpinner(new SpinnerNumberModel(9.95, 0d, 100d, .01)), new JSpinner(new SpinnerNumberModel(9.95, 0d, 1000d, .01)) };

        String[] labels = { "Product Name:", "Product Unit Name:", "Purchase Date:", "Quantity:", "Price Per Unit:", "Total Price:", "Discount:", "Total:", "VAT:", "Grand Total:" };

        try {
            JComponent labelsAndFields = getTwoColumnLayout(labels, components);
            DetailsDialog dialog = new DetailsDialog("Cool or not", labelsAndFields);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Provides a JPanel with two columns (labels & fields) laid out using GroupLayout. The arrays must be of equal size. Typical fields would be single line textual/input components such as
     * JTextField, JPasswordField, JFormattedTextField, JSpinner, JComboBox, JCheckBox.. & the multi-line components wrapped in a JScrollPane - JTextArea or (at a stretch) JList or JTable.
     *
     * @param labels
     *            The first column contains labels.
     * @param fields
     *            The last column contains fields.
     * @param addMnemonics
     *            Add mnemonic by next available letter in label text.
     * @return JComponent A JPanel with two columns of the components provided.
     */
    public static JComponent getTwoColumnLayout(JLabel[] labels, JComponent[] fields, boolean addMnemonics) {
        if (labels.length != fields.length) {
            String s = labels.length + " labels supplied for " + fields.length + " fields!";
            throw new IllegalArgumentException(s);
        }
        JComponent panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);
        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        GroupLayout.Group yLabelGroup = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        hGroup.addGroup(yLabelGroup);
        GroupLayout.Group yFieldGroup = layout.createParallelGroup();
        hGroup.addGroup(yFieldGroup);
        layout.setHorizontalGroup(hGroup);
        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        layout.setVerticalGroup(vGroup);

        int p = GroupLayout.PREFERRED_SIZE;
        // add the components to the groups
        for (JLabel label : labels) {
            yLabelGroup.addComponent(label);
        }
        for (Component field : fields) {
            yFieldGroup.addComponent(field, p, p, p);
        }
        for (int ii = 0; ii < labels.length; ii++) {
            vGroup.addGroup(layout.createParallelGroup().addComponent(labels[ii]).addComponent(fields[ii], p, p, p));
        }

        if (addMnemonics) {
            addMnemonics(labels, fields);
        }

        return panel;
    }

    private final static void addMnemonics(JLabel[] labels, JComponent[] fields) {
        Map<Character, Object> m = new HashMap<Character, Object>();
        for (int ii = 0; ii < labels.length; ii++) {
            labels[ii].setLabelFor(fields[ii]);
            String lwr = labels[ii].getText().toLowerCase();
            for (int jj = 0; jj < lwr.length(); jj++) {
                char ch = lwr.charAt(jj);
                if (m.get(ch) == null && Character.isLetterOrDigit(ch)) {
                    m.put(ch, ch);
                    labels[ii].setDisplayedMnemonic(ch);
                    break;
                }
            }
        }
    }

    /**
     * Provides a JPanel with two columns (labels & fields) laid out using GroupLayout. The arrays must be of equal size.
     *
     * @param labelStrings
     *            Strings that will be used for labels.
     * @param fields
     *            The corresponding fields.
     * @return JComponent A JPanel with two columns of the components provided.
     */
    public static JComponent getTwoColumnLayout(String[] labelStrings, JComponent[] fields) {
        JLabel[] labels = new JLabel[labelStrings.length];
        for (int ii = 0; ii < labels.length; ii++) {
            labels[ii] = new JLabel(labelStrings[ii]);
        }
        return getTwoColumnLayout(labels, fields, false);
    }

    /**
     * Provides a JPanel with two columns (labels & fields) laid out using GroupLayout. The arrays must be of equal size.
     *
     * @param labels
     *            The first column contains labels.
     * @param fields
     *            The last column contains fields.
     * @return JComponent A JPanel with two columns of the components provided.
     */
    public static JComponent getTwoColumnLayout(JLabel[] labels, JComponent[] fields) {
        return getTwoColumnLayout(labels, fields, true);
    }

    /**
     * Create the dialog.
     */

    public DetailsDialog(final String caption, JComponent labelsAndFields) {
        setBounds(100, 100, 450, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // add dynamic part
        JComponent coreForm = new JPanel(new BorderLayout(5, 5));
        coreForm.add(new JLabel(caption, SwingConstants.CENTER), BorderLayout.NORTH);
        coreForm.add(labelsAndFields, BorderLayout.CENTER);

        contentPanel.add(coreForm, BorderLayout.CENTER);

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");

                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        dispatchEvent(new WindowEvent(DetailsDialog.this, WindowEvent.WINDOW_CLOSING));
                    }
                });

                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        dispatchEvent(new WindowEvent(DetailsDialog.this, WindowEvent.WINDOW_CLOSING));
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

}
