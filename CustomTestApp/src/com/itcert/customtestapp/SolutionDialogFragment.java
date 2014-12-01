/**
 * 
 */
package com.itcert.customtestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * @author james_r_bray
 *
 */
public class SolutionDialogFragment extends DialogFragment {
	
	private String mSolution;

	/**
	 * 
	 */
	public SolutionDialogFragment() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public SolutionDialogFragment(String _solution) {
		this.mSolution = _solution;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getActivity().getResources().getString(R.string.label_solution));
		builder.setMessage(getSolution());
		builder.setPositiveButton(getActivity().getResources().getString(R.string.close_label), null);
		return builder.create();
	}
	
	public void setSolutionText(String _solution) {
		this.mSolution = _solution;
	}
	
	public String getSolution() {
		if(mSolution == null) {
			mSolution = "";
		}
		return mSolution;
	}

}
