package txlabz.com.geoconfess.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;

import java.util.List;
import java.util.Locale;

import txlabz.com.geoconfess.Adapters.PlaceAutocompleteAdapter;
import txlabz.com.geoconfess.HomeActivity;
import txlabz.com.geoconfess.R;
import txlabz.com.geoconfess.views.SupportMap;

/**
 * Created by arslan on 5/5/2016.
 */
public class Spot_Creation_Step3Fragment  extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener

{
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocompleteView;
    String lat;
    private static final int REQUEST_CODE_LOCATION = 2;
    String Putaddress;
    String provider;
    protected boolean gps_enabled,network_enabled;
    private GoogleMap googleMap;
    private static LatLngBounds BOUNDS_GREATER_SYDNEY=null ;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0; // 1 minute
    //	private String state;
    private LocationManager locationManager;
    static public String longitude = " ";
    static public String latitude = " ";
    SupportMap mMapFragment;
    private Location location;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spotcreation_step3, container, false);
        getGps();

        if( mGoogleApiClient==null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), 0 /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .build();
        }
        mAutocompleteView=(AutoCompleteTextView)view.findViewById(R.id.autocomplete);

        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        mAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        mAutocompleteView.setAdapter(mAdapter);



        if (mMapFragment == null) {
            mMapFragment = new SupportMap();

            SupportMap.MapViewCreatedListener mapViewCreatedListener = new SupportMap.MapViewCreatedListener() {
                @Override
                public void onMapCreated() {
                    googleMap = mMapFragment.getMap();
                    if (googleMap != null) {
                        update();
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


    private void update()
    {

        try {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
            }


            googleMap.setMyLocationEnabled(true);
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                // Use default InfoWindow frame
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                // Defines the contents of the InfoWindow
                @Override
                public View getInfoContents(Marker arg0) {

                    // Getting view from the layout file info_window_layout
                    View v = getActivity().getLayoutInflater().inflate(R.layout.windowmarker, null);

                    TextView address=(TextView)v.findViewById(R.id.address);
                    address.setText(Putaddress);
                    Button add=(Button)v.findViewById(R.id.add);
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });


                    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            dialogshow();
                        }
                    });


                    return v;

                }
            });



        }
        catch (Exception e) {
            e.printStackTrace();
        }


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // Clears any existing markers from the GoogleMap
                googleMap.clear();

                // Creating an instance of MarkerOptions to set position
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting position on the MarkerOptions
                markerOptions.position(arg0);

                // Animating to the currently touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));

                // Adding marker on the GoogleMap
                Marker marker = googleMap.addMarker(markerOptions);

                // Showing InfoWindow on the GoogleMap
                marker.showInfoWindow();

            }
        });

        LatLng TutorialsPoint = new LatLng(location.getLatitude() , location.getLongitude());

        Putaddress=getCompleteAddressString(location.getLatitude(),location.getLongitude());
        pointToPosition(TutorialsPoint);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user:

                break;
            case R.id.btn_priest:

                break;


        }
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);
   PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                places.release();
                return;
            }
            final Place place = places.get(0);

            Putaddress=mAutocompleteView.getText().toString();

            pointToPosition(place.getLatLng());
            places.release();
        }
    };

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){


        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(getActivity(),
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }


    private void pointToPosition(LatLng position) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(14).build();
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
                            BOUNDS_GREATER_SYDNEY = new LatLngBounds(
                                    new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(location.getLatitude(), location.getLongitude()));


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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    private void dialogshow() {
        final Dialog dialog2 = new Dialog(getActivity());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog2.setContentView(R.layout.dialogshow);
        dialog2.show();

        Button ok = (Button) dialog2.findViewById(R.id.ok);
        Button cancel = (Button) dialog2.findViewById(R.id.cancel);
        final EditText spotname=(EditText)dialog2.findViewById(R.id.spotname);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();

                if(!spotname.getText().toString().equalsIgnoreCase("")) {
                    Spot_Creation_Step4Fragment f = new Spot_Creation_Step4Fragment();
                    Bundle b = new Bundle();
                    b.putString("spotname", spotname.getText().toString());
                    f.setArguments(b);
                    ((HomeActivity) getActivity()).loadFragment(f, true, false);
                }
                else
                {
                    Toast.makeText(getActivity(),"Please enter a Spot name",Toast.LENGTH_LONG).show();

                }
                }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

    }







}