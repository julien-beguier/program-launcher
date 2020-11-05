package fr.julienbeguier.pl.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.julienbeguier.pl.config.Settings;
import fr.julienbeguier.pl.json.ProgramElementJson;

public class ProgramElement extends JPanel {

	private static final long serialVersionUID = 8537448547823870703L;

	// DATA
	private ProgramElementJson pej;

	// GUI
	private final JLabel label;
	private final JButton button;
	private ImageIcon icon;

	public ProgramElement(ProgramElementJson pej) {
		super();

		this.pej = pej;
		
		this.icon = new ImageIcon(this.pej.getIconPath()); // TODO
		this.label = new JLabel(this.pej.getName());

		Image img = icon.getImage();
		Image rImg = img.getScaledInstance(64, 64,  Image.SCALE_SMOOTH);
		this.icon = new ImageIcon(rImg);
		this.label.setIcon(icon);

		this.button = new JButton("Launch");
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					Runtime.getRuntime().exec(pej.getBinaryPath());
					System.out.println("Launching : " + pej.getBinaryPath());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error : Cannot find program : \"" + pej.getBinaryPath() + "\" !", "Error", JOptionPane.ERROR_MESSAGE, IconLoader.getInstance().errorIcon);
					return;
				}
				if (Settings.getInstance().getQuitOnProgramLaunched()) {
					System.out.println("Duty fulfilled, exiting ...");
					System.exit(0);
				}
			}
		});

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.setPreferredSize(new Dimension(300, 80));

		this.add(label);
		this.add(this.button, BorderLayout.EAST);
	}
	
	public ProgramElementJson getProgramElementJson() {
		return this.pej;
	}

	public JButton getButton() {
		return this.button;
	}
}
