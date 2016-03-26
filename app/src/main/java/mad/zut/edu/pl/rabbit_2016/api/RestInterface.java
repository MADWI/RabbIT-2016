package mad.zut.edu.pl.rabbit_2016.api;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.Constants;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;
import mad.zut.edu.pl.rabbit_2016.model.event.Event;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Bartosz Kozajda on 24.03.2016.
 */
public interface RestInterface {
    @GET("/" + Constants.COMPANIES_API_KEY + "/")
    void getAllCompanies(Callback<List<Company>> cb);
    @GET("/" + Constants.EVENTS_API_KEY + "/")
    void getAllEvents(Callback<List<Event>> cb);
}
