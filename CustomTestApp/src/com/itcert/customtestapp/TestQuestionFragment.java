/**
 * 
 */
package com.itcert.customtestapp;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itcert.customtestapp.model.Question;
import com.itcert.customtestapp.model.TestObject;

/**
 * @author james_r_bray
 *
 */
public class TestQuestionFragment extends Fragment {
	
	private static final String DEBUG_TAG = "Gestures";
	private static final String TAG = "TestQuestionFragment";
	
	private static final int MAX_NUM_QUESTIONS = 10;
	private static final int MIN_NUM_QUESTIONS = 1;
	
	private GestureDetectorCompat mDetector; 
	private TestObject myTest;
	private TestObject selectedTest;
	//List<Question> questions;
	
	private TextView timerValue;
	private TextView questionText;
	private Button endTestButton;
	private Button aButton;
	private Button bButton;
	private Button cButton;
	private Button dButton;
	private Button eButton;
	private Button solutionButton;
	private ImageView questionImageView;
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime10 = 600000;
	//long updatedTime10 = 10000;  //TODO for testing only, remove when ready to ship
	private int testIndex;
	private int currentIndex;
	
	public interface OnTestListener {
		public void onEndTestClick();
	}
	
	OnTestListener mCallback;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_question, root, false);
		
		timerValue = (TextView)v.findViewById(R.id.timerTextView);
		questionText = (TextView)v.findViewById(R.id.questionTextView);
		endTestButton = (Button)v.findViewById(R.id.endTestBtn);
		aButton = (Button)v.findViewById(R.id.buttonA);
		bButton = (Button)v.findViewById(R.id.buttonB);
		cButton = (Button)v.findViewById(R.id.buttonC);
		dButton = (Button)v.findViewById(R.id.buttonD);
		eButton = (Button)v.findViewById(R.id.buttonE);
		questionImageView = (ImageView)v.findViewById(R.id.questionImageView);
		solutionButton = (Button)v.findViewById(R.id.buttonSolution);
		
		selectedTest = (TestObject)getArguments().getSerializable(MainActivity.TEST);
		
		testIndex = getArguments().getInt("index");
		//TODO BEGIN the following code is for testing only
		myTest = selectedTest; // = new TestObject();
		//questions = selectedTest.getQuestions(); // = new ArrayList<Question>();
		currentIndex = 0;
		
		questionText.setText("Question " + selectedTest.getQuestions().get(0).getQuestionNumber() + "/10");
		
		myTest.setQuestions(selectedTest.getQuestions());
		
		endTestButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onEndTestClick();
				
			}
			
		});
		
		aButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				defaultButtonTextColor();
				updateCurrentQuestion("A");
				updateButtonColor(aButton);
			}
			
		});
		bButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				defaultButtonTextColor();
				updateCurrentQuestion("B");
				updateButtonColor(bButton);
			}
			
		});
		cButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				defaultButtonTextColor();
				updateCurrentQuestion("C");
				updateButtonColor(cButton);
			}
			
		});
		dButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				defaultButtonTextColor();
				updateCurrentQuestion("D");
				updateButtonColor(dButton);
			}
			
		});
		eButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				defaultButtonTextColor();
				updateCurrentQuestion("E");
				updateButtonColor(eButton);
			}
			
		});
		solutionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Solution!", Toast.LENGTH_SHORT).show();
			}});
		
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
		    	 timerValue.setTextColor(getActivity().getResources().getColor(R.color.red));
		    	 endTestButton.setText(getActivity().getResources().getString(R.string.see_results));
		    	 aButton.setEnabled(false);
		    	 bButton.setEnabled(false);
		    	 cButton.setEnabled(false);
		    	 dButton.setEnabled(false);
		    	 eButton.setEnabled(false);
		    	 solutionButton.setEnabled(true);
		     }
		  }.start();
		  updateImage();

		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallback = (OnTestListener) activity;
	}
	
	private void defaultButtonTextColor() {
		aButton.setTextColor(getActivity().getResources().getColor(R.color.black));
		bButton.setTextColor(getActivity().getResources().getColor(R.color.black));
		cButton.setTextColor(getActivity().getResources().getColor(R.color.black));
		dButton.setTextColor(getActivity().getResources().getColor(R.color.black));
		eButton.setTextColor(getActivity().getResources().getColor(R.color.black));
	}
	
	private void updateButtonColor(Button targetButton) {
		targetButton.setTextColor(getActivity().getResources().getColor(R.color.green));
	}
	
	private void updateCurrentQuestion(String _selectedOption) {
		selectedTest.getQuestions().get(currentIndex).setSelectedOption(_selectedOption);
	}
	
	private void updateImage() {
		try 
		{
			int t = testIndex + 1;
			int q = currentIndex + 1;
			String imageName = "pictures/" + t + "-" + q + ".jpg";
			Log.d(DEBUG_TAG, imageName);
		    // get input stream
		    InputStream ims = getActivity().getAssets().open(imageName);
		    // load image as Drawable
		    Drawable d = Drawable.createFromStream(ims, null);
		    // set image to ImageView
		    questionImageView.setImageDrawable(d);
		    questionImageView.setMinimumWidth(1600);
		    questionImageView.setMinimumHeight(1000);
		}
		catch(IOException ex) 
		{
		    return;
		}
	}

	/**
	 * 
	 * @author james_r_bray
	 *
	 */
	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures"; 
        int x = 1;
        
        @Override
        public boolean onDown(MotionEvent event) { 
            Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, 
                float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
            //Toast.makeText(getActivity(), DEBUG_TAG, Toast.LENGTH_SHORT).show();
            float x1 = event1.getX();
            float x2 = event2.getX();
            // Left to Right swipe action
            if (x2 > x1) {
                //Toast.makeText(getActivity(), "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show();
            	if(x > MIN_NUM_QUESTIONS) {
                	x--;
                	// reset button colors when going to new question
                	defaultButtonTextColor();
                }
            }
            // Right to left swipe action               
            else {
                //Toast.makeText(getActivity(), "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show();
                if(x < MAX_NUM_QUESTIONS) {
                	x++;
                	// reset button colors when going to new question
                	defaultButtonTextColor();
                }
            }
            currentIndex = x - 1;
            updateImage();
            String opts = selectedTest.getQuestions().get(currentIndex).getSelectedOption();
            if(opts != null) {
            	if(opts.equals("A")) {
            		updateButtonColor(aButton);
            	}
            	if(opts.equals("B")) {
            		updateButtonColor(bButton);
            	}
            	if(opts.equals("C")) {
            		updateButtonColor(cButton);
            	}
            	if(opts.equals("D")) {
            		updateButtonColor(dButton);
            	}
            	if(opts.equals("E")) {
            		updateButtonColor(eButton);
            	}
            }
            String questionTitle = "Question " + x + "/10";
            questionText.setText(questionTitle);
            return true;
        }
    }
	
	protected void loadAndParseLocalXml() {
		Log.d(TAG, "loadAndParseLocalXml");
		XmlResourceParser configXml = getActivity().getResources().getXml(R.xml.config);
		try {
			Log.d(TAG, "Start parsing XML...");
			int eventType = configXml.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT) {
				Log.d(TAG, "In while loop...");
				if(eventType == XmlPullParser.START_DOCUMENT) {
					
				}
				if(eventType == XmlPullParser.START_TAG) {
					String element = configXml.getName();
					Log.d(TAG, "XML element is " + element);
					if(element.equals("question_text")) {
						configXml.next();
						Question q = new Question();
						q.setQuestion(configXml.getText().toString());
						Log.d(TAG, "Value of the question is " + configXml.getText());
					}
				}
				if(eventType == XmlPullParser.END_TAG) {
					
				}
				configXml.next();
				//Log.d(TAG, "Value of the element is " + configXml.getText());
				eventType = configXml.getEventType();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
