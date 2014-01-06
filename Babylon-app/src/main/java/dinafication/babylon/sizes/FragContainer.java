package dinafication.babylon.sizes;

import java.util.ArrayList;

import dinafication.babylon.fragments.PlayFragment;
import dinafication.babylon.fragments.SettingsList;
import dinafication.babylon.types.SmallQuestion;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

public abstract class FragContainer {
	
	
	public abstract void  addFragTrans(FragmentManager fmng, Activity a);
	

	public abstract void showPlay(ArrayList<SmallQuestion> ret);
	
	abstract public String getLevel();
	
	abstract public String getLanguage();
	
	abstract public String getNLanguage();
	

	abstract public void setLevel(String s);
	
	abstract public void setLanguage(String s);
	
	abstract public void setNLanguage(String s);
	
	
	abstract public Fragment getSettFrag();
	
	abstract public PlayFragment getPlayFrag();
	
	abstract public Fragment getFragment(int i);


	abstract public void saveWord();
	
	abstract public void setOrientation();
	
	
}
