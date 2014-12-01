package com.itcert.customtestapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.itcert.customtestapp.TestListFragment.OnTestSelectedListener;
import com.itcert.customtestapp.TestQuestionFragment.OnTestListener;
import com.itcert.customtestapp.model.Question;
import com.itcert.customtestapp.model.TestObject;



public class MainActivity extends Activity implements OnTestSelectedListener, OnTestListener {
	
	private FragmentManager fragmentManager;
	private Fragment mTestListFragment;
	private TestQuestionFragment testQuestionFragment;
	private ArrayList<TestObject> testObjectList;
	
	private final static String TAG = "MainActivity";
	public final static String TESTS = "tests";
	public final static String TEST = "test";
	private final static int MAX_NUMBER_OF_QUESTIONS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        
        // Populate the test data from the config.xml file
        testObjectList = loadAndParseLocalXml();
        
        //TODO populate the solution text from the solutions.xml file
        loadAndParseSolutionsXml(testObjectList);
        
        fragmentManager = getFragmentManager();
        mTestListFragment = fragmentManager.findFragmentById(R.id.test_list_fragment);
        
        if (savedInstanceState == null) {
        	if(mTestListFragment == null) {
        		mTestListFragment = new TestListFragment();
        	}
        	fragmentManager.beginTransaction().add(R.id.fragmentContainer, mTestListFragment).commit();
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_help) {
        	//Toast.makeText(this, "app help", Toast.LENGTH_SHORT).show();
        	openHelp();
            return true;
        }
        if (id == R.id.action_quit) {
        	quitApp(this);
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onTestSelected(int index) {
		// TODO Auto-generated method stub
		ArrayList<String> testList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.tests)));
		//Toast.makeText(this, "Test selected:  " + testList.get(index), Toast.LENGTH_SHORT).show();
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		
		testQuestionFragment = new TestQuestionFragment();
		
		Bundle arguments = new Bundle();
		//myTest = testObjectList.get(index);
		//arguments.putCharSequence("title", myTest.getTestTitle());
		//arguments.putInt("index", myTest.getIndex());
		TestObject to = testObjectList.get(index);
		arguments.putInt("index", index);
		arguments.putSerializable(TEST, to);
		testQuestionFragment.setArguments(arguments);
		
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, testQuestionFragment).commit();
		
		setTitle(testList.get(index));
	}


	@Override
	public void onEndTestClick() {
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		if(mTestListFragment == null) {
    		mTestListFragment = new TestListFragment();
    	}
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, mTestListFragment).commit();
	}
	
	@Override
	public void onShowSoluton(String _solutionText) {
		// TODO Auto-generated method stub
		openSolutionDialog(_solutionText);
	}
	
	private void openHelp() {
		openHelpDialog();
	}
	
	private void quitApp(Activity activity) {
		activity.finish();
	}

	/**
	 * 
	 */
	private void openHelpDialog() {
		//TODO Flesh this method out
    	DialogFragment dmHelp = new HelpDialogFragment();
    	dmHelp.show(getFragmentManager(), getResources().getString(R.string.action_help));
    	
    }
	
	private void openSolutionDialog(String _solutionText) {
		DialogFragment dmSolution = new SolutionDialogFragment(_solutionText);
		dmSolution.show(getFragmentManager(), _solutionText);
	}
	
	/**
	 * <pre>
	 * Populate the list of TestObjects from data in the config.xml file
	 * </pre>
	 * @return
	 */
	private ArrayList<TestObject> loadAndParseLocalXml() {
		Log.d(TAG, "loadAndParseLocalXml");
		ArrayList<TestObject> tests = new ArrayList<TestObject>();
		XmlResourceParser configXml = getResources().getXml(R.xml.config);
		try {
			Log.d(TAG, "Start parsing XML...");
			int eventType = configXml.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT) {
				//Log.d(TAG, "In while loop...");
				if(eventType == XmlPullParser.START_DOCUMENT) {
					
				}
				if(eventType == XmlPullParser.START_TAG) {
					String element = configXml.getName();
					Log.d(TAG, "XML element is " + element);
					if(element.equals("test")) {
						TestObject to = new TestObject();
						List<Question> questions = new ArrayList<Question>();
						Log.d(TAG, "ID of the test is " + configXml.getAttributeValue(0));
						Log.d(TAG, "Title of the test is " + configXml.getAttributeValue(1));
						to.setTestID(configXml.getAttributeValue(0));
						to.setTestTitle(configXml.getAttributeValue(1));
						// iterate through questions
						for(int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) {
							configXml.next();
							if((configXml.getName() != null) && (configXml.getName().equals("question"))) {
								String _solution =  configXml.getAttributeValue(1);
								String strIndex = configXml.getAttributeValue(0);
								int _index = Integer.parseInt(strIndex);
								Question myQ = new Question();
								configXml.next();
								if(configXml.getName().equals("question_text")) {
									configXml.next();
									String _questionText = configXml.getText().toString();
									Log.d(TAG, "The question is: " + _questionText);
									configXml.next();
									configXml.next();
									configXml.next();
									String _imagePath = configXml.getText().toString();
									Log.d(TAG, "The image path is: " + _imagePath);
									configXml.next();
									configXml.next();
									configXml.next();
									String optA = configXml.getText().toString();
									configXml.next();
									configXml.next();
									configXml.next();
									String optB = configXml.getText().toString();
									configXml.next();
									configXml.next();
									configXml.next();
									String optC = configXml.getText().toString();
									configXml.next();
									configXml.next();
									configXml.next();
									String optD = configXml.getText().toString();
									configXml.next();
									configXml.next();
									configXml.next();
									String optE = configXml.getText().toString();
									Log.d(TAG, "The options are: " + optA + ", " + optB + ", " + optC + ", " + optD + ", " + optE);
									ArrayList<String> questionOptions = new ArrayList<String>();
									questionOptions.add(optA);
									questionOptions.add(optB);
									questionOptions.add(optC);
									questionOptions.add(optD);
									questionOptions.add(optE);
									
									myQ.setQuestionOptions(questionOptions);
									myQ.setQuestion(_questionText);
									myQ.setSolution(_solution); 
									myQ.setQuestionNumber(_index);
									Log.d(TAG, "The solution is: " + _solution);
									configXml.next();
									configXml.next();
								}
								questions.add(myQ);
							}
						}
						to.setQuestions(questions);
						tests.add(to);
					} 
				}
				if(eventType == XmlPullParser.END_TAG) {
					
				}
				if(eventType != XmlPullParser.END_DOCUMENT) {
					configXml.next();  //comment out if necessary
				}
				//Log.d(TAG, "Value of the element is " + configXml.getText());
				eventType = configXml.getEventType();
			}
		} catch (XmlPullParserException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		return tests;
	}
	
	/**
	 * Read the solutions XML file and populate the solutions with
	 */
	protected void loadAndParseSolutionsXml(ArrayList<TestObject> _tests) {
		Log.d(TAG, "loadAndParseSolutionsXml");
		XmlResourceParser solutionsXml = getResources().getXml(R.xml.solutions);
		//String testID = "";
		int _testIndex = 0;
		try {
			Log.d(TAG, "Start parsing XML...");
			int eventType = solutionsXml.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
					String element = solutionsXml.getName();
					Log.d(TAG, "XML element is " + element);
					if(element.equals("test")) {
						//TestObject to = new TestObject();  //TODO CHANGE THIS
						//List<Question> questions = new ArrayList<Question>();
						//testID = solutionsXml.getAttributeValue(0);
						Log.d(TAG, "ID of the test is " + solutionsXml.getAttributeValue(0));
						Log.d(TAG, "Title of the test is " + solutionsXml.getAttributeValue(1));
						/*
						for(TestObject t : _tests) {
							if(t.getTestID().equals(testID)) {
								//TODO populate test solution text
								for(Question q : t.getQuestions()) {
									q.setSolutionText("Test solution text for " + testID + " question " + q.getQuestionNumber());
								}
							}
						}
						*/
						for(int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) { 
							solutionsXml.next();
							if((solutionsXml.getName() != null) && (solutionsXml.getName().equals("question"))) {
								//String _solution =  solutionsXml.getAttributeValue(1);
								String _strIndex = solutionsXml.getAttributeValue(0);
								int _index = Integer.parseInt(_strIndex) - 1;
								
								solutionsXml.next();  // go to node after question node
								String _solutionText = solutionsXml.getText().toString();
								//TODO add logic to add solution to test objects
								testObjectList.get(_testIndex).getQuestions().get(_index).setSolutionText(_solutionText);
								Log.d(TAG, "The solution text is: " + _solutionText);
								solutionsXml.next();
							}
						}
						solutionsXml.next();
						Log.d(TAG, "The current text index is " + _testIndex);
						_testIndex++;
					}
				}
				if(eventType != XmlPullParser.END_DOCUMENT) {
					solutionsXml.next();  //comment out if necessary
				}
				eventType = solutionsXml.getEventType();
			}
		} catch (XmlPullParserException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
	}

}
