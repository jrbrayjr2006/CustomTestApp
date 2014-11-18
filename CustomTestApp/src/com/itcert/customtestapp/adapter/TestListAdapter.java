/**
 * 
 */
package com.itcert.customtestapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TestObject test = getItem(position);
		
		return convertView;
	}

}
