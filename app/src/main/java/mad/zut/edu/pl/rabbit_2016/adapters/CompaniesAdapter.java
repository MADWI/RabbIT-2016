package mad.zut.edu.pl.rabbit_2016.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.CompaniesHolder> {

    private List<Company> company;

    public CompaniesAdapter() {}

    @Override
    public CompaniesAdapter.CompaniesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);
        return new CompaniesHolder(view);
    }

    @Override
    public void onBindViewHolder(CompaniesAdapter.CompaniesHolder holder, int position) {
        holder.companyName.setText(company.get(position).getName());
        holder.companyRoom.setText("Sala: " + company.get(position).getRoom());
        Picasso.with(holder.itemView.getContext())
                .load(company.get(position).getLogoUrl())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .fit()
                .centerInside()
                .into(holder.companyLogo);
    }

    @Override
    public int getItemCount() {
        try{
            return company.size();
        }catch (NullPointerException e){

        }
        return 0;
    }

    public class CompaniesHolder extends RecyclerView.ViewHolder {
        public final TextView companyName;
        public final TextView companyRoom;
        public final ImageView companyLogo;

        public CompaniesHolder(View itemView) {
            super(itemView);
            companyName = (TextView) itemView.findViewById(R.id.company_name);
            companyRoom = (TextView) itemView.findViewById(R.id.company_room);
            companyLogo = (ImageView) itemView.findViewById(R.id.company_logo);
        }
    }

    public void setCompanies(List<Company> data) {
        company = data;
    }
}
