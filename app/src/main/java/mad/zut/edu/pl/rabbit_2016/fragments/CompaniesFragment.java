package mad.zut.edu.pl.rabbit_2016.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.activities.CompanyActivity;
import mad.zut.edu.pl.rabbit_2016.adapters.CompaniesAdapter;
import mad.zut.edu.pl.rabbit_2016.api.RequestCallback;
import mad.zut.edu.pl.rabbit_2016.api.RequestListener;
import mad.zut.edu.pl.rabbit_2016.api.RestClientManager;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;
import retrofit.RetrofitError;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class CompaniesFragment extends Fragment implements CompaniesAdapter.ClickListener{

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

    @Override
    public void onItemClick(int position, View v) {
        CompanyActivity.setPosition(position);
        Intent myIntent = new Intent(getActivity(), CompanyActivity.class);
        startActivity(myIntent);
    }

    private void initRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        companiesAdapter = new CompaniesAdapter();
        companiesAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(companiesAdapter);
    }

    private void downloadCompanies(){
        RestClientManager.getAllCompanies(new RequestCallback<>(new RequestListener<List<Company>>() {
            @Override
            public void onSuccess(List<Company> response) {
                companiesAdapter.setCompanies(response);
                CompanyActivity.setCompanies(response);
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));
    }
}