package enfieldacademy.spotifystreamer.fragments;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import enfieldacademy.spotifystreamer.R;
import enfieldacademy.spotifystreamer.activities.MainActivity;
import enfieldacademy.spotifystreamer.adapters.ArtistsSearchResultAdapter;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Pager;

public class BandSearchFragment extends Fragment {

    //private final String TAG = "BandSearchFragment";

    // to satisfy looking for bands with very short names like U2, but also trying to respect API limits
    private final int NUM_OF_CHARS_BEFORE_SEARCH = 1;

    private SpotifyService spotify;
    private ArtistsSearchResultAdapter artistsSearchResultAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_band_search, container, false);
        SpotifyApi api = new SpotifyApi();
        spotify = api.getService();

        EditText musicSearchBox = (EditText) rootView.findViewById(R.id.music_search_box);
        musicSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > NUM_OF_CHARS_BEFORE_SEARCH) {
                    SearchArtistsTask search = new SearchArtistsTask(s.toString());
                    search.execute();
                }
            }
        });

        ListView lv = (ListView) rootView.findViewById(R.id.searchListView);
        artistsSearchResultAdapter = new ArtistsSearchResultAdapter(getActivity());
        lv.setAdapter(artistsSearchResultAdapter);
        lv.setOnItemClickListener(artistsSearchResultAdapter);
        return rootView;
    }

    public class SearchArtistsTask extends AsyncTask<Void, Void, Void> {

        private String searchStr;

        public SearchArtistsTask(String searchStr){
            this.searchStr = searchStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // added the asterisk so that partial names still find bands/singers
            // for ex: 'Subli' will find Sublime whereas without an asterisk it would not
            ArtistsPager artistsPager = spotify.searchArtists(searchStr + "*");
            Pager<Artist> pagerOfArtists = artistsPager.artists;
            MainActivity.artistList = pagerOfArtists.items;
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            artistsSearchResultAdapter.notifyDataSetChanged();
        }
    }
}
