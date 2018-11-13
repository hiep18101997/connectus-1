package com.connect.chat.connectus.ui.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.presenter.MapPresenter;
import com.connect.chat.connectus.presenter.impl.MapPresenterImpl;
import com.connect.chat.connectus.utils.PermissionSettingUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends BaseFragment<MapPresenter> implements OnMapReadyCallback,
        GoogleMap.OnMyLocationChangeListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.InfoWindowAdapter, View.OnClickListener, MapView {
    private static final String TAG = MapFragment.class.getSimpleName();
    private static final int MY_PERMISSION = 0;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_PERMISSION = 2;
    public static GoogleMap mGoogleMap;
    public static Geocoder geocoder;
    public static Marker marker;
    private boolean isFirstChangeLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private LocationCallback mLocationCallback;
    Location mCurrentLocation;

    private FusedLocationProviderClient mFusedLocationClient;

    private String provider;
    private boolean isNetworkLocation, isGPSLocation;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_map;
    }

    @Override
    public void initializeComponents(View view) {

        LocationManager mListener = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (mListener != null) {
            isGPSLocation = mListener.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkLocation = mListener.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.e("gps, network", String.valueOf(isGPSLocation + "," + isNetworkLocation));
        }
        if (isGPSLocation) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (isNetworkLocation) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            PermissionSettingUtils.LocationSettingDialog.newInstance().show(getChildFragmentManager(), "Setting");
        }
        SupportMapFragment mapFragment
                = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);


        // Sét đặt sự kiện thời điểm GoogleMap đã sẵn sàng.
        mapFragment.getMapAsync(this);
    }


    @Override
    public MapPresenter createPresenter() {
        return new MapPresenterImpl(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        initPermis();
        mGoogleMap = googleMap;
        initGoogleMap(mGoogleMap);
        mGoogleMap.setTrafficEnabled(true);

    }

    public void initGoogleMap(GoogleMap mGoogleMap) {

        MapFragment.mGoogleMap = mGoogleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setTrafficEnabled(true);
        Log.d("LLLLLLLL", mGoogleMap.isTrafficEnabled() + "");

        //set up UI
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        initMyLocation(mGoogleMap);

//        initPermis();
//
//        getActivity().finish();
//        getActivity().startActivity(getActivity().getIntent());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            //permion
            int checkPermisonLocation =
                    ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION);

            if (checkPermisonLocation == PackageManager.PERMISSION_GRANTED) {
                initMyLocation(mGoogleMap);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int per : grantResults) {
            if (per == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }

        //nguoi dung dong y het
        initMyLocation(mGoogleMap);

    }

    @SuppressLint("MissingPermission")
    private void initMyLocation(GoogleMap mMap) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        SettingsClient mSettingsClient = LocationServices.getSettingsClient(getActivity());

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult result) {
                super.onLocationResult(result);
                //mCurrentLocation = locationResult.getLastLocation();
                mCurrentLocation = result.getLocations().get(0);

                MarkerOptions options = new MarkerOptions();
                options.position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
                BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                options.icon(icon);
                Marker marker = mMap.addMarker(options);

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));

            }

            @Override
            public void onLocationAvailability(LocationAvailability availability) {
                //boolean isLocation = availability.isLocationAvailable();
            }
        };
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setNumUpdates(1);
        if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        } else {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        LocationSettingsRequest mLocationSettingsRequest = builder.build();

        Task<LocationSettingsResponse> locationResponse = mSettingsClient.checkLocationSettings(mLocationSettingsRequest);
        locationResponse.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.e("Response", "Successful acquisition of location information!!");
                //
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            }
        });
        locationResponse.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("onFailure", "faile");
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        String errorMessage = "faild";
                        Log.e("onFailure", errorMessage);
                }
            }
        });
//        //custom windown adpater: content marker
////        googleMap.setOnInfoWindowClickListener(this);
////        googleMap.setInfoWindowAdapter(this);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //Permission Check
            PermissionSettingUtils.LocationSettingDialog.newInstance().show(getChildFragmentManager(), "Setting");
        } else {
            mGoogleMap.setMyLocationEnabled(true);
        }
        checkOpenLocation();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        Log.d("Adres", "location lat: " + location.getLatitude());
        Log.d("Adres", "location long: " + location.getLongitude());
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


        if (!isFirstChangeLocation) {
            isFirstChangeLocation = true;
            //co chuc nang di chuyen den vi tri position
            CameraPosition cameraPosition =
                    new CameraPosition(latLng, 15, 0, 0);
            //dua camera position vao google map
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            MarkerOptions options = new MarkerOptions();
            options.
                    icon(BitmapDescriptorFactory.
                            defaultMarker(BitmapDescriptorFactory.HUE_RED));
            options.position(latLng);
            options.title("My location");
            options.snippet(getLocation(latLng));
            marker = mGoogleMap.addMarker(options);
//            marker.showInfoWindow();

        } else {
            marker.setTitle("My location");
            marker.setSnippet(getLocation(latLng));
            marker.setPosition(latLng);

        }

    }

    private String getLocation(LatLng latLng) {
        try {
            List<Address> addresses =
                    geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses == null || addresses.size() == 0) {
                return null;
            }
            String result = "";
            int maxLine = addresses.get(0).getMaxAddressLineIndex();
            result = addresses.get(0).getAddressLine(0);
            for (int i = 1; i < maxLine; i++) {
                result += ", " + addresses.get(0).getAddressLine(i);
            }
            result += ", " + addresses.get(0).getCountryName();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //check open loaction

    public boolean checkOpenLocation() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Open location");
            dialog.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(myIntent, 102);
                    //get gps
                }
            });
            dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
            return false;
        } else {
            return true;
        }
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }


    @Override
    public void onClick(View view) {

    }
}
