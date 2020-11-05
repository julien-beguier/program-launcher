package fr.julienbeguier.pl.gui;

import javax.swing.ImageIcon;

public class IconLoader {

	public static IconLoader instance = null;

	// Icons
	public ImageIcon programIcon;
	public ImageIcon aboutIcon;
	public ImageIcon programMenuIcon;
	public ImageIcon addProgramMenuIcon;
	public ImageIcon removeProgramMenuIcon;
	public ImageIcon settingsMenuIcon;
	public ImageIcon saveSettingsMenuIcon;
	public ImageIcon exitMenuIcon;
	public ImageIcon errorIcon;

	private IconLoader() {
	}

	public static IconLoader getInstance() {
		if (instance == null)
			instance = new IconLoader();
		return instance;
	}
	
	public void loadIcons() {
		this.programIcon = new ImageIcon(getClass().getResource("/launcher_logo_512px.png"));
		this.aboutIcon = new ImageIcon(getClass().getResource("/launcher_logo_128px.png"));
		this.programMenuIcon = new ImageIcon(getClass().getResource("/launcher_logo_16px.png"));
		this.addProgramMenuIcon = new ImageIcon(getClass().getResource("/add_16px.png"));
		this.removeProgramMenuIcon = new ImageIcon(getClass().getResource("/remove_16px.png"));
		this.settingsMenuIcon = new ImageIcon(getClass().getResource("/manage_16px.png"));
		this.saveSettingsMenuIcon = new ImageIcon(getClass().getResource("/save_16px.png"));
		this.exitMenuIcon = new ImageIcon(getClass().getResource("/exit_16px.png"));
		this.errorIcon = new ImageIcon(getClass().getResource("/dialog_error_64px.png"));
	}

}
