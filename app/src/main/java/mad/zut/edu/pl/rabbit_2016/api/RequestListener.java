package mad.zut.edu.pl.rabbit_2016.api;

import retrofit.RetrofitError;

/**
 * Created by Bartosz Kozajda on 24.03.2016.
 */
public interface RequestListener<T> {

    void onSuccess(T response);

    void onFailure(RetrofitError error);
}