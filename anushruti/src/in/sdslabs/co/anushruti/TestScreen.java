package in.sdslabs.co.anushruti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TestScreen extends FragmentActivity implements OnClickListener {

	DBHandler db;
	static boolean correct_ans=false;
	static int trial;//no of times the user attempts the ques
	int ATTEMPTS=3;// max attempts allowed
	int TEXT_SIZE = 30;
	boolean isCorrect;
	int Place = 0;// for doing next insert animation,eg 67 place_0--6 place_1--7
	float cursor_x, cursor_y;
	int answer = -1;
	int digits = 2;
	int turn = 0;
	TextView[] tv = new TextView[3];
	int[] txtViewId = new int[2];
	float[] tvCursor_x = new float[2];// save cursor position for a text view
	TextView[] tvCreate = new TextView[2];
	Button done;
	GridView gridView;
	int x, y; // stores the values of the numbers generated
	static int z;
	int left;
	int xSaved, ySaved, zSaved;
	int posSaved[] = new int[2];
	int leftSaved;
	int etSelect[] = new int[2]; // selects the Edit Text whose value will be
	Animation anim;
	View relativeLayout;
	List<Map<String, String>> numbersList = new ArrayList<Map<String, String>>();
	TextView txt;
	TextView tvSign1, tvSign2;// for signs "+","="
	float gridItems_X[] = new float[10];// save position of the integers(0 to 9)
										// in grid view
	float gridItems_Y[] = new float[10];
	float scrWidth;
	float scrHeight;
	int place = -1;

	//showing dialog box in case of incorrect answer
		@SuppressLint("ValidFragment")
		private static	class FireMissilesDialogFragment extends DialogFragment {
				public Dialog onCreateDialog(Bundle savedInstanceState) 
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					/*if(correct_ans)
					{
						builder.setMessage(" Cogratulations! correct answer ");
						DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
						//start new ques
								final Intent intent = new Intent(getActivity(), Result.class);
								
							}
						};
						builder.setPositiveButton("Next Question",listener);
					}*/
					if(!correct_ans)
						{		
						if(trial<3)
						{
							builder.setMessage(" You missed it this time \n Wanna try again ");
							DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									//remove the previous answer
								}
							};
							builder.setPositiveButton("Retry",listener);
						}
						/*if(trial==3)
						{
							builder.setMessage(" Sorry u got it wrong \n correct answer is "+z);
							DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									//new question
								}
							};
							builder.setPositiveButton("Do Next",listener);
						}*/
					}
					return builder.create();
					
				}
			}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions);
		relativeLayout = (RelativeLayout) findViewById(R.id.rr);
		getScreenParams();
		initializeViews();
		done.setOnClickListener(this);
		db = new DBHandler(this);
		/*try {*/
			/*Bundle getBundle = getIntent().getExtras();
			String res = getBundle.getString("res");
			x = getBundle.getInt("x");
			y = getBundle.getInt("y");
			z = getBundle.getInt("z");
			left = getBundle.getInt("left");
			etSelect[0] = (left + 1) % 3;
			etSelect[1] = (left + 2) % 3;
			Arrays.sort(etSelect);*/

			/*if (res.contentEquals("Correct"))*/
				generateQuestion();
			/*else {
				tv[etSelect[0]].setText(x + "");
				tv[etSelect[1]].setText(y + "");
				tv[0].setTextSize(TEXT_SIZE);
				tv[1].setTextSize(TEXT_SIZE);
				tv[2].setTextSize(TEXT_SIZE);
				// placing of text view (similar to weights)2 1 2 1 2
				// for textview 1
				cursor_x = 0;
				cursor_y = 0;
				// for 2nd
				if (3 - etSelect[0] - etSelect[1] == 1) {
					cursor_x = scrWidth * 3;
					cursor_x /= 8;
				}
				if (3 - etSelect[0] - etSelect[1] == 2) {
					cursor_x = scrWidth * 6;
					cursor_x /= 8;
				}
				z = y + x;
				if (etSelect[1] == 2)
					z = y - x;
			}
		} catch (Exception e) {
			generateQuestion();
		}*/
		Log.e("order", "Create called");
		
	}

	private void getScreenParams() {
		// TODO Auto-generated method stub
		Display display = getWindowManager().getDefaultDisplay();
		scrWidth = display.getWidth();
		scrHeight = display.getHeight();
	}

	@SuppressLint("NewApi")
	private void generateQuestion() {
		// TODO Auto-generated method stub

		 trial=0;
		tv[0].setText("");
		tv[1].setText("");
		tv[2].setText("");
		tv[0].setTextSize(TEXT_SIZE);
		tv[1].setTextSize(TEXT_SIZE);
		tv[2].setTextSize(TEXT_SIZE);
		Random a = new Random();
		Random b = new Random();
		Random c = new Random();
		x = Math.abs((a.nextInt(100)));
		y = Math.abs((b.nextInt(100)));
		etSelect[0] = (Math.abs(c.nextInt(3)));
		etSelect[1] = (etSelect[0] + 1) % 3;
		left = (etSelect[0] + 2) % 3;

		Arrays.sort(etSelect);

		if (x > y) {
			z = x;
			x = y;
			y = z;
		}

		z = y + x;
		if (etSelect[1] == 2)
			z = y - x;
		if (z > 99) {
			y = a.nextInt(99 - x);
			z = x + y;
		}
		tv[etSelect[0]].setText(x + "");
		tv[etSelect[1]].setText(y + "");
		// placing of text view (similar to weights)2 1 2 1 2
		// for textview 1
		cursor_x = 0;
		cursor_y = 0;
		// for 2nd
		if (3 - etSelect[0] - etSelect[1] == 1) {
			cursor_x = scrWidth * 3;
			cursor_x /= 8;
		}
		if (3 - etSelect[0] - etSelect[1] == 2) {
			cursor_x = scrWidth * 6;
			cursor_x /= 8;
		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		finish();
		super.onRestart();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		/*outState.putInt("x", x);
		outState.putInt("y", y);
		outState.putInt("left", left);
		outState.putString("res", "Incorrect");*/
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	private void initializeViews() {
		// TODO Auto-generated method stub
		tv[0] = (TextView) findViewById(R.id.Text1);
		tv[1] = (TextView) findViewById(R.id.Text2);
		tv[2] = (TextView) findViewById(R.id.Text3);
		tvSign1 = (TextView) findViewById(R.id.textView2);
		tvSign2 = (TextView) findViewById(R.id.textView3);
		done = (Button) findViewById(R.id.submit);
		gridView = (GridView) findViewById(R.id.gridView);

		for (int i = 0; i <= 9; i++)
			numbersList.add(createEntry("number", "" + i));
		gridView.setAdapter(new SimpleAdapter(this, numbersList,
				android.R.layout.simple_list_item_1, new String[] { "number" },
				new int[] { android.R.id.text1 }));
		setListenerGV();
	}

	public void setListenerGV() {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				float x = gridView.getX() + v.getX();
				gridItems_X[position] = x;
				float y = gridView.getY() + v.getY();
				gridItems_Y[position] = y;

				if (turn < 2) {
					animate(x, y, cursor_x, cursor_y);
					startTextAnimation(position);
					turn++;
					if (turn == 1)
						answer = 0; // when no input answer=-1
					answer *= 10;
					answer += position;

				}
			}
		});

	}

	private void startTextAnimation(int position) {
		// TODO Auto-generated method stub
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
			}
		});
		tvCreate[Place] = new TextView(this);
		tvCreate[Place].setOnClickListener(this);
		tvCreate[Place].setId(44 + Place);// setting ids to 44(for 1st
											// textView),45 for other
		tvCursor_x[Place] = cursor_x;
		tvCreate[Place].setText(position + "");
		tvCreate[Place].setTextSize(TEXT_SIZE);
		tvCreate[Place].setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tvCreate[Place].setClickable(true);
		((RelativeLayout) relativeLayout).addView(tvCreate[Place]);
		tvCreate[Place].setVisibility(View.VISIBLE);
		cursor_x += TEXT_SIZE;
		tvCreate[Place].startAnimation(anim);
		Place++;

	}

	private void animate(float lx, float ly, float lcursor_x, float lcursor_y) {
		// TODO Auto-generated method stub

		anim = new TranslateAnimation(lx, lcursor_x, ly, lcursor_y);
		anim.setDuration(1000);
		anim.setFillAfter(true);

	}

	private HashMap<String, String> createEntry(String key, String name) {
		HashMap<String, String> entry = new HashMap<String, String>();
		entry.put(key, name);
		return entry;

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.submit) {

			if (answer == z) {
				
				int n=db.getQuestionsCount();
				Question q= new Question();
				q.setQuesNo(n+1);
				q.setStatus(1);
				db.addQues(q);
				db.close();
				animatedStartActivity("Correct");
				/*correct_ans=true;
				DialogFragment newFragment = new FireMissilesDialogFragment();
				newFragment.show(getSupportFragmentManager(),
						"missiles");*/
				
			}

			else {
				trial++;
				if(trial<3)
				{
				DialogFragment newFragment = new FireMissilesDialogFragment();
				newFragment.show(getSupportFragmentManager(),
						"missiles");
				}
					if(trial==3)
				{
					int n=db.getQuestionsCount();
					Question q= new Question();
					q.setQuesNo(n+1);
					q.setStatus(2);
					db.addQues(q);
					db.close();
					animatedStartActivity("Incorrect");
				}
				}
					

		} else {

			int id_num;
			float del_x;
			int digit;
			float del_y = cursor_y;
			int idDeleted = v.getId();
			if (idDeleted == 44) {
				id_num = 0;
				del_x = tvCursor_x[id_num];

				((ViewGroup) relativeLayout).removeView(v);
				animate(del_x, del_y, gridItems_X[answer], gridItems_Y[answer]);
				startDeleteTextAnimation(answer);
				cursor_x -= TEXT_SIZE;
				answer = -1;
				Place = 0;// next insertion at place 0

			} else if (idDeleted == 45) {
				id_num = 1;
				del_x = tvCursor_x[id_num];
				cursor_x -= TEXT_SIZE;

				((ViewGroup) relativeLayout).removeView(v);
				animate(del_x, del_y, gridItems_X[answer % 10],
						gridItems_Y[answer % 10]);

				startDeleteTextAnimation(answer % 10);
				answer /= 10;
				Place = 1;

			}
			if (!(turn <= 0))
				turn--;
		}

	}

	@Override
	protected void onResume() {
		// animateIn this activity
		ActivitySwitcher.animationIn(findViewById(R.id.rr), getWindowManager());
		super.onResume();
	}

	private void animatedStartActivity(String result) {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to
		// implement it.
		final Intent intent = new Intent(getApplicationContext(), Result.class);
		// disable default animation for new intent
		Bundle setResult = new Bundle();
		setResult.putString("result", result);
		/*setResult.putInt("x", x);
		setResult.putInt("y", y);
		setResult.putInt("z", z);
		setResult.putInt("left", left);*/
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtras(setResult);
		ActivitySwitcher.animationOut(findViewById(R.id.rr),
				getWindowManager(),
				new ActivitySwitcher.AnimationFinishedListener() {
					@Override
					public void onAnimationFinished() {
						startActivity(intent);
					}
				});
	}

	private void startDeleteTextAnimation(int i) {

		// TODO Auto-generated method stub
		anim.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				txt.clearAnimation();
				txt.setVisibility(View.GONE);

			}
		});
		txt = new TextView(TestScreen.this);
		txt.setOnClickListener(TestScreen.this);
		txt.setText(i + "");
		txt.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		((RelativeLayout) relativeLayout).addView(txt);
		txt.setVisibility(View.VISIBLE);
		txt.startAnimation(anim);

	}

}

