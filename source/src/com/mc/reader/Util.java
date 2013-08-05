package com.mc.reader;


import com.mc.reader.database.Database;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Util {
	private static String log = "Config";

	private Context myContext;

	private Database myDatabase = null;

	public final static int TITLE = 1;
	public final static int CATEGORY_AND_DATE = 2;
	public final static int CONTENT = 3;
	public final static int AUTHOR = 4;
	public final static int URL = 5;

	public final static String IMAGE_CACHE_DIR = "images";
	public final static String EXTRA_IMAGE = "extra_image";

	public Util(Context context, Database database) {
		super();
		myContext = context;
		myDatabase = database;
	}

	/*
	 * get
	 * 
	 * @description search in tblConfig row based in config_key and return
	 * conffig_value
	 * 
	 * @return String
	 */
	public String get(String config_key) {
		Log.i(log, "Get: " + config_key);
		String[] where = new String[] { config_key };
		String result = myDatabase.getConfig(where);
		// db.disconnect();
		return result;
	}

	/*
	 * set
	 * 
	 * @description Insert o update config row
	 * 
	 * @return none
	 */
	public void set(String config_key, String config_value) {
		myDatabase.setConfig(config_key, config_value);
		// db.disconnect();
	}

	/*
	 * getOperatorName
	 * 
	 * @description Identifique operator name
	 * 
	 * @return String
	 */
	public String getOperatorName() {
		TelephonyManager telephonyManager = ((TelephonyManager) myContext
				.getSystemService(Context.TELEPHONY_SERVICE));
		return telephonyManager.getNetworkOperatorName();
	}

	/*
	 * getDeviceId
	 * 
	 * @description Get unique device id
	 * 
	 * @return String
	 */
	public String getDeviceId() {
		String response = "";
		TelephonyManager telephonyManager = ((TelephonyManager) myContext
				.getSystemService(Context.TELEPHONY_SERVICE));
		if (telephonyManager.getDeviceId() == null) {
			response = "empty";
		} else {
			response = telephonyManager.getDeviceId();
		}
		return response;
	}

	public String getDeviceVersion() {
		PackageInfo pInfo = null;
		String version = "";
		try {
			pInfo = myContext.getPackageManager().getPackageInfo(
					myContext.getPackageName(), 0);
			version = pInfo.versionName;
		} catch (NameNotFoundException e) {
			version = "0";
		}
		return version;
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
