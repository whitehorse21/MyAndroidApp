package txlabz.com.geoconfess;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import txlabz.com.geoconfess.Fragments.Home_Fragment;
import txlabz.com.geoconfess.Fragments.LoginFragment;
import txlabz.com.geoconfess.Fragments.Spot_Creation_Step1Fragment;
import txlabz.com.geoconfess.web.AppApiController;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView left_drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView icon_back;
    private LinearLayout indisponsilble;
    private LinearLayout indisponsilblegreen;
    private Boolean backstatus=false;
    private ImageView footer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        left_drawer=(ListView)findViewById(R.id.left_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(null,
                GravityCompat.START);
        icon_back=(ImageView)findViewById(R.id.icon_back);
        icon_back.setImageResource(R.drawable.menu);
        icon_back.setVisibility(View.VISIBLE);
        icon_back.setOnClickListener(this);
        indisponsilble=(LinearLayout)findViewById(R.id.indisponsilble);
        footer=(ImageView)findViewById(R.id.footer);
        indisponsilble.setOnClickListener(this);
        indisponsilblegreen=(LinearLayout)findViewById(R.id.indisponsilblegreen);
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        HashMap<String,String>map=new HashMap<>();
        map.put("item","Log Out");
        fillMaps.add(map);
        String[] from = new String[] {"item"};
        int[] to = new int[] {  android.R.id.text1};

        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, android.R.layout.simple_expandable_list_item_2, from, to);
        left_drawer.setAdapter(adapter);
        left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Utils.saveDataString("token","",HomeActivity.this);

                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initHomeFragment();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.indisponsilble:
                indisponsilble.setVisibility(View.GONE);
                indisponsilblegreen.setVisibility(View.VISIBLE);
                Spot_Creation_Step1Fragment f=new Spot_Creation_Step1Fragment();

                loadFragment(f, true,true);

                break;

            case R.id.icon_back:

                if(backstatus) {

                    onBackPressed();
                }
                else {
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


    private void initHomeFragment() {
        Home_Fragment myf = new Home_Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame, myf);
        transaction.commit();

    }

    public void loadFragment(final Fragment fragment,boolean Is_Back_btn_show,boolean Is_Footer_show ) {
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(fragment.getClass().getName());

        transaction.commit();
        if(Is_Back_btn_show)
        {
            icon_back.setImageResource(R.drawable.backred);
            backstatus=Is_Back_btn_show;
        }
        else {

            icon_back.setImageResource(R.drawable.menu);
            backstatus=Is_Back_btn_show;

        }
        if(Is_Footer_show)
        {
            footer.setVisibility(View.VISIBLE);

        }
        else
        {
            footer.setVisibility(View.GONE);


        }
    }





    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();






        if(manager.getBackStackEntryCount() == 1 ) {
//                unlockDrawer();

            icon_back.setImageResource(R.drawable.menu);
            backstatus=false;
            indisponsilble.setVisibility(View.VISIBLE);
            indisponsilblegreen.setVisibility(View.GONE);
            footer.setVisibility(View.GONE);
        }
        GeneralUtility.hideKeyBoard(this);
        super.onBackPressed();
    }





}
