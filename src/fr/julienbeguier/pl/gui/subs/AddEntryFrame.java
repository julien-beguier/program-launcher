package fr.julienbeguier.pl.gui.subs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;

import fr.julienbeguier.pl.config.Configuration;
import fr.julienbeguier.pl.gui.IconLoader;
import fr.julienbeguier.pl.gui.LauncherPanel;
import fr.julienbeguier.pl.json.ProgramElementJson;

public class AddEntryFrame extends AEntryFrame {

	private static final long serialVersionUID = -2873844258681771170L;

	// CONST
	private final String EMPTY = "";

	// GUI
	private JTextField nameInput;
	private JTextField pathInput;
	private JTextField optionalIconPathInput;
	private Dimension frameSize = new Dimension(500, 225);
	private Dimension fieldSize = new Dimension(500, 30);
	private Dimension inputAndButtonSize = new Dimension(450, 30);


	public AddEntryFrame(ImageIcon icon, LauncherPanel lp) {
		super(icon);
		this.setTitle("Add entry");
		this.setSize(this.frameSize);
		this.setResizable(false);
		this.setVisible(true);

		JLabel nameLabel = new JLabel("Name");
		this.nameInput = new JTextField();
		this.nameInput.setPreferredSize(this.fieldSize);

		JLabel pathLabel = new JLabel("Path");
		JPanel pathPanel = new JPanel();
		this.pathInput = new JTextField();
		this.pathInput.setPreferredSize(this.inputAndButtonSize);
		JButton pathButton = new JButton("...");
		pathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.setDialogTitle("Select the binary from explorer");
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				// Open the explorer dialog
				int returnValue = fileChooser.showDialog(null, "Select");
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String pathString = fileChooser.getSelectedFile().getPath();
					pathInput.setText(pathString);

					boolean binIsExecutable = Files.isExecutable(Paths.get(pathString));
					if (!binIsExecutable) {
						IconLoader iconLoader = IconLoader.getInstance();
						JOptionPane.showMessageDialog(null, "File selected :\n" + pathString + " is not executable.", "Warning", JOptionPane.WARNING_MESSAGE, iconLoader.warningIcon);
					}
				}
			}
		});
		pathPanel.setLayout(new BorderLayout());
		pathPanel.setPreferredSize(this.fieldSize);
		pathPanel.add(this.pathInput, BorderLayout.WEST);
		pathPanel.add(pathButton, BorderLayout.EAST);

		JLabel optionalIconPathLabel = new JLabel("(Optional) Icon path");
		JPanel iconPanel = new JPanel();
		this.optionalIconPathInput = new JTextField();
		this.optionalIconPathInput.setPreferredSize(this.inputAndButtonSize);
		// TODO add an image filtered explorer button

		JButton iconButton = new JButton("...");
		iconButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.setDialogTitle("Select an image from explorer");
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				// Open the explorer dialog
				int returnValue = fileChooser.showDialog(null, "Select");
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String iconPathString = fileChooser.getSelectedFile().getPath();
					optionalIconPathInput.setText(iconPathString);
				}
			}
		});
		iconPanel.setLayout(new BorderLayout());
		iconPanel.setPreferredSize(this.fieldSize);
		iconPanel.add(this.optionalIconPathInput, BorderLayout.WEST);
		iconPanel.add(iconButton, BorderLayout.EAST);

		JPanel fieldInputPanel = new JPanel();
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		Border loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
		Border compoundBorder = BorderFactory.createCompoundBorder(raisedBevelBorder, loweredBevelBorder);

		fieldInputPanel.setLayout(new BoxLayout(fieldInputPanel, BoxLayout.PAGE_AXIS));
		fieldInputPanel.setBorder(compoundBorder);

		fieldInputPanel.add(nameLabel);
		fieldInputPanel.add(this.nameInput);
		fieldInputPanel.add(pathLabel);
		fieldInputPanel.add(pathPanel);
		fieldInputPanel.add(optionalIconPathLabel);
		fieldInputPanel.add(iconPanel);

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

					// Add new entry to configuration file
					JSONObject jsonPe = new JSONObject(pej);
					Configuration config = Configuration.getInstance();
					config.getPrograms().put(jsonPe);
					config.writeConfiguration();

					/* Adding the new entry to main panel is not
					 * needed since refreshProgramList() is called
					 * instead
					 */
					lp.refreshProgramList();

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
				// Reset input fields
				resetInputFields();

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
		String oIconPath = this.optionalIconPathInput.getText();

		IconLoader iconLoader = IconLoader.getInstance();
		if (name.isBlank() || path.isBlank()) {
			JOptionPane.showMessageDialog(null, "Please fill in the name and path fields", "Error", JOptionPane.ERROR_MESSAGE, iconLoader.errorIcon);
			return false;
		}

		if (!oIconPath.isBlank()) {
			File icon = new File(oIconPath);
			if (!icon.exists()) {
				JOptionPane.showMessageDialog(null, "The image selected :\n" + oIconPath + " does not exists !", "Warning", JOptionPane.WARNING_MESSAGE, iconLoader.warningIcon);
			}
		}

		return true;
	}

	private void resetInputFields() {
		this.nameInput.setText(EMPTY);
		this.pathInput.setText(EMPTY);
		this.optionalIconPathInput.setText(EMPTY);
	}
}
