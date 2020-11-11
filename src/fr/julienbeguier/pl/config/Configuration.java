package fr.julienbeguier.pl.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
	private final String	CONFIGURATION_PATH = "/config.json";
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
	URL confFile;

	// CONF OBJ
	private JSONObject	configurationObj;
	private JSONObject	launcherInfosObj;
	private JSONArray	programs;
	private int			nPrograms;
	private JSONObject	settingsObj;

	private Configuration() {
		readConfiguration();
	}

	public static Configuration getInstance() {
		if (instance == null)
			instance = new Configuration();
		return instance;
	}

	public void readConfiguration() {
		this.confFile = this.getClass().getResource(this.CONFIGURATION_PATH);
		String confFileContent = null;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.confFile.openStream()));
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
			System.err.println("Error, the program couldn't read the configuration file: \'" + this.CONFIGURATION_PATH + "\'");
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

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void writeConfiguration() {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(this.confFile.getPath()));
			writer.write(this.configurationObj.toString(4));
			writer.close();
			System.out.println("Write on " + this.confFile.getPath());
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
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
