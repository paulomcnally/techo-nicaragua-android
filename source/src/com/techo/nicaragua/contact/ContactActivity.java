package com.techo.nicaragua.contact;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.techo.nicaragua.MainActivity;
import com.techo.nicaragua.R;
import com.techo.nicaragua.R.layout;
import com.techo.nicaragua.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class ContactActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		
		initActionBarSherlock();
		actionsList();
	}
	
	private void actionsList() {
		LinearLayout linearLayoutNiPhone = (LinearLayout) findViewById(R.id.linearLayoutNiPhone);
		linearLayoutNiPhone.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + getString(R.string.contact_phone_ni_text)));
                startActivity(callIntent);
			}
		});
		
		LinearLayout linearLayoutNiEmail = (LinearLayout) findViewById(R.id.linearLayoutNiEmail);
		linearLayoutNiEmail.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/plain");
                emailIntent.setData(Uri.parse("mailto:" + getString(R.string.contact_email_ni_text)));
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(emailIntent);
			}
		});
		
		LinearLayout linearLayoutNiAddress = (LinearLayout) findViewById(R.id.linearLayoutNiAddress);
		linearLayoutNiAddress.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent locateIntent = new Intent(android.content.Intent.ACTION_VIEW, 
						Uri.parse(getString(R.string.contact_address_ni_text)));
					startActivity(locateIntent);
			}
		});
		
		LinearLayout linearLayoutNiFacebook = (LinearLayout) findViewById(R.id.linearLayoutNiFacebook);
		linearLayoutNiFacebook.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {				
				Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.social_network_url_facebook)));
				startActivity(intent);
			}
		});
		
		LinearLayout linearLayoutNiTwitter = (LinearLayout) findViewById(R.id.linearLayoutNiTwitter);
		linearLayoutNiTwitter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {				
				Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.social_network_url_twitter)));
				startActivity(intent);
			}
		});
		
		LinearLayout linearLayoutNiYoutube = (LinearLayout) findViewById(R.id.linearLayoutNiYoutube);
		linearLayoutNiYoutube.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {				
				Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.social_network_url_youtube)));
				startActivity(intent);
			}
		});
	}

	private void initActionBarSherlock() {
		// Show back button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		int itemId = item.getItemId();
		switch (itemId) {
		case android.R.id.home:
			goToHome();
			break;

		}

		return true;
	}

	private void goToHome() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}

}
