package fr.julienbeguier.pl.gui.subs;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ProgramListElement implements ListCellRenderer<JLabel> {

	private Color forgroundTextColor = Color.BLACK;
	private Color selectionTextColor = Color.WHITE;
	private Color selectionBackgroundColor = Color.DARK_GRAY;

	public ProgramListElement() {
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends JLabel> list, JLabel value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = value;

		if (isSelected) {
			label.setBackground(this.selectionBackgroundColor);
			label.setForeground(this.selectionTextColor);
		} else {
			label.setBackground(null);
			label.setForeground(this.forgroundTextColor);
		}
		label.setEnabled(true);
		label.setOpaque(true);
		return label;
	}
}
