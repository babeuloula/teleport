package fr.babeuloula.teleport.v2;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Infos extends Activity {
	
	TextView siteBaB;
	TextView siteRed;
	TextView mailBaB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infos);
		
		siteBaB = (TextView) findViewById(R.id.siteBaB);
		siteBaB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = getBaseContext().getString(R.string.siteBaB);
				Intent iSiteBaB = new Intent(Intent.ACTION_VIEW);
				iSiteBaB.setData(Uri.parse(url));
				startActivity(iSiteBaB);
			}
		});
		
		siteRed = (TextView) findViewById(R.id.siteRed);
		siteRed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = getBaseContext().getString(R.string.siteRed);
				Intent iSiteRed = new Intent(Intent.ACTION_VIEW);
				iSiteRed.setData(Uri.parse(url));
				startActivity(iSiteRed);
			}
		});
		
		mailBaB = (TextView) findViewById(R.id.mailBaB);
		mailBaB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iMailBaB = new Intent(Intent.ACTION_SEND);
				iMailBaB.setType("message/rfc822");
				iMailBaB.putExtra(Intent.EXTRA_EMAIL, new String[]{getBaseContext().getString(R.string.mailBaB)});  
				iMailBaB.putExtra(Intent.EXTRA_SUBJECT, getBaseContext().getString(R.string.subject));  
				startActivity(Intent.createChooser(iMailBaB, getBaseContext().getString(R.string.selectEmailApp)));
			}
		});
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
