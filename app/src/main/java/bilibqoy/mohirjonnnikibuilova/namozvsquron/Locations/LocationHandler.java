package bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;


public class LocationHandler {

    public boolean NEW_LOCATION_FLAG;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallbacks;

    public LocationHandler(final Activity activity, Context context, final String tempLocationFile, int interval){

        locationRequest = new LocationRequest();
        locationRequest.setInterval(interval);
        locationRequest.setFastestInterval(interval);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        NEW_LOCATION_FLAG = false;


        mFusedLocationClient = new FusedLocationProviderClient(context);

        locationCallbacks = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if(locationResult == null ){
                    return;
                }
                android.location.Location location = locationResult.getLastLocation();
                NEW_LOCATION_FLAG = true;

                _LocationSET.assignLocation(activity,location,tempLocationFile);
                finish();
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        };

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallbacks,activity.getMainLooper());
    }
    public void finish(){
        mFusedLocationClient.removeLocationUpdates(locationCallbacks);
        mFusedLocationClient.flushLocations();
    }
}

