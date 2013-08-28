package com.techo.nicaragua;

import java.io.File;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.androidquery.util.AQUtility;

import com.techo.nicaragua.utils.ListTagHandler;

import android.os.Bundle;
import android.os.Environment;
import android.content.Context;

import android.text.Html;

import android.widget.TextView;

public class ArticleActivity extends SherlockFragmentActivity {

	String title;
	String picture;
	String content;

	Context myContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);

		myContext = this;
		
		File ext = Environment.getExternalStorageDirectory();
		File cacheDir = new File(ext, "Android/data/" + getPackageName());
		AQUtility.setCacheDir(cacheDir);

		initData();
		initActionBarSherlock();
	}

	private void initData() {
		title = getIntent().getExtras().getString("title");
		picture = getIntent().getExtras().getString("picture");
		content = getIntent().getExtras().getString("content");

		TextView articleContent = (TextView) findViewById(R.id.articleContent);
		articleContent.setText(Html.fromHtml(content, null,
				new ListTagHandler()));

		ImageOptions options = new ImageOptions();
		options.fileCache = true;
		options.memCache = true;
		options.ratio = 1;
		options.anchor = (float) 1.0;
		AQuery aq = new AQuery(this);
		aq.id(R.id.articleImage).image(picture,options);

	}

	private void initActionBarSherlock() {
		// Show back button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(title);
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
		// Intent intent = new Intent();
		// intent.setClass(getApplicationContext(), MainActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
		// | Intent.FLAG_ACTIVITY_CLEAR_TOP
		// | Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivity(intent);
		// finish();
		finish();
	}

}
