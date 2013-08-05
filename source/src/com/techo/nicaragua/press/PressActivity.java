package com.techo.nicaragua.press;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.techo.nicaragua.MainActivity;
import com.techo.nicaragua.R;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;

public class PressActivity extends SherlockFragmentActivity implements
ActionBar.OnNavigationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_press);
		
		initActionBarSherlock();
		
	}
	
	private void initActionBarSherlock() {
		// Set Navigator data
		Context context = getSupportActionBar().getThemedContext();
		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(
				context, R.array.press, R.layout.techo_spinner_item);
		list.setDropDownViewResource(R.layout.techo_spinner_dropdown_item);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list,
				(OnNavigationListener) this);

		// Show back button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// Title hide
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		showProgress(false);
	}
	
	public void showProgress(Boolean status){
		setSupportProgressBarIndeterminateVisibility(status);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		

		addFragment(itemPosition);

		return false;
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
	
	void addFragment(int position) {

		Fragment myFragment = getSupportFragmentManager().findFragmentById(
				R.id.pressPage);

		// Instantiate a new fragment.
		com.actionbarsherlock.app.SherlockFragment f = PressFragment
				.newInstance(position);
		// Add the fragment to the activity, pushing this transaction
		// on to the back stack.

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (myFragment != null) {
			ft.remove(myFragment);
		}
		ft.replace(R.id.pressPage, f);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	goToHome();
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
