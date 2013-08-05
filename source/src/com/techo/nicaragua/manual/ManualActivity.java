package com.techo.nicaragua.manual;

import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.techo.nicaragua.MainActivity;
import com.techo.nicaragua.R;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class ManualActivity extends SherlockFragmentActivity implements
		ActionBar.OnNavigationListener {

	private String[] mSections;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual);

		mSections = getResources().getStringArray(R.array.sections);

		initActionBarSherlock();

		addFragment(0);

	}

	private void initActionBarSherlock() {
		// Set Navigator data
		Context context = getSupportActionBar().getThemedContext();
		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(
				context, R.array.sections, R.layout.techo_spinner_item);
		list.setDropDownViewResource(R.layout.techo_spinner_dropdown_item);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list,
				(OnNavigationListener) this);

		// Show back button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// Title hide
		getSupportActionBar().setDisplayShowTitleEnabled(false);
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

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		setSupportProgressBarIndeterminateVisibility(true);

		addFragment(itemPosition);

		return false;
	}

	void addFragment(int position) {

		Fragment myFragment = getSupportFragmentManager().findFragmentById(
				R.id.manualPage);

		// Instantiate a new fragment.
		com.actionbarsherlock.app.SherlockFragment f = ManualFragment
				.newInstance(position);
		// Add the fragment to the activity, pushing this transaction
		// on to the back stack.

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (myFragment != null) {
			ft.remove(myFragment);
		}
		ft.replace(R.id.manualPage, f);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	goToHome();
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
