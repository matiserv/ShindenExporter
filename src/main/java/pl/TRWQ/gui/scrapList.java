package pl.TRWQ.gui;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JOptionPane;

import pl.TRWQ.Scraper;
public class scrapList {
	public boolean scrap() {
		String url = App.getFormattedTextField().getText();
		if(url.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Wpisz adres url do list! Przyk³ad: https://shinden.pl/animelist/00000-example");
			updateButton(true);
			return false;
		}
		
		String type = "xml";
		
		if(App.getRdbtnNewRadioButton().isSelected())
			type = "json";
		
		String path = getPath(type);
		if(path == "false") {
			updateButton(true);
			return false;
		}
		
		Scraper scr = new Scraper();
		
		if(scr.scrap(url, path, type)) {
			JOptionPane.showMessageDialog(null, "Wyeksportowano poprawnie! ("+path+")");
		} else {
			JOptionPane.showMessageDialog(null, "Wyst¹pi³ b³¹d: "+scr.getError());
			System.out.println(scr.getError());
		}
		updateButton(true);
		return true;
	}
	
	private String getPath(final String ext) {
		FileDialog fileDialog = new FileDialog(new Frame(), "Zapisz", FileDialog.SAVE);
		fileDialog.setFilenameFilter(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(ext);
			}
		});
		fileDialog.setFile("ShindenExport"+ext);
		fileDialog.setVisible(true);
		if (fileDialog.getFile() == null)
			  return "false";
		return fileDialog.getDirectory()+File.separator+fileDialog.getFile();
	}

	public static void updateProgress(int i) {
		App.getProgressBar().setValue(i);
	}
	
	public static void updateButton(Boolean bool) {
		App.setEnabled(bool);
	}
	
}
