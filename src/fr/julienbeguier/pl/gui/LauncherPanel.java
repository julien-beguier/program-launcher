package fr.julienbeguier.pl.gui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import fr.julienbeguier.pl.config.Configuration;

public class LauncherPanel extends JPanel {

	private static final long serialVersionUID = -407878933197950750L;

	// GUI

	public LauncherPanel(int width, int height) {
		super();
		this.setLayout(new GridLayout(0, 1));

		refreshProgramList();
	}

	public void refreshProgramList() {
		this.removeAll();

		// Build or rebuild the program list
		Configuration config = Configuration.getInstance();
		List<ProgramElement> programList = config.getProgramList();

		for (ProgramElement pe: programList) {
			this.add(pe);
		}
		this.validate();
	}

	public void addElement(ProgramElement pe) {
		this.add(pe);
		this.validate();
	}

	public void removeElement(ProgramElement pe) {
		this.remove(pe);
		this.validate();
	}
}
