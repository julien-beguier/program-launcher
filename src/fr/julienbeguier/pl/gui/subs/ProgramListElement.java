package fr.julienbeguier.pl.gui.subs;

import javax.swing.JLabel;

import fr.julienbeguier.pl.gui.ProgramElement;

public class ProgramListElement extends JLabel {

	private static final long serialVersionUID = 7059076863974061917L;

	// DATA
	private ProgramElement programElement;
	
	public ProgramListElement(String name, ProgramElement pe) {
		super(name);

		this.programElement = pe;
	}

	public ProgramElement getProgramElement() {
		return this.programElement;
	}
}
