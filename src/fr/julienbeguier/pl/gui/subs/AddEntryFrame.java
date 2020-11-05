package fr.julienbeguier.pl.gui.subs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.json.JSONObject;

import fr.julienbeguier.pl.config.Configuration;
import fr.julienbeguier.pl.gui.IconLoader;
import fr.julienbeguier.pl.gui.LauncherPanel;
import fr.julienbeguier.pl.gui.ProgramElement;
import fr.julienbeguier.pl.json.ProgramElementJson;

public class AddEntryFrame extends AEntryFrame {

	private static final long serialVersionUID = -2873844258681771170L;

	// CONST
	private final String EMPTY = "";

	// GUI
	private JTextField nameInput;
	private JTextField pathInput;
	private JTextField optionalIconPathInput;

	public AddEntryFrame(ImageIcon icon, LauncherPanel lp) {
		super(icon);
		this.setTitle("Add entry");
		this.setSize(500, 225);
		this.setResizable(false);
		this.setVisible(true);

		JLabel nameLabel = new JLabel("Name");
		this.nameInput = new JTextField();

		JLabel pathLabel = new JLabel("Path");
		this.pathInput = new JTextField();
		// TODO add an executable filtered explorer button

		JLabel optionalIconPathLabel = new JLabel("(Optional) Icon path");
		this.optionalIconPathInput = new JTextField();
		// TODO add an image filtered explorer button

		JPanel fieldInputPanel = new JPanel();

		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		Border loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
		Border compoundBorder = BorderFactory.createCompoundBorder(raisedBevelBorder, loweredBevelBorder);

		fieldInputPanel.setLayout(new BoxLayout(fieldInputPanel, BoxLayout.PAGE_AXIS));
		fieldInputPanel.setBorder(compoundBorder);

		fieldInputPanel.add(nameLabel);
		fieldInputPanel.add(this.nameInput);
		fieldInputPanel.add(pathLabel);
		fieldInputPanel.add(this.pathInput);
		fieldInputPanel.add(optionalIconPathLabel);
		fieldInputPanel.add(this.optionalIconPathInput);

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				boolean inputsAreValid = validateInputs();
				if (inputsAreValid) {
					/* Because id starts at 0, if we have 4 programs
					 * in the list, the last Component added will have
					 * id = 3 but will be Component n°4 so we can use
					 * the number of components added to determine the
					 * next id
					 */
					int id = lp.getComponentCount();

					// Build new entry
					String name = nameInput.getText();
					String path = pathInput.getText();
					String oIconPath = optionalIconPathInput.getText();
					ProgramElementJson pej = new ProgramElementJson(id, name, path, oIconPath);
					ProgramElement pe = new ProgramElement(pej);

					// Add new entry to main panel
					lp.addElement(pe);

					// Add new entry to configuration file
					JSONObject jsonPe = new JSONObject(pej);
					Configuration config = Configuration.getInstance();
					config.getPrograms().put(jsonPe);
					config.writeConfiguration();

					// Reset input fields
					resetInputFields();

					setVisible(false);
				}
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
			}
		});
		JPanel actionsPanel = new JPanel();
		actionsPanel.add(okButton);
		actionsPanel.add(cancelButton);

		this.getContentPane().add(fieldInputPanel, BorderLayout.NORTH);
		this.getContentPane().add(actionsPanel, BorderLayout.SOUTH);
	}

	private boolean validateInputs() {
		String name = this.nameInput.getText();
		String path = this.pathInput.getText();
		String oIconPath = this.optionalIconPathInput.getText(); // TODO check if icon exists

		IconLoader iconLoader = IconLoader.getInstance();
		if (name.isEmpty() || path.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill in the name and path fields", "Error", JOptionPane.ERROR_MESSAGE, iconLoader.errorIcon);
			return false;
		}

		return true;
	}

	private void resetInputFields() {
		this.nameInput.setText(EMPTY);
		this.pathInput.setText(EMPTY);
		this.optionalIconPathInput.setText(EMPTY);
	}
}
