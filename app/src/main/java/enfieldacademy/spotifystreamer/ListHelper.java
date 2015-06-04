package enfieldacademy.spotifystreamer;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

public class ListHelper
{
    private static List<Artist> artistList;
    private static List<Track> topTenList;

    public static List<Artist> getArtistList() {
        return artistList;
    }

    public static void setArtistList(List<Artist> artists){
        artistList = artists;
    }

    public static List<Track> getTopTenList(){
        return topTenList;
    }

    public static void setTopTenList(List<Track> tracks){
        topTenList = tracks;
    }

}
