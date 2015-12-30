package fr.julienbeguier.pl.gui.subs;

import javax.swing.ImageIcon;

public class AddEntryFrame extends AEntryFrame {

	private static final long serialVersionUID = -2873844258681771170L;

	public AddEntryFrame(ImageIcon icon) {
		super(icon);
		this.setTitle("Add entry");
		this.setSize(300, 400);
		this.setResizable(false);
		this.setVisible(true);
	}
}
