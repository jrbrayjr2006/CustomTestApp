/**
 * 
 */
package com.itcert.customtestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author james_r_bray
 *
 */
public class ResultsDialogFragment extends DialogFragment {
	
	private TextView mPerformanceTextView;
	private TextView mScoreTextView;
	private TextView mReviewOutputTextView;
	private Button mStartNewTestBtn;
	private int mNumberCorrect;
	
	private String mTitle;
	private String mIncorrectAnswers;
	
	private Bundle arguments;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTitle = getArguments().getString(MainActivity.TEST_TITLE);
	}
	
	public static ResultsDialogFragment newInstance() {
		return new ResultsDialogFragment();
	}
	
	public static ResultsDialogFragment newInstance(int _numCorrect, String _rList, String _title) {
		ResultsDialogFragment rdf = new ResultsDialogFragment();
		Bundle arguments = new Bundle();
		arguments.putInt(MainActivity.NUM_CORRECT_KEY, _numCorrect);
		arguments.putString(MainActivity.REVIEW_LIST_KEY, _rList);
		arguments.putString(MainActivity.TEST_TITLE, _title);
		rdf.setArguments(arguments);
		return rdf;
	}
	/*
	public ResultsDialogFragment(Bundle savedInstanceState) {
		this.arguments = savedInstanceState;
	}
	
	private ResultsDialogFragment(int _numCorrect, String _rList, String _title) {
		this.mTitle = _title;
	}
	*/
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		this.mTitle = getArguments().getString(MainActivity.TEST_TITLE);
		this.mNumberCorrect = getArguments().getInt(MainActivity.NUM_CORRECT_KEY);
		this.mIncorrectAnswers = getArguments().getString(MainActivity.REVIEW_LIST_KEY);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(mTitle);
		builder.setMessage(generateMessege());
		builder.setPositiveButton(getActivity().getResources().getString(R.string.start_new_test_label), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ((MainActivity)getActivity()).doPositiveClick();
            }
        });
		builder.setNegativeButton(getActivity().getResources().getString(R.string.close_label), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((MainActivity)getActivity()).doNegativeClick();
				
			}
		});
		return builder.create();
	}
	
	private String generateHelpText() {
		StringBuilder sb = new StringBuilder();
		sb.append("Score");
		return sb.toString();
	}

	
	/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
		super.onCreateView(inflater, root, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_result, root);
		
		mNumberCorrect = getArguments().getInt(MainActivity.NUM_CORRECT_KEY);
		mIncorrectAnswers = getArguments().getString(MainActivity.REVIEW_LIST_KEY);
		
		String evalMessege = getTestEvaluation(mNumberCorrect);
		
		mScoreTextView = (TextView)v.findViewById(R.id.scoreTextView);
		mScoreTextView.setText(getActivity().getResources().getString(R.string.correct_answers_text) + " " + mNumberCorrect + "/10");
		
		mReviewOutputTextView = (TextView)v.findViewById(R.id.reviewOutputTextView);
		mReviewOutputTextView.setText(mIncorrectAnswers);
		
		mPerformanceTextView = (TextView)v.findViewById(R.id.performanceTextView);
		mPerformanceTextView.setText(evalMessege);
		
		return v;
	}
	*/
	
	private String getTestEvaluation(int _results) {
		String messege = getActivity().getResources().getString(R.string.average_performance);
		if(_results < 5) {
			messege = getActivity().getResources().getString(R.string.low_performance);
		}
		if(_results >= 8) {
			messege = getActivity().getResources().getString(R.string.excellent_performance);
		}
		return messege;
	}
	
	private String generateMessege() {
		StringBuffer sb = new StringBuffer();
		sb.append(getTestEvaluation(this.mNumberCorrect));
		sb.append("\nCorrect Answers ");
		sb.append(this.mNumberCorrect);
		sb.append("/10");
		sb.append("\n");
		sb.append("\nReview These Questions");
		sb.append("\n");
		sb.append(this.mIncorrectAnswers);
		return sb.toString();
	}

}
