package fr.julienbeguier.pl.gui.subs;

import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public abstract class AEntryFrame extends JFrame {

	private static final long serialVersionUID = -8818230835094160612L;

	public AEntryFrame(ImageIcon icon) {
		this.setLocationRelativeTo(null);
		this.setIconImage(icon.getImage());

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				setVisible(true);
			}
		});
	}
}
