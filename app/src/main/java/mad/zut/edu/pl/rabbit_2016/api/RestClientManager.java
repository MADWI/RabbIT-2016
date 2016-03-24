package mad.zut.edu.pl.rabbit_2016.api;

import retrofit.Callback;

/**
 * Created by Bartosz Kozajda on 24.03.2016.
 */
public class RestClientManager {
    public static RestClient client = new RestClient();

    public static RestInterface getRestApi(){
        return client.getService();
    }
}
