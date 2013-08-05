package com.techo.nicaragua;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.Window;
import com.mc.reader.Util;
import com.mc.reader.database.Database;
import com.mc.reader.database.OdbArticle;
import com.mc.reader.images.ImageCache;
import com.mc.reader.images.ImageFetcher;
import com.mc.reader.images.ImageCache.ImageCacheParams;
import com.mc.reader.request.Api;
import com.techo.nicaragua.contact.ContactActivity;
import com.techo.nicaragua.manual.ManualActivity;
import com.techo.nicaragua.press.PressActivity;
import com.techo.nicaragua.settings.SettingsActivity;
import com.techo.nicaragua.socialnetworks.SocialNetworksActivity;
import com.techo.nicaragua.techo.TechoActivity;
import com.techo.nicaragua.utils.Connection;

public class MainActivity extends SherlockFragmentActivity {
	public static final int HOME = 0;
	public static final int MANUAL = 1;
	public static final int TECHO = 2;
	public static final int PRESS = 3;
	public static final int CONTACT = 4;
	public static final int SOCIAL_NETWORKS = 5;
	public static final int SETTINGS = 6;

	private static ImageFetcher mImageFetcher;
	private static Context myContext;
	static Database database = null;
	static Util util = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myContext = this;
		database = new Database(this);

		initImages();

		MainTaskList presslisttask = new MainTaskList();
		MainTaskList.init(this, util);
		presslisttask.execute("inform-yourself");

	}

	public static void loadData(Cursor data) {
		if (data.getCount() > 0) {

			LayoutInflater inflater = (LayoutInflater) myContext
					.getApplicationContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);

			LinearLayout container = (LinearLayout) ((SherlockFragmentActivity) myContext)
					.findViewById(R.id.linearLayoutListHome);

			while (data.moveToNext()) {
				final OdbArticle article = new OdbArticle(
						database.getArticle(data.getString(data
								.getColumnIndex("article_id"))));

				View myView = inflater.inflate(R.layout.press_articles, null);

				Typeface face = Typeface.createFromAsset(myContext.getAssets(),
						"fonts/arial.ttf");

				TextView title = (TextView) myView
						.findViewById(R.id.textViewArticleTitle);
				title.setText(article.getTitle());
				title.setTypeface(face);

				ImageView picture = (ImageView) myView
						.findViewById(R.id.textViewArticleImage);
				mImageFetcher.loadImage(article.getPicture(), picture);
				picture.setScaleType(ScaleType.CENTER);
				
				myView.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent();
						intent.setClass(myContext, ArticleActivity.class);
						intent.putExtra("title", article.getTitle());
						intent.putExtra("picture", article.getPicture());
						intent.putExtra("content", article.getContent());
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
								| Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_NEW_TASK);
						myContext.startActivity(intent);
					}
					
				});

				container.addView(myView);
			}
		}
		data.close();
	}

	private void initImages() {
		// Fetch screen height and width, to use as our max size when loading
		// images as this
		// activity runs full screen
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		final int height = displayMetrics.heightPixels;
		final int width = displayMetrics.widthPixels;

		// For this sample we'll use half of the longest width to resize our
		// images. As the
		// image scaling ensures the image is larger than this, we should be
		// left with a
		// resolution that is appropriate for both portrait and landscape. For
		// best image quality
		// we shouldn't divide by 2, but this will use more memory and require a
		// larger memory
		// cache.
		final int longest = (height > width ? height : width) / 2;

		ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(this,
				Util.IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory

		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(this, longest);
		mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageFadeIn(false);
	}

	private void goTo(int page) {
		Intent intent = new Intent();

		switch (page) {
		case HOME:
			intent.setClass(getApplicationContext(), MainActivity.class);
			break;
		case MANUAL:
			intent.setClass(getApplicationContext(), ManualActivity.class);
			break;
		case TECHO:
			intent.setClass(getApplicationContext(), TechoActivity.class);
			break;
		case PRESS:
			intent.setClass(getApplicationContext(), PressActivity.class);
			break;
		case CONTACT:
			intent.setClass(getApplicationContext(), ContactActivity.class);
			break;
		case SOCIAL_NETWORKS:
			intent.setClass(getApplicationContext(),
					SocialNetworksActivity.class);
			break;
		case SETTINGS:
			intent.setClass(getApplicationContext(),
					SettingsActivity.class);
			break;
		default:
			break;

		}
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.app.SherlockActivity#onCreateOptionsMenu(android
	 * .view.Menu)
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu = menu.addSubMenu("Action Item");
		subMenu.add(0, TECHO, 0, getString(R.string.title_techo));
		subMenu.add(0, PRESS, 1, getString(R.string.title_press_room));
		subMenu.add(0, MANUAL, 4, getString(R.string.title_manual));
		subMenu.add(0, CONTACT, 2, getString(R.string.title_contact));
		subMenu.add(0, SOCIAL_NETWORKS, 3,
				getString(R.string.title_social_networks));
		subMenu.add(0, SETTINGS, 5, getString(R.string.title_settings));

		MenuItem subMenuItem = subMenu.getItem();
		subMenuItem.setIcon(R.drawable.ic_action_overflow);
		subMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * *(non-Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.app.SherlockActivity#onOptionsItemSelected(android
	 * .view.MenuItem)
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			return true;
			// Manual
		case MANUAL:
			goTo(MANUAL);
			return true;
		case TECHO:
			goTo(TECHO);
			return true;
		case PRESS:
			goTo(PRESS);
			return true;
		case CONTACT:
			goTo(CONTACT);
			return true;
		case SOCIAL_NETWORKS:
			goTo(SOCIAL_NETWORKS);
			return true;
		case SETTINGS:
			goTo(SETTINGS);
			return true;
		}
		return false;
	}

	public static class MainTaskList extends AsyncTask<String, String, Cursor> {
		static Database database;
		static Util myUtil;
		static SherlockFragmentActivity mySherlock;

		public static void init(SherlockFragmentActivity sherlock, Util util) {
			database = new Database(myContext);
			mySherlock = sherlock;
		}

		protected void onPreExecute() {
			((SherlockFragmentActivity) mySherlock)
					.setSupportProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Cursor doInBackground(String... params) {
			if (new Connection(myContext).isConnected()) {
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
			loadData(data);
			database.sqlite.close();
			((SherlockFragmentActivity) mySherlock)
					.setSupportProgressBarIndeterminateVisibility(false);
		}

	}

}