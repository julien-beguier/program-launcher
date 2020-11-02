package fr.julienbeguier.pl.gui.subs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ManageEntryFrame extends AEntryFrame {

	private static final long serialVersionUID = -2873844258681771170L;
	
	private final Dimension frameDimension = new Dimension(500, 400);
	private final Dimension scrollPanelDimension = new Dimension(440, 380);
	
	private ProgramListPanel pListPanel;

	public ManageEntryFrame(ImageIcon icon) {
		super(icon);
		this.setTitle("Manage entries");
		this.setSize(this.frameDimension);
		this.setResizable(false);
		this.setVisible(true);

//		JPanel listContent = new JPanel();
		JPanel actionsContent = new JPanel();
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setVisible(false); // TODO
			}
		});
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setVisible(false); // TODO
			}
		});
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
			}
		});

//		listContent.setSize(50, 50);
		actionsContent.setSize(100, 50);

//		addButton.setSize(0, 32);
//		deleteButton.setSize(0, 32);
//		okButton.setSize(0, 32);

		actionsContent.add(addButton);
		actionsContent.add(deleteButton);
		actionsContent.add(doneButton);

//		actionsContent.setSize(0, 32);

		System.out.println("addButton : " + addButton.getSize().getWidth() + " & " + addButton.getSize().getHeight());
		System.out.println("actionsContent : " + actionsContent.getSize().getWidth() + " & " + actionsContent.getSize().getHeight());

		this.pListPanel = new ProgramListPanel(this.scrollPanelDimension);
		ScrollPane sp = new ScrollPane();
		int customScrollWidth = (int) (this.scrollPanelDimension.getWidth() - actionsContent.getWidth());
		int customScrollHeight = (int) (this.scrollPanelDimension.getHeight() - actionsContent.getHeight());
		Dimension customScrollPanelDimension = new Dimension(customScrollWidth, customScrollHeight);
		System.out.println("customSP : " + customScrollPanelDimension.getWidth() + " & " + customScrollPanelDimension.getHeight());
		sp.setPreferredSize(customScrollPanelDimension);
		sp.setSize(customScrollPanelDimension);
		sp.add(pListPanel);
//		listContent.add(sp);

//		content.add(pListPanel);
		this.getContentPane().add(sp, BorderLayout.NORTH);
		this.getContentPane().add(actionsContent, BorderLayout.SOUTH);
	}
}
