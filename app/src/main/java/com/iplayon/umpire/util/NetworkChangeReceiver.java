package com.iplayon.umpire.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by shalinibr on 10/26/17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Boolean status = NetworkUtil.getConnectivityStatusString(context);
    }
}