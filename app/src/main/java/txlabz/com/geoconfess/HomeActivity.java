package txlabz.com.geoconfess;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import txlabz.com.geoconfess.web.AppApiController;

public class HomeActivity extends Activity implements View.OnClickListener {
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
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.indisponsilble:


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
