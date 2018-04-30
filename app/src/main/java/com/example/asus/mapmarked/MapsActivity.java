package com.example.asus.mapmarked;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Double Lng,Lat;
    private GoogleMap mMap;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Location/longitude");
        mDatabaseReference1 = FirebaseDatabase.getInstance().getReference().child("Location/latitude");


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMap.clear();

                final Double Lng = dataSnapshot.getValue(Double.class);
                mDatabaseReference1.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Double Lat = dataSnapshot.getValue(Double.class);
                        LatLng newaddress = new LatLng(Lat,Lng);

                        mMap.addMarker(new MarkerOptions().position(newaddress).title(newaddress.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newaddress,19));
                        mMap.setMinZoomPreference(6.0f);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error",
                        Toast.LENGTH_LONG).show();

            }

        });
        mDatabaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMap.clear();

                final Double Lng = dataSnapshot.getValue(Double.class);
                mDatabaseReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Double Lat = dataSnapshot.getValue(Double.class);
                        LatLng newaddress = new LatLng(Lat,Lng);

                        mMap.addMarker(new MarkerOptions().position(newaddress).title(newaddress.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newaddress,19));
                        mMap.setMinZoomPreference(6.0f);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error",
                        Toast.LENGTH_LONG).show();

            }

        });
    }
}
