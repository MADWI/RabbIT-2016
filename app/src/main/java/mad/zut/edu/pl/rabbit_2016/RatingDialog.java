package mad.zut.edu.pl.rabbit_2016;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mad.zut.edu.pl.rabbit_2016.api.RestClientManager;
import mad.zut.edu.pl.rabbit_2016.model.CompanyPostData;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;
import retrofit.client.Response;

/**
 * Created by Marek Macko on 01.04.2016.
 */
public class RatingDialog extends Dialog {

    @Bind(R.id.rating_company_name_view)
    TextView companyNameView;

    @Bind({R.id.rating_bar_criterion1, R.id.rating_bar_criterion2, R.id.rating_bar_criterion3})
    List<RatingBar> ratingBars;


    public RatingDialog(Context context, Company company) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rating_dialog);
        ButterKnife.bind(this);

        companyNameView.setText(company.getName());
    }

    @OnClick(R.id.btn_send_ratings)
    public void onClick() {
        if (isAllRatesSet()) {
            Byte[] opinions = new Byte[3];
            opinions[0] = 3;
            opinions[1] = 1;
            opinions[2] = 5;
            Response response = RestClientManager.sendCompanyOpinions(new CompanyPostData(1, opinions, 234234, new Byte[]{2, 3, 4, 2, 3, 4}));
            Toast.makeText(getContext(), response.getReason(), Toast.LENGTH_SHORT).show();
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
}
