package pl.TRWQ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.jamesmurty.utils.XMLBuilder2;

import pl.TRWQ.List.Abandoned;
import pl.TRWQ.List.Bridge;
import pl.TRWQ.List.Planned;
import pl.TRWQ.List.Skipped;
import pl.TRWQ.List.Suspended;
import pl.TRWQ.List.Watched;
import pl.TRWQ.List.Watching;
import pl.TRWQ.gui.App;
import pl.TRWQ.gui.scrapList;

public class Scraper {

	public static List<Output> output = new ArrayList<Output>();
	private String exception;
	
	public boolean scrap(String url, String path, String type) {
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
	    	
	    	if(type == "json") {
		    	Gson gson = new Gson();
		    	
			    String json = gson.toJson(output);
				    
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
	    	}
	    	int correct = 0;
	    	int current = 0;
	    	int animes = 0;
	    	if(type == "xml") {
		    	scrapList.updateProgress(100);
	    		String animeList = "";
	    		for(int i = 0; i < output.size(); i++) {
	    			System.out.println(output.get(i).getType());
	    			List<animeArray> list = output.get(i).getArray();
	    			animes = list.size();
		    		for(int a = 0; a < list.size(); a++) {
		    			current++;
		    			String shName = noramlizeName(list.get(a).getName());
		    			if(shName.toLowerCase().replace(" ", "").length() < 3) {
		    				continue;
		    			}
		    			
		    			String mal = getMal(shName);

		    			if(mal.isEmpty() || mal == "false") {
		    				continue;
		    			}
		    			
		    			JSONObject obj = new JSONObject(mal);
		    			int id = obj.getJSONArray("results").getJSONObject(0).getInt("mal_id");
		    			String name = noramlizeName(obj.getJSONArray("results").getJSONObject(0).getString("title"));
		    			String aType = obj.getJSONArray("results").getJSONObject(0).getString("type");
		    			int episodes = obj.getJSONArray("results").getJSONObject(0).getInt("episodes");
		    			
		    			Boolean err = false;
						if(name.toLowerCase().replace(" ", "").equals(shName.toLowerCase().replace(" ", ""))) {
		    				correct += 1;
		    				err = true;
						} else {
							if(!App.getChckbxNewCheckBox().isSelected()) {
								continue;
							}
							for(int x = 1; x < obj.getJSONArray("results").length(); x++) {
								String js = obj.getJSONArray("results").getJSONObject(x).getString("title");
								if(js.toLowerCase().replace(" ", "").equals(shName.toLowerCase().replace(" ", ""))) {
									name = js;
								}
							}
						}

		    			
		    			
		    			if(name.toLowerCase().replace(" ", "").equals(shName.toLowerCase().replace(" ", "")) && !err) {
		    				correct += 1;
						}
		    			
					    System.out.println("["+id+":"+name+"] "+shName);
					    
					    String status = "Watching";
					    switch(list.get(a).getStatus()) {
						    case("Oglądam"):
						    	status = "Watching";
						    	break;
						    case("Obejrzane"):
						    	status = "Completed";
						    	break;
						    case("Pomijam"):
						    	status = "On-Hold";
						    	break;
						    case("Wstrzymane"):
						    	status = "On-Hold";
						    	break;
						    case("Porzucone"):
						    	status = "Dropped";
						    	break;
						    case("Planuję"):
						    	status = "Plan to Watch";
						    	break;
					    }
					    
					    animeList += "<anime>\r\n"
					    		+ "<series_animedb_id>"+Integer.toString(id)+"</series_animedb_id>\r\n"
					    		+ "<series_title>\r\n"
					    		+ "<![CDATA[ "+name+" ]]>\r\n"
					    		+ "</series_title>\r\n"
					    		+ "<series_type>"+aType+"</series_type>\r\n"
					    		+ "<series_episodes>"+Integer.toString(episodes)+"</series_episodes>\r\n"
					    		+ "<my_id>0</my_id>\r\n"
					    		+ "<my_watched_episodes>"+list.get(a).getWatched()+"</my_watched_episodes>\r\n"
					    		+ "<my_start_date>0000-00-00</my_start_date>\r\n"
					    		+ "<my_finish_date>0000-00-00</my_finish_date>\r\n"
					    		+ "<my_rated/>\r\n"
					    		+ "<my_score>"+list.get(a).getRating()+"</my_score>\r\n"
					    		+ "<my_storage/>\r\n"
					    		+ "<my_storage_value>0.00</my_storage_value>\r\n"
					    		+ "<my_status>"+status+"</my_status>\r\n"
					    		+ "<my_comments>\r\n"
					    		+ "<![CDATA[ ]]>\r\n"
					    		+ "</my_comments>\r\n"
					    		+ "<my_times_watched>0</my_times_watched>\r\n"
					    		+ "<my_rewatch_value/>\r\n"
					    		+ "<my_priority>LOW</my_priority>\r\n"
					    		+ "<my_tags>\r\n"
					    		+ "<![CDATA[ ]]>\r\n"
					    		+ "</my_tags>\r\n"
					    		+ "<my_rewatching>0</my_rewatching>\r\n"
					    		+ "<my_rewatching_ep>0</my_rewatching_ep>\r\n"
					    		+ "<my_discuss>0</my_discuss>\r\n"
					    		+ "<my_sns>default</my_sns>\r\n"
					    		+ "<update_on_import>"+"1"+"</update_on_import>\r\n"
					    		+ "</anime>";
					    
					    int percentage = (current * 100 + (animes >> 1)) / animes;

				    	scrapList.updateProgress(percentage);
		    			System.out.println("(correct) "+correct+"/"+current);
		    			try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
		    			
		    		}
	    		}
	    		String xml = "<!-- \r\n"
	    				+ "		 Created by XML Export feature at MyAnimeList.net\r\n"
	    				+ "		 Version 1.1.0\r\n"
	    				+ "		 -->\r\n"
	    				+ "<myanimelist>\r\n"
	    				+ "<myinfo>\r\n"
	    				+ "<user_id>"+"0"+"</user_id>\r\n"
	    				+ "<user_name>"+"USERNAME"+"</user_name>\r\n"
	    				+ "<user_export_type>"+"1"+"</user_export_type>\r\n"
	    				+ "<user_total_anime>"+"0"+"</user_total_anime>\r\n"
	    				+ "<user_total_watching>"+"0"+"</user_total_watching>\r\n"
	    				+ "<user_total_completed>"+"0"+"</user_total_completed>\r\n"
	    				+ "<user_total_onhold>"+"0"+"</user_total_onhold>\r\n"
	    				+ "<user_total_dropped>"+"0"+"</user_total_dropped>\r\n"
	    				+ "<user_total_plantowatch>"+"0"+"</user_total_plantowatch>\r\n"
	    				+ "</myinfo>\r\n"
	    				+ animeList
	    				+ "</myanimelist>";
	    		
			    FileOutputStream outputStream = null;
				try {
					outputStream = new FileOutputStream(path);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			    byte[] strToBytes = xml.getBytes();
			    try {
					outputStream.write(strToBytes);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			 
		
				outputStream.close();
		    	scrapList.updateProgress(100);
	    	}
	    	
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
	
	private String getMal(String name) {
		Connection con = new Connection();
		URI uri = null;
		try {
			uri = new URI(
			        "https", 
			        "api.jikan.moe", 
			        "/v3/search/anime",
			        "q="+name,
			        null);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String request = uri.toASCIIString();
		try {
			return con.getMalData(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "false";
		}
	}
	private String noramlizeName(String str) {
		return Normalizer.normalize(str, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
}
