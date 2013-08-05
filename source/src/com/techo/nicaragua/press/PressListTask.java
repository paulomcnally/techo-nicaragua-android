package com.techo.nicaragua.press;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.mc.reader.Util;
import com.mc.reader.database.Database;
import com.mc.reader.request.Api;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

public class PressListTask extends AsyncTask<String, String, Cursor> {
	Context myContext;
	Database database;
	Util myUtil;
	SherlockFragmentActivity mySherlock;

	public void init(Context context,
			SherlockFragmentActivity sherlockFragmentActivity, Util util) {
		myContext = context;
		database = new Database(myContext);
		myUtil = util;
		mySherlock = sherlockFragmentActivity;
	}

	protected void onPreExecute() {
		((SherlockFragmentActivity) mySherlock)
				.setSupportProgressBarIndeterminateVisibility(true);
	}

	@Override
	protected Cursor doInBackground(String... params) {
		if (myUtil.isConnected()) {
			JSONArray json_array = Api.getArticles(params[0]);

			if (json_array.length() > 0) {
				for (int i = 0; i < json_array.length(); i++) {
					try {
						JSONObject article = json_array.getJSONObject(i);
						database.setArticle(article);
					} catch (JSONException e) {
						Log.e("JSONException", e.getMessage());
					}
				}
			}
		}

		return database.getAllArticle(params[0]);
	}

	protected void onPostExecute(Cursor data) {
		new PressFragment().loadData(data);
		database.sqlite.close();
		((SherlockFragmentActivity) mySherlock)
				.setSupportProgressBarIndeterminateVisibility(false);
	}

}
