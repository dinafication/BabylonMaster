package dinafication.babylon.sizes;

import java.util.ArrayList;

import dinafication.babylon.fragments.HelpFrag;
import dinafication.babylon.fragments.PlayFragment;
import dinafication.babylon.fragments.SettingsList;
import dinafication.babylon.fragments.WordsFrag;
import dinafication.babylon.main.MainActivity;
import dinafication.babylon.main.R;
import dinafication.babylon.types.SmallQuestion;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.widget.FrameLayout;

public class Normal extends FragContainer {

	private FragmentManager fmng;
	private Activity a;

	private FragmentTabHost mTabHost;

	public static final String tab1 = "Home";
	public static final String tab2 = "Play";
	public static final String tab3 = "My words";
	public static final String tab4 = "Help";

	public final static int sdk = android.os.Build.VERSION.SDK_INT;

	@Override
	public void addFragTrans(FragmentManager fmng, Activity a) {

		this.fmng = fmng;
		this.a = a;

		createTabs();
	}

	private void createTabs() {

		mTabHost = (FragmentTabHost) a.findViewById(R.id.fragmentViewGroup);
		mTabHost.setup(a, fmng, R.id.tabFrameLayout);

		TabSpec ts1 = mTabHost.newTabSpec(tab1).setIndicator(tab1,
				a.getResources().getDrawable(R.drawable.user_5_light));
		mTabHost.addTab(ts1, SettingsList.class, new Bundle());
		mTabHost.addTab(
				mTabHost.newTabSpec(tab2).setIndicator(tab2,
						a.getResources().getDrawable(R.drawable.money3_light)),
				PlayFragment.class, null);

		mTabHost.addTab(
				mTabHost.newTabSpec(tab3).setIndicator(tab3,
						a.getResources().getDrawable(R.drawable.user_5_light2)),
				WordsFrag.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec(tab4).setIndicator(
						tab4,
						a.getResources().getDrawable(
								R.drawable.help_putokaz_2_light)),
				HelpFrag.class, null);

		final GradientDrawable gd_selected = new GradientDrawable(
				GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
						a.getResources().getInteger(
								R.integer.selected_tab_color1),
						a.getResources().getInteger(
								R.integer.selected_tab_color2) });// 0188CC
																	// 0B2DD6
		gd_selected.setCornerRadius(0f);

		final Drawable gd = mTabHost.getTabWidget().getChildAt(1)
				.getBackground();
		final Drawable gd_unselected = ((DrawableContainer) gd).getCurrent();
		
		// 
//		final GradientDrawable gd_unselected = new GradientDrawable(
//				GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
//						a.getResources().getInteger(R.integer.unselected_tab_color1),
//						a.getResources().getInteger(R.integer.unselected_tab_color2) });
//		gd_unselected.setStroke(1, 0xFF424242, 10, 0);

		addDrawableToLay(mTabHost.getTabWidget().getChildAt(0), gd_selected);

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String str) {

				// ako je odabran play
				if (tab2.equals(str)) {
					// a nisu postavljeni settingsi
					if (getLanguage() == null || getLevel() == null) {
						((MainActivity) a).btnPlayOnClck(null);

						mTabHost.setCurrentTabByTag(tab1);
						return;
					}
				}

				View tab = mTabHost.getTabWidget().getChildAt(
						mTabHost.getCurrentTab());

				addDrawableToLay(tab, gd_selected);

				// yahradcodirano, jer get children premalo!
				for (int i = 0; i < 4; i++) {
					if (i == mTabHost.getCurrentTab())
						continue;

					tab = mTabHost.getTabWidget().getChildAt(i);
					addDrawableToLay(tab, gd_unselected);
				}

			}
		});
	}

	@SuppressLint("NewApi")
	private void addDrawableToLay(View tab, Drawable d) {
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			tab.setBackgroundDrawable(d);
		} else {
			tab.setBackground(d);
		}
	}

	@Override
	public void showPlay(ArrayList<SmallQuestion> ret) {
		
		
		String lng = getLanguage();
		String lvl = getLevel();
		if (lng == null || lvl == null) {
			((MainActivity) a).btnPlayOnClck(null);
		} else{

			//((MainActivity) a).fetchQuestions();
			mTabHost.setCurrentTabByTag(tab2);
			//((MainActivity) a).showNewQuestion();
		}
	}

	@Override
	public SettingsList getSettFrag() {

		Fragment f = fmng.findFragmentByTag(tab1);
		return (SettingsList) f;
	}

	@Override
	public PlayFragment getPlayFrag() {
		Fragment f = fmng.findFragmentByTag(tab2);
		return (PlayFragment) f;
	}

	@Override
	public String getLevel() {

		Fragment f = fmng.findFragmentByTag(tab1);
		if (f != null && (f instanceof SettingsList)) {
			return ((SettingsList) f).adapter.level;
		}
		return null;
	}

	@Override
	public String getLanguage() {
		Fragment f = fmng.findFragmentByTag(tab1);
		if (f != null && (f instanceof SettingsList)) {
			return ((SettingsList) f).adapter.language;
		}
		return null;
	}


	@Override
	public String getNLanguage() {
		Fragment f = fmng.findFragmentByTag(tab1);
		if (f != null && (f instanceof SettingsList)) {
			return ((SettingsList) f).adapter.nativeL;
		}
		return null;
	}

	@Override
	public Fragment getFragment(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLevel(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLanguage(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNLanguage(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveWord() {
		WordsFrag f = (WordsFrag) fmng.findFragmentByTag(tab3);
		if(f!=null){
			f.prepareSettingsResource();
			f.initSettingsPage();
			f.adapter.notifyDataSetChanged();
		}		
	}

	@Override
	public void setOrientation() {

		//a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
	}

}
