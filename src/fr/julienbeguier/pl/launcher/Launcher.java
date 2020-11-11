package fr.julienbeguier.pl.launcher;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fr.julienbeguier.pl.config.Configuration;
import fr.julienbeguier.pl.gui.LauncherFrame;

public class Launcher {

	private final String PROGRAM_NAME = "Program Launcher";
	
	public Launcher() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		Configuration.getInstance(); // Read conf file now

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LauncherFrame window = new LauncherFrame(PROGRAM_NAME);
					window.setLocationRelativeTo(null);
					window.setVisible(true);
					window.initGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		new Launcher();
	}
}
