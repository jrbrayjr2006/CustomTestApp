package com.itcert.customtestapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.itcert.customtestapp.TestListFragment.OnTestSelectedListener;
import com.itcert.customtestapp.TestQuestionFragment.OnTestListener;
import com.itcert.customtestapp.model.TestObject;



public class MainActivity extends Activity implements OnTestSelectedListener, OnTestListener {
	
	private FragmentManager fragmentManager;
	private Fragment mTestListFragment;
	private TestQuestionFragment testQuestionFragment;
	private ArrayList<TestObject> testObjectList;
	//private TestObject myTest;
	
	private ArrayList<String> mTestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        
        fragmentManager = getFragmentManager();
        mTestListFragment = fragmentManager.findFragmentById(R.id.test_list_fragment);
        
        if (savedInstanceState == null) {
        	if(mTestListFragment == null) {
        		mTestListFragment = new TestListFragment();
        	}
        	fragmentManager.beginTransaction().add(R.id.fragmentContainer, mTestListFragment).commit();
        }
        
        mTestList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.tests)));
        
        // initialize tests
        testObjectList = new ArrayList<TestObject>();
        int i = 0;
        // populate test list
        for(String _testTitle : mTestList) {
        	TestObject to = new TestObject();
        	i++;
        	to.setTestTitle(_testTitle);
        	to.setIndex(i);
        	testObjectList.add(to);
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
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        if (id == R.id.action_help) {
        	//Toast.makeText(this, "app help", Toast.LENGTH_SHORT).show();
        	openHelp();
            return true;
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
		arguments.putInt("index", index);
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
	
	private void openHelp() {
		openHelpDialog();
	}

	/**
	 * 
	 */
	private void openHelpDialog() {
		//TODO Flesh this method out
    	DialogFragment dmHelp = new DialogFragment();
    	dmHelp.show(getFragmentManager(), getResources().getString(R.string.action_help));
    	
    }

}
