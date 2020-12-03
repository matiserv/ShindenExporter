package pl.TRWQ;

import java.util.List;

public class Output {
	
    private String type;

    private List<animeArray> animeArray;
    
    public Output() {

    }
    
    public Output(String type, List<animeArray> animeArray) {
        this.type = type;
        this.animeArray = animeArray;
    }

    public List<animeArray> getArray() {
    	return animeArray;
    }
    
    public void setArray(List<animeArray> animeArray) {
    	this.animeArray = animeArray;
    }
    
    public String getType() {
    	return type;
    }
    
    public void setType(String type) {
    	this.type = type;
    }
    
    @Override
    public String toString ()
    {
        return "Watching [type = "+type+", array = "+animeArray+"]";
    }
}
