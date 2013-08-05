package com.mc.reader.request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class Api {
	private static String log = "Api";
	private static String api_url = "http://cms-mobile.aws.af.cm/api/";

	public Api() {
		super();
	}

	private static String getUrl(String query) {
		return api_url + query;

	}

	public static JSONArray getArticles(String type) {
		Log.i(log, "getArticles");
		HttpResponse response = null;
		String response_string = "[]";
		JSONArray result = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(getUrl("articles/" + type)));
			response = client.execute(request);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				response_string = EntityUtils.toString(responseEntity);
			}
			try {
				result = new JSONArray(response_string);
			} catch (JSONException e) {
				Log.e("URISyntaxException", e.getMessage());
			}
		} catch (URISyntaxException e) {
			Log.e("URISyntaxException", e.getMessage());
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.e("ClientProtocolException", e.getMessage());
		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}

		return result;
	}
}
