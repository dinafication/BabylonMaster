package dinafication.babylon.sizes;

import java.util.ArrayList;

import dinafication.babylon.bigSettings.fragments.AboutFrag;
import dinafication.babylon.bigSettings.fragments.BigSettingsFrag;
import dinafication.babylon.bigSettings.fragments.LanguageFrag;
import dinafication.babylon.bigSettings.fragments.LvlFrag;
import dinafication.babylon.bigSettings.fragments.NativeLanguageFrag;
import dinafication.babylon.fragments.HelpFrag;
import dinafication.babylon.fragments.PlayFragment;
import dinafication.babylon.fragments.SettingsList;
import dinafication.babylon.fragments.WordsFrag;
import dinafication.babylon.main.ApplicationConstants;
import dinafication.babylon.main.MainActivity;
import dinafication.babylon.main.R;
import dinafication.babylon.types.SmallQuestion;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

public class XLarge extends FragContainer {
	
	private BigSettingsFrag settFrag;
	private LanguageFrag lngFrag;
	private NativeLanguageFrag nlngFrag;
	private LvlFrag lvlFrag;
	private PlayFragment plyFrag;
	private WordsFrag wFrag;
	private AboutFrag abFrag;
	private HelpFrag hlpFrag;
	
	public String lng;
	public String lvl;
	public String nlng;
	
	private MainActivity a;
	private SharedPreferences appSettings;
	
	public XLarge( SharedPreferences appSettings) {
		this.appSettings = appSettings;
	}
	
	
	@Override
	public void addFragTrans(FragmentManager fmng, Activity a) {

		settFrag = new BigSettingsFrag();

		FragmentTransaction fragmentTransaction = fmng.beginTransaction();
		fragmentTransaction.add(R.id.activity_main_sett_frag, settFrag);
		fragmentTransaction.commit();

		
		plyFrag = new PlayFragment();

		FragmentTransaction fragmentTransaction2 = fmng.beginTransaction();
		fragmentTransaction2.add(R.id.activity_main_play_frag, plyFrag, "rightScreen");
		fragmentTransaction2.commit();
		
		this.a = (MainActivity)a;
		
	}

	@Override
	public void showPlay(ArrayList<SmallQuestion> ret) {
		
	}

	@Override
	public String getLevel() {
		
		if(lvl == null){
			if (appSettings.contains(ApplicationConstants.LEVEL_PREFERENCES)) {
				lvl = appSettings.getString(ApplicationConstants.LEVEL_PREFERENCES, "");
			}
		}
		return lvl;
	}

	@Override
	public String getLanguage() {
		
		if(lng == null){
			if (appSettings.contains(ApplicationConstants.LANGUAGE_PREFERENCES)) {
				lng = appSettings.getString(ApplicationConstants.LANGUAGE_PREFERENCES, "");
			}
		}
		return lng;
	}
	
	@Override
	public String getNLanguage() {
		if(nlng == null){
			if (appSettings.contains(ApplicationConstants.NATIVEL_PREFERENCES)) {
				nlng = appSettings.getString(ApplicationConstants.NATIVEL_PREFERENCES, "");
			}
		}
		return nlng;
	}
	
	@Override
	public Fragment getSettFrag() {// TODO
		return settFrag;
	}
	@Override
	public PlayFragment getPlayFrag() {
		return (PlayFragment) getFragment(3);
	}


	

	@Override
	public Fragment getFragment(int position) {

		Fragment f = null;
		
		// languages
		if (position == 0) {

			if(lngFrag == null) {
				lngFrag = new LanguageFrag();
			}
			f = lngFrag;
		}
		// level
		if (position == 1) {
			if(lvlFrag == null) {
				lvlFrag = new LvlFrag();
			}
			f = lvlFrag;
		}
		// native
		if (position == 2) {

			if(nlngFrag == null) {
				nlngFrag = new NativeLanguageFrag();
			}
			f = nlngFrag;
		}
		// play
		if (position == 3) {
			if(plyFrag == null) {
				plyFrag = new PlayFragment();
			}
			f = plyFrag;
		}
		// words
		if (position == 4) {

			if(wFrag == null) {
				wFrag = new WordsFrag();
			}
			f = wFrag;
		}
		// about
		if (position == 5) {

			if(abFrag == null) {
				abFrag = new AboutFrag();
			}
			f = abFrag;
		}
		// help
		if (position == 6) {
			if(hlpFrag == null) {
				hlpFrag = new HelpFrag();
			}
			f = hlpFrag;
		}
		return f;
	}

	@Override
	public void setLevel(String s) {
		lvl = s;
		Editor editor = appSettings.edit();
		editor.putString(ApplicationConstants.LEVEL_PREFERENCES, lvl);

		editor.commit();
	}

	@Override
	public void setLanguage(String s) {
		lng = s;
		Editor editor = appSettings.edit();
		editor.putString(ApplicationConstants.LANGUAGE_PREFERENCES, lng);

		editor.commit();
	}

	@Override
	public void setNLanguage(String s) {
		nlng = s;
		Editor editor = appSettings.edit();
		editor.putString(ApplicationConstants.NATIVEL_PREFERENCES, nlng);

		editor.commit();
	}


	@Override
	public void saveWord() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setOrientation() {
		//a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
		//a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
	}


	
}
