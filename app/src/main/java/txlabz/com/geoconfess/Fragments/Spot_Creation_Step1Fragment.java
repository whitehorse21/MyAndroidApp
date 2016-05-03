/**
 * Created by arslan on 5/3/2016.
 */
package txlabz.com.geoconfess.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import txlabz.com.geoconfess.DialogUtility;
import txlabz.com.geoconfess.GeneralUtility;
import txlabz.com.geoconfess.MainActivity;
import txlabz.com.geoconfess.R;
import txlabz.com.geoconfess.web.AppApiController;

/**
 * Created by irfanelahi on 27/04/2016.
 */
public class Spot_Creation_Step1Fragment extends Fragment implements View.OnClickListener{

    Button btnUser;
    Button btnPriest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spotcreation_step1, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user:
                Bundle bundle=new Bundle();
                bundle.putString("isshow", "0");
                SignUpStep2Fragment f=new SignUpStep2Fragment();
                f.setArguments(bundle);
                ((MainActivity) getActivity()).loadFragment(f, true);

                break;
            case R.id.btn_priest:
                Bundle bundle2=new Bundle();
                bundle2.putString("isshow", "1");
                SignUpStep2Fragment f2=new SignUpStep2Fragment();
                f2.setArguments(bundle2);
                ((MainActivity) getActivity()).loadFragment(f2, true);

                break;


        }
    }


}
