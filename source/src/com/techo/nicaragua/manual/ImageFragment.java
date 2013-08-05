package com.techo.nicaragua.manual;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.mc.reader.Util;
import com.mc.reader.images.ImageCache;
import com.mc.reader.images.ImageFetcher;
import com.mc.reader.images.ImageCache.ImageCacheParams;
import com.techo.nicaragua.R;

public class ImageFragment extends SherlockFragment {
	int mNum;
	private ImageFetcher mImageFetcher;
	private static String[] urls;

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	public static ImageFragment newInstance(int num) {
		ImageFragment f = new ImageFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);

		return f;
	}

	public static void setUrls(String[] list) {
		urls = list;
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
		mImageFetcher = new ImageFetcher(getActivity(), longest);
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(),
				cacheParams);
		mImageFetcher.setImageFadeIn(false);
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;
		initImages();
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.item_imageview, container, false);
		ImageView iv = (ImageView) v.findViewById(R.id.imageViewItem);
		mImageFetcher.loadImage(urls[mNum], iv);
		return v;
	}

}
