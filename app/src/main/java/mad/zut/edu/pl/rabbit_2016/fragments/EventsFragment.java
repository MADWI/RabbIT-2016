package mad.zut.edu.pl.rabbit_2016.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.adapters.EventsAdapter;
import mad.zut.edu.pl.rabbit_2016.api.RequestCallback;
import mad.zut.edu.pl.rabbit_2016.api.RequestListener;
import mad.zut.edu.pl.rabbit_2016.api.RestClientManager;
import mad.zut.edu.pl.rabbit_2016.model.event.Event;
import retrofit.RetrofitError;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class EventsFragment extends Fragment {
    private RecyclerView recyclerView;
    private EventsAdapter eventsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.events_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(android.R.id.list);

        initRecyclerView();
        downloadCompanies();

        return v;
    }

    private void initRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        eventsAdapter = new EventsAdapter();
        recyclerView.setAdapter(eventsAdapter);
    }

    private void downloadCompanies(){
        RestClientManager.getAllEvents(new RequestCallback<>(new RequestListener<List<Event>>() {
            @Override
            public void onSuccess(List<Event> response) {
               eventsAdapter.setCompanies(response);
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));
    }
}
