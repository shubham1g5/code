package com.example.pptviewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TitlesFragment extends ListFragment {

	String files[];
	OnTitleSelectedListener mListener;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (OnTitleSelectedListener) activity;	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle args = getArguments();
		files = args.getStringArray(MainActivity.FILE_NAMES);
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1, files));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		mListener.OnTitleSelected(position);
	}

}
