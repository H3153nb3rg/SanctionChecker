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
import javax.swing.WindowConstants;
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

    public static void main(final String[] args) {

        final JComponent[] components = { new JTextField(15), new JTextField(10), new JTextField(8), new JSpinner(new SpinnerNumberModel(1, 0, 10, 1)),
                new JSpinner(new SpinnerNumberModel(9.95, 0d, 100d, .01)), new JSpinner(new SpinnerNumberModel(9.95, 0d, 1000d, .01)), new JSpinner(new SpinnerNumberModel(9.95, 0d, 100d, .01)),
                new JSpinner(new SpinnerNumberModel(9.95, 0d, 1000d, .01)), new JSpinner(new SpinnerNumberModel(9.95, 0d, 100d, .01)), new JSpinner(new SpinnerNumberModel(9.95, 0d, 1000d, .01)) };

        final String[] labels = { "Product Name:", "Product Unit Name:", "Purchase Date:", "Quantity:", "Price Per Unit:", "Total Price:", "Discount:", "Total:", "VAT:", "Grand Total:" };

        try {
            final JComponent labelsAndFields = getTwoColumnLayout(labels, components);
            final DetailsDialog dialog = new DetailsDialog("Cool or not", labelsAndFields);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
        catch (final Exception e) {
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
    public static JComponent getTwoColumnLayout(final JLabel[] labels, final JComponent[] fields, final boolean addMnemonics) {
        if (labels.length != fields.length) {
            final String s = labels.length + " labels supplied for " + fields.length + " fields!";
            throw new IllegalArgumentException(s);
        }
        final JComponent panel = new JPanel();
        final GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);
        // Create a sequential group for the horizontal axis.
        final GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        final GroupLayout.Group yLabelGroup = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        hGroup.addGroup(yLabelGroup);
        final GroupLayout.Group yFieldGroup = layout.createParallelGroup();
        hGroup.addGroup(yFieldGroup);
        layout.setHorizontalGroup(hGroup);
        // Create a sequential group for the vertical axis.
        final GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        layout.setVerticalGroup(vGroup);

        final int p = GroupLayout.PREFERRED_SIZE;
        // add the components to the groups
        for (final JLabel label : labels) {
            yLabelGroup.addComponent(label);
        }
        for (final Component field : fields) {
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

    private final static void addMnemonics(final JLabel[] labels, final JComponent[] fields) {
        final Map<Character, Object> m = new HashMap<Character, Object>();
        for (int ii = 0; ii < labels.length; ii++) {
            labels[ii].setLabelFor(fields[ii]);
            final String lwr = labels[ii].getText().toLowerCase();
            for (int jj = 0; jj < lwr.length(); jj++) {
                final char ch = lwr.charAt(jj);
                if ((m.get(ch) == null) && Character.isLetterOrDigit(ch)) {
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
    public static JComponent getTwoColumnLayout(final String[] labelStrings, final JComponent[] fields) {
        final JLabel[] labels = new JLabel[labelStrings.length];
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
    public static JComponent getTwoColumnLayout(final JLabel[] labels, final JComponent[] fields) {
        return getTwoColumnLayout(labels, fields, true);
    }

    /**
     * Create the dialog.
     */

    public DetailsDialog(final String caption, final JComponent labelsAndFields) {
        setBounds(100, 100, 450, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // add dynamic part
        final JComponent coreForm = new JPanel(new BorderLayout(5, 5));
        coreForm.add(new JLabel(caption, SwingConstants.CENTER), BorderLayout.NORTH);
        coreForm.add(labelsAndFields, BorderLayout.CENTER);

        contentPanel.add(coreForm, BorderLayout.CENTER);

        {
            final JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                final JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");

                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {

                        dispatchEvent(new WindowEvent(DetailsDialog.this, WindowEvent.WINDOW_CLOSING));
                    }
                });

                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                final JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {

                        dispatchEvent(new WindowEvent(DetailsDialog.this, WindowEvent.WINDOW_CLOSING));
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

}
