/**
 * Created by arslan on 5/3/2016.
 */
package txlabz.com.geoconfess.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import txlabz.com.geoconfess.HomeActivity;
import txlabz.com.geoconfess.R;
import txlabz.com.geoconfess.Utils;
import txlabz.com.geoconfess.service.TrackLocation;

/**
 * Created by irfanelahi on 27/04/2016.
 */
public class Spot_Creation_Step1Fragment extends Fragment implements View.OnClickListener {

    Button btn_spot;
    Button btn_livetracking;
    static Intent i;
    String name, accessToken;
    String visibilityStatus;
    LinearLayout indisponsilblegreen,indisponsilble;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spotcreation_step1, container, false);

        btn_spot = (Button) view.findViewById(R.id.btn_spot);
        btn_livetracking = (Button) view.findViewById(R.id.btn_livetracking);
        indisponsilblegreen=(LinearLayout)getActivity().findViewById(R.id.indisponsilblegreen);
        indisponsilble=(LinearLayout)getActivity().findViewById(R.id.indisponsilble);
        btn_livetracking.setOnClickListener(this);
        btn_spot.setOnClickListener(this);



          /*
        * Setting defalt Value of name and access_token
        * */
        name = "priest@example.com";
        accessToken = Utils.getDatastring("token", null, getActivity());


          /*
        *  Setting shared preference value of service tracking to notRunnig for the first time to avoid null value further
        * */
        if (Utils.getDatastring("ServiceStatus", null, getActivity()) == null) {
            Utils.saveDataString("ServiceStatus", "NotRunning", getActivity());
        }


          /*
        * Initializing Tracking Service Intent if null
        * */
        if (i == null) {
            i = new Intent(getActivity(), TrackLocation.class);
            i.putExtra("name", name);
            i.putExtra("access_token", accessToken);
            Utils.saveDataString("ServiceStatus", "NotRunning", getActivity());
        }

        visibilityStatus = Utils.getDatastring("ServiceStatus", null, getActivity());
        if (visibilityStatus.equals("Running")) {
            indisponsilble.setVisibility(View.GONE);
            indisponsilblegreen.setVisibility(View.VISIBLE);
        } else {
            indisponsilble.setVisibility(View.VISIBLE);
            indisponsilblegreen.setVisibility(View.GONE);
        }


       btn_spot=(Button)view.findViewById(R.id.btn_spot);
        btn_livetracking=(Button)view.findViewById(R.id.btn_livetracking);
        btn_livetracking.setOnClickListener(this);
        btn_spot.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_spot:
//                Bundle bundle=new Bundle();
//                bundle.putString("isshow", "0");
//

                Spot_Creation_Step2Fragment f=new Spot_Creation_Step2Fragment();
              //  f.setArguments(bundle);
                ((HomeActivity) getActivity()).loadFragment(f, true,false);

                break;
            case R.id.btn_priest:
//                Bundle bundle2=new Bundle();
//                bundle2.putString("isshow", "1");
//                SignUpStep2Fragment f2=new SignUpStep2Fragment();
//                f2.setArguments(bundle2);
//                ((MainActivity) getActivity()).loadFragment(f2, true);

                break;

            case R.id.btn_livetracking:
/*
                * Starting Tracking Service if not tracking  and Stopping if running
                * */
                visibilityStatus = Utils.getDatastring("ServiceStatus", null, getActivity());
                if (!visibilityStatus.equals("Running")) {
                    if(Utils.isgpson(getActivity())) {
                        if (Utils.haveInternet(getActivity())) {
                            getActivity().startService(i);
                            indisponsilble.setVisibility(View.GONE);
                            indisponsilblegreen.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_gps_message), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    getActivity().stopService(i);
                    indisponsilble.setVisibility(View.VISIBLE);
                    indisponsilblegreen.setVisibility(View.GONE);
                }

                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        visibilityStatus = Utils.getDatastring("ServiceStatus", null, getActivity());
    }
}
