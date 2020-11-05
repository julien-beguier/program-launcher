package fr.julienbeguier.pl.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JMenuItem addProgramMenuItem;
	private JMenuItem removeProgramMenuItem;
	private JMenuItem manageProgramsMenuItem;
	private JMenuItem settingsMenuItem;
	private JMenuItem saveSettingsMenuItem;
	private JMenuItem quitMenuItem;
	private JMenuItem aboutProgramLauncherMenuItem;	

	// ICONS
	private IconLoader iconLoader;

	// FRAMES
	private JFrame manageEntriesFrame;
	private JFrame subsAddFrame;
	private JFrame subsDeleteFrame;

	// PANELS
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
		this.iconLoader = IconLoader.getInstance();
		this.iconLoader.loadIcons();

		//		this.test = (ImageIcon)fsv.getSystemIcon(new File("abc.exe")); // TODO FIND A WAY TO DISPLAY .EXE ICON
	}

	private void initFrame() {
		this.setSize(WIDTH, HEIGHT);
		this.setIconImage(this.iconLoader.programIcon.getImage());
		
		this.manageEntriesFrame = null;
		this.subsAddFrame = null;
		this.subsDeleteFrame = null;
	}

	private void initMenuBar() {
		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.editMenu = new JMenu("Edit");
		this.aboutMenu = new JMenu("About");
		this.settingsMenuItem = new JMenuItem("Settings", this.iconLoader.settingsMenuIcon);
		this.saveSettingsMenuItem = new JMenuItem("Save settings", this.iconLoader.saveSettingsMenuIcon);
		this.quitMenuItem = new JMenuItem("Quit", this.iconLoader.exitMenuIcon);
		this.addProgramMenuItem = new JMenuItem("Add program", this.iconLoader.addProgramMenuIcon);
		this.removeProgramMenuItem = new JMenuItem("Remove program", this.iconLoader.removeProgramMenuIcon);
		this.manageProgramsMenuItem = new JMenuItem("Manage programs", this.iconLoader.manageProgramsMenuIcon);
		this.aboutProgramLauncherMenuItem = new JMenuItem("About Program Launcher", this.iconLoader.programMenuIcon);

		this.settingsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// TODO
			}
		});
		this.saveSettingsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Configuration.getInstance().writeConfiguration();
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
					subsAddFrame = new AddEntryFrame(iconLoader.programIcon, launcherPanel);
				} else {
					subsAddFrame.setVisible(true);
					subsAddFrame.requestFocusInWindow();
				}
			}
		});
		this.removeProgramMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (null == subsDeleteFrame) {
					subsDeleteFrame = new DeleteEntryFrame(iconLoader.programIcon);
				} else {
					subsDeleteFrame.setVisible(true);
					subsDeleteFrame.requestFocusInWindow();
				}
			}
		});
		this.manageProgramsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (null == manageEntriesFrame) {
					manageEntriesFrame = new ManageEntryFrame(iconLoader.programIcon);
				} else {
					manageEntriesFrame.setVisible(true);
					manageEntriesFrame.requestFocusInWindow();
				}
			}
		});
		this.aboutProgramLauncherMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, "Program Launcher made\nby Julien B�guier !\n\nVersion: " + PROGRAM_VERSION, "About Program Launcher", JOptionPane.INFORMATION_MESSAGE, iconLoader.aboutIcon);
			}
		});

		this.fileMenu.add(this.settingsMenuItem);
		this.fileMenu.add(this.saveSettingsMenuItem);
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
		// Init program version
		try {
			Configuration configFile = Configuration.getInstance();
			this.PROGRAM_VERSION = configFile.getProgramInfos().getString(configFile.getConfKeyProgramInfosVersion());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// Init program settings
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
