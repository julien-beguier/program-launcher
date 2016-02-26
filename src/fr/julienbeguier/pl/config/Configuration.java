package fr.julienbeguier.pl.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Configuration {

	public static Configuration instance = null;

	// CONST VARS
	private final String	CONFIGURATION_PATH = "/config.json";
	private final String	CONF_KEY_LAUNCHER_INFOS = "launcher_infos";
	private final String	CONF_KEY_LAUNCHER_INFOS_VERSION = "version";
	private final String	CONF_KEY_PROGRAMS = "programs";
	private final String	CONF_KEY_PROGRAM_ID = "id";
	private final String	CONF_KEY_PROGRAM_NAME = "name";
	private final String	CONF_KEY_PROGRAM_PATH = "path";
	private final String	CONF_KEY_PROGRAM_ICONPATH = "icon_path";
	private final String	CONF_KEY_SETTINGS = "settings";
	private final String	CONF_KEY_SETTINGS_QUIT_ON_PROGRAM_LAUNCHED = "quit_on_program_launched";

	// CONF OBJ
	private JSONObject	configurationObj;
	private JSONObject	launcherInfosObj;
	private JSONArray	programs;
	private int			nPrograms;
	private JSONObject	settingsObj;

	private Configuration() {
		URL confFile = this.getClass().getResource(this.CONFIGURATION_PATH);
		String confFileContent = null;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(confFile.openStream()));
			String line;
			StringBuffer fileContent = new StringBuffer(); 
			while ((line = br.readLine()) != null) {
				fileContent.append(line);
				fileContent.append('\r');
			}
			confFileContent = fileContent.toString().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (confFileContent == null) {
			System.err.println("Error, the program couldn't read the confifuartion file: \'" + this.CONFIGURATION_PATH + "\'");
			System.exit(-1);
		}

		try {
			this.configurationObj = new JSONObject(confFileContent);
			this.launcherInfosObj = this.configurationObj.getJSONObject(this.CONF_KEY_LAUNCHER_INFOS);
			this.programs = this.configurationObj.getJSONArray(this.CONF_KEY_PROGRAMS);
			this.nPrograms = this.programs.length();

			Settings settings = Settings.getInstance();
			this.settingsObj = this.configurationObj.getJSONObject(this.CONF_KEY_SETTINGS);
			settings.setQuitOnProgramLaunched(this.settingsObj.getBoolean(this.CONF_KEY_SETTINGS_QUIT_ON_PROGRAM_LAUNCHED));

			//			for (int i = 0; i < this.nEclipse; i++) {
			//				System.out.println("OBJ : " + eclipses.getJSONObject(i));
			//			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static Configuration getInstance() {
		if (instance == null)
			instance = new Configuration();
		return instance;
	}

	public JSONObject getConfiguration() {
		return this.configurationObj;
	}

	public JSONObject getProgramInfos() {
		return this.launcherInfosObj;
	}

	public JSONArray getPrograms() {
		return this.programs;
	}

	public int getNProgram() {
		return this.nPrograms;
	}

	public final String getConfKeyProgramInfosVersion() {
		return this.CONF_KEY_LAUNCHER_INFOS_VERSION;
	}

	public final String getConfKeyProgramId() {
		return this.CONF_KEY_PROGRAM_ID;
	}

	public final String getConfKeyProgramName() {
		return this.CONF_KEY_PROGRAM_NAME;
	}

	public final String getConfKeyProgramPath() {
		return this.CONF_KEY_PROGRAM_PATH;
	}

	public final String getConfKeyProgramIconPath() {
		return this.CONF_KEY_PROGRAM_ICONPATH;
	}
}
