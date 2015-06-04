package enfieldacademy.spotifystreamer.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import enfieldacademy.spotifystreamer.R;

public class TopTenFragment extends Fragment {

    public TopTenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_ten, container, false);
    }
}
