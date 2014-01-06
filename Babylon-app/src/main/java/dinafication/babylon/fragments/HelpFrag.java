package dinafication.babylon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dinafication.babylon.main.R;

public class HelpFrag  extends Fragment{

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.help_frag, null);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		TextView v = (TextView) getView().findViewById(R.id.helptest);
		v.setText("Heelp! I need somebody.. Help! not just anybody.. Help! I need someone..         a designer");
	}
}
