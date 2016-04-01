package mad.zut.edu.pl.rabbit_2016;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;

/**
 * Created by Marek Macko on 01.04.2016.
 */
public class RatingDialog extends Dialog {

    @Bind(R.id.rating_company_name_view)
    TextView companyNameView;

    public RatingDialog(Context context, Company company) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rating_dialog);
        ButterKnife.bind(this);

        companyNameView.setText(company.getName());
    }

    @OnClick(R.id.btn_send_ratings)
    public void onClick(View v) {
        Toast.makeText(getContext(), "Button clicked :)", Toast.LENGTH_SHORT).show();
    }
}
