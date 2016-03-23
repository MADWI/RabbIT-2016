package mad.zut.edu.pl.rabbit_2016.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Standalone fragment displaying hardcoded text at center
 * for use when we don't have real fragment yet
 */
public class PlaceholderFragment extends Fragment {
    // Text at top of placeholder fragment
    private static final String ARG_PLACEHOLDER_TITLE = "placeholder_title";

    @NonNull
    public static Bundle makeArguments(String placeholderTitle) {
        Bundle args = new Bundle();
        args.putString(ARG_PLACEHOLDER_TITLE, placeholderTitle);
        return args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        if (getArguments() != null) {
            textView.setText(getArguments().getString(ARG_PLACEHOLDER_TITLE));
        }
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
