package br.com.summitpcm.app.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkManager.getConnectivityStatusString(context);

        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
    private boolean isNetworkAvailable(Context context) {
        if(NetworkManager.getConnectivityStatus(context) != 0)
            return true;

        return false;
    }
}