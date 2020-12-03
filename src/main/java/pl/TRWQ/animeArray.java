package pl.TRWQ;

public class animeArray
{

    private String name;

    private String rating;
    
    private String status;

    private String watched;
    
    private String episodes;
    
    public animeArray() {

    }
    
    public animeArray(String name, String rating, String status, String watched, String episodes) {
        this.name = name;
        this.rating = rating;
        this.status = status;
        this.watched = watched;
        this.episodes = episodes;
    }
    
    
    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }
    
    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public String getStatus ()
    {
        return status;
    }
    
    public void setStatus (String status)
    {
        this.status = status;
    }
    
    public String getWatched ()
    {
        return watched;
    }
    
    public void setWatched (String watched)
    {
        this.watched = watched;
    }
    
    public String getEpisodes ()
    {
        return episodes;
    }
    
    public void setEpisodes (String episodes)
    {
        this.episodes = episodes;
    }

    @Override
    public String toString ()
    {
        return "Watching [name = "+name+", rating = "+rating+", status = "+status+", watched = "+watched+", episodes = "+episodes+"]";
    }
}