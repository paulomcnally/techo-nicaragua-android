package com.techo.nicaragua.manual;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.mc.reader.Util;
import com.mc.reader.database.Database;
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

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		File ext = Environment.getExternalStorageDirectory();
		File cacheDir = new File(ext, "Android/data/" + getActivity().getPackageName());
		AQUtility.setCacheDir(cacheDir);
		initClass();
		
		
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
			break;
		case GROUND:
			v = inflater.inflate(R.layout.manual_ground, container, false);
			// ImageView load image

			AQuery aq_ground_a = new AQuery(v);
			aq_ground_a.id(R.id.imageViewManualGroundA).image(
					getString(R.string.manual_ground_2));

			AQuery aq_ground_b = new AQuery(v);
			aq_ground_b.id(R.id.imageViewManualGroundB).image(
					getString(R.string.manual_ground_13));

			AQuery aq_ground_c = new AQuery(v);
			aq_ground_c.id(R.id.imageViewManualGroundC).image(
					getString(R.string.manual_ground_15));

			AQuery aq_ground_d = new AQuery(v);
			aq_ground_d.id(R.id.imageViewManualGroundD).image(
					getString(R.string.manual_ground_18));

			AQuery aq_ground_e = new AQuery(v);
			aq_ground_e.id(R.id.imageViewManualGroundE).image(
					getString(R.string.manual_ground_21));

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

			AQuery aq_pilote_a = new AQuery(v);
			aq_pilote_a.id(R.id.imageViewManualPilotesA).image(
					getString(R.string.manual_pilotes_2));

			AQuery aq_pilote_b = new AQuery(v);
			aq_pilote_b.id(R.id.imageViewManualPilotesB).image(
					getString(R.string.manual_pilotes_13));

			AQuery aq_pilote_c = new AQuery(v);
			aq_pilote_c.id(R.id.imageViewManualPilotesC).image(
					getString(R.string.manual_pilotes_15));

			AQuery aq_pilote_d = new AQuery(v);
			aq_pilote_d.id(R.id.imageViewManualPilotesD).image(
					getString(R.string.manual_pilotes_17));

			AQuery aq_pilote_e = new AQuery(v);
			aq_pilote_e.id(R.id.imageViewManualPilotesE).image(
					getString(R.string.manual_pilotes_21));

			AQuery aq_pilote_f = new AQuery(v);
			aq_pilote_f.id(R.id.imageViewManualPilotesF).image(
					getString(R.string.manual_pilotes_24));

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
			AQuery aq_floor_a = new AQuery(v);
			aq_floor_a.id(R.id.imageViewManualFloorA).image(
					getString(R.string.manual_floor_3));

			AQuery aq_floor_b = new AQuery(v);
			aq_floor_b.id(R.id.imageViewManualFloorB).image(
					getString(R.string.manual_floor_5));

			AQuery aq_floor_c = new AQuery(v);
			aq_floor_c.id(R.id.imageViewManualFloorC).image(
					getString(R.string.manual_floor_8));

			AQuery aq_floor_d = new AQuery(v);
			aq_floor_d.id(R.id.imageViewManualFloorD).image(
					getString(R.string.manual_floor_12));

			AQuery aq_floor_e = new AQuery(v);
			aq_floor_e.id(R.id.imageViewManualFloorE).image(
					getString(R.string.manual_floor_14));

			AQuery aq_floor_f = new AQuery(v);
			aq_floor_f.id(R.id.imageViewManualFloorF).image(
					getString(R.string.manual_floor_16));

			AQuery aq_floor_g = new AQuery(v);
			aq_floor_g.id(R.id.imageViewManualFloorG).image(
					getString(R.string.manual_floor_19));

			AQuery aq_floor_h = new AQuery(v);
			aq_floor_h.id(R.id.imageViewManualFloorH).image(
					getString(R.string.manual_floor_21));

			AQuery aq_floor_i = new AQuery(v);
			aq_floor_i.id(R.id.imageViewManualFloorI).image(
					getString(R.string.manual_floor_22));

			AQuery aq_floor_j = new AQuery(v);
			aq_floor_j.id(R.id.imageViewManualFloorJ).image(
					getString(R.string.manual_floor_25));

			break;
		case WALLS:
			v = inflater.inflate(R.layout.manual_walls, container, false);

			// ImageView load image

			AQuery aq_walls_a = new AQuery(v);
			aq_walls_a.id(R.id.imageViewManualWallsA).image(
					getString(R.string.manual_walls_1));

			AQuery aq_walls_b = new AQuery(v);
			aq_walls_b.id(R.id.imageViewManualWallsB).image(
					getString(R.string.manual_walls_4));

			AQuery aq_walls_c = new AQuery(v);
			aq_walls_c.id(R.id.imageViewManualWallsC).image(
					getString(R.string.manual_walls_6));

			AQuery aq_walls_d = new AQuery(v);
			aq_walls_d.id(R.id.imageViewManualWallsD).image(
					getString(R.string.manual_walls_9));

			AQuery aq_walls_e = new AQuery(v);
			aq_walls_e.id(R.id.imageViewManualWallsE).image(
					getString(R.string.manual_walls_20));

			AQuery aq_walls_f = new AQuery(v);
			aq_walls_f.id(R.id.imageViewManualWallsF).image(
					getString(R.string.manual_walls_22));

			AQuery aq_walls_g = new AQuery(v);
			aq_walls_g.id(R.id.imageViewManualWallsG).image(
					getString(R.string.manual_walls_23));

			AQuery aq_walls_h = new AQuery(v);
			aq_walls_h.id(R.id.imageViewManualWallsH).image(
					getString(R.string.manual_walls_25));

			AQuery aq_walls_i = new AQuery(v);
			aq_walls_i.id(R.id.imageViewManualWallsI).image(
					getString(R.string.manual_walls_27));

			AQuery aq_walls_j = new AQuery(v);
			aq_walls_j.id(R.id.imageViewManualWallsJ).image(
					getString(R.string.manual_walls_30));

			break;
		case ROOF:
			v = inflater.inflate(R.layout.manual_roof, container, false);

			// ImageView load image
			AQuery aq_roof_a = new AQuery(v);
			aq_roof_a.id(R.id.imageViewManualRoofA).image(
					getString(R.string.manual_roof_4));

			AQuery aq_roof_b = new AQuery(v);
			aq_roof_b.id(R.id.imageViewManualRoofB).image(
					getString(R.string.manual_roof_6));

			AQuery aq_roof_c = new AQuery(v);
			aq_roof_c.id(R.id.imageViewManualRoofC).image(
					getString(R.string.manual_roof_8));

			AQuery aq_roof_d = new AQuery(v);
			aq_roof_d.id(R.id.imageViewManualRoofD).image(
					getString(R.string.manual_roof_11));

			AQuery aq_roof_e = new AQuery(v);
			aq_roof_e.id(R.id.imageViewManualRoofE).image(
					getString(R.string.manual_roof_12));

			AQuery aq_roof_f = new AQuery(v);
			aq_roof_f.id(R.id.imageViewManualRoofF).image(
					getString(R.string.manual_roof_13));

			AQuery aq_roof_g = new AQuery(v);
			aq_roof_g.id(R.id.imageViewManualRoofG).image(
					getString(R.string.manual_roof_15));

			AQuery aq_roof_h = new AQuery(v);
			aq_roof_h.id(R.id.imageViewManualRoofH).image(
					getString(R.string.manual_roof_16));

			AQuery aq_roof_i = new AQuery(v);
			aq_roof_i.id(R.id.imageViewManualRoofI).image(
					getString(R.string.manual_roof_18));

			AQuery aq_roof_j = new AQuery(v);
			aq_roof_j.id(R.id.imageViewManualRoofJ).image(
					getString(R.string.manual_roof_19));

			AQuery aq_roof_k = new AQuery(v);
			aq_roof_k.id(R.id.imageViewManualRoofK).image(
					getString(R.string.manual_roof_21));

			AQuery aq_roof_l = new AQuery(v);
			aq_roof_l.id(R.id.imageViewManualRoofL).image(
					getString(R.string.manual_roof_23));

			AQuery aq_roof_m = new AQuery(v);
			aq_roof_m.id(R.id.imageViewManualRoofM).image(
					getString(R.string.manual_roof_25));

			AQuery aq_roof_n = new AQuery(v);
			aq_roof_n.id(R.id.imageViewManualRoofN).image(
					getString(R.string.manual_roof_26));

			AQuery aq_roof_o = new AQuery(v);
			aq_roof_o.id(R.id.imageViewManualRoofO).image(
					getString(R.string.manual_roof_27));

			AQuery aq_roof_p = new AQuery(v);
			aq_roof_p.id(R.id.imageViewManualRoofP).image(
					getString(R.string.manual_roof_28));

			AQuery aq_roof_q = new AQuery(v);
			aq_roof_q.id(R.id.imageViewManualRoofQ).image(
					getString(R.string.manual_roof_30));

			AQuery aq_roof_r = new AQuery(v);
			aq_roof_r.id(R.id.imageViewManualRoofR).image(
					getString(R.string.manual_roof_31));

			break;
		case DOOR_AND_WINDOWS:
			v = inflater.inflate(R.layout.manual_door_and_windows, container,
					false);

			// ImageView load image
			AQuery aq_daw_a = new AQuery(v);
			aq_daw_a.id(R.id.imageViewManualDoorWindowsA).image(
					getString(R.string.manual_door_and_windows_2));

			AQuery aq_daw_b = new AQuery(v);
			aq_daw_b.id(R.id.imageViewManualDoorWindowsB).image(
					getString(R.string.manual_door_and_windows_4));

			AQuery aq_daw_c = new AQuery(v);
			aq_daw_c.id(R.id.imageViewManualDoorWindowsC).image(
					getString(R.string.manual_door_and_windows_8));

			AQuery aq_daw_d = new AQuery(v);
			aq_daw_d.id(R.id.imageViewManualDoorWindowsD).image(
					getString(R.string.manual_door_and_windows_9));

			AQuery aq_daw_e = new AQuery(v);
			aq_daw_e.id(R.id.imageViewManualDoorWindowsE).image(
					getString(R.string.manual_door_and_windows_14));

			AQuery aq_daw_f = new AQuery(v);
			aq_daw_f.id(R.id.imageViewManualDoorWindowsF).image(
					getString(R.string.manual_door_and_windows_16));

			break;

		}

		return v;
	}

}