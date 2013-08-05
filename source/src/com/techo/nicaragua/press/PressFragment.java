package com.techo.nicaragua.press;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.mc.reader.Util;
import com.mc.reader.database.Database;
import com.mc.reader.database.OdbArticle;
import com.mc.reader.images.ImageCache;
import com.mc.reader.images.ImageFetcher;
import com.mc.reader.images.ImageCache.ImageCacheParams;
import com.techo.nicaragua.ArticleActivity;
import com.techo.nicaragua.R;

public class PressFragment extends SherlockFragment {
	int mNum;
	static Context myContext;
	private static ImageFetcher mImageFetcher;
	static Database database = null;
	static Util util = null;

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	public static PressFragment newInstance(int num) {

		PressFragment f = new PressFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);

		return f;

	}
	
	private void initImages() {
		// Fetch screen height and width, to use as our max size when loading
		// images as this
		// activity runs full screen
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
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

		ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
				getActivity(), Util.IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory

		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(getActivity(),longest);
		
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(),
				cacheParams);
		mImageFetcher.setImageFadeIn(false);
	}
	
	public static void loadData(Cursor data){
		if (data.getCount() > 0) {
			
			
			Fragment myFragment = ((FragmentActivity) myContext).getSupportFragmentManager().findFragmentById(
					R.id.pressPage);
			
			LayoutInflater inflater = (LayoutInflater) myContext.getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			LinearLayout container = ( LinearLayout)myFragment.getView().findViewById(R.id.linearLayoutList);

			
			 while (data.moveToNext()) {
				 final OdbArticle article = new OdbArticle(
                        database.getArticle(data.getString(data
                                        .getColumnIndex("article_id"))));
				 
				 View myView = inflater.inflate(R.layout.press_articles, null);
				 
				 Typeface face = Typeface.createFromAsset(myContext.getAssets(),
				            "fonts/arial.ttf");
				 
				 TextView title = (TextView) myView.findViewById(R.id.textViewArticleTitle);
				 title.setText(article.getTitle());
				 title.setTypeface(face);
				 
				 ImageView picture = (ImageView) myView.findViewById(R.id.textViewArticleImage);
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

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initImages();
		database = new Database( getActivity() );
		util = new Util( getActivity(), database );
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;
	}

	/**
	 * instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			View v = null;
			myContext = getSherlockActivity();
			PressListTask presslisttask = new PressListTask();
			presslisttask.init(myContext,getSherlockActivity(),util);
			
			
		switch (mNum) {
		case 0:
			v = inflater.inflate(R.layout.item_linearlayout, container, false);
			presslisttask.execute("press-releases");
			break;
			
		case 1:
			v = inflater.inflate(R.layout.item_linearlayout, container, false);
			presslisttask.execute("activities");
			break;
		}
		
		return v;
	}

}