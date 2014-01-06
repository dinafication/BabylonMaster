package dinafication.babylon.types;

import dinafication.babylon.main.ApplicationConstants;
import dinafication.babylon.main.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class OneBtnDialog extends DialogFragment{

    Context mContext;
    public OneBtnDialog() {
        mContext = getActivity();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(getArguments().getString(ApplicationConstants.ONE_BTNDF_TIT));
        alertDialogBuilder.setMessage(getArguments().getString(ApplicationConstants.ONE_BTNDF_MSG));
        alertDialogBuilder.setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog d = alertDialogBuilder.create();
        d.setCanceledOnTouchOutside(true);
        return d;
    }
  
}
