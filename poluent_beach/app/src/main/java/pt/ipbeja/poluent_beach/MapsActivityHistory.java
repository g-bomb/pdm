package pt.ipbeja.poluent_beach;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 *
 * Show Location of Report
 *
 * @author Tiago Azevedo 17427
 * @author Bruno Guerra 16247
 *
 * IPBEJA - PDM 29/01/2020
 */
public class MapsActivityHistory extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String gpsCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_history);

        //variable the retrieve the gps coordinates passed on by the previous activity
        this.gpsCoordinates = getIntent().getExtras().getString("gps");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Get coordinates to a String
        String coordinates = String.valueOf(this.gpsCoordinates);

        //Separate Latitude from Longitude
        String[] latlong =  coordinates.split("-");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble("-" + latlong[1]);

        // Add a marker in Coordinates and move the camera
        LatLng marker = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(marker).title(coordinates));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15));
    }
}