package fr.julienbeguier.pl.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.julienbeguier.pl.config.Configuration;
import fr.julienbeguier.pl.json.ProgramElementJson;

public class LauncherPanel extends JPanel {

	private static final long serialVersionUID = -407878933197950750L;

	// GUI

	public LauncherPanel(int width, int height) {
		super();

		Configuration config = Configuration.getInstance();
		JSONArray programArray = config.getPrograms();
		int nProgram = config.getNProgram();

		this.setLayout(new GridLayout(0, 1));

		JSONObject program;
		ProgramElement pe;

		for (int i = 0; i < nProgram; i++) {
			try {
				program = programArray.getJSONObject(i);
				ProgramElementJson pej = new ProgramElementJson(program.getInt(config.getConfKeyProgramId()), program.getString(config.getConfKeyProgramName()), program.getString(config.getConfKeyProgramPath()), program.getString(config.getConfKeyProgramIconPath()));
				pe = new ProgramElement(pej);
				this.add(pe, pe.getProgramElementJson().getId());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void addElement(ProgramElement pe) {
		this.add(pe);
		this.validate();
	}

	public void removeElement(ProgramElement pe) {
		this.remove(pe.getProgramElementJson().getId());
	}
}
