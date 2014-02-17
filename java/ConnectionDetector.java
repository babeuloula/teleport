package fr.babeuloula.teleport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
	private Context context;
	private AlertDialog alertDialog;
	 
    public ConnectionDetector(Context context){
        this.context = context;
    }
 
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null) {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) {
                  for (int i = 0; i < info.length; i++) {
                      if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                          return true;
                      }
                  }
              }
          }
          return false;
    }
    
    public void ErreurConnection() {
    	AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(context.getString(R.string.errorInternet));
        alert.setIcon(R.drawable.ic_launcher);
        alert.setMessage(context.getString(R.string.msgErrorInternet));

        alert.setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int whichButton) {
        		alertDialog.dismiss();
        	}
        });           
        alertDialog = alert.create();
        alertDialog.show();
    }
}
