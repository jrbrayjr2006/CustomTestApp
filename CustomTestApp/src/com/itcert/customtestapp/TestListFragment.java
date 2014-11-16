/**
 * 
 */
package com.itcert.customtestapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author james_r_bray
 *
 */
public class TestListFragment extends Fragment {
	
	private ListView mTestListView;
	
	private ArrayAdapter<String> mArrayAdapter;
	private ArrayList<String> mTestList;
	
	public interface OnTestSelectedListener {
		public void onTestSelected(int index);
	}
	
	OnTestSelectedListener mListener; 
	
	private final String ERROR = "TestListFragment Error";
	private final String TAG = "TestListFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_question_list, root, false);
		
		mTestListView = (ListView)v.findViewById(R.id.questionsListView);
		
		int layoutID = android.R.layout.simple_list_item_1;
		mTestList = new ArrayList<String>(Arrays.asList(getActivity().getResources().getStringArray(R.array.tests)));
		mArrayAdapter = new ArrayAdapter<String>(getActivity(), layoutID, mTestList);
		mTestListView.setAdapter(mArrayAdapter);
		
		mTestListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mListener.onTestSelected(position);
				Log.d(TAG, mTestList.get(position));
				//Toast.makeText(getActivity(), mTestList.get(position), Toast.LENGTH_SHORT).show();
			}
			
		});
		
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnTestSelectedListener)activity;
		} catch(ClassCastException cce) {
			Log.e(ERROR, "Error occurred while casting activity to OnTestSelectedListener");
		}
	}

}
