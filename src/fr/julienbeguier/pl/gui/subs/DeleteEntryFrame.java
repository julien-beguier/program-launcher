package fr.julienbeguier.pl.gui.subs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import fr.julienbeguier.pl.config.Configuration;
import fr.julienbeguier.pl.gui.LauncherPanel;
import fr.julienbeguier.pl.gui.ProgramElement;

public class DeleteEntryFrame extends AEntryFrame {

	private static final long serialVersionUID = -7034148138064108201L;

	// GUI
	private final Dimension frameDimension = new Dimension(500, 330);
	private final Dimension scrollPanelDimension = new Dimension(440, 300);
	private ScrollPane scrollPane;

	// DATA
	private JList<ProgramListElement> programsListed;

	public DeleteEntryFrame(ImageIcon icon, LauncherPanel lp) {
		super(icon);
		this.setTitle("Delete entry");
		this.setSize(frameDimension);
		this.setResizable(false);
		this.setVisible(true);

		JPanel actionsContent = new JPanel();
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				// Get selected entry
				ProgramListElement ple = programsListed.getSelectedValue();

				// if none selected, abort delete action
				if (null == ple)
					return;

				ProgramElement pe = ple.getProgramElement();

				// Remove entry from scroll panel list
				programsListed.remove(ple);

				// Remove entry from configuration file
				Configuration config = Configuration.getInstance();
				config.removeElementAtId(pe.getProgramElementJson().getId());

				/* Removing the entry from main panel is
				 * not needed since refreshProgramList()
				 * is called on Done Button action
				 */

				// Refresh the delete program list
				refreshDeleteProgramList();
			}
		});
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				// Write modification to configuration file
				Configuration.getInstance().writeConfiguration();

				// Refresh the main program list
				lp.refreshProgramList();

				setVisible(false);
			}
		});

		actionsContent.setSize(100, 50);

		actionsContent.add(deleteButton);
		actionsContent.add(doneButton);

		this.scrollPane = new ScrollPane();

		// Build the program list
		Configuration config = Configuration.getInstance();
		this.programsListed = config.getProgramList(this.scrollPanelDimension.width);

		Dimension customScrollPanelDimension = calculateCustomScrollPanelDimension(this.scrollPanelDimension, actionsContent.getWidth(), actionsContent.getHeight());
		this.scrollPane.setPreferredSize(customScrollPanelDimension);
		this.scrollPane.setSize(customScrollPanelDimension);
		this.scrollPane.add(this.programsListed);

		this.getContentPane().add(scrollPane, BorderLayout.NORTH);
		this.getContentPane().add(actionsContent, BorderLayout.SOUTH);
	}

	private Dimension calculateCustomScrollPanelDimension(Dimension scrollPanelDimension, int actionPanelWidth, int actionPanelHeight) {
		int customScrollWidth = (int) (scrollPanelDimension.getWidth() - actionPanelWidth);
		int customScrollHeight = (int) (scrollPanelDimension.getHeight() - actionPanelHeight);
		return new Dimension(customScrollWidth, customScrollHeight);
	}

	public void refreshDeleteProgramList() {
		this.scrollPane.removeAll();

		// Rebuild the program list
		Configuration config = Configuration.getInstance();
		this.programsListed = config.getProgramList(this.scrollPanelDimension.width);
		this.scrollPane.add(this.programsListed);
		this.validate();
	}
}
