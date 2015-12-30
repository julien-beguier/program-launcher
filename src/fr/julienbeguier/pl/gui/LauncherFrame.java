package fr.julienbeguier.pl.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.json.JSONException;

import fr.julienbeguier.pl.config.Configuration;
import fr.julienbeguier.pl.gui.subs.AddEntryFrame;
import fr.julienbeguier.pl.gui.subs.DeleteEntryFrame;
import fr.julienbeguier.pl.gui.subs.ManageEntryFrame;

public class LauncherFrame extends JFrame {

	private static final long serialVersionUID = 6232060395028975287L;
	private String PROGRAM_VERSION;

	private final int WIDTH = 400;
	private final int HEIGHT = 200;

	// GUI
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu aboutMenu;
	private JMenuItem quitMenuItem;
	private JMenuItem addProgramMenuItem;
	private JMenuItem removeProgramMenuItem;
	private JMenuItem manageProgramsMenuItem;
	private JMenuItem settingsMenuItem;
	private JMenuItem aboutProgramLauncherMenuItem;	

	private ImageIcon programIcon;
	private ImageIcon aboutIcon;
	private ImageIcon programMenuIcon;
	private ImageIcon addProgramMenuIcon;
	private ImageIcon removeProgramMenuIcon;
	private ImageIcon manageProgramsMenuIcon;
	private ImageIcon settingsMenuIcon;
	
	private JFrame manageEntriesFrame;
	private JFrame subsAddFrame;
	private JFrame subsDeleteFrame;

	//	private ImageIcon test;

	private LauncherPanel launcherPanel;

	public LauncherFrame(String name) {
		super(name);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.launcherPanel = new LauncherPanel(WIDTH, HEIGHT);
		this.setContentPane(this.launcherPanel);
		this.setResizable(false);
		this.pack();
		this.validate();
	}

	private void initImages() {
		this.programIcon = new ImageIcon(getClass().getResource("/launcher_logo_512px.png"));
		this.aboutIcon = new ImageIcon(getClass().getResource("/launcher_logo_128px.png"));
		this.programMenuIcon = new ImageIcon(getClass().getResource("/launcher_logo_16px.png"));
		this.addProgramMenuIcon = new ImageIcon(getClass().getResource("/add_16px.png"));
		this.removeProgramMenuIcon = new ImageIcon(getClass().getResource("/remove_16px.png"));
		this.manageProgramsMenuIcon = new ImageIcon(getClass().getResource("/manage_16px.png"));
		this.settingsMenuIcon = this.manageProgramsMenuIcon;

		//		this.test = (ImageIcon)fsv.getSystemIcon(new File("abc.exe")); // TODO FIND A WAY TO DISPLAY .EXE ICON
	}

	private void initFrame() {
		this.setSize(WIDTH, HEIGHT);
		this.setIconImage(programIcon.getImage());
		
		this.manageEntriesFrame = null;
		this.subsAddFrame = null;
		this.subsDeleteFrame = null;
	}

	private void initMenuBar() {
		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.editMenu = new JMenu("Edit");
		this.aboutMenu = new JMenu("About");
		this.settingsMenuItem = new JMenuItem("Settings", this.settingsMenuIcon);
		this.quitMenuItem = new JMenuItem("Quit");
		this.addProgramMenuItem = new JMenuItem("Add Program", this.addProgramMenuIcon);
		this.removeProgramMenuItem = new JMenuItem("Remove Program", this.removeProgramMenuIcon);
		this.manageProgramsMenuItem = new JMenuItem("Manage Programs", this.manageProgramsMenuIcon);
		this.aboutProgramLauncherMenuItem = new JMenuItem("About Program Launcher", this.programMenuIcon);

		this.settingsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
			}
		});
		this.quitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(1);
			}
		});
		this.addProgramMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (null == subsAddFrame) {
					subsAddFrame = new AddEntryFrame(programIcon);
				} else {
					subsAddFrame.setVisible(true);
					subsAddFrame.requestFocusInWindow();
				}
			}
		});
		this.removeProgramMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (null == subsDeleteFrame) {
					subsDeleteFrame = new DeleteEntryFrame(programIcon);					
				} else {
					subsDeleteFrame.setVisible(true);
					subsDeleteFrame.requestFocusInWindow();
				}
			}
		});
		this.manageProgramsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (null == manageEntriesFrame) {
					manageEntriesFrame = new ManageEntryFrame(programIcon);					
				} else {
					manageEntriesFrame.setVisible(true);
					manageEntriesFrame.requestFocusInWindow();
				}
			}
		});
		this.aboutProgramLauncherMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, "Program Launcher made\nby Julien Béguier !\n\nVersion: " + PROGRAM_VERSION, "About Program Launcher", JOptionPane.INFORMATION_MESSAGE, aboutIcon);
			}
		});

		this.fileMenu.add(this.settingsMenuItem);
		this.fileMenu.add(this.quitMenuItem);
		this.editMenu.add(this.addProgramMenuItem);
		this.editMenu.add(this.removeProgramMenuItem);
		this.editMenu.add(this.manageProgramsMenuItem);
		this.aboutMenu.add(this.aboutProgramLauncherMenuItem);
		this.menuBar.add(this.fileMenu);
		this.menuBar.add(this.editMenu);
		this.menuBar.add(this.aboutMenu);
		this.setJMenuBar(this.menuBar);
	}

	private void initOthers() {
		try {
			this.PROGRAM_VERSION = Configuration.getInstance().getProgramInfos().getString(Configuration.getInstance().getConfKeyProgramInfosVersion());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void initGUI() {
		initOthers();
		initImages();
		initFrame();
		initMenuBar();
		this.pack();
		this.validate();
	}
}
