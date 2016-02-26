package fr.julienbeguier.pl.gui.subs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ManageEntryFrame extends AEntryFrame {

	private static final long serialVersionUID = -2873844258681771170L;
	
	private final Dimension dimension = new Dimension(500, 400);
	
	private ProgramListPanel pListPanel;

	public ManageEntryFrame(ImageIcon icon) {
		super(icon);
		this.setTitle("Manage entries");
		this.setSize(this.dimension);
		this.setResizable(false);
		this.setVisible(true);

		JPanel content = new JPanel();
		JButton addButton = new JButton("Add");
		JButton deleteButton = new JButton("Delete");
		JButton okButton = new JButton("Ok");
		
		this.pListPanel = new ProgramListPanel(this.dimension);
		ScrollPane sp = new ScrollPane();
		sp.setPreferredSize(this.pListPanel.getDimension());
		sp.setSize(this.pListPanel.getDimension());
		sp.add(pListPanel);

//		content.add(pListPanel);
		content.add(addButton);
		content.add(deleteButton);
		content.add(okButton);
		this.getContentPane().add(sp, BorderLayout.PAGE_START);
		this.getContentPane().add(content, BorderLayout.CENTER);
	}
}
