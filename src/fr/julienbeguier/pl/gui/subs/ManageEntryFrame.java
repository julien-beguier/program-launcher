package fr.julienbeguier.pl.gui.subs;

import javax.swing.ImageIcon;

public class ManageEntryFrame extends AEntryFrame {

	private static final long serialVersionUID = -2873844258681771170L;

	public ManageEntryFrame(ImageIcon icon) {
		super(icon);
		this.setTitle("Manage entries");
		this.setSize(500, 400);
		this.setResizable(false);
		this.setVisible(true);
	}
}
