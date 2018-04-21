package com.connect.chat.connectus.ui.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends BaseFragment<MapPresenter> implements OnMapReadyCallback,
        GoogleMap.OnMyLocationChangeListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.InfoWindowAdapter, View.OnClickListener, MapView {

    private static final int MY_PERMISSION = 0;
    private static final String TAG = MapFragment.class.getSimpleName();
    public static GoogleMap mGoogleMap;
    public static Geocoder geocoder;
    private boolean isFirstChangeLocation;
    public static Marker marker;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_map;
    }

    @Override
    public void initializeComponents(View view) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            }, MY_PERMISSION);

            new AlertDialog.Builder(getContext())
                    .setTitle("Confirm Permission for Application")
                    .setMessage("Please Select Permissions and Enable Location")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, 101);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                    getActivity().startActivity(getActivity().getIntent());
                    getActivity().overridePendingTransition(0, 0);
                    return;

                }
            }).create().show();

            return;

        } else {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            geocoder = new Geocoder(getContext(), Locale.getDefault());
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public MapPresenter createPresenter() {
        return new MapPresenterImpl(this);
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
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
        initMyLocation();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            //permion
            int checkPermisonLocation =
                    ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION);

            if (checkPermisonLocation == PackageManager.PERMISSION_GRANTED) {
                initMyLocation();
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
        initMyLocation();

    }

    private void initMyLocation() {
        mGoogleMap.setOnMyLocationChangeListener(this);
//        //custom windown adpater: content marker
////        googleMap.setOnInfoWindowClickListener(this);
////        googleMap.setInfoWindowAdapter(this);
        mGoogleMap.setMyLocationEnabled(true);
        checkOpenLocation();

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
