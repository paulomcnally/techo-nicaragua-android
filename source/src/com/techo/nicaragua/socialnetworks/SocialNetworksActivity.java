package com.techo.nicaragua.socialnetworks;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.techo.nicaragua.MainActivity;
import com.techo.nicaragua.R;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.techo.nicaragua.socialnetworks.SocialNetworksFragment;

public class SocialNetworksActivity extends SherlockFragmentActivity {

	private static final String[] TAB_TITLES = new String[] { "FACEBOOK",
			"TWITTER", "YOUTUBE" };

	SocialNetworksFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initActionBarSherlock();

		setContentView(R.layout.activity_social_networks);

		mAdapter = new SocialNetworksFragmentAdapter(
				getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pagerSocialNetworks);
		mPager.setAdapter(mAdapter);

		mIndicator = (TabPageIndicator) findViewById(R.id.indicatorSocialNetworks);
		mIndicator.setViewPager(mPager);
	}

	private void initActionBarSherlock() {
		// Show back button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// Title hide
		getSupportActionBar().setTitle(
				getString(R.string.title_social_networks));
	}

	class SocialNetworksFragmentAdapter extends FragmentPagerAdapter {

		private int mCount = TAB_TITLES.length;

		public SocialNetworksFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			return SocialNetworksFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			return mCount;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TAB_TITLES[position];
		}
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
