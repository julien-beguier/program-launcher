package fr.julienbeguier.pl.json;

public class ProgramElementJson {

	// DATA
	private int id;
	private String name;
	private String binaryPath;
	private String iconPath;
	
	public ProgramElementJson(int id, String name, String binaryPath, String iconPath) {
		this.id = id;
		this.name = name;
		this.binaryPath = binaryPath;
		this.iconPath = iconPath;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getBinaryPath() {
		return this.binaryPath;
	}

	public String getIconPath() {
		return this.iconPath;
	}
}
