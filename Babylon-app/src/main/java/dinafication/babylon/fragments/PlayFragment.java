package dinafication.babylon.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dinafication.babylon.main.MainActivity;
import dinafication.babylon.main.R;
import dinafication.babylon.types.SmallQuestion;

import android.widget.Button;

public class PlayFragment extends Fragment {

	
	public View view;

	public ArrayList<SmallQuestion> ret;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.play_frag, null);
		

		MainActivity a = ((MainActivity)getActivity());
		
		a.fetchQuestions();
		ret = ((MainActivity) getActivity()).ret;		
		showNewQuestion();
		a.showScore();

		return view;
	}


	public void showNewQuestion() {
		MainActivity a = ((MainActivity)getActivity());

		if (ret == null) {
			// za svaki slucaj			
			Button b = ((Button)view.findViewById(R.id.question));
			b.setText("please select settings..");
			
			Button answ = ((Button)view.findViewById(R.id.answer));
			answ.setText("");
			//answ.setClickable(false);
		} else if (ret.size() <= a.curr) {
			// TODO Toast novi nivo
		} else {
			SmallQuestion sq = ret.get(a.curr);

			TextView q = (TextView) view.findViewById(R.id.question);
			q.setText(sq.getQ());

			TextView answ = (TextView)view.findViewById(R.id.answer);
			answ.setText("?" + getResources().getString(R.string.show) + "?");
			
			//answ.setClickable(true);
			
			a.ableEvaluation(false);

		}
	}
	

}
