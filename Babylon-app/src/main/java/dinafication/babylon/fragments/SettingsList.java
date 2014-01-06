package dinafication.babylon.fragments;

import java.util.ArrayList;
import java.util.List;

import dinafication.babylon.main.ApplicationConstants;
import dinafication.babylon.main.R;
import dinafication.babylon.main.ResourceMapper;
import dinafication.babylon.settings.expandableList.ExpandableListAdapter;
import dinafication.babylon.settings.expandableList.GroupEntity;
import dinafication.babylon.settings.expandableList.GroupEntity.GroupItemEntity;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

public class SettingsList extends Fragment{
	
	  public ExpandableListView mExpandableListView;
	    public ExpandableListAdapter adapter;
	    private List<GroupEntity> mGroupCollection;

	    View view;
	    
	    private int lastExpandedGroupIndx = 100;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
		 view = inflater.inflate(R.layout.sett_frag, null);
	       // getActivity().setContentView(R.layout.main);
	          initSettingsPage();
	          
	          return view;
	    }
	 

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState)
	    {
	        super.onActivityCreated(savedInstanceState);

	       // TextView v = (TextView) getView().findViewById(R.id.TextView_HelpText);
	        //v.setText("settings");
	    }


	    @Override
	    public void onAttach(Activity activity) {
	        // TODO Auto-generated method stub
	        super.onAttach(activity);
	       
	        prepareSettingsResource();       
	    }
    
    protected void prepareSettingsResource() {

        mGroupCollection = new ArrayList<GroupEntity>();

     // jezik za ucenje
        GroupEntity lng = new GroupEntity();
        
        GroupItemEntity eng = lng.new GroupItemEntity();
        lng.GroupItemCollection.add(eng);

        GroupItemEntity ger = lng.new GroupItemEntity();
        lng.GroupItemCollection.add(ger);

        GroupItemEntity tal = lng.new GroupItemEntity();
        lng.GroupItemCollection.add(tal);

        GroupItemEntity span = lng.new GroupItemEntity();
        lng.GroupItemCollection.add(span);

        mGroupCollection.add(lng);

        // level
        GroupEntity lvl = new GroupEntity();

        GroupItemEntity e = lvl.new GroupItemEntity();
        lvl.GroupItemCollection.add(e);

        GroupItemEntity pi = lvl.new GroupItemEntity();
        lvl.GroupItemCollection.add(pi);

        GroupItemEntity i = lvl.new GroupItemEntity();
        lvl.GroupItemCollection.add(i);

        GroupItemEntity ui = lvl.new GroupItemEntity();
        lvl.GroupItemCollection.add(ui);
        
        GroupItemEntity ad = lvl.new GroupItemEntity();
        lvl.GroupItemCollection.add(ad);

        mGroupCollection.add(lvl);
        
        // native jezik 
        GroupEntity mt = new GroupEntity();
        
        GroupItemEntity eng2 = lng.new GroupItemEntity();
        mt.GroupItemCollection.add(eng2);

        GroupItemEntity ger2 = lng.new GroupItemEntity();
        mt.GroupItemCollection.add(ger2);

        GroupItemEntity tal2 = lng.new GroupItemEntity();
        mt.GroupItemCollection.add(tal2);

        GroupItemEntity span2 = lng.new GroupItemEntity();
        mt.GroupItemCollection.add(span2);

        mGroupCollection.add(mt);

    }

    protected void initSettingsPage() {
    	

        // za listu
        mExpandableListView = (ExpandableListView) view
                .findViewById(R.id.expandableListView);

    	//neki glupi bug, ali mice ugradjeni selector
        mExpandableListView.setIndicatorBounds(0, 0);

        
        // ako se layout prvi put prikazuje, treba stvoriti novi adapter, inace ne
        if(adapter == null){
        	
        	SharedPreferences sp = getActivity().getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);
            adapter = new ExpandableListAdapter(getActivity(), mExpandableListView,
                    mGroupCollection, getResources(), getActivity().getPackageName(), sp);
        }
        else{
            adapter.setmExpandableListView(mExpandableListView);
        }
       
        mExpandableListView.setAdapter(adapter);
        

        mExpandableListView
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                	@Override
                    public void onGroupExpand(int groupPosition) {

                        if (lastExpandedGroupIndx < 5
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
        mExpandableListView
                .setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                	

                    @Override
                    public boolean onChildClick(ExpandableListView parent,
                            View v, int groupPosition, int childPosition,
                            long id) {

                        if (groupPosition == 0) {
                            //((MainActivity) getActivity()).language = Mapper
                                   //.getLanguage(childPosition);
                        }

                        if (groupPosition == 1) {
                            //((MainActivity) getActivity()).level = Mapper
                                   // .getLevel(childPosition);
                        }

                        mExpandableListView.collapseGroup(groupPosition);
                        return true;
                    }
                });

    }

  //Convert pixel to dip
    public int GetDipsFromPixel(float pixels)
    {
            // Get the screen's density scale
            final float scale = getResources().getDisplayMetrics().density;
            // Convert the dps to pixels, based on density scale
            return (int) (pixels * scale + 0.5f);
    }

}
