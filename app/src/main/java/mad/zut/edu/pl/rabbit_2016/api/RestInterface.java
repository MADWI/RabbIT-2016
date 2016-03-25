package mad.zut.edu.pl.rabbit_2016.api;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.model.company.Company;
import mad.zut.edu.pl.rabbit_2016.model.event.Event;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Bartosz Kozajda on 24.03.2016.
 */
public interface RestInterface {
    @GET("/{json}")
    void getAllCompanies(@Path("json") String json, Callback<List<Company>> cb);
    void getAllEvents(@Path("json") String json, Callback<List<Event>> cb);
}
