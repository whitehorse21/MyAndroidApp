package txlabz.com.geoconfess;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import txlabz.com.geoconfess.Fragments.ToggleAvailability;
import txlabz.com.geoconfess.web.AppApiController;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView icon_back;
    LinearLayout indisponsilble;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(null,
                GravityCompat.START);
        icon_back=(ImageView)findViewById(R.id.icon_back);
        icon_back.setImageResource(R.drawable.menu);
        icon_back.setVisibility(View.VISIBLE);
        icon_back.setOnClickListener(this);
        indisponsilble=(LinearLayout)findViewById(R.id.indisponsilble);
        indisponsilble.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.indisponsilble:
                /*
                * Loading ToggleAvailability fragment on the frame layout to allow user to be set his visibility
                * */

                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.content_frame,new ToggleAvailability(),"").commit();
                break;

            case R.id.icon_back:
                if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                    mDrawerLayout.setScrimColor(Color.TRANSPARENT);

                } else {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);

                    mDrawerLayout.setScrimColor(Color.TRANSPARENT);
                }
                break;
        }
    }


}
