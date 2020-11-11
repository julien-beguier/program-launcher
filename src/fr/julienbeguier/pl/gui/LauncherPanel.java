package fr.julienbeguier.pl.gui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import fr.julienbeguier.pl.config.Configuration;

public class LauncherPanel extends JPanel {

	private static final long serialVersionUID = -407878933197950750L;

	public LauncherPanel() {
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

//		int newPanelHeight = programList.size() * ProgramElement.cellSize.height;
//		Dimension newPanelDimension = new Dimension(ProgramElement.cellSize.width, newPanelHeight);
//		this.setSize(newPanelDimension);
//		this.setPreferredSize(newPanelDimension);

		this.validate();
	}

	public void addElement(ProgramElement pe) {
		this.add(pe);
//		this.validate();
		this.refreshProgramList();
	}

	public void removeElement(ProgramElement pe) {
		this.remove(pe);
//		this.validate();
		this.refreshProgramList();
	}
}
