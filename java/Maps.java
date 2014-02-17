package fr.babeuloula.teleport;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends FragmentActivity implements LocationListener {
	
	private GoogleMap gMap;
	private String ville = "";
	private String latitude = "";
	private String longitude = "";
	private LatLng coordonnees;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		ville = this.getIntent().getStringExtra("ville");
		latitude = this.getIntent().getStringExtra("latitude");
		longitude = this.getIntent().getStringExtra("longitude");
		
		gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		gMap.setMyLocationEnabled(false);
		gMap.getUiSettings().setCompassEnabled(true);
		
		
		if(getIntent().getStringExtra("ville").equals("null")) {
			coordonnees = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
		} else {
			try {
				Geocoder geocoder = new Geocoder(getBaseContext());;
	            List<Address> addresses = geocoder.getFromLocationName(ville, 1);
	            coordonnees = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
			} catch (IOException e) {
				Log.e("Erreur getAdresse", e.getMessage());
			}
		}
		
		
		Marker marker = gMap.addMarker(new MarkerOptions()
							        .position(coordonnees)
							        .title(getBaseContext().getString(R.string.youAreHere))
							        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
		marker.showInfoWindow();
		
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordonnees, 15));
		
		Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.teleport_success), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLocationChanged(Location location) {}

	@Override
	public void onProviderDisabled(String provider) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.teleport) {
			Intent iRechercher = new Intent(this, Main.class);
			finish();
			startActivity(iRechercher);
		} else if (item.getItemId() == R.id.infos) {
			Intent iFavoris = new Intent(this, Infos.class);
			finish();
			startActivity(iFavoris);
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
