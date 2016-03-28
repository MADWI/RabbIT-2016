package mad.zut.edu.pl.rabbit_2016.api;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.model.company.Company;
import mad.zut.edu.pl.rabbit_2016.model.event.Event;
import retrofit.Callback;

/**
 * Created by Bartosz Kozajda on 24.03.2016.
 */
public class RestClientManager {
    public static RestClient client = new RestClient();

    public static RestInterface getRestApi(){
        return client.getService();
    }

    public static void getAllCompanies(Callback<List<Company>> callback){
        RestInterface restInterface = getRestApi();
        restInterface.getAllCompanies(callback);
    }

    public static void getAllEvents(Callback<List<Event>> callback){
        RestInterface restInterface = getRestApi();
        restInterface.getAllEvents(callback);
    }
}
