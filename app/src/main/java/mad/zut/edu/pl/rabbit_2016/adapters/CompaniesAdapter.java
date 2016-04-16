package mad.zut.edu.pl.rabbit_2016.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mad.zut.edu.pl.rabbit_2016.Constants;
import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.CompaniesHolder> {

    private List<Company> company;
    private ClickListener clickListener;
    private SharedPreferences sharedPreferences;

    public CompaniesAdapter() {}

    @Override
    public CompaniesAdapter.CompaniesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);
        sharedPreferences = view.getContext().getSharedPreferences(Constants.PREFERENCES_RATINGS, Context.MODE_PRIVATE);
        return new CompaniesHolder(view);
    }

    @Override
    public void onBindViewHolder(CompaniesAdapter.CompaniesHolder holder, int position) {
        holder.rate = sharedPreferences.getFloat(company.get(position).getId() + Constants.AVERAGE_KEY, 0);
        if(holder.rate > 0){
            holder.checkIcon.setColorFilter(Color.parseColor("#00C853"));
            holder.checkIcon.setVisibility(View.VISIBLE);
        }else{
            holder.checkIcon.setVisibility(View.GONE);
        }

        holder.companyName.setText(company.get(position).getName());
        holder.companyRoom.setText("Sala: " + company.get(position).getRoom());
        Picasso.with(holder.itemView.getContext())
                .load(company.get(position).getLogoUrl())
                .error(R.drawable.no_image)
                .fit()
                .centerInside()
                .into(holder.companyLogo);
    }

    @Override
    public int getItemCount() {
        if (company != null) {
            return company.size();
        }

        return 0;
    }

    public class CompaniesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.company_name) TextView companyName;
        @Bind(R.id.company_room) TextView companyRoom;
        @Bind(R.id.company_logo) ImageView companyLogo;
        @Bind(R.id.checkbox) ImageView checkIcon;
        public float rate;

        public CompaniesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rate = 0;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setCompanies(List<Company> data) {
        company = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
