package ui;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import ui.config.Parameters;

public class UsefulTools extends JToolBar {
	JFileChooser jfc;
	JFrame toplevel;
	Vue vue;
	File importFile, exportFile;

	public UsefulTools(JFrame f, Vue v1, boolean training) {
		vue = v1;
		toplevel = f;
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(Parameters.defaultFolder));
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		if (training) {
		JButton jb = new JButton(new AbstractAction("Importer", new ImageIcon("img/Open16.gif")) {
			public void actionPerformed(ActionEvent e) {
				int result = jfc.showOpenDialog(toplevel);
				if (result == JFileChooser.APPROVE_OPTION) {
					importFile = jfc.getSelectedFile();
					vue.loadData(importFile.getName());
				}
			}
		});
		this.add(jb);

		jb = new JButton(new AbstractAction("Exporter", new ImageIcon("img/Save16.gif")) {
			public void actionPerformed(ActionEvent e) {
				vue.exportWorkspaceData();
//				int result = jfc.showOpenDialog(toplevel);
//				if (result == JFileChooser.APPROVE_OPTION) {
//					exportFile = jfc.getSelectedFile();
//					vue.exportData(exportFile.getAbsolutePath());
//				}
			}
		});
		this.add(jb);
		jb = new JButton(new AbstractAction("Clear Drawing") {
			public void actionPerformed(ActionEvent e) {
				vue.clear(false);
			}
		});
		this.add(jb);

		jb = new JButton(new AbstractAction("Load Lexicon") {
			public void actionPerformed(ActionEvent e) {
				vue.createLexiconFromSamples();
			}
		});
		this.add(jb);
		
		jb = new JButton(new AbstractAction("Test Recognition") {
			public void actionPerformed(ActionEvent e) {
				(new MainWindow(false, "Test Recognition")).setVisible(true);;
			}
		});
		this.add(jb);

	}else {
		JButton jb = new JButton(new AbstractAction("Recognize") {
			public void actionPerformed(ActionEvent e) {
				//todo
			}
		});
		this.add(jb);

	}
	}
}
