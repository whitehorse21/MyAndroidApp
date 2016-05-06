package txlabz.com.geoconfess.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;


public class SupportMap extends SupportMapFragment {


    public  MapViewCreatedListener itsMapViewCreatedListener;

    public abstract static class MapViewCreatedListener {
        public abstract void onMapCreated();
    }
    public SupportMap()
    {


    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // Notify the view has been created
        if( itsMapViewCreatedListener != null ) {
            itsMapViewCreatedListener.onMapCreated();
        }
        return view;
    }





}
