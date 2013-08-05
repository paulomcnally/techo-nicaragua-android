package com.techo.nicaragua;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.mc.reader.Util;
import com.mc.reader.images.ImageCache;
import com.mc.reader.images.ImageFetcher;
import com.mc.reader.images.ImageCache.ImageCacheParams;
import com.techo.nicaragua.utils.ListTagHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class ArticleActivity extends SherlockFragmentActivity {
	
	String title;
	String picture;
	String content;
	
	private static ImageFetcher mImageFetcher;
	
	Context myContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		myContext = this;
		
		
		initImages();
		initData();
		initActionBarSherlock();
	}
	
	private void initData(){
		title = getIntent().getExtras().getString("title");
		picture = getIntent().getExtras().getString("picture");
		content = getIntent().getExtras().getString("content");
		
		TextView articleContent = (TextView) findViewById(R.id.articleContent);
		articleContent.setText(Html.fromHtml(content, null, new ListTagHandler()));
		
		ImageView articleImage = (ImageView) findViewById(R.id.articleImage);
		 mImageFetcher.loadImage(picture, articleImage);
		 articleImage.setScaleType(ScaleType.CENTER);
		
	}

	private void initActionBarSherlock() {
		// Show back button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(title);
	}
	
	private void initImages() {
		// Fetch screen height and width, to use as our max size when loading
		// images as this
		// activity runs full screen
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		((Activity) myContext).getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		final int height = displayMetrics.heightPixels;
		final int width = displayMetrics.widthPixels;
	

		final int longest = (height > width ? height : width) / 2;

		ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
				myContext, Util.IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory

		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(myContext,longest);
		
		mImageFetcher.addImageCache(((FragmentActivity) myContext).getSupportFragmentManager(),
				cacheParams);
		mImageFetcher.setImageFadeIn(false);
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
//		Intent intent = new Intent();
//		intent.setClass(getApplicationContext(), MainActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
//				| Intent.FLAG_ACTIVITY_CLEAR_TOP
//				| Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(intent);
//		finish();
		finish();
	}

}
