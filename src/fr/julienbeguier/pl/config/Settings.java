package fr.julienbeguier.pl.config;

public class Settings {

	public static Settings instance = null;

	// Settings
	private boolean quitOnProgramLaunched;

	private Settings() {
	}

	public static Settings getInstance() {
		if (instance == null)
			instance = new Settings();
		return instance;
	}

	public void setQuitOnProgramLaunched(boolean value) {
		this.quitOnProgramLaunched = value;
	}

	public boolean getQuitOnProgramLaunched() {
		return this.quitOnProgramLaunched;
	}
}
