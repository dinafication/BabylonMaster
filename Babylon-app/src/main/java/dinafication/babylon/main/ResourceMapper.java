package dinafication.babylon.main;

import java.util.ArrayList;

import android.content.res.Resources;
import static java.util.Arrays.asList;

public class ResourceMapper {

	public static final ArrayList<String> lngs = new ArrayList<String>(asList(
			"TAL", "GER", "FRA", "SPAN"));
	
	public static final ArrayList<String> nlngs = new ArrayList<String>(asList(
			"TAL", "GER", "FRA", "SPAN"));

	public static final ArrayList<String> lvls = new ArrayList<String>(asList(
			"E", "PI", "I", "UI", "A", "P"));

	public static String getRes(int parent, int child) {

		if (parent == 0 || parent==2)
			return lngs.get(child);
		else
			return lvls.get(child);
	}
	
	
	public static String getParent(int p, String lng, String lvl, String nlng) {

		// language
		if (p == 0) {

			if (lng != null)
				return lng;
			else
				return "LNG";
		} else if (p == 2) {

			if (nlng != null)
				return nlng;
			else
				return "NLNG";
		} 
		else {
			if (lvl != null)
				return lvl;
			return "LVL";
		}
	}

	public static String getParent2(int p) {

		// language
		if (p == 0) {
				return "LNG";
		} else if (p == 2) {
				return "NLNG";
		} 
		else {
			return "LVL";
		}
	}
}