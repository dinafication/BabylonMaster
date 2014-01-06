package dinafication.babylon.fragments;

import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import dinafication.babylon.fragments.words.ExpandableAdapterWords;
import dinafication.babylon.main.MainActivity;
import dinafication.babylon.main.R;
import dinafication.babylon.settings.expandableList.GroupEntity;
import dinafication.babylon.settings.expandableList.GroupEntity.GroupItemEntity;
import dinafication.babylon.types.MyGroup;
import dinafication.babylon.types.MyWord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class WordsFrag extends Fragment {

	public ExpandableListView mExpandableListView;
	public ExpandableAdapterWords adapter;
	private List<GroupEntity> mGroupCollection;

	View view;

	private int lastExpandedGroupIndx = 1000;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		prepareSettingsResource();
	}
	
	@Override
	public void onInflate(Activity activity, AttributeSet attrs,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onInflate(activity, attrs, savedInstanceState);
		prepareSettingsResource();
		initSettingsPage();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

//		view = inflater.inflate(R.layout.words_frag, null);
//		initSettingsPage();
//		return view;

		//onAttach(getActivity());

		view = inflater.inflate(R.layout.words_frag, null);
		//getActivity().setContentView(R.layout.main);
		prepareSettingsResource();
		
		initSettingsPage();

		return view;
	}


	public void prepareSettingsResource() {

		mGroupCollection = new ArrayList<GroupEntity>();

		HashSet<MyGroup> s = ((MainActivity) getActivity()).getMyWords();

		GroupEntity ge = null;
		GroupItemEntity gie = null;

		Iterator<MyGroup> itr = s.iterator();
		while (itr.hasNext()) {

			MyGroup mg = itr.next();

			ge = new GroupEntity();
			
			String lng = getResources().getString(getResources().getIdentifier(mg.getLng().toUpperCase(), "string", getActivity().getPackageName()));
			String lvl = getResources().getString(getResources().getIdentifier(mg.getLvl().toUpperCase(), "string", getActivity().getPackageName()));
			ge.Name = lng + " - " + lvl;
			ge.id = mg.getId();

			ArrayList<MyWord> ws = mg.getWords();
			for (int j = 0; j < ws.size(); j++) {

				gie = ge.new GroupItemEntity();
				gie.Name = ws.get(j).getWord();
				gie.note = ws.get(j).getNote();
				gie.id = ws.get(j).getId();

				ge.GroupItemCollection.add(gie);
			}
			mGroupCollection.add(ge);
		}

	}

	public void initSettingsPage() {
		
		// ako ne postoje grupe
		if(mGroupCollection.size()==0){
			 
			TextView noWords = (TextView) view.findViewById(R.id.noWords);
			noWords.setVisibility(ExpandableListView.VISIBLE);
			//mExpandableListView.setVisibility(ExpandableListView.INVISIBLE);
			return;
		}
		
		TextView noWords = (TextView) view.findViewById(R.id.noWords);
		noWords.setVisibility(ExpandableListView.INVISIBLE);

		// za listu
		mExpandableListView = (ExpandableListView) view
				.findViewById(R.id.expandableListViewWords);
		mExpandableListView.setVisibility(ExpandableListView.VISIBLE);
		// ako se layout prv put prikazuje, treba stvoriti novi adapter, inace
		// ne
		//if (adapter == null) {
			adapter = new ExpandableAdapterWords(getActivity(),
					mExpandableListView, mGroupCollection);
		//} else {
			//adapter.setmExpandableListView(mExpandableListView);
		//}

		mExpandableListView.setAdapter(adapter);

		mExpandableListView
				.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {

						if (lastExpandedGroupIndx < 999
								&& lastExpandedGroupIndx != groupPosition) {

							mExpandableListView
									.collapseGroup(lastExpandedGroupIndx);
							adapter.groupStatus[groupPosition] = 0;
						}
						adapter.groupStatus[groupPosition] = 1;

						lastExpandedGroupIndx = groupPosition;
					}
				});

		// U biti se ne koristi, jer je view prosiren preko cijele sirine,
		// ostavljen za svaki slucaj
//		mExpandableListView
//				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//
//					@Override
//					public boolean onChildClick(ExpandableListView parent,
//							View v, int groupPosition, int childPosition,
//							long id) {
//
//						if (groupPosition == 0) {
//							((MainActivity) getActivity()).language = Mapper
//									.getLanguage(childPosition);
//						}
//
//						if (groupPosition == 1) {
//							((MainActivity) getActivity()).level = Mapper
//									.getLevel(childPosition);
//						}
//
//						mExpandableListView.collapseGroup(groupPosition);
//						return true;
//					}
//				});

	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

}
