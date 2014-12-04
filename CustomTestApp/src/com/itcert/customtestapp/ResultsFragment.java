/**
 * 
 */
package com.itcert.customtestapp;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.itcert.customtestapp.model.Question;
import com.itcert.customtestapp.model.TestObject;

/**
 * @author james_r_bray
 *
 */
public class ResultsFragment extends Fragment {
	
	private TextView mReviewOutputTextView;
	private Button mStartNewTestBtn;
	private int mNumberCorrect;
	private String mIncorrectAnswers;
	
	OnResultsListener mCallback;
	
	public interface OnResultsListener {
		public void onShowTestList();
	}

	/**
	 * 
	 */
	public ResultsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
		super.onCreateView(inflater, root, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_result, root, false);
		
		//selectedTest = (TestObject)getArguments().getSerializable(MainActivity.TEST);
		mNumberCorrect = getArguments().getInt(MainActivity.NUM_CORRECT_KEY);
		mIncorrectAnswers = getArguments().getString(MainActivity.REVIEW_LIST_KEY);
		
		mReviewOutputTextView = (TextView)v.findViewById(R.id.reviewOutputTextView);
		mReviewOutputTextView.setText(mIncorrectAnswers);
		
		mStartNewTestBtn = (Button)v.findViewById(R.id.startNewTestBtn);
		mStartNewTestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCallback.onShowTestList();
			}
		});
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallback = (OnResultsListener)activity;
	}

}
