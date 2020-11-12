package fr.julienbeguier.pl.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.julienbeguier.pl.config.Configuration;

public class LauncherPanel extends JPanel {

	private static final long serialVersionUID = -407878933197950750L;

	private JFrame mainFrame;

	public LauncherPanel(JFrame parentComponent) {
		super();
		this.setLayout(new GridLayout(0, 1));

		this.mainFrame = parentComponent;

		refreshProgramList();
	}

	public void refreshProgramList() {
		this.removeAll();

		// Build or rebuild the program list
		List<ProgramElement> programList = Configuration.getInstance().getProgramList();
		programList.stream().forEach(p -> this.add(p));

		int newPanelHeight = programList.size() * ProgramElement.cellSize.height;
		Dimension newPanelDimension = new Dimension(ProgramElement.cellSize.width, newPanelHeight);
		this.setSize(newPanelDimension);
		this.setPreferredSize(newPanelDimension);

		// Re-validate the root Component in order to see changes
		this.mainFrame.pack();
		this.mainFrame.validate();
	}
}
