package fr.babeuloula.teleport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main extends Activity {
	
	private EditText selectVille;
	private EditText latitude;
	private EditText longitude;
	
	private Button btn_teleport1;
	private Button btn_teleport2;
	
	private ConnectionDetector internet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		internet = new ConnectionDetector(this);
		
		selectVille = (EditText) findViewById(R.id.selectVille);
		latitude = (EditText) findViewById(R.id.latitude);
		longitude = (EditText) findViewById(R.id.longitude);
		
		btn_teleport1 = (Button) findViewById(R.id.btn_teleport1);
		btn_teleport2 = (Button) findViewById(R.id.btn_teleport2);
		
		btn_teleport1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(selectVille.getText().toString().equals("")) {
					Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.erreurVille), Toast.LENGTH_SHORT).show();
				} else {
					if(internet.isConnectingToInternet()) {
						LinearLayout mainLayout;
						mainLayout = (LinearLayout)findViewById(R.id.mainLayout);
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
						
						alertTeleport("null", "null", selectVille.getText().toString());
					} else {
						internet.ErreurConnection();
					}
				}
			}
		});
		
		btn_teleport2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String erreur = "";
				boolean valid = true;
				
				if(latitude.getText().toString().equals("")) {
					erreur += getBaseContext().getString(R.string.erreurLatitude);
					valid = false;
				}
				if(longitude.getText().toString().equals("")) {
					if(erreur.equals("")) {
						erreur += getBaseContext().getString(R.string.erreurLongitude);
					} else {
						erreur += "\n"+getBaseContext().getString(R.string.erreurLongitude);
					}
					valid = false;
				}
				if(valid) {
					if(internet.isConnectingToInternet()) {
						LinearLayout mainLayout;
						mainLayout = (LinearLayout)findViewById(R.id.mainLayout);
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
						
						alertTeleport(latitude.getText().toString(), longitude.getText().toString(), "null");
					} else {
						internet.ErreurConnection();
					}
				} else {
					Toast.makeText(getBaseContext(), erreur, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void alertTeleport(final String lat, final String lon, final String ville) {
		new AlertDialog.Builder(this)
	    	.setTitle(getBaseContext().getString(R.string.alertTitle))
	    	.setMessage(getBaseContext().getString(R.string.alertText))
	    	.setIcon(R.drawable.attention)
	    	.setPositiveButton(getBaseContext().getString(R.string.btn_teleport), new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	lunchTeleport(lat, lon, ville);
		        }
		    })
		    .setNegativeButton(getBaseContext().getString(R.string.btn_annuler), new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {}
		    })
		    .show();
	}

	private void lunchTeleport(final String latitude, final String longitude, final String ville) {
		final ProgressDialog progressDialog;
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(R.drawable.ic_launcher);
		progressDialog.setTitle(R.string.progressTeleport);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		
		final Double ramdom = Math.ceil(Math.random()*(10 - 0));
		
		final Thread t = new Thread(new Runnable(){
			public void run() {
				for (int i = 1; i <= ramdom; i++) {
					try {
	    				Thread.sleep(1000);
	    				progressDialog.incrementProgressBy((int)(100/ramdom));
					}
					catch (InterruptedException e) {
						Log.e("erreur Thread", e.getMessage());
					}
				}
				
				//Retire la boite de dialog
				progressDialog.dismiss();
				
				Intent i = new Intent(getBaseContext(), Maps.class);
				i.putExtra("ville", ville);
				i.putExtra("latitude", latitude);
				i.putExtra("longitude", longitude);
				startActivity(i);
			}
		});
		
		progressDialog.show();
		t.start();
	}
	
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
