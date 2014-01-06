package dinafication.babylon.types;

import dinafication.babylon.main.ApplicationConstants;
import dinafication.babylon.main.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TwoBtnDialog extends DialogFragment {

	Context mContext;
	DialogInterface.OnClickListener okLsn;
	DialogInterface.OnClickListener noLsn;

	public TwoBtnDialog() {
		mContext = getActivity();
	}
	
	public void setListeners(DialogInterface.OnClickListener okLsn,
			DialogInterface.OnClickListener noLsn){
		this.okLsn = okLsn;
		this.noLsn = noLsn;
		
	}

	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		alertDialogBuilder.setTitle(R.string.Hello);
		alertDialogBuilder.setMessage(getArguments().getString(ApplicationConstants.TWO_BTNDF_MSG));
		alertDialogBuilder.setPositiveButton(getArguments().getString(ApplicationConstants.TWO_BTNDF_OK), okLsn);

		alertDialogBuilder.setNegativeButton(getArguments().getString(ApplicationConstants.TWO_BTNDF_NO), noLsn);

		Dialog d = alertDialogBuilder.create();
		
		d.setCanceledOnTouchOutside(true);
		
		return d;
	}

}
