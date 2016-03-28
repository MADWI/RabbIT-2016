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

import java.util.List;

import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.api.NetworkStateReceiver;
import mad.zut.edu.pl.rabbit_2016.model.company.Company;

/**
 * Created by Bartosz Kozajda on 26.03.2016.
 */
public class CompanyActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    private static List<Company> company;
    private static int position;
    private ImageView companyImageView;
    private TextView companyNameView;
    private TextView companyWebsiteView;
    private TextView companyRoomView;
    private TextView companyDescView;
    private ScrollView scrollView;
    private NetworkStateReceiver networkStateReceiver;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        scrollView = (ScrollView) findViewById(R.id.layout_id);
        companyImageView = (ImageView) findViewById(R.id.company_image_view);
        companyNameView = (TextView) findViewById(R.id.company_name_view);
        companyWebsiteView = (TextView) findViewById(R.id.company_website_view);
        companyRoomView = (TextView) findViewById(R.id.company_room_view);
        companyDescView = (TextView) findViewById(R.id.company_desc_view);

        snackbar = Snackbar
                .make(scrollView, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.settings), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                });

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        SwipeBack.attach(this, Position.LEFT)
                .setSwipeBackView(R.layout.swipeback_default);

        setData(position);
    }

    private void setData(int position){
        companyNameView.setText(company.get(position).getName());
        companyWebsiteView.setText(company.get(position).getWebsiteUrl());
        companyRoomView.setText(company.get(position).getRoom());
        companyDescView.setText(company.get(position).getDescription());
        Picasso.with(this)
                .load(company.get(position).getLogoUrl())
                .error(R.drawable.no_image)
                .fit()
                .centerInside()
                .into(companyImageView);
    }

    public static void setCompanies(List<Company> data) {
        company = data;
    }

    public static void setPosition(int data){
        position = data;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        overridePendingTransition(R.anim.swipeback_stack_to_front, R.anim.swipeback_stack_right_out);
        this.unregisterReceiver(networkStateReceiver);
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
        super.onPause();
        try{
            this.unregisterReceiver(networkStateReceiver);
        }catch (IllegalArgumentException e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            this.unregisterReceiver(networkStateReceiver);
        }catch (IllegalArgumentException e){

        }
    }
}
