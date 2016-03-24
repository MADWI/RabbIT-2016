package mad.zut.edu.pl.rabbit_2016.api;

import mad.zut.edu.pl.rabbit_2016.model.company.Datum;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Bartosz Kozajda on 24.03.2016.
 */
public interface RestInterface {
    @GET("/{json}")
    void getAllCompanies(@Path("json") String json, Callback<Datum> cb);
    void getAllEvents(@Path("json") String json, Callback<mad.zut.edu.pl.rabbit_2016.model.event.Datum> cb);
}
