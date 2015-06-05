package enfieldacademy.spotifystreamer;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

public class SpotifyListHelper
{
    private static List<Artist> artists;
    private static List<Track> topTenSongs;

    public static List<Artist> getArtistList() {
        return artists;
    }

    public static void setArtistList(List<Artist> artistList){
        artists = artistList;
    }

    public static List<Track> getTopTenList(){
        return topTenSongs;
    }

    public static void setTopTenList(List<Track> tracks){
        topTenSongs = tracks;
    }

}
