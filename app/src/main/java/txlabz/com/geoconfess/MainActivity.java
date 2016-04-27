package txlabz.com.geoconfess;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import txlabz.com.geoconfess.Fragments.LoginFragment;
import txlabz.com.geoconfess.views.CustomProgressDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = CustomProgressDialog.ctor(this);
        initLoginFragment();
    }

    private void initLoginFragment() {
        LoginFragment myf = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mainframe, myf);
        transaction.commit();

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public void showDialog() {
        progressDialog.show();
    }

    public void hideDialog() {
        progressDialog.dismiss();
    }

    public void loadFragment(final Fragment fragment) {
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.mainframe, fragment);
        transaction.addToBackStack(fragment.getClass().getName());

        transaction.commit();
    }


    @Override
    public void onBackPressed() {
            FragmentManager manager = getSupportFragmentManager();
            if(manager.getBackStackEntryCount() == 1 ) {
//                unlockDrawer();
            }
            GeneralUtility.hideKeyBoard(this);
            super.onBackPressed();
        }


}

