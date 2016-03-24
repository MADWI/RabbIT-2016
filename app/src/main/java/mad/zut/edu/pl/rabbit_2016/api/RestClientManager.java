package mad.zut.edu.pl.rabbit_2016.api;

import mad.zut.edu.pl.rabbit_2016.model.company.Datum;
import retrofit.Callback;

/**
 * Created by Bartosz Kozajda on 24.03.2016.
 */
public class RestClientManager {
    public static RestClient client = new RestClient();

    public static RestInterface getRestApi(){
        return client.getService();
    }
    public static void getAllCompanies(String json, Callback<Datum> callback){
        RestInterface restInterface = getRestApi();
        restInterface.getAllCompanies(json, callback);
    }

    public static void getAllEvents(String json, Callback<mad.zut.edu.pl.rabbit_2016.model.event.Datum> callback){
        RestInterface restInterface = getRestApi();
        restInterface.getAllEvents(json, callback);
    }
}
