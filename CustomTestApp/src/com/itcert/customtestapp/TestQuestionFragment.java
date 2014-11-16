/**
 * 
 */
package com.itcert.customtestapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author james_r_bray
 *
 */
public class TestQuestionFragment extends Fragment {
	
	private static final String DEBUG_TAG = "Gestures";
	
	private GestureDetectorCompat mDetector; 
	
	private TextView timerValue;
	private Button endTestButton;
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime10 = 600000;
	
	public interface OnTestListener {
		public void onEndTestClick();
	}
	
	OnTestListener mCallback;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceStqte) {
		View v = inflater.inflate(R.layout.fragment_question, root, false);
		
		timerValue = (TextView)v.findViewById(R.id.timerTextView);
		endTestButton = (Button)v.findViewById(R.id.endTestBtn);
		
		endTestButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onEndTestClick();
				
			}
			
		});
		
		mDetector = new GestureDetectorCompat(getActivity(), new MyGestureListener());
		
		v.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.d(DEBUG_TAG, "Set onTouch event...");
				mDetector.onTouchEvent(event);
				v.performClick();
				return true;
			}
			
		});
		
		new CountDownTimer(updatedTime10, 1000) {

		     public void onTick(long millisUntilFinished) {
		    	 int secs = (int) (millisUntilFinished / 1000);
		    	 int mins = secs / 60;
		    	 int displaySecs = secs - (mins * 60);
		    	 timerValue.setText("remaining time: " + "" + mins + ":" + String.format("%02d", displaySecs));
		     }

		     public void onFinish() {
		    	 timerValue.setText("The time expired!");
		    	 endTestButton.setText(getActivity().getResources().getString(R.string.see_results));
		    	 
		    	 //TODO disable all question choices
		     }
		  }.start();


		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallback = (OnTestListener) activity;
	}

	/**
	 * 
	 * @author james_r_bray
	 *
	 */
	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures"; 
        
        @Override
        public boolean onDown(MotionEvent event) { 
            Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, 
                float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
            Toast.makeText(getActivity(), DEBUG_TAG, Toast.LENGTH_SHORT).show();
            return true;
        }
    }


}
