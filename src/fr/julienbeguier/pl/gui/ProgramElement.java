package fr.julienbeguier.pl.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProgramElement extends JPanel {

	private static final long serialVersionUID = 8537448547823870703L;

	// GUI
	private final JLabel label;
	private final JButton button;
	private ImageIcon icon;

	private final String binaryPath;

	public ProgramElement(String name, String binaryPath, String iconPath) {
		super();
		this.icon = new ImageIcon(iconPath);
		this.binaryPath = binaryPath;
		this.label = new JLabel(name);

		Image img = icon.getImage();
		Image rImg = img.getScaledInstance(64, 64,  Image.SCALE_SMOOTH);
		this.icon = new ImageIcon(rImg);
		this.label.setIcon(icon);

		this.button = new JButton("Launch");
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println("Launching : " + binaryPath);
//				try {
//					Runtime.getRuntime().exec(binaryPath);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				if (Settings.getInstance().quitOnProgramLaunched())
//					System.exit(0);
			}
		});

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.setPreferredSize(new Dimension(300, 80));

		this.add(label);
		this.add(this.button, BorderLayout.EAST);
	}

	public String getName() {
		return this.button.getText();
	}

	public String getBinaryPath() {
		return this.binaryPath;
	}

	public JButton getButton() {
		return this.button;
	}
}
