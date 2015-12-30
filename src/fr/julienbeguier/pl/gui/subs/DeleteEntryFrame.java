package fr.julienbeguier.pl.gui.subs;

import javax.swing.ImageIcon;

public class DeleteEntryFrame extends AEntryFrame {

	private static final long serialVersionUID = -7034148138064108201L;

	public DeleteEntryFrame(ImageIcon icon) {
		super(icon);
		this.setTitle("Delete entry");
		this.setSize(500, 400);
		this.setResizable(false);
		this.setVisible(true);
	}
}
