package mad.zut.edu.pl.rabbit_2016.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.model.event.Event;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsHolder> {

    private List<Event> event;

    public EventsAdapter() {}

    @Override
    public EventsAdapter.EventsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture_item, parent, false);
        return new EventsHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.EventsHolder holder, int position) {
        holder.lectureDateView.setText(event.get(position).getData());
        holder.lectureNameView.setText(event.get(position).getTitle());
        holder.lectureHourView.setText(event.get(position).getTime().substring(0,5) );
        holder.lectureRoomView.setText("Sala: " + event.get(position).getRoom());
        holder.lectureCompanyNameView.setText(event.get(position).getSpeaker());
        Picasso.with(holder.itemView.getContext())
                .load(event.get(position).getSpeakerPictureUrl())
                .error(R.drawable.no_image)
                .fit()
                .centerInside()
                .into(holder.circularImageView);

    }

    @Override
    public int getItemCount() {
        if (event != null) {
            return event.size();
        }

        return 0;
    }

    public class EventsHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.lecture_date_view) TextView lectureDateView;
        @Bind(R.id.lecture_name_view) TextView lectureNameView;
        @Bind(R.id.lecture_hour_view) TextView lectureHourView;
        @Bind(R.id.lecture_room_view) TextView lectureRoomView;
        @Bind(R.id.speaker_icon_view) ImageView circularImageView;
        @Bind(R.id.lecture_company_name_view) TextView lectureCompanyNameView;

        public EventsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setCompanies(List<Event> data) {
        event = data;
        notifyDataSetChanged();
    }
}
