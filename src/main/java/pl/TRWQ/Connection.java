package pl.TRWQ;

import org.jsoup.Jsoup;

import java.io.IOException;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

public class Connection {
	
	public Document getUserData(String url) throws IOException {
		
		Response response= Jsoup.connect(url)
	            .ignoreContentType(true)
	            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
	            .referrer("http://www.google.com")   
	            .timeout(12000) 
	            .followRedirects(true)
	            .execute();

	    Document doc = response.parse();
	    return doc;
	}
	
	public String getMalData(String url) throws IOException {
		
		Response response= Jsoup.connect(url)
	            .ignoreContentType(true)
	            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
	            .referrer("http://www.google.com")   
	            .timeout(12000) 
	            .followRedirects(true)
	            .execute();
		if(response.statusCode() != 200) {
			return "false";
		}
		String doc = response.body();
	    return doc;
	}
	
}
