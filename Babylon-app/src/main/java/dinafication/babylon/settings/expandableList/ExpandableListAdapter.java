package dinafication.babylon.settings.expandableList;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import dinafication.babylon.main.*;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	public ExpandableListView mExpandableListView;
	private List<GroupEntity> mGroupCollection;
	public int[] groupStatus;

	private Resources res;
	private String pck;

	public String level;
	public String language;
	public String nativeL;
	
	private SharedPreferences appSettings;

	public ExpandableListAdapter(Context pContext,
			ExpandableListView pExpandableListView,
			List<GroupEntity> pGroupCollection, Resources res, String pck, SharedPreferences appSettings) {

		mContext = pContext;
		mGroupCollection = pGroupCollection;
		mExpandableListView = pExpandableListView;
		groupStatus = new int[mGroupCollection.size()];

		this.res = res;
		this.pck = pck;

		setListEvent();

		this.appSettings = appSettings;
		loadPreferences();
	}
	
	private void loadPreferences() {		 

		if (appSettings.contains(ApplicationConstants.LANGUAGE_PREFERENCES)) {
			language = appSettings.getString(ApplicationConstants.LANGUAGE_PREFERENCES, "");
		}

		if (appSettings.contains(ApplicationConstants.LEVEL_PREFERENCES)) {
			level = appSettings.getString(ApplicationConstants.LEVEL_PREFERENCES, "");
		}
		
		if (appSettings.contains(ApplicationConstants.NATIVEL_PREFERENCES)) {
			nativeL = appSettings.getString(ApplicationConstants.NATIVEL_PREFERENCES, "");
		}
	}

	public void setmExpandableListView(ExpandableListView mExpandableListView) {
		this.mExpandableListView = mExpandableListView;
		setListEvent();
	}

	private void setListEvent() {

		mExpandableListView
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					public void onGroupExpand(int arg0) {
						groupStatus[arg0] = 1;
					}
				});

		mExpandableListView
				.setOnGroupCollapseListener(new OnGroupCollapseListener() {

					public void onGroupCollapse(int arg0) {
						groupStatus[arg0] = 0;
					}
				});
		mExpandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				parent.collapseGroup(groupPosition);
				mExpandableListView.collapseGroup(groupPosition);
				return true;
			}
		});
	}

	public String getChild(int arg0, int arg1) {
		return mGroupCollection.get(arg0).GroupItemCollection.get(arg1).Name;
	}

	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	public View getChildView(final int arg0, final int arg1, boolean arg2,
			View arg3, final ViewGroup arg4) {

		final ChildHolder childHolder;
		if (arg3 == null) {
			arg3 = LayoutInflater.from(mContext).inflate(
					R.layout.list_group_item, null);

			childHolder = new ChildHolder();

			childHolder.title = (TextView) arg3
					.findViewById(R.id.item_title_text);
			childHolder.groupItem = (LinearLayout) arg3
					.findViewById(R.id.groupItemText);

			arg3.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) arg3.getTag();
		}
		String cTitle = res.getString(res.getIdentifier(
        		ResourceMapper.getRes(arg0, arg1), "string", pck));
		childHolder.title.setText(cTitle);
		childHolder.groupItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handleOnChildClick(arg0, arg1);

			}

		});

		childHolder.title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handleOnChildClick(arg0, arg1);
			}
		});

		return arg3;
	}

	private void handleOnChildClick(final int arg0, final int arg1) {
		mExpandableListView.collapseGroup(arg0);

		if (arg0 == 0){
			language = ResourceMapper.getRes(arg0, arg1);
			saveLng(language);
		}
		else if (arg0 == 2){
			nativeL = ResourceMapper.getRes(arg0, arg1);
			saveNLng(nativeL);
		}
			
		else{
			level = ResourceMapper.getRes(arg0, arg1);
			saveLvl(level);
		}
			
	}


	public int getChildrenCount(int arg0) {
		return mGroupCollection.get(arg0).GroupItemCollection.size();
	}

	public Object getGroup(int arg0) {
		return mGroupCollection.get(arg0);
	}

	public int getGroupCount() {
		return mGroupCollection.size();
	}

	public long getGroupId(int arg0) {
		return arg0;
	}

	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		GroupHolder groupHolder;
		if (arg2 == null) {
			arg2 = LayoutInflater.from(mContext).inflate(R.layout.list_group,
					null);
			groupHolder = new GroupHolder();
			groupHolder.arr = (ImageView) arg2.findViewById(R.id.arr);
			groupHolder.img = (ImageView) arg2.findViewById(R.id.group_img);
			groupHolder.title = (TextView) arg2
					.findViewById(R.id.group_title_text);
			arg2.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) arg2.getTag();
		}
		if (groupStatus[arg0] == 0) {
			groupHolder.arr.setImageResource(R.drawable.group_down);
		} else {
			groupHolder.arr.setImageResource(R.drawable.group_up);
		}
		
		if(arg0==0){
			groupHolder.img.setImageResource(R.drawable.help_putokaz_2_light);
		}
		else if(arg0==1){
			groupHolder.img.setImageResource(R.drawable.money3_light);
		}
		else{
			groupHolder.img.setImageResource(R.drawable.user_5_light);
		}
		String title = res.getString(res.getIdentifier(ResourceMapper.getParent2(arg0)
				, "string", pck));
		groupHolder.title.setText(title);

		return arg2;
	}

	class GroupHolder {
		ImageView img;
		ImageView arr;
		TextView title;
	}

	class ChildHolder {
		LinearLayout groupItem;
		TextView title;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}
	
	
	private void saveLng(String result) {
		Editor editor = appSettings.edit();
		editor.putString(ApplicationConstants.LANGUAGE_PREFERENCES, result);

		editor.commit();
	}

	private void saveNLng(String result) {
		Editor editor = appSettings.edit();
		editor.putString(ApplicationConstants.NATIVEL_PREFERENCES, result);

		editor.commit();
		
	}
	private void saveLvl(String result) {
		Editor editor = appSettings.edit();
		editor.putString(ApplicationConstants.LEVEL_PREFERENCES, result);

		editor.commit();
	}

}
