package dinafication.babylon.fragments.words;

import java.util.List;

import dinafication.babylon.db.DbHelper;
import dinafication.babylon.main.ApplicationConstants;
import dinafication.babylon.main.MainActivity;
import dinafication.babylon.main.R;
import dinafication.babylon.settings.expandableList.GroupEntity;
import dinafication.babylon.settings.expandableList.GroupEntity.GroupItemEntity;
import dinafication.babylon.types.MyWord;
import dinafication.babylon.types.TwoBtnDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

public class ExpandableAdapterWords extends BaseExpandableListAdapter {

	private Context mContext;
	public ExpandableListView mExpandableListView;
	private List<GroupEntity> mGroupCollection;
	public int[] groupStatus;

	public ExpandableAdapterWords(Context pContext,
			ExpandableListView pExpandableListView,
			List<GroupEntity> pGroupCollection) {
		mContext = pContext;
		mGroupCollection = pGroupCollection;
		mExpandableListView = pExpandableListView;
		groupStatus = new int[mGroupCollection.size()];

		setListEvent();
	}

	public void setmExpandableListView(ExpandableListView mExpandableListView) {
		this.mExpandableListView = mExpandableListView;
		setListEvent();
	}

	private void setListEvent() {

		mExpandableListView
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					public void onGroupExpand(int arg0) {

						// TODO Auto-generated method stub
						groupStatus[arg0] = 1;
					}
				});

		mExpandableListView
				.setOnGroupCollapseListener(new OnGroupCollapseListener() {

					public void onGroupCollapse(int arg0) {

						// TODO Auto-generated method stub
						groupStatus[arg0] = 0;
					}
				});
		mExpandableListView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				parent.collapseGroup(groupPosition);
				mExpandableListView.collapseGroup(groupPosition);
				return true;
			}
		});
	}

	public String getChild(int arg0, int arg1) {

		// TODO Auto-generated method stub
		return mGroupCollection.get(arg0).GroupItemCollection.get(arg1).Name;
	}

	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	public View getChildView(final int arg0, final int arg1, boolean arg2,
			View arg3, final ViewGroup arg4) {

		// TODO Auto-generated method stub

		final ChildHolder childHolder;
		// if (arg3 == null) {
		arg3 = LayoutInflater.from(mContext).inflate(
				R.layout.list_group_item_word, null);

		childHolder = new ChildHolder();
		GroupItemEntity gie = mGroupCollection.get(arg0).GroupItemCollection
				.get(arg1);

		childHolder.title = (TextView) arg3
				.findViewById(R.id.item_title_text_word);
		childHolder.title.setText(gie.Name);

		childHolder.note = (TextView) arg3
				.findViewById(R.id.item_title_note_word);
		childHolder.note.setText(gie.note);

		childHolder.groupItem = (LinearLayout) arg3
				.findViewById(R.id.groupItemText);

		childHolder.del = (ImageView) arg3.findViewById(R.id.del_word);

		childHolder.id = gie.id;

		// arg3.setTag(childHolder);
		// }else {
		// childHolder = (ChildHolder) arg3.getTag();
		// }

		// childHolder.title.setImageResource(ResourceMapper.getResource(arg0,
		// arg1).getChildDrawable()); //
		// Text(mGroupCollection.get(arg0).GroupItemCollection.get(arg1).Name);

		childHolder.groupItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handleOnChildClick(arg0, arg1);

			}
		});

		childHolder.title.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				handleOnChildClick(arg0, arg1);
			}
		});

		// remove child
		childHolder.del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// r u sure
				TwoBtnDialog testDialog = new TwoBtnDialog();
				Resources res = mContext.getResources();

				FragmentManager fm = ((MainActivity) mContext)
						.getSupportFragmentManager();

				Bundle b = new Bundle();
				b.putString(ApplicationConstants.TWO_BTNDF_MSG,
						res.getString(R.string.sureDelMsg));
				b.putString(ApplicationConstants.TWO_BTNDF_OK,
						res.getString(R.string.NewGamegOK));
				b.putString(ApplicationConstants.TWO_BTNDF_NO,
						res.getString(R.string.DeleteNO));

				testDialog.setArguments(b);
				testDialog.setListeners(new DialogInterface.OnClickListener() {

					// oK
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

						// TODO brisanje u bazi
						int success = 1;
						String msg = mContext.getResources().getString(
								R.string.UnsucessDel);
						GroupEntity ge = (GroupEntity) mGroupCollection
								.get(arg0);
						GroupItemEntity mw = ge.GroupItemCollection.get(arg1);
						String id = mw.id;
						success = ((MainActivity) mContext).myDbHelper
								.deleteWord(id);
						// ako je uspjesno, makni iz liste
						if (success == 1) {
							ge.GroupItemCollection.remove(arg1);

							// ako je grupa ostala prazna, maknuti ju iz
							// prikaza, jer micanje grupe nije pokriveno s
							// notify
							if (ge.GroupItemCollection.size() == 0) {
								mGroupCollection.remove(arg0);
								groupStatus = new int[mGroupCollection.size()];
								if (mGroupCollection.size() > arg0)
									mExpandableListView.collapseGroup(arg0);
							}
							notifyDataSetChanged();
							msg = mContext.getResources().getString(
									R.string.SucessDel);
						}
						// TODO toast o uspjesnosti
						Toast.makeText(mContext.getApplicationContext(), msg,
								Toast.LENGTH_SHORT).show();

					}
					// no
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				testDialog.show(fm, "del_word");

			}
		});

		return arg3;
	}

	private void handleOnChildClick(final int arg0, final int arg1) {
		mExpandableListView.collapseGroup(arg0);
		// TODO tu ce ici kupljenje podataka za edit, mislim :)
		if (arg0 == 0) {
			String s = mGroupCollection.get(arg0).GroupItemCollection.get(arg1).Name;
			// TODO ((MainActivity)mContext).language = Mapper.getLanguage(s);

		} else {
			String s = mGroupCollection.get(arg0).GroupItemCollection.get(arg1).Name;
			// TODO ((MainActivity)mContext).level = Mapper.getLevel(s);
		}

	}

	public int getChildrenCount(int arg0) {

		// TODO Auto-generated method stub
		return mGroupCollection.get(arg0).GroupItemCollection.size();
	}

	public Object getGroup(int arg0) {

		// TODO Auto-generated method stub
		return mGroupCollection.get(arg0);
	}

	public int getGroupCount() {

		// TODO Auto-generated method stub
		return mGroupCollection.size();
	}

	public long getGroupId(int arg0) {

		// TODO Auto-generated method stub
		return arg0;
	}

	public View getGroupView(final int arg0, boolean arg1, View arg2,
			ViewGroup arg3) {

		// TODO Auto-generated method stub
		GroupHolder groupHolder;
		if (arg2 == null) {
			arg2 = LayoutInflater.from(mContext).inflate(
					R.layout.list_group_word, null);
			groupHolder = new GroupHolder();
			groupHolder.img = (ImageView) arg2
					.findViewById(R.id.group_word_img);
			groupHolder.title = (TextView) arg2
					.findViewById(R.id.group_title_word_text);
			arg2.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) arg2.getTag();
		}
		if (groupStatus[arg0] == 0) {
			groupHolder.img.setImageResource(R.drawable.group_down);
		} else {
			groupHolder.img.setImageResource(R.drawable.group_up);
		}

		groupHolder.del = (ImageView) arg2.findViewById(R.id.del_group_word);
		groupHolder.del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// r u sure
				TwoBtnDialog testDialog = new TwoBtnDialog();
				Resources res = mContext.getResources();

				FragmentManager fm = ((MainActivity) mContext)
						.getSupportFragmentManager();

				Bundle b = new Bundle();
				b.putString(ApplicationConstants.TWO_BTNDF_MSG,
						res.getString(R.string.sureDelMsg));
				b.putString(ApplicationConstants.TWO_BTNDF_OK,
						res.getString(R.string.NewGamegOK));
				b.putString(ApplicationConstants.TWO_BTNDF_NO,
						res.getString(R.string.DeleteNO));

				testDialog.setArguments(b);
				testDialog.setListeners(new DialogInterface.OnClickListener() {

					// oK
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

						// TODO brisanje u bazi
						int success = 1;
						String msg = mContext.getResources().getString(
								R.string.UnsucessDel);
						GroupEntity ge = (GroupEntity) mGroupCollection
								.get(arg0);
						String id = ge.id;
						success = ((MainActivity) mContext).myDbHelper
								.deleteGroup(id);
						// ako je uspjesno, makni iz liste
						if (success == 1) {

							mGroupCollection.remove(arg0);
							groupStatus = new int[mGroupCollection.size()];
							if (mGroupCollection.size() > arg0)
								mExpandableListView.collapseGroup(arg0);

							notifyDataSetChanged();
							msg = mContext.getResources().getString(
									R.string.SucessDel);
						}
						// TODO toast o uspjesnosti
						Toast.makeText(mContext.getApplicationContext(), msg,
								Toast.LENGTH_SHORT).show();

					}
					// no
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				testDialog.show(fm, "del_group");

			}
		});

		groupHolder.title.setText(mGroupCollection.get(arg0).Name);

		return arg2;
	}

	class GroupHolder {
		ImageView img;
		TextView title;

		ImageView del;
	}

	class ChildHolder {
		LinearLayout groupItem;
		TextView title;
		TextView note;
		ImageView del;

		String id;
	}

	public boolean hasStableIds() {

		// TODO Auto-generated method stub
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {

		// TODO Auto-generated method stub
		return true;
	}

}
