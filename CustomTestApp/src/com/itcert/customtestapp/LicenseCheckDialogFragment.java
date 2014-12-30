/**
 * 
 */
package com.itcert.customtestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

/**
 * @author james_r_bray
 *
 */
public class LicenseCheckDialogFragment extends DialogFragment {
	
	public LicenseCheckDialogFragment() {}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getActivity().getResources().getString(R.string.check_license));
		builder.setMessage(getActivity().getResources().getString(R.string.unlicensed_dialog_body));
		builder.setPositiveButton(getActivity().getResources().getString(R.string.retry_button), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				closeAppOnLicenseFailure();
				
			}});
		return builder.create();
	}
	
	/**
	 * Other code can be added to this method to perform other activities related to unlicensed apps
	 */
	private void closeAppOnLicenseFailure(){
		getActivity().finish();
	}

}
