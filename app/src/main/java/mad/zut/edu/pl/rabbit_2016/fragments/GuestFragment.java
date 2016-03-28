package mad.zut.edu.pl.rabbit_2016.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mad.zut.edu.pl.rabbit_2016.R;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class GuestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.special_guest, container, false);
        return v;
    }
}
