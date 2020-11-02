package fr.julienbeguier.pl.config;

public class Settings {

	public static Settings instance = null;

	// Settings
	private boolean quit_on_program_launched;

	private Settings() {
	}

	public static Settings getInstance() {
		if (instance == null)
			instance = new Settings();
		return instance;
	}

	public void setQuitOnProgramLaunched(boolean value) {
		this.quit_on_program_launched = value;
	}

	public boolean getQuitOnProgramLaunched() {
		return this.quit_on_program_launched;
	}
}
