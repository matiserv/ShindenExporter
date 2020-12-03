package pl.TRWQ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import pl.TRWQ.List.Abandoned;
import pl.TRWQ.List.Bridge;
import pl.TRWQ.List.Planned;
import pl.TRWQ.List.Skipped;
import pl.TRWQ.List.Suspended;
import pl.TRWQ.List.Watched;
import pl.TRWQ.List.Watching;
import pl.TRWQ.gui.scrapList;

public class Scraper {

	public static List<Output> output = new ArrayList<Output>();
	private String exception;
	
	public boolean scrap(String url, String path) {
		try {
			Bridge.url = url;
	
	    	File dir = new File(path);
	    	if (!dir.getParentFile().exists()) {
	    		dir.getParentFile().mkdirs();
	    	}
	    	
	    	Watching watching = new Watching();
	    	Abandoned abandoned = new Abandoned();
	    	Planned planned = new Planned();
	    	Skipped skipped = new Skipped();
	    	Suspended suspended = new Suspended();
	    	Watched watched = new Watched();
	    	
	    	
	    	scrapList.updateProgress(1);
	    	//Watching
	    	Output w1 = new Output();
	    	w1.setType("Watching");
	    	w1.setArray(watching.getList());
	    	scrapList.updateProgress(10);
	    	output.add(w1);
	    	//Abandoned
	    	Output a1 = new Output();
	    	a1.setType("Abandoned");
	    	a1.setArray(abandoned.getList());
	    	scrapList.updateProgress(20);
	    	output.add(a1);
	    	//Planned
	    	Output p1 = new Output();
	    	p1.setType("Planned");
	    	p1.setArray(planned.getList());
	    	scrapList.updateProgress(30);
	    	output.add(p1);
	    	//Skipped
	    	Output s1 = new Output();
	    	s1.setType("Skipped");
	    	s1.setArray(skipped.getList());
	    	scrapList.updateProgress(60);
	    	output.add(s1);
	    	//Suspended
	    	Output s2 = new Output();
	    	s2.setType("Suspended");
	    	s2.setArray(suspended.getList());
	    	scrapList.updateProgress(80);
	    	output.add(s2);
	    	//Watched
	    	Output w2 = new Output();
	    	w2.setType("Watched");
	    	w2.setArray(watched.getList());
	    	scrapList.updateProgress(90);
	    	output.add(w2);
	    	
	    	Gson gson = new Gson();
	    	
		    String json = gson.toJson(output);
			    
		    System.out.println(json);
		    FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    byte[] strToBytes = json.getBytes();
		    try {
				outputStream.write(strToBytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	
			outputStream.close();
	    	scrapList.updateProgress(100);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			exception = e1.toString();
			return false;
		}
		return true;
	}
	
	public String getError() {
		return exception;
	}
}
