package pl.TRWQ.List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.TRWQ.Connection;
import pl.TRWQ.animeArray;

public class Abandoned {
	private static List<animeArray> animeList = new ArrayList<animeArray>();
	
	public List<animeArray> getList() throws IOException {
		Connection con = new Connection();
		Document doc = con.getUserData(Bridge.url+"/dropped");
	    Elements tableRaw = doc.select("table"); //select the first table.
	    if(!tableRaw.isEmpty()) {
		    Element table = tableRaw.get(0);
		    
		    Elements rows = table.select("tr");


		    
		    for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
		        Element row = rows.get(i);
		        Elements cols = row.select("td");
		        
		        animeArray list = new animeArray();
		        
		        String progressRaw = cols.get(4).text();
		        String[] progress = progressRaw.split("/", -1);
			    
		        String name = cols.get(1).text();
		        String rating = cols.get(2).text();
		        String status = cols.get(3).text();
		        String watched = progress[0];
		        String Episodes = progress[1];
		        list.setName(name);
		        list.setRating(rating);
		        list.setStatus(status);
		        list.setWatched(watched);
		        list.setEpisodes(Episodes);
		        
			    animeList.add(list);
		        
		    }
	    }
		
		return animeList;
	}
}
