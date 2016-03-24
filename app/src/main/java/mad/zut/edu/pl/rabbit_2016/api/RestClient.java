package mad.zut.edu.pl.rabbit_2016.api;

import mad.zut.edu.pl.rabbit_2016.Constants;
import retrofit.RestAdapter;

/**
 * Created by Bartosz Kozajda on 24.03.2016.
 */
public class RestClient {
    private RestInterface restInterface;

    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .build();

        restInterface = restAdapter.create(RestInterface.class);
    }

    public RestInterface getService(){
        return restInterface;
    }
}
