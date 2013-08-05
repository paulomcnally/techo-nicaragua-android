package com.techo.nicaragua.manual;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.mc.reader.Util;
import com.mc.reader.database.Database;
import com.mc.reader.images.ImageCache;
import com.mc.reader.images.ImageCache.ImageCacheParams;
import com.mc.reader.images.ImageFetcher;
import com.techo.nicaragua.R;
import com.viewpagerindicator.LinePageIndicator;

public class ManualFragment extends SherlockFragment {
	int mNum;

	ImageViewAdapter mAdapter;
	ViewPager mPager;

	LinePageIndicator mIndicator;

	Database database = null;
	Util util = null;

	private static final int MANUAL = 0;
	private static final int GROUND = 1;
	private static final int PILOTES = 2;
	private static final int FLOOR = 3;
	private static final int WALLS = 4;
	private static final int ROOF = 5;
	private static final int DOOR_AND_WINDOWS = 6;

	private ImageFetcher mImageFetcher;

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	public static ManualFragment newInstance(int num) {

		ManualFragment f = new ManualFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);

		return f;

	}

	private void initClass() {
		database = new Database(getActivity());
		util = new Util(getActivity(), database);
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
		initClass();
		initImages();
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;
	}

	/**
	 * instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		switch (mNum) {
		case MANUAL:
			v = inflater.inflate(R.layout.manual_manual, container, false);
			
			// Pagination image view
						String[] urls_manuals = new String[6];
						urls_manuals[0] = getString(R.string.manual_manual_im_1);
						urls_manuals[1] = getString(R.string.manual_manual_im_2);
						urls_manuals[2] = getString(R.string.manual_manual_im_3);
						urls_manuals[3] = getString(R.string.manual_manual_im_4);
						urls_manuals[4] = getString(R.string.manual_manual_im_5);
						urls_manuals[5] = getString(R.string.manual_manual_im_6);

						mAdapter = new ImageViewAdapter(getActivity()
								.getSupportFragmentManager());
						mAdapter.setCount(6);
						mAdapter.setUrls(urls_manuals);
						mPager = (ViewPager) v.findViewById(R.id.pagerManual);
						mPager.setAdapter(mAdapter);
						mPager.setOnTouchListener(new View.OnTouchListener() {

							@Override
							public boolean onTouch(View v, MotionEvent event) {
								mPager.getParent().requestDisallowInterceptTouchEvent(true);
								return false;
							}
						});

						mIndicator = (LinePageIndicator) v
								.findViewById(R.id.indicatorManual);
						mIndicator.setViewPager(mPager);
			
			
			
			break;
		case GROUND:
			v = inflater.inflate(R.layout.manual_ground, container, false);
			// ImageView load image
			ImageView manual_ground_a = (ImageView) v
					.findViewById(R.id.imageViewManualGroundA);
			mImageFetcher.loadImage(getString(R.string.manual_ground_2),
					manual_ground_a);

			ImageView manual_ground_b = (ImageView) v
					.findViewById(R.id.imageViewManualGroundB);
			mImageFetcher.loadImage(getString(R.string.manual_ground_13),
					manual_ground_b);

			ImageView manual_ground_c = (ImageView) v
					.findViewById(R.id.imageViewManualGroundC);
			mImageFetcher.loadImage(getString(R.string.manual_ground_15),
					manual_ground_c);

			ImageView manual_ground_d = (ImageView) v
					.findViewById(R.id.imageViewManualGroundD);
			mImageFetcher.loadImage(getString(R.string.manual_ground_18),
					manual_ground_d);

			ImageView manual_ground_e = (ImageView) v
					.findViewById(R.id.imageViewManualGroundE);
			mImageFetcher.loadImage(getString(R.string.manual_ground_21),
					manual_ground_e);

			// CheckBox actions
			LinearLayout linearLayoutGroundMaster = (LinearLayout) v
					.findViewById(R.id.linearLayoutGroundMaster);

			int childcount = linearLayoutGroundMaster.getChildCount();

			for (int i = 0; i < childcount; i++) {
				View view_obj = linearLayoutGroundMaster.getChildAt(i);

				if (view_obj.getClass().getName().toString()
						.equals("android.widget.CheckBox")) {

					final String string_id = view_obj.getTag().toString();

					int id = getResources().getIdentifier(string_id, "id",
							getActivity().getPackageName());

					CheckBox chk = (CheckBox) v.findViewById(id);

					if (util.get(string_id).equals("1")) {

						chk.setChecked(true);
					}

					chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (buttonView.isChecked()) {

								util.set(string_id, "1");
							} else {
								util.set(string_id, "0");
							}
						}
					});
				}
			}

			break;
		case PILOTES:
			v = inflater.inflate(R.layout.manual_pilotes, container, false);

			ImageView manual_pilotes_a = (ImageView) v
					.findViewById(R.id.imageViewManualPilotesA);
			mImageFetcher.loadImage(getString(R.string.manual_pilotes_2),
					manual_pilotes_a);

			ImageView manual_pilotes_b = (ImageView) v
					.findViewById(R.id.imageViewManualPilotesB);
			mImageFetcher.loadImage(getString(R.string.manual_pilotes_13),
					manual_pilotes_b);

			ImageView manual_pilotes_c = (ImageView) v
					.findViewById(R.id.imageViewManualPilotesC);
			mImageFetcher.loadImage(getString(R.string.manual_pilotes_15),
					manual_pilotes_c);

			ImageView manual_pilotes_d = (ImageView) v
					.findViewById(R.id.imageViewManualPilotesD);
			mImageFetcher.loadImage(getString(R.string.manual_pilotes_17),
					manual_pilotes_d);

			ImageView manual_pilotes_e = (ImageView) v
					.findViewById(R.id.imageViewManualPilotesE);
			mImageFetcher.loadImage(getString(R.string.manual_pilotes_21),
					manual_pilotes_e);

			ImageView manual_pilotes_f = (ImageView) v
					.findViewById(R.id.imageViewManualPilotesF);
			mImageFetcher.loadImage(getString(R.string.manual_pilotes_24),
					manual_pilotes_f);

			// Pagination image view
			String[] urls_pilotes = new String[5];
			urls_pilotes[0] = getString(R.string.manual_pilotes_im_1);
			urls_pilotes[1] = getString(R.string.manual_pilotes_im_2);
			urls_pilotes[2] = getString(R.string.manual_pilotes_im_3);
			urls_pilotes[3] = getString(R.string.manual_pilotes_im_4);
			urls_pilotes[4] = getString(R.string.manual_pilotes_im_5);

			mAdapter = new ImageViewAdapter(getActivity()
					.getSupportFragmentManager());
			mAdapter.setCount(5);
			mAdapter.setUrls(urls_pilotes);
			mPager = (ViewPager) v.findViewById(R.id.pagerPilotes);
			mPager.setAdapter(mAdapter);
			mPager.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					mPager.getParent().requestDisallowInterceptTouchEvent(true);
					return false;
				}
			});

			mIndicator = (LinePageIndicator) v
					.findViewById(R.id.indicatorPilotes);
			mIndicator.setViewPager(mPager);

			break;

		case FLOOR:
			v = inflater.inflate(R.layout.manual_floor, container, false);

			// ImageView load image
			ImageView manual_floor_a = (ImageView) v
					.findViewById(R.id.imageViewManualFloorA);
			mImageFetcher.loadImage(getString(R.string.manual_floor_3),
					manual_floor_a);

			ImageView manual_floor_b = (ImageView) v
					.findViewById(R.id.imageViewManualFloorB);
			mImageFetcher.loadImage(getString(R.string.manual_floor_5),
					manual_floor_b);

			ImageView manual_floor_c = (ImageView) v
					.findViewById(R.id.imageViewManualFloorC);
			mImageFetcher.loadImage(getString(R.string.manual_floor_8),
					manual_floor_c);

			ImageView manual_floor_d = (ImageView) v
					.findViewById(R.id.imageViewManualFloorD);
			mImageFetcher.loadImage(getString(R.string.manual_floor_12),
					manual_floor_d);

			ImageView manual_floor_e = (ImageView) v
					.findViewById(R.id.imageViewManualFloorE);
			mImageFetcher.loadImage(getString(R.string.manual_floor_14),
					manual_floor_e);

			ImageView manual_floor_f = (ImageView) v
					.findViewById(R.id.imageViewManualFloorF);
			mImageFetcher.loadImage(getString(R.string.manual_floor_16),
					manual_floor_f);

			ImageView manual_floor_g = (ImageView) v
					.findViewById(R.id.imageViewManualFloorG);
			mImageFetcher.loadImage(getString(R.string.manual_floor_19),
					manual_floor_g);

			ImageView manual_floor_h = (ImageView) v
					.findViewById(R.id.imageViewManualFloorH);
			mImageFetcher.loadImage(getString(R.string.manual_floor_21),
					manual_floor_h);

			ImageView manual_floor_i = (ImageView) v
					.findViewById(R.id.imageViewManualFloorI);
			mImageFetcher.loadImage(getString(R.string.manual_floor_22),
					manual_floor_i);

			ImageView manual_floor_j = (ImageView) v
					.findViewById(R.id.imageViewManualFloorJ);
			mImageFetcher.loadImage(getString(R.string.manual_floor_25),
					manual_floor_j);

			break;
		case WALLS:
			v = inflater.inflate(R.layout.manual_walls, container, false);

			// ImageView load image
			ImageView manual_walls_a = (ImageView) v
					.findViewById(R.id.imageViewManualWallsA);
			mImageFetcher.loadImage(getString(R.string.manual_walls_1),
					manual_walls_a);

			ImageView manual_walls_b = (ImageView) v
					.findViewById(R.id.imageViewManualWallsB);
			mImageFetcher.loadImage(getString(R.string.manual_walls_4),
					manual_walls_b);

			ImageView manual_walls_c = (ImageView) v
					.findViewById(R.id.imageViewManualWallsC);
			mImageFetcher.loadImage(getString(R.string.manual_walls_6),
					manual_walls_c);

			ImageView manual_walls_d = (ImageView) v
					.findViewById(R.id.imageViewManualWallsD);
			mImageFetcher.loadImage(getString(R.string.manual_walls_9),
					manual_walls_d);

			ImageView manual_walls_e = (ImageView) v
					.findViewById(R.id.imageViewManualWallsE);
			mImageFetcher.loadImage(getString(R.string.manual_walls_20),
					manual_walls_e);

			ImageView manual_walls_f = (ImageView) v
					.findViewById(R.id.imageViewManualWallsF);
			mImageFetcher.loadImage(getString(R.string.manual_walls_22),
					manual_walls_f);

			ImageView manual_walls_g = (ImageView) v
					.findViewById(R.id.imageViewManualWallsG);
			mImageFetcher.loadImage(getString(R.string.manual_walls_23),
					manual_walls_g);

			ImageView manual_walls_h = (ImageView) v
					.findViewById(R.id.imageViewManualWallsH);
			mImageFetcher.loadImage(getString(R.string.manual_walls_25),
					manual_walls_h);

			ImageView manual_walls_i = (ImageView) v
					.findViewById(R.id.imageViewManualWallsI);
			mImageFetcher.loadImage(getString(R.string.manual_walls_27),
					manual_walls_i);

			ImageView manual_walls_j = (ImageView) v
					.findViewById(R.id.imageViewManualWallsJ);
			mImageFetcher.loadImage(getString(R.string.manual_walls_30),
					manual_walls_j);

			break;
		case ROOF:
			v = inflater.inflate(R.layout.manual_roof, container, false);

			// ImageView load image
			ImageView manual_roof_a = (ImageView) v
					.findViewById(R.id.imageViewManualRoofA);
			mImageFetcher.loadImage(getString(R.string.manual_roof_4),
					manual_roof_a);

			ImageView manual_roof_b = (ImageView) v
					.findViewById(R.id.imageViewManualRoofB);
			mImageFetcher.loadImage(getString(R.string.manual_roof_6),
					manual_roof_b);

			ImageView manual_roof_c = (ImageView) v
					.findViewById(R.id.imageViewManualRoofC);
			mImageFetcher.loadImage(getString(R.string.manual_roof_8),
					manual_roof_c);

			ImageView manual_roof_d = (ImageView) v
					.findViewById(R.id.imageViewManualRoofD);
			mImageFetcher.loadImage(getString(R.string.manual_roof_11),
					manual_roof_d);

			ImageView manual_roof_e = (ImageView) v
					.findViewById(R.id.imageViewManualRoofE);
			mImageFetcher.loadImage(getString(R.string.manual_roof_12),
					manual_roof_e);

			ImageView manual_roof_f = (ImageView) v
					.findViewById(R.id.imageViewManualRoofF);
			mImageFetcher.loadImage(getString(R.string.manual_roof_13),
					manual_roof_f);

			ImageView manual_roof_g = (ImageView) v
					.findViewById(R.id.imageViewManualRoofG);
			mImageFetcher.loadImage(getString(R.string.manual_roof_15),
					manual_roof_g);

			ImageView manual_roof_h = (ImageView) v
					.findViewById(R.id.imageViewManualRoofH);
			mImageFetcher.loadImage(getString(R.string.manual_roof_16),
					manual_roof_h);

			ImageView manual_roof_i = (ImageView) v
					.findViewById(R.id.imageViewManualRoofI);
			mImageFetcher.loadImage(getString(R.string.manual_roof_18),
					manual_roof_i);

			ImageView manual_roof_j = (ImageView) v
					.findViewById(R.id.imageViewManualRoofJ);
			mImageFetcher.loadImage(getString(R.string.manual_roof_19),
					manual_roof_j);

			ImageView manual_roof_k = (ImageView) v
					.findViewById(R.id.imageViewManualRoofK);
			mImageFetcher.loadImage(getString(R.string.manual_roof_21),
					manual_roof_k);

			ImageView manual_roof_l = (ImageView) v
					.findViewById(R.id.imageViewManualRoofL);
			mImageFetcher.loadImage(getString(R.string.manual_roof_23),
					manual_roof_l);

			ImageView manual_roof_m = (ImageView) v
					.findViewById(R.id.imageViewManualRoofM);
			mImageFetcher.loadImage(getString(R.string.manual_roof_25),
					manual_roof_m);

			ImageView manual_roof_n = (ImageView) v
					.findViewById(R.id.imageViewManualRoofN);
			mImageFetcher.loadImage(getString(R.string.manual_roof_26),
					manual_roof_n);

			ImageView manual_roof_o = (ImageView) v
					.findViewById(R.id.imageViewManualRoofO);
			mImageFetcher.loadImage(getString(R.string.manual_roof_27),
					manual_roof_o);

			ImageView manual_roof_p = (ImageView) v
					.findViewById(R.id.imageViewManualRoofP);
			mImageFetcher.loadImage(getString(R.string.manual_roof_28),
					manual_roof_p);

			ImageView manual_roof_q = (ImageView) v
					.findViewById(R.id.imageViewManualRoofQ);
			mImageFetcher.loadImage(getString(R.string.manual_roof_30),
					manual_roof_q);

			ImageView manual_roof_r = (ImageView) v
					.findViewById(R.id.imageViewManualRoofR);
			mImageFetcher.loadImage(getString(R.string.manual_roof_31),
					manual_roof_r);

			break;
		case DOOR_AND_WINDOWS:
			v = inflater.inflate(R.layout.manual_door_and_windows, container,
					false);

			// ImageView load image
			ImageView manual_door_windows_a = (ImageView) v
					.findViewById(R.id.imageViewManualDoorWindowsA);
			mImageFetcher.loadImage(
					getString(R.string.manual_door_and_windows_2),
					manual_door_windows_a);

			ImageView manual_door_windows_b = (ImageView) v
					.findViewById(R.id.imageViewManualDoorWindowsB);
			mImageFetcher.loadImage(
					getString(R.string.manual_door_and_windows_4),
					manual_door_windows_b);
			
			ImageView manual_door_windows_c = (ImageView) v
					.findViewById(R.id.imageViewManualDoorWindowsC);
			mImageFetcher.loadImage(
					getString(R.string.manual_door_and_windows_8),
					manual_door_windows_c);
			
			ImageView manual_door_windows_d = (ImageView) v
					.findViewById(R.id.imageViewManualDoorWindowsD);
			mImageFetcher.loadImage(
					getString(R.string.manual_door_and_windows_9),
					manual_door_windows_d);
			
			ImageView manual_door_windows_e = (ImageView) v
					.findViewById(R.id.imageViewManualDoorWindowsE);
			mImageFetcher.loadImage(
					getString(R.string.manual_door_and_windows_14),
					manual_door_windows_e);
			
			ImageView manual_door_windows_f = (ImageView) v
					.findViewById(R.id.imageViewManualDoorWindowsF);
			mImageFetcher.loadImage(
					getString(R.string.manual_door_and_windows_16),
					manual_door_windows_f);

			break;

		}

		return v;
	}

}