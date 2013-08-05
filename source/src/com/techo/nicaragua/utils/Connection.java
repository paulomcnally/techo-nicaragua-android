package com.techo.nicaragua.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connection {
	Context myContext;
	public Connection(Context context) {
		super();
		myContext = context;

	}
	
	public Boolean isConnected() {
		NetworkInfo networkInfo = ((ConnectivityManager) myContext
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();

		if (networkInfo == null || !networkInfo.isConnected()) {
			return false;
		}
		return true;
	}

}
