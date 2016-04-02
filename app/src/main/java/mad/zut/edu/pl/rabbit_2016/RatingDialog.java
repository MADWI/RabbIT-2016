package mad.zut.edu.pl.rabbit_2016;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
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

/**
 * Created by Marek Macko on 01.04.2016.
 */
public class RatingDialog extends android.support.v4.app.DialogFragment {

    @Bind(R.id.rating_company_name_view)
    TextView companyNameView;

    @Bind({R.id.rating_bar_criterion1, R.id.rating_bar_criterion2, R.id.rating_bar_criterion3})
    List<RatingBar> ratingBars;

    private int companyId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rating_dialog, container, false);
        ButterKnife.bind(this, view);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Bundle arguments = getArguments();
        String companyName = arguments.getString(Constants.COMPANY_NAME_KEY);
        companyId = arguments.getInt(Constants.COMPANY_ID_KEY);

        companyNameView.setText(companyName);

        return view;
    }



    @OnClick(R.id.btn_send_ratings)
    public void onClick() {
        if (isAllRatesSet()) {
            Byte[] opinions = new Byte[ratingBars.size()];
            for (int i = 0; i < ratingBars.size(); i++) {
                opinions[i] = (byte) ratingBars.get(i).getRating();
            }

            sendCompanyOpinions(opinions);
        }
    }

    private void sendCompanyOpinions(Byte[] opinions) {
        RestClientManager.sendCompanyOpinions(
                new CompanyPostData(companyId, opinions, getDeviceId(), getMd5Hash()), new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        dismiss();
                        Toast.makeText(getContext(), R.string.send_success, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getContext(), R.string.send_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Boolean isAllRatesSet() {
        for (RatingBar ratingBar : ratingBars) {
            if (ratingBar.getRating() < 1) {
                Toast.makeText(getContext(), "You must set all criterion", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Nullable
    private String getMd5Hash() {
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
        return Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
