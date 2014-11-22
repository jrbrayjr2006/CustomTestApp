/**
 * 
 */
package com.itcert.customtestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;

/**
 * @author james_r_bray
 *
 */
public class HelpDialogFragment extends DialogFragment {

	/**
	 * 
	 */
	public HelpDialogFragment() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getActivity().getResources().getString(R.string.action_help));
		builder.setMessage(generateHelpText());
		return builder.create();
	}
	
	private String generateHelpText() {
		StringBuffer sb = new StringBuffer();
		sb.append("- Each test contains 10 questions and lasts 10 minutes\n");
		sb.append("- SWIPE right or left with your finger to navigate through the question\n");
 		sb.append("- There are 5 possible answers for each question, but ONLY one it's correct\n");
 		sb.append("- Choose the CORRECT answer by pressing one the buttons: A, B, C, D or E\n");
 		sb.append("- Once the test is ENDED you can check the solutions and the obtained score.");
		return sb.toString();
	}

}
