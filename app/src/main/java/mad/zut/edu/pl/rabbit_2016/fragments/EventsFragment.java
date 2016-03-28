package mad.zut.edu.pl.rabbit_2016.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.adapters.EventsAdapter;
import mad.zut.edu.pl.rabbit_2016.api.NetworkStateReceiver;
import mad.zut.edu.pl.rabbit_2016.api.RequestCallback;
import mad.zut.edu.pl.rabbit_2016.api.RequestListener;
import mad.zut.edu.pl.rabbit_2016.api.RestClientManager;
import mad.zut.edu.pl.rabbit_2016.model.event.Event;
import retrofit.RetrofitError;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class EventsFragment extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener {
    private RecyclerView recyclerView;
    private EventsAdapter eventsAdapter;
    private ProgressBar progressBar;
    private NetworkStateReceiver networkStateReceiver;
    private Snackbar snackbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.events_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(android.R.id.list);
        progressBar = (ProgressBar) v.findViewById(R.id.loadingPanel);

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);

        progressBar.setVisibility(View.VISIBLE);
        initRecyclerView();

        return v;
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        eventsAdapter = new EventsAdapter();
        recyclerView.setAdapter(eventsAdapter);
    }

    private void downloadEvents() {
        RestClientManager.getAllEvents(new RequestCallback<>(new RequestListener<List<Event>>() {
            @Override
            public void onSuccess(List<Event> response) {
                eventsAdapter.setCompanies(response);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        snackbar = Snackbar
                .make(getView(), getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.settings), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                });
    }

    @Override
    public void networkAvailable() {
        snackbar.dismiss();
        downloadEvents();
    }

    @Override
    public void networkUnavailable() {
        snackbar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(networkStateReceiver);
    }
}
