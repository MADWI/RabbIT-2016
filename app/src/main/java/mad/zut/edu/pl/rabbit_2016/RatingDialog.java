package mad.zut.edu.pl.rabbit_2016;

import android.app.Dialog;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.Nullable;
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
import mad.zut.edu.pl.rabbit_2016.model.company.Company;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Marek Macko on 01.04.2016.
 */
public class RatingDialog extends Dialog {

    @Bind(R.id.rating_company_name_view)
    TextView companyNameView;

    @Bind({R.id.rating_bar_criterion1, R.id.rating_bar_criterion2, R.id.rating_bar_criterion3})
    List<RatingBar> ratingBars;

    private Company company;

    public RatingDialog(Context context, Company company) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rating_dialog);
        ButterKnife.bind(this);

        this.company = company;
        companyNameView.setText(company.getName());
    }

    @OnClick(R.id.btn_send_ratings)
    public void onClick() {
        if (isAllRatesSet()) {
            Byte[] opinions = new Byte[ratingBars.size()];
            for (int i = 0; i < ratingBars.size(); i++) {
                opinions[i] = (byte) ratingBars.get(i).getRating();
            }

            RestClientManager.sendCompanyOpinions(
                    new CompanyPostData(Integer.valueOf(company.getId()), opinions, getDeviceId(), getMd5Hash()), new Callback<Response>() {
                        @Override
                        public void success(Response response, Response response2) {
                            Toast.makeText(getContext(), response.getReason(), Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
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
