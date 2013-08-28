package com.techo.nicaragua.manual;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;

import com.techo.nicaragua.R;

public class ImageFragment extends SherlockFragment {
	int mNum;
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

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		File ext = Environment.getExternalStorageDirectory();
		File cacheDir = new File(ext, "Android/data/" + getActivity().getPackageName());
		AQUtility.setCacheDir(cacheDir);
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;

	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.item_imageview, container, false);

		AQuery aq = new AQuery(v);
		aq.id(R.id.imageViewItem).image(urls[mNum]);

		return v;
	}

}
