package fr.julienbeguier.pl.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.julienbeguier.pl.gui.ProgramElement;
import fr.julienbeguier.pl.gui.subs.ProgramListElement;
import fr.julienbeguier.pl.gui.subs.ProgramListElementCellRenderer;
import fr.julienbeguier.pl.json.ProgramElementJson;

public class Configuration {

	public static Configuration instance = null;

	// CONST VARS
	private final String	CONFIGURATION_DEFAULT_PATH = "/settings.json";
	private final String	SAVED_SETTINGS_FILE = "/program_launcher_settings.json";
	private final String	SAVED_CONFIGURATION_WINDOWS_PATH = "/AppData";
	private final String	SAVED_CONFIGURATION_UNIX_PATH = "/.config";
	private final String	APPLICATION_DIRECTORY_PATH = "/fr.julienbeguier";
	private final String	CONF_KEY_LAUNCHER_INFOS = "launcher_infos";
	private final String	CONF_KEY_LAUNCHER_INFOS_VERSION = "version";
	private final String	CONF_KEY_PROGRAMS = "programs";
	private final String	CONF_KEY_PROGRAM_ID = "id";
	private final String	CONF_KEY_PROGRAM_NAME = "name";
	private final String	CONF_KEY_PROGRAM_PATH = "binaryPath";
	private final String	CONF_KEY_PROGRAM_ICONPATH = "iconPath";
	private final String	CONF_KEY_SETTINGS = "settings";
	private final String	CONF_KEY_SETTINGS_QUIT_ON_PROGRAM_LAUNCHED = "quit_on_program_launched";

	// CONF FILE
	private String		osBasedSettingsFilePath;
	private InputStream	inputConfigFile;
//	private URL			configFileURL;

	// CONF OBJ
	private String		configFileContent = null;
	private JSONObject	configurationObj;
	private JSONObject	launcherInfosObj;
	private JSONArray	programs;
	private int			nPrograms;
	private JSONObject	settingsObj;

	// DATA
	private final SystemProperties systemProperties;

	private Configuration() {
		this.systemProperties = determineSystemProperties();

		readConfiguration();
	}

	public static Configuration getInstance() {
		if (instance == null)
			instance = new Configuration();
		return instance;
	}

	private boolean checkForSavedConfiguration() {
		String userHome = this.systemProperties.getUserHome();
		if (this.systemProperties.isUnix()) {
			// Checking if the system is Unix
			this.osBasedSettingsFilePath =
					userHome + SAVED_CONFIGURATION_UNIX_PATH + APPLICATION_DIRECTORY_PATH + SAVED_SETTINGS_FILE;
		} else {
			// Otherwise, it should be Windows, well I hope so
			this.osBasedSettingsFilePath =
					userHome + SAVED_CONFIGURATION_WINDOWS_PATH + APPLICATION_DIRECTORY_PATH + SAVED_SETTINGS_FILE;
		}

		File settingsFile = new File(this.osBasedSettingsFilePath);
		if (!settingsFile.exists()) {
			// Create the directories to validate the path
			try {
				settingsFile.getParentFile().mkdirs();
				settingsFile.createNewFile();
			} catch (IOException e) {
				System.err.println("Failed to create directory or file for path :" + this.osBasedSettingsFilePath);
				e.printStackTrace();
			}
			// No saved settings file found
			return false;
		}

		try {
			FileInputStream configFileInputStream = new FileInputStream(settingsFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(configFileInputStream));
			String line;
			StringBuffer fileContent = new StringBuffer();
			while ((line = br.readLine()) != null) {
				fileContent.append(line);
			}
			this.configFileContent = fileContent.toString().trim();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Settings loaded from existing settings file
		return true;
	}

	private void readDefaultConfiguration() {
		this.inputConfigFile = this.getClass().getResourceAsStream(this.CONFIGURATION_DEFAULT_PATH);
		if (this.inputConfigFile == null) {
			System.err.println("Error : \'" + this.CONFIGURATION_DEFAULT_PATH + "\' is not found !");
			// Can't read the default settings file inside the jar? THIS is a problem
			System.exit(-1);
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.inputConfigFile));
			String line;
			StringBuffer fileContent = new StringBuffer();
			while ((line = br.readLine()) != null) {
				fileContent.append(line);
			}
			this.configFileContent = fileContent.toString().trim();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseOnlyInfosAndSettings() {
		try {
			this.configurationObj = new JSONObject(this.configFileContent);
			this.launcherInfosObj = this.configurationObj.getJSONObject(this.CONF_KEY_LAUNCHER_INFOS);

			Settings settings = Settings.getInstance();
			this.settingsObj = this.configurationObj.getJSONObject(this.CONF_KEY_SETTINGS);
			settings.setQuitOnProgramLaunched(this.settingsObj.getBoolean(this.CONF_KEY_SETTINGS_QUIT_ON_PROGRAM_LAUNCHED));

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parseSettingsJson() {
		try {
			this.configurationObj = new JSONObject(this.configFileContent);
			this.programs = this.configurationObj.getJSONArray(this.CONF_KEY_PROGRAMS);
			this.nPrograms = this.programs.length();

			// Put the loaded default launcher_infos & settings
			this.configurationObj.put(this.CONF_KEY_LAUNCHER_INFOS, this.launcherInfosObj);
			this.configurationObj.put(this.CONF_KEY_SETTINGS, this.settingsObj);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void readConfiguration() {
		/*
		 *  Reading the default settings file inside Jar (or
		 *  resources) and then parse only the launcher_infos
		 *  and settings in case of changes
		 */
		readDefaultConfiguration();
		parseOnlyInfosAndSettings();
		
		// Checking for a saved settings file
		checkForSavedConfiguration();

		// When the settings file is read, load it's content into JsonObjects for manipulation
		parseSettingsJson();

		// Finally, write the loaded settings file on disk
		writeConfiguration();
	}

	public void writeConfiguration() {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(this.osBasedSettingsFilePath));
			writer.write(this.configurationObj.toString(4));
			writer.close();
			System.out.println("Write on " + this.osBasedSettingsFilePath);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}

	public SystemProperties determineSystemProperties() {
		String os = System.getProperty("os.name");
		String home = System.getProperty("user.home");

		return new SystemProperties(os, home);
	}

	public List<ProgramElement> getProgramList() {
		JSONObject element;
		int id;
		String name;
		String path;
		String iconPath;
		ProgramElement pe;

		int nbProgram = this.programs.length();
		List<ProgramElement> programList = new ArrayList<ProgramElement>();

		for (int i = 0; i < nbProgram; i++) {
			try {
				element = this.programs.getJSONObject(i);
				id = element.getInt(CONF_KEY_PROGRAM_ID);
				name = element.getString(CONF_KEY_PROGRAM_NAME);
				path = element.getString(CONF_KEY_PROGRAM_PATH);
				iconPath= element.getString(CONF_KEY_PROGRAM_ICONPATH);

				pe = new ProgramElement(new ProgramElementJson(id, name, path, iconPath));
				programList.add(pe);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return programList;
	}

	public JList<ProgramListElement> getProgramList(int scrollPanelWidth) {
		JSONObject element;
		int id;
		String name;
		String path;
		String iconPath;
		ProgramElement pe;
		String labelText;

		int nbProgram = this.programs.length();
		ProgramListElement[] content = new ProgramListElement[nbProgram];
		for (int i = 0; i < nbProgram; i++) {
			try {
				element = this.programs.getJSONObject(i);
				id = element.getInt(CONF_KEY_PROGRAM_ID);
				name = element.getString(CONF_KEY_PROGRAM_NAME);
				path = element.getString(CONF_KEY_PROGRAM_PATH);
				iconPath= element.getString(CONF_KEY_PROGRAM_ICONPATH);

				labelText = " " + name;
				pe = new ProgramElement(new ProgramElementJson(id, name, path, iconPath));
				content[i] = new ProgramListElement(labelText, pe);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		JList<ProgramListElement> programList = new JList<ProgramListElement>(content);
		programList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		programList.setLayoutOrientation(JList.VERTICAL);
		programList.setCellRenderer(new ProgramListElementCellRenderer());
		programList.setOpaque(false);
		programList.setFixedCellWidth(scrollPanelWidth);
		programList.setFixedCellHeight(30);

		return programList;
	}

	public void removeElementAtId(int id) {
		int indexToRemove = 0;

		for (int i = 0; i < this.programs.length(); i++) {
			JSONObject element;
			int elementId;

			try {
				element = this.programs.getJSONObject(i);
				elementId = element.getInt(CONF_KEY_PROGRAM_ID);

				if (elementId == id) {
					// To avoid editing the array while iterating
					indexToRemove = i;
					break;
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		this.programs.remove(indexToRemove);
	}

	public JSONObject getConfiguration() {
		return this.configurationObj;
	}

	public JSONObject getLauncherInfos() {
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
