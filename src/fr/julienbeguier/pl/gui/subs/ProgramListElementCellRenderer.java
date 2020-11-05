package fr.julienbeguier.pl.gui.subs;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ProgramListElementCellRenderer implements ListCellRenderer<ProgramListElement> {

	private Color forgroundTextColor = Color.BLACK;
//	private Color selectionTextColor = Color.WHITE;
	private Color selectionBackgroundColor = Color.LIGHT_GRAY;

	public ProgramListElementCellRenderer() {
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends ProgramListElement> list, ProgramListElement value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = value;

		label.setForeground(this.forgroundTextColor);

		if (isSelected) {
			label.setBackground(this.selectionBackgroundColor);
		} else {
			label.setBackground(null);
		}
		label.setEnabled(true);
		label.setOpaque(true);
		return label;
	}
}
