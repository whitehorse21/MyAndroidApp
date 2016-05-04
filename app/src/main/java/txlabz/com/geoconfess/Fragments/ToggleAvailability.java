package txlabz.com.geoconfess.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import txlabz.com.geoconfess.R;
import txlabz.com.geoconfess.sharedpreference.MySharedPreference;
import txlabz.com.geoconfess.tracking.service.TrackLocation;

/**
 * This fragment is used to toggle the Availability of the user
 *
 * @author CanopusInfoSystems
 * @version 1.0
 * @since 2016-05-04
 */
public class ToggleAvailability extends Fragment implements View.OnClickListener {

    /*
    * Declaring the Views
    * */
    LinearLayout indisponsilble;
    ImageView circleIndisonible;
    TextView indisponible;
    ImageView mMobile;

    String visibilityStatus;
    MySharedPreference mySharedPreference;
    String name, accessToken;
    static Intent i;


    public ToggleAvailability() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toggle_availability, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        /*
        * Initializing Views of the fragements
        * */
        indisponsilble = (LinearLayout) getActivity().findViewById(R.id.indisponsilble);
        circleIndisonible = (ImageView) getActivity().findViewById(R.id.circle_indisponible);
        indisponible = (TextView) getActivity().findViewById(R.id.indisponsilble_text);
        mMobile = (ImageView) getActivity().findViewById(R.id.mobile);


        /*
        * Setting defalt Value of name and access_token
        * */
        name = "priest@example.com";
        accessToken = mySharedPreference.getInstance(getActivity()).getString("accessToken");

        /*
        *  Setting shared preference value of service tracking to notRunnig for the first time to avoid null value further
        * */
        if (mySharedPreference.getInstance(getActivity()).getString("ServiceStatus").equals("")) {
            mySharedPreference.getInstance(getActivity()).putString("ServiceStatus", "NotRunning");
        }

        /*
        * Setting the color of Layouts according to the tracking status saves in Shared Preferences
        * */
        visibilityStatus = mySharedPreference.getInstance(getActivity()).getString("ServiceStatus");
        if (visibilityStatus.equals("Running")) {
            indisponsilble.setBackground(getActivity().getDrawable(R.drawable.disponible_1));
            circleIndisonible.setImageResource(R.drawable.disponible_2);
            indisponible.setTextColor(Color.GREEN);
        } else {
            indisponsilble.setBackground(getActivity().getDrawable(R.drawable.indisponible));
            circleIndisonible.setImageResource(R.drawable.indisponible2);
            indisponible.setTextColor(Color.RED);
        }

        mMobile.setOnClickListener(this);

        /*
        * Initializing Tracking Service Intent if null
        * */
        if (i == null) {
            i = new Intent(getActivity(), TrackLocation.class);
            i.putExtra("name", name);
            i.putExtra("access_token", accessToken);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mobile:
                /*
                * Starting Tracking Service if not tracking  and Stopping if running
                * */
                visibilityStatus = mySharedPreference.getInstance(getActivity()).getString("ServiceStatus");
                if (!visibilityStatus.equals("Running")) {
                    indisponsilble.setBackground(getActivity().getDrawable(R.drawable.disponible_1));
                    circleIndisonible.setImageResource(R.drawable.disponible_2);
                    indisponible.setTextColor(Color.GREEN);

                    getActivity().startService(i);
                } else {
                    mySharedPreference.getInstance(getActivity()).putString("ServiceStatus", "NotRunning");
                    getActivity().stopService(i);
                }
                break;
        }

    }

}
