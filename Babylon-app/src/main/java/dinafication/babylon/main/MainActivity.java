package dinafication.babylon.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import dinafication.babylon.db.DbHelper;
import dinafication.babylon.fragments.PlayFragment;
import dinafication.babylon.sizes.FragContainer;
import dinafication.babylon.sizes.XLarge;
import dinafication.babylon.sizes.Normal;
import dinafication.babylon.types.MyGroup;
import dinafication.babylon.types.OneBtnDialog;
import dinafication.babylon.types.SmallQuestion;
import dinafication.babylon.types.TwoBtnDialog;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;

public class MainActivity extends FragmentActivity {

	public FragContainer size;

	// db questions
	public ArrayList<SmallQuestion> ret;
	public int numAll = 0;
	public int numRight = 0;
	public DbHelper myDbHelper;

	// score
	public int curr;
	public int right;

	// fragments
	PlayFragment playFrag;

	// save
	private PopupWindow pwSaveNEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		chooseSize();
		size.addFragTrans(getSupportFragmentManager(), this);

		createDb();

		init();
	}

	public void init() {
		curr = 0;
		right = 0;
	}

	// TODO
	public void getFrags() {

		playFrag = size.getPlayFrag();
	}

	private void chooseSize() {

		int screenSize = getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK;

		switch (screenSize) {

		case Configuration.SCREENLAYOUT_SIZE_XLARGE:
			size = new XLarge(getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE));          
			break;
		case Configuration.SCREENLAYOUT_SIZE_LARGE:
			size =new XLarge(getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE));
			break;
		default:
			size = new Normal();
		}

	}

	/*
	 * database
	 */
	private void createDb() {
		try {
			myDbHelper = new DbHelper(this);
			myDbHelper.createDataBase();
			myDbHelper.openDataBase();

		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		myDbHelper.closeDataBase();
		super.onDestroy();
	}

	/*
	 * db questions
	 */
	public void fetchQuestions() {

		String lng = size.getLanguage();
		String lvl = size.getLevel();
		if (lng != null && lvl != null)
			ret = myDbHelper.getQuestions(lng, lvl, null); // TODO
	}

	// iz baze izvuce Moje rijeci
	public HashSet<MyGroup> getMyWords() {

		HashSet<MyGroup> groups = myDbHelper.getMyGroups();

		return groups;
	}

	/*
	 * action handlers
	 */

	public void btnPlayOnClck(View v) {

		String level = size.getLevel();
		String language = size.getLanguage();

		if (language == null || level == null || v == null) {
			// TODO
			FragmentManager fm = getSupportFragmentManager();

			Bundle b = new Bundle();
			b.putString(ApplicationConstants.ONE_BTNDF_MSG,
					getResources().getString(R.string.SelectMsg));
			b.putString(ApplicationConstants.ONE_BTNDF_TIT,
					getResources().getString(R.string.Hello));

			OneBtnDialog testDialog = new OneBtnDialog();
			testDialog.setArguments(b);
			testDialog.show(fm, "not_selected");

		} else {

			// fetchQuestions(language, level);
			size.showPlay(ret);
			// showNewQuestion();
		}
	}

	public void ableEvaluation(boolean b) {

		getFrags();

		((ImageButton) playFrag.view.findViewById(R.id.imgBtnTrue))
				.setClickable(b);
		((ImageButton) playFrag.view.findViewById(R.id.imgBtnFalse))
				.setClickable(b);

	}

	public void onClckShow(View view) {

		if (ret == null) {
		} else if (ret.size() <= curr) {
			// TODO Popup novi nivo
		} else {

			Button a = (Button) size.getPlayFrag().view
					.findViewById(R.id.answer);

			// ako nije prikazan odgovor, prikaz rezultata
			if (a.getText().toString().contains("?")) {
				SmallQuestion sq = ret.get(curr);
				a.setText(sq.getA());
			}
			// ako je, omoguci sejvanje rijeci
			else {
				onClckWord(a);

			}

			// a.setClickable(false);

			ableEvaluation(true);

		}
	}

	public void onClckTrue(View v) {

		curr++;
		right++;

		showScore();

		getFrags();
		playFrag.showNewQuestion();

	}

	public void showScore() {
		TextView score = (TextView) size.getPlayFrag().view
				.findViewById(R.id.score);
		score.setText(right + "/" + curr);
	}

	public void onClckFalse(View v) {

		curr++;

		showScore();
		getFrags();
		playFrag.showNewQuestion();

	}

	public void onClckNewGame(View v) {

		FragmentManager fm = getSupportFragmentManager();

		TwoBtnDialog testDialog = new TwoBtnDialog();

		Bundle b = new Bundle();
		b.putString(ApplicationConstants.TWO_BTNDF_MSG,
				getResources().getString(R.string.NewGameMsg));
		b.putString(ApplicationConstants.TWO_BTNDF_OK,
				getResources().getString(R.string.NewGamegOK));
		b.putString(ApplicationConstants.TWO_BTNDF_NO,
				getResources().getString(R.string.NewGameNO));

		testDialog.setArguments(b);
		testDialog.setListeners(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();

				init();
				showScore();
				getFrags();
				fetchQuestions();
				playFrag.ret = ret;
				playFrag.showNewQuestion();
			}
		}, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				getFrags();
				playFrag.showNewQuestion();
			}
		});
		testDialog.show(fm, "new_game");
	}

	public void onClckFinish(View v) {

		FragmentManager fm = getSupportFragmentManager();
		Bundle b = new Bundle();

		// TODO
		double eval = Double.valueOf(right) / Double.valueOf(curr);
		String msg = null;
		if (eval > 0.95 && curr < 6)
			msg = getResources().getString(R.string.FinishMsg5);
		else if (eval > 0.9)
			msg = getResources().getString(R.string.FinishMsg4);
		else if (eval > 0.7)
			msg = getResources().getString(R.string.FinishMsg3);
		else if (eval > 0.45)
			msg = getResources().getString(R.string.FinishMsg2);
		else
			msg = getResources().getString(R.string.FinishMsg1);

		b.putString("TwoBtnDialogMsg", msg);
		b.putString("TwoBtnDialogOK",
				getResources().getString(R.string.FinishMsgOK));
		b.putString("TwoBtnDialogNO",
				getResources().getString(R.string.FinishMsgNO));

		TwoBtnDialog testDialog = new TwoBtnDialog();
		testDialog.setArguments(b);
		testDialog.setListeners(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// keep playing
				dialog.dismiss();

			}
		}, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				onBackPressed();
			}
		});
		testDialog.show(fm, "finish");
	}

	public void onClckWord(View view) {

		LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.popupsave, null);
		pwSaveNEdit = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		pwSaveNEdit.setBackgroundDrawable(new BitmapDrawable());
		pwSaveNEdit.setOutsideTouchable(true);

		pwSaveNEdit.showAsDropDown(view, (int) (view.getWidth() + 1), -1);
	}

	public void onclickSave(View view) {

		// myDbHelper.openDataBase();
		String q = ((TextView) findViewById(R.id.question)).getText()
				.toString();
		String a = ((TextView) findViewById(R.id.answer)).getText().toString();
		if (a.contains("?"))
			a = "";

		int success = myDbHelper.saveWord(q, a, size.getLanguage(),
				size.getLevel());

		// myDbHelper.closeDataBase();

		dismissPopUp();

		// successful save
		if (success == 1) {
			Toast.makeText(getApplicationContext(), R.string.SuccessSave,
					Toast.LENGTH_SHORT).show();

			size.saveWord();
		}
		// unsuccess
		else if (success == -1)
			Toast.makeText(getApplicationContext(), R.string.UnsucessSave,
					Toast.LENGTH_SHORT).show();
		// already existing entry
		else
			Toast.makeText(getApplicationContext(), R.string.ExistingSave,
					Toast.LENGTH_SHORT).show();

	}

	private void dismissPopUp() {
		if (pwSaveNEdit != null)
			pwSaveNEdit.dismiss();

	}

	@Override
	public void onBackPressed() {
		if (pwSaveNEdit != null && pwSaveNEdit.isShowing() == true)
			pwSaveNEdit.dismiss();
		else {
			// jeste li sigurni da zelite izaci

			FragmentManager fm = getSupportFragmentManager();
			Bundle b = new Bundle();

			b.putString("TwoBtnDialogMsg",
					getResources().getString(R.string.Quit));
			b.putString("TwoBtnDialogOK",
					getResources().getString(R.string.FinishMsgOK));
			b.putString("TwoBtnDialogNO",
					getResources().getString(R.string.FinishMsgNO));

			TwoBtnDialog testDialog = new TwoBtnDialog();
			testDialog.setArguments(b);

			testDialog.setListeners(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					// keep playing
					dialog.dismiss();

				}
			}, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					// exit
					finish();
				}
			});
			testDialog.show(fm, "finish");
		}
	}

	public void rbLngonClck(View v) {

		String val = (String) ((RadioButton) v).getTag();
		size.setLanguage(val);
	}
	
	public void rbnLngonClck(View v) {

		String val = (String) ((RadioButton) v).getTag();
		size.setNLanguage(val);
	}

	public void rbLvlonClick(View v) {

		String val = (String) ((RadioButton) v).getTag();
		size.setLevel(val);
	}
}
