package pt.ipbeja.poluent_beach;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

/**
 *
 * Choose Place Where the Report Is
 *
 * @author Tiago Azevedo 17427
 * @author Bruno Guerra 16247
 *
 * IPBEJA - PDM 29/01/2020
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION = 101;
    private Button confirmButton;
    private Geocoder geocoder;
    private String lat;
    private String log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        confirmButton = findViewById(R.id.confirmMap);

        //Pass Information with PutExtra
        confirmButton.setOnClickListener(v -> {
            Intent prevIntent = getIntent();
            prevIntent.putExtra("lat", this.lat);
            prevIntent.putExtra("log", this.log);
            setResult(Activity.RESULT_OK, prevIntent);
            finish();
        });

        //Get Current Location From User
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
    }

    /**
     * Method that retrieves the current location of the user. Location permissions are requested before accessing this location.
     * After retrieving the current location the map will be centered on that location.
     */
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                supportMapFragment.getMapAsync(MapsActivity.this);
                LatLng place = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
            }
        });
    }

    /**
     * Save to Strings GPS Latitude and Longitude
     * @param latLng coordinates
     */
    public  void coordinates(LatLng latLng)
    {
        this.lat = String.valueOf(latLng.latitude);
        this.log = String.valueOf(latLng.longitude);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this, Locale.getDefault());

        //Click listener on the map that allows users to mark the position of their findings
        //The address of the location marked is acquired using the latitude and longitude
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                coordinates(latLng);

                mMap.addMarker(new MarkerOptions().position(latLng));
                confirmButton.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Method that retrieves the current location of the user after permissions being granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (LOCATION_PERMISSION) {
            case LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }
}
