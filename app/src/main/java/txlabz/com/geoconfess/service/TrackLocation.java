package txlabz.com.geoconfess.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import txlabz.com.geoconfess.R;
import txlabz.com.geoconfess.Utils;
import txlabz.com.geoconfess.models.response.SpotResponse;
import txlabz.com.geoconfess.web.AppApiController;

/**
 * This is the service class which handles the location tracking using Google Fused Api
 *
 * @author CanopusInfoSystems
 * @version 1.0
 * @since 2016-05-04
 */

public class TrackLocation extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    String name, accessToken;
    GoogleApiClient googleApiClient;
    LocationRequest mLocationRequest;
    boolean spotCreation = false;
    Location mCurrentLocation, mLastLocation;
    Context context;
    int spotId;
    long trackingIntervel = 600000l; // set In millis
    float distanceToUpdate = 100f; //in Meters


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);

        name = (String) intent.getExtras().get("name");
        accessToken = (String) intent.getExtras().get("access_token");

         /*
        * Initializing GoogleApiClient object for Using Google Fused Api for Location Service
        * */
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        /*
        * Connecting Google API Client to start using Fused API
        * */
        googleApiClient.connect();


        /*
        * Starting service not sticky so that the service will be running even after the application is being killed
        * */
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Utils.saveDataString("ServiceStatus", "NotRunning", this);
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

        Call<ResponseBody> oathAPICall = AppApiController.getApiInstance().deleteSpot(spotId, accessToken);

        oathAPICall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Tracking Stopped \n" +
                            "Last Latitiute = " + mLastLocation.getLatitude() + "\n" +
                            "Last Longitude = " + mLastLocation.getLongitude() + "\n"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(context, "Message Error.sopt update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(context, "Message" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
             /*
        * Initializing Location request for fused API
        * Here we will set the intervel at which we want location updates in millis
        * */
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(trackingIntervel);
        //mLocationRequest.setFastestInterval(60000l);
        //mLocationRequest.setSmallestDisplacement(100f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Starting Fused Api
        startLocationUpdates();

        context = this;

        //Updating shared preference to indicate that the service is running in future
        Utils.saveDataString("ServiceStatus", "Running", this);

        //Leaving toast Message as service has been started
        Toast.makeText(this, this.getResources().getString(R.string.service_start_toast_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(final Location location) {
        mCurrentLocation = location;

        if (!spotCreation) {
           /*
            * Create Spot on the server if the user is first time setting his availability to visible
            * */

            Call<SpotResponse> oathAPICall = AppApiController.getApiInstance().createSpot(name, "dynamic", "" + mCurrentLocation.getLatitude(), "" + mCurrentLocation.getLongitude(), accessToken);
            oathAPICall.enqueue(new Callback<SpotResponse>() {
                @Override
                public void onResponse(Call<SpotResponse> call, Response<SpotResponse> response) {
                    if (response.isSuccessful()) {
                        SpotResponse responseBody = response.body();
                        /*
                        * Keep the id of the newly created dynamic spot
                        * */
                        spotId = responseBody.getId();
                        Toast.makeText(context, "Spot Created " + "\n" +
                                "Latitiute = " + location.getLatitude() + "\n" +
                                "Longitude = " + location.getLongitude() + "\n" +
                                "Updated at Server = Updated", Toast.LENGTH_SHORT).show();

                    } else {
                        // Toast.makeText(context, "Message Error spot creation.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SpotResponse> call, Throwable t) {

                }
            });

            spotCreation = true;
            mLastLocation = location;
        } else {
            /*
            * Comparing last location with current location and updating spot if the user has travelled 100 meters
            * */

            final float meterDistance = location.distanceTo(mLastLocation);
            if (meterDistance > distanceToUpdate)
            //  if(true)
            {
                /*
                 *  Updating Spot location Each time onLocation is called depending upon the parameters pased to the fused api
                * */
                Call<ResponseBody> oathAPICall = AppApiController.getApiInstance().updateSpot(spotId, "" + mCurrentLocation.getLatitude(), "" + mCurrentLocation.getLongitude(), accessToken);

                oathAPICall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "10 Min Update " + "\n" +
                                    "Latitiute = " + location.getLatitude() + "\n" +
                                    "Longitude = " + location.getLongitude() + "\n" +
                                    "Last Latitiute = " + mLastLocation.getLatitude() + "\n" +
                                    "Last Longitude = " + mLastLocation.getLongitude() + "\n" +
                                    "Distance = " + meterDistance + "Meters" + "\n" +
                                    "Updated at Server = Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            //   Toast.makeText(context, "Message Error.sopt update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //   Toast.makeText(context, "Message" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }else
            {
                Toast.makeText(context, "10 Min Update " + "\n" +
                        "Latitiute = " + location.getLatitude() + "\n" +
                        "Longitude = " + location.getLongitude() + "\n" +
                        "Last Latitiute = " + mLastLocation.getLatitude() + "\n" +
                        "Last Longitude = " + mLastLocation.getLongitude() + "\n" +
                        "Distance = " + meterDistance + "Meters" + "\n" +
                        "Updated at Server = Not Updating", Toast.LENGTH_SHORT).show();
            }
            mLastLocation = location;
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        //Updating shared preference to indicate that the service is running in future
        Utils.saveDataString("ServiceStatus", "NotRunning", this);
        stopSelf();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }
}
