package mad.zut.edu.pl.rabbit_2016.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import mad.zut.edu.pl.rabbit_2016.Constants;
import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.RatingDialog;
import mad.zut.edu.pl.rabbit_2016.api.NetworkStateReceiver;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class CompanyActivity extends AppCompatActivity
        implements NetworkStateReceiver.NetworkStateReceiverListener, RatingDialog.OnRatesSendListener {

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

    @Bind(R.id.bar_rate_company)
    RatingBar barRateCompany;

    private Company company;
    private NetworkStateReceiver networkStateReceiver;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        company = (Company) getIntent().getExtras().getSerializable("item");
        SwipeBack.attach(this, Position.LEFT)
                .setSwipeBackView(R.layout.swipeback_default);


        initSnackBar();
        initNetworkReceiver();
        initRatingDialog();
        setData();
    }

    private void setData() {
        readSavedData();

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


    private void readSavedData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCES_RATINGS, Context.MODE_PRIVATE);
        float averageRate = sharedPreferences.getFloat(company.getId() + Constants.AVERAGE_KEY, 0);

        if (barRateCompany != null) {
            barRateCompany.setRating(averageRate);
        }
    }

    private void initRatingDialog() {
        barRateCompany.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bundle arguments = new Bundle();
                    arguments.putString(Constants.COMPANY_NAME_KEY, company.getName());
                    arguments.putInt(Constants.COMPANY_ID_KEY, Integer.valueOf(company.getId()));

                    RatingDialog ratingDialog = new RatingDialog();
                    ratingDialog.setArguments(arguments);
                    ratingDialog.show(getSupportFragmentManager(), Constants.RATING_FRAGMENT);

                    return true;
                }

                return false;
            }
        });
    }

    private void initSnackBar() {
        snackbar = Snackbar
                .make(scrollView, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.settings), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    }
                });
    }

    private void initNetworkReceiver() {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
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

    @Override
    public void onRatesSend(float averageRate) {
        if (barRateCompany == null) {
            return;
        }

        barRateCompany.setRating(averageRate);
    }
}
