package com.techo.nicaragua.techo;

import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import com.techo.nicaragua.R;

import com.techo.nicaragua.utils.HtmlReader;

public class TechoFragment extends SherlockFragment {
	int mNum;

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	public static TechoFragment newInstance(int num) {

		TechoFragment f = new TechoFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);

		return f;

	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;
	}

	/**
	 * instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.item_textview, container, false);
		TextView text = (TextView) v.findViewById(R.id.textViewItem);
		switch (mNum) {
		case 0:
			text.setText(HtmlReader.get(getActivity(), R.raw.techo_history),
					TextView.BufferType.SPANNABLE);
			break;
		case 1:
			text.setText(HtmlReader.get(getActivity(), R.raw.techo_mission_vision),
					TextView.BufferType.SPANNABLE);
			break;
		case 2:
			text.setText(HtmlReader.get(getActivity(), R.raw.techo_intervention_model),
					TextView.BufferType.SPANNABLE);
			break;
			
		case 3:
			text.setText(HtmlReader.get(getActivity(), R.raw.techo_countries),
					TextView.BufferType.SPANNABLE);
			break;
		case 4:
			text.setText(HtmlReader.get(getActivity(), R.raw.techo_faq),
					TextView.BufferType.SPANNABLE);
			break;
		case 5:
			text.setText(HtmlReader.get(getActivity(), R.raw.techo_what_techo),
					TextView.BufferType.SPANNABLE);
			break;
		case 6:
			text.setText(HtmlReader.get(getActivity(), R.raw.techo_values),
					TextView.BufferType.SPANNABLE);
			break;
		}
		
		return v;
	}

}