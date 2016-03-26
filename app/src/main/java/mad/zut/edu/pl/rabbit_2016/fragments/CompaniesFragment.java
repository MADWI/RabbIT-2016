package mad.zut.edu.pl.rabbit_2016.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.Constants;
import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.adapters.CompaniesAdapter;
import mad.zut.edu.pl.rabbit_2016.api.RequestCallback;
import mad.zut.edu.pl.rabbit_2016.api.RequestListener;
import mad.zut.edu.pl.rabbit_2016.api.RestClientManager;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;
import retrofit.RetrofitError;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class CompaniesFragment extends Fragment{

    private RecyclerView recyclerView;
    private CompaniesAdapter companiesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.companies_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(android.R.id.list);

        initRecyclerView();
        downloadCompanies();

        return v;
    }

    private void initRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        companiesAdapter = new CompaniesAdapter();
        recyclerView.setAdapter(companiesAdapter);
    }

    private void downloadCompanies(){
        RestClientManager.getAllCompanies(Constants.COMPANIES_API_KEY, new RequestCallback<>(new RequestListener<List<Company>>() {
            @Override
            public void onSuccess(List<Company> response) {
                companiesAdapter.setCompanies(response);
                companiesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));
    }
}
