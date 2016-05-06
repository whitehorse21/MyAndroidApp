package txlabz.com.geoconfess.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import txlabz.com.geoconfess.R;
import txlabz.com.geoconfess.views.SupportMap;

/**
 * Created by arslan on 5/3/2016.
 */
public class Home_Fragment extends Fragment {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 0; // 1 minute
    private GoogleMap googleMap;
    SupportMap mMapFragment;
    private static final int REQUEST_CODE_LOCATION = 2;
    private LocationManager locationManager;
    static public String longitude = " ";
    static public String latitude = " ";
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        getGps();
        if (mMapFragment == null) {
            mMapFragment = new SupportMap();

            SupportMap.MapViewCreatedListener mapViewCreatedListener = new SupportMap.MapViewCreatedListener() {
                @Override
                public void onMapCreated() {
                    googleMap = mMapFragment.getMap();
                    if (googleMap != null) {
                        upadte();
                    }

                }
            };
            mMapFragment.itsMapViewCreatedListener = mapViewCreatedListener;

            FragmentManager fm = getChildFragmentManager();
            SupportMapFragment supportMapFragment = mMapFragment;
            fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();

        }


        return view;
    }
    public void upadte()
    {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }


        googleMap.setMyLocationEnabled(true);


        LatLng TutorialsPoint = new LatLng(location.getLatitude() , location.getLongitude());

        pointToPosition(TutorialsPoint);



    }

    private void pointToPosition(LatLng position) {
        //Build camera position
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(14).build();
        //Zoom in and animate the camera.
        googleMap.addMarker(new MarkerOptions()
                .position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer)));

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    @Override
    public void onResume() {
        super.onResume();
        getGps();
    }

    private void getGps() {

        try {

            locationManager = (LocationManager) getActivity().getSystemService(
                    Context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            System.out.println("isgps en:" + isGPSEnabled);


            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            System.out.println("isgps nt en:" + isNetworkEnabled);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled

                System.out.println("no network called");

            } else {

                // this.canGetLocation = true;
                // First get location from Network Provider
                System.out.println("can get");

                if (isNetworkEnabled) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
                    }

                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);

                    // locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
                    // locationListener, null);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = String.valueOf(location.getLatitude());
                            longitude = String.valueOf(location.getLongitude());
                            System.out.println("longitude:" + longitude);
                            System.out.println("lotitude:" + latitude);


                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services

                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                                locationListener);

                        Log.d("GPS Enabled", "GPS Enabled");

                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = String.valueOf(location
                                        .getLatitude());
                                longitude = String.valueOf(location
                                        .getLongitude());
                                System.out.println("longitude:" + longitude);
                                System.out.println("lotitude:" + latitude);

                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String arg0) {

        }

        @Override
        public void onLocationChanged(Location location) {

            if (location != null) {
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());



            }

        }
    };


}
