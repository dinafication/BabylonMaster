package dinafication.babylon.bigSettings.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dinafication.babylon.bigSettings.BigSettingsAdapter;
import dinafication.babylon.bigSettings.RowItem;
import dinafication.babylon.fragments.HelpFrag;
import dinafication.babylon.fragments.PlayFragment;
import dinafication.babylon.fragments.SettingsList;
import dinafication.babylon.fragments.WordsFrag;
import dinafication.babylon.main.ApplicationConstants;
import dinafication.babylon.main.MainActivity;
import dinafication.babylon.main.R;
import dinafication.babylon.settings.expandableList.ExpandableListAdapter;
import dinafication.babylon.settings.expandableList.GroupEntity;
import dinafication.babylon.settings.expandableList.GroupEntity.GroupItemEntity;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BigSettingsFrag extends Fragment implements OnItemClickListener {

	public static final int[] titles = new int[] { R.string.LNG, R.string.LVL,
			R.string.NLNG, R.string.PLY, R.string.WS, R.string.About,
			R.string.Hlp };

	public static final Integer[] images = { R.drawable.money3_light,
			R.drawable.user_5_light, R.drawable.user_5_light2,
			R.drawable.help_putokaz_2_light, R.drawable.user_5_light2,
			R.drawable.help_putokaz_2_light, R.drawable.money3_light };

	ListView listView;
	List<RowItem> rowItems;
	BigSettingsAdapter listAdapter;
	View view;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.sett_frag, null);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		TextView v = (TextView) getView().findViewById(R.id.TextView_HelpText);
		v.setText("settings");

		createList();
	}

	private void createList() {
		rowItems = new ArrayList<RowItem>();
		for (int i = 0; i < titles.length; i++) {
			RowItem item = new RowItem(images[i], getActivity().getResources()
					.getString(titles[i]));
			rowItems.add(item);
		}

		listView = (ListView) getActivity().findViewById(R.id.mainListView);
		BigSettingsAdapter adapter = new BigSettingsAdapter(getActivity(),
				R.layout.simple_row, rowItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		
		listView.setBackgroundColor(getResources().getColor(R.color.group_item));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Fragment f = ((MainActivity)getActivity()).size.getFragment(position);
		
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.replace(R.id.activity_main_play_frag, f, "rightScreen");
		ft.commit();
	}

}
