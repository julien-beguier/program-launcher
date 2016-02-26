package fr.julienbeguier.pl.gui.subs;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import org.json.JSONArray;
import org.json.JSONException;

import fr.julienbeguier.pl.config.Configuration;

public class ProgramListPanel extends JPanel {

	private static final long serialVersionUID = -5299811736248054811L;

	private Dimension dimension = new Dimension(100, 340);
	
	private JList<JLabel> programsListed;

	public ProgramListPanel(Dimension parentDimension) {
		this.setPreferredSize(this.dimension);
		this.setSize(this.dimension);
		
		Configuration config = Configuration.getInstance();
		JSONArray programs = config.getPrograms();
		
		JLabel[] content = new JLabel[programs.length()];
		for (int i = 0; i < programs.length(); i++) {
			try {
				content[i] = new JLabel(" " + programs.getJSONObject(i).getString(config.getConfKeyProgramName()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		this.programsListed = new JList<JLabel>(content);
		this.programsListed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.programsListed.setLayoutOrientation(JList.VERTICAL);
		this.programsListed.setCellRenderer(new ProgramListElement());
		this.programsListed.setOpaque(false);
		this.programsListed.setFixedCellWidth(parentDimension.width - 40);
		this.programsListed.setFixedCellHeight(20);

		this.add(this.programsListed);
	}

	public Dimension getDimension() {
		return this.dimension;
	}
}
