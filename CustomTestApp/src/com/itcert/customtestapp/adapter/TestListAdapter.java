/**
 * 
 */
package com.itcert.customtestapp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itcert.customtestapp.R;
import com.itcert.customtestapp.model.TestObject;

/**
 * @author james_r_bray
 *
 */
public class TestListAdapter extends ArrayAdapter<TestObject> {
	
	private final LayoutInflater mInflater;

	public TestListAdapter(Context context, int resource) {
		super(context, resource);
		mInflater = LayoutInflater.from(context);
	}
	
	public TestListAdapter(Context context, List<TestObject> objects) {
		super(context, -1, objects);
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TestObject test = getItem(position);
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.list_row, parent, false);
		}
		
		TextView tv = (TextView)convertView.findViewById(R.id.textViewTestName);
		tv.setText(test.getTestTitle());
		
		TextView tvScore = (TextView)convertView.findViewById(R.id.textViewTestScore);
		tvScore.setText(test.getScoreText());
		
		return convertView;
	}

}
