package com.itcert.customtestapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.itcert.customtestapp.TestListFragment.OnTestSelectedListener;
import com.itcert.customtestapp.TestQuestionFragment.OnTestListener;



public class MainActivity extends Activity implements OnTestSelectedListener, OnTestListener {
	
	private FragmentManager fragmentManager;
	private Fragment mTestListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onTestSelected(int index) {
		// TODO Auto-generated method stub
		ArrayList<String> testList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.tests)));
		Toast.makeText(this, "Test selected:  " + testList.get(index), Toast.LENGTH_SHORT).show();
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		
		TestQuestionFragment testQuestionFragment = new TestQuestionFragment();
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, testQuestionFragment).commit();
		setTitle(testList.get(index));
		
	}


	@Override
	public void onEndTestClick() {
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, mTestListFragment).commit();
	}

}
