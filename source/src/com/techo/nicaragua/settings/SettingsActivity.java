package com.techo.nicaragua.settings;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.techo.nicaragua.MainActivity;
import com.techo.nicaragua.R;
import com.techo.nicaragua.utils.HtmlReader;

import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import android.app.AlertDialog;
import android.content.Intent;

public class SettingsActivity extends SherlockFragmentActivity {

	Spanned LicenseInfo;
	Spanned Contributors;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		LicenseInfo = HtmlReader.get(this, R.raw.open_source_licenses);
		Contributors = HtmlReader.get(this, R.raw.contributors);

		initActionBarSherlock();
		actionsList();
	}

	private void actionsList() {
		LinearLayout open_sources = (LinearLayout) findViewById(R.id.linearLayoutOpenSourceLicenses);
		open_sources.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// String LicenseInfo =
				// GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getApplicationContext());

				AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(
						SettingsActivity.this);
				LicenseDialog.setTitle(getString(R.string.settings_open_source));
				LicenseDialog.setMessage(LicenseInfo);
				LicenseDialog.show();
			}

		});
		
		LinearLayout linearLayoutColaborators = (LinearLayout) findViewById(R.id.linearLayoutColaborators);
		linearLayoutColaborators.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// String LicenseInfo =
				// GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getApplicationContext());

				AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(
						SettingsActivity.this);
				LicenseDialog.setTitle(getString(R.string.settings_contributors));
				LicenseDialog.setMessage(Contributors);
				LicenseDialog.show();
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
