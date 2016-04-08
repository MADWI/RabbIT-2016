package mad.zut.edu.pl.rabbit_2016.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mad.zut.edu.pl.rabbit_2016.Constants;
import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.RatingDialog;
import mad.zut.edu.pl.rabbit_2016.api.NetworkStateReceiver;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class CompanyActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    @Bind(R.id.company_image_view)
    ImageView companyImageView;

    @Bind(R.id.company_name_view)
    TextView companyNameView;

    @Bind(R.id.company_website_view)
    TextView companyWebsiteView;

    @Bind(R.id.company_room_view)
    TextView companyRoomView;

    @Bind(R.id.company_desc_view)
    TextView companyDescView;

    @Bind(R.id.layout_id)
    ScrollView scrollView;

    private Company company;
    private NetworkStateReceiver networkStateReceiver;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        snackbar = Snackbar
                .make(scrollView, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.settings), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    }
                });

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);

        SwipeBack.attach(this, Position.LEFT)
                .setSwipeBackView(R.layout.swipeback_default);

        company = (Company) getIntent().getExtras().getSerializable("item");
        setData();
    }

    private void setData(){
        companyNameView.setText(company.getName());
        companyWebsiteView.setText(company.getWebsiteUrl());
        companyRoomView.setText(company.getRoom());
        companyDescView.setText(company.getDescription());
        Picasso.with(this)
                .load(company.getLogoUrl())
                .error(R.drawable.no_image)
                .fit()
                .centerInside()
                .into(companyImageView);
    }

    @OnClick(R.id.btn_rate_company)
    public void onClick(View v) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.COMPANY_NAME_KEY, company.getName());
        arguments.putInt(Constants.COMPANY_ID_KEY, Integer.valueOf(company.getId()));

        RatingDialog ratingDialog = new RatingDialog();
        ratingDialog.setArguments(arguments);
        ratingDialog.show(getSupportFragmentManager(), Constants.RATING_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        overridePendingTransition(R.anim.swipeback_stack_to_front, R.anim.swipeback_stack_right_out);
    }

    @Override
    public void networkAvailable() {
        snackbar.dismiss();
    }

    @Override
    public void networkUnavailable() {
        snackbar.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        unregisterReceiver(networkStateReceiver);
        super.onPause();
    }
}
