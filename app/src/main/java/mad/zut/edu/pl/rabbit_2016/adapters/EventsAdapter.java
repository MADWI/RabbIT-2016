package mad.zut.edu.pl.rabbit_2016.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsHolder> {

    private List<Company> company;

    public EventsAdapter() {}

    @Override
    public EventsAdapter.EventsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture_item, parent, false);
        return new EventsHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.EventsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class EventsHolder extends RecyclerView.ViewHolder {

        public EventsHolder(View itemView) {
            super(itemView);
        }
    }
}
