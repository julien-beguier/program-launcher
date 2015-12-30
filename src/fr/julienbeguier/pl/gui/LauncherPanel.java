package fr.julienbeguier.pl.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.julienbeguier.pl.config.Configuration;

public class LauncherPanel extends JPanel {

	private static final long serialVersionUID = -407878933197950750L;

	// GUI

	public LauncherPanel(int width, int height) {
		super();

		Configuration config = Configuration.getInstance();
		JSONArray programs = config.getPrograms();
		int nProgram = config.getNProgram();

		this.setLayout(new GridLayout(nProgram, 1));

		JSONObject program;
		ProgramElement pe;

		for (int i = 0; i < nProgram; i++) {
			try {
				program = programs.getJSONObject(i);
				pe = new ProgramElement(program.getString(config.getConfKeyProgramName()), program.getString(config.getConfKeyProgramPath()), program.getString(config.getConfKeyProgramIconPath()));
				this.add(pe);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
