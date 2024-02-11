package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.JPanel;

import geo.Geste;
import geo.Lexique;
import geo.Trace;
import ui.config.Parameters;

public class Vue extends JPanel {
	Color bgColor;
	Color fgColor; 
	int width, height;
	Geste geste;
	Lexique lexicon;
		
	public Vue(int width, int height) {
		super();
		this.bgColor = Couleur.bg; 
		this.fgColor = Couleur.fg; 
		this.width = width;
		this.height = height;	
		this.setBackground(Couleur.bg);
		this.setPreferredSize(new Dimension(width, width));
		Tracker t = new Tracker(this);
		this.addMouseListener(t);
		this.addMouseMotionListener(t);
		geste = new Geste("unknown");
	}

	void createLexiconFromSamples() {
		String dirName, fileName;
		File dir, f;
		for (int i = 0; i < 16; i++) {
			dirName = Parameters.defaultFolder + "/" + Parameters.baseGestureFileName + i;
			dir = new File(dirName);
			if (!(dir).exists())
				dir.mkdir();
			fileName = Parameters.defaultFolder+ "/" +
					Parameters.lexiconFolder + "/" + Parameters.baseTraceFileName+ i+".csv";
			f = new File(fileName);
			if (f.exists())
				System.out.println(" copying lexicon from "+fileName);
				try {
					Files.copy(FileSystems.getDefault().getPath(Parameters.defaultFolder,
							Parameters.lexiconFolder, Parameters.baseTraceFileName+ "-"+i+".csv"),
							FileSystems.getDefault().getPath(Parameters.defaultFolder, 
									Parameters.baseGestureFileName + i, 
									Parameters.baseGestureFileName + i + "-" + Parameters.baseModelName+ ".csv"), 
							java.nio.file.StandardCopyOption.REPLACE_EXISTING, 
							java.nio.file.StandardCopyOption.COPY_ATTRIBUTES);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		// todo -> après avoir complété les méthodes d'initialisation du recognizer, décommenter la ligne suivante 
		// lexicon = new Lexique();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaintMode(); 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);	
		g2d.setColor(fgColor);
		g2d.translate(10, 10);
		if (geste != null)
			for (Trace go: geste.getTraces()) {
				go.draw(g2d);
			}
	}

	public void add(Trace t) {
		if (geste != null) 
			geste.addTrace(t);
	}
	
	//convention dirName = geste.nom
	public void exportData(String dirName) {
		File dir = new File(dirName);
		boolean isDir = dir.isDirectory();
		int i = 0;
		if (!(isDir)) {
			dir.mkdir();
		}
		for (Trace g : geste.getTraces()) {
			g.exportWhenConfirmed(dirName + File.separator + Parameters.baseGestureFileName + "-" + i + ".csv");
			i++;
		}
	}
	
	public void exportWorkspaceData() {
		exportData(Parameters.defaultFolder +"/" +geste.getName());
	}
	
	public void clear(boolean clearModel) {
		Trace t = geste.getTraces().size() > 0 ? geste.get(0):null;
		geste.getTraces().clear();
		if (geste != null && !clearModel) geste.addTrace(t);
		repaint();
	}
	
	public void resample() {
		//todo resample
	}

	public void loadData(String path) {
		String name = path; 
		geste = new Geste(name);
		repaint();
	}

	

}
