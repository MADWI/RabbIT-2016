package mad.zut.edu.pl.rabbit_2016;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mad.zut.edu.pl.rabbit_2016.api.RestClientManager;
import mad.zut.edu.pl.rabbit_2016.model.CompanyPostData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.client.UrlConnectionClient;
import retrofit.mime.TypedByteArray;

/**
 * Created by Marek Macko on 01.04.2016.
 */
public class RatingDialog extends android.support.v4.app.DialogFragment {

    @Bind(R.id.rating_company_name_view)
    TextView companyNameView;

    @Bind({R.id.rating_bar_criterion1, R.id.rating_bar_criterion2, R.id.rating_bar_criterion3})
    List<RatingBar> ratingBars;

    private int companyId;
    private float averageRate;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rating_dialog, container, false);
        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        initCompanyId();

        sharedPreferences = getContext().getSharedPreferences(Constants.PREFERENCES_RATINGS, Context.MODE_PRIVATE);
        for (int i = 0; i < ratingBars.size(); i++) {
            float rating = sharedPreferences.getInt(companyId + Constants.CRITERION_KEY + i, 0);
            ratingBars.get(i).setRating(rating);
        }
    }

    @OnClick(R.id.btn_send_ratings)
    public void onClick() {
        if (isAllRatesSet()) {
            byte[] ratings = new byte[ratingBars.size()];

            for (int i = 0; i < ratingBars.size(); i++) {
                ratings[i] = (byte) ratingBars.get(i).getRating();
                averageRate += ratings[i];
            }
            averageRate /= ratingBars.size();

            sendCompanyOpinions(ratings);
        }
    }

    private void saveRatings(final byte[] ratings) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        averageRate = 0;
        for (int i = 0; i < ratingBars.size(); i++) {
            byte rating = ratings[i];
            editor.putInt(companyId + Constants.CRITERION_KEY + i, rating);
            averageRate += rating;
        }

        averageRate /= ratingBars.size();
        editor.putFloat(companyId + Constants.AVERAGE_KEY, averageRate);
        editor.apply();
    }

    private void sendCompanyOpinions(final byte[] companyRatings) {
        CompanyPostData.Ratings ratings = new CompanyPostData.Ratings(
                companyRatings[0],
                companyRatings[1],
                companyRatings[2]
        );
        RestClientManager.sendCompanyOpinions(
                new CompanyPostData(companyId, ratings, getDeviceId(), getHash()), new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        dismiss();
                        saveRatings(companyRatings);
                        if (getActivity() instanceof OnRatesSendListener) {
                            ((OnRatesSendListener)getActivity()).onRatesSend(averageRate);
                        }

                        Toast.makeText(getContext(), R.string.send_success, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getContext(), R.string.send_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initCompanyId() {
        Bundle arguments = getArguments();
        String companyName = arguments.getString(Constants.COMPANY_NAME_KEY);
        companyId = arguments.getInt(Constants.COMPANY_ID_KEY);
        companyNameView.setText(companyName);
    }

    private boolean isAllRatesSet() {
        for (RatingBar ratingBar : ratingBars) {
            if (ratingBar.getRating() < 1) {
                Toast.makeText(getContext(), R.string.criterion_error, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Nullable
    private String getHash() {
        String key = Constants.MD5_KEY + getDeviceId();
        StringBuilder digest = new StringBuilder();
        byte[] digestBytes;

        try {
            digestBytes = MessageDigest.getInstance("md5").digest(key.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        for (int i = 0; i < 16; i++) {
            digest.append(String.format("%02x", digestBytes[i]));
        }

        return digest.toString();
    }

    private String getDeviceId() {
        return Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public interface OnRatesSendListener {
        void onRatesSend(float averageRate);
    }
}
