package mad.zut.edu.pl.rabbit_2016.api;

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.model.CompanyPostData;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;
import mad.zut.edu.pl.rabbit_2016.model.event.Event;
import retrofit.Callback;
import retrofit.client.Response;

/**
 * Created by Bartosz Kozajda on 24.03.2016.
 */
public class RestClientManager {
    public static final RestClient client = new RestClient();

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

    public static void sendCompanyOpinions(CompanyPostData companyPostData, Callback<Response> callback) {
        RestInterface restInterface = getRestApi();
        restInterface.sendCompanyOpinions(companyPostData, callback);
    }
}
