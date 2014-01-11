package in.sdslabs.co.anushruti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class Questions extends Activity implements OnClickListener {

	int TEXT_SIZE = 30;
	int Place = 0;// for doing next insert animation,eg 67 place_0--6  place_1--7
	float cursor_x, cursor_y;
	int answer = -1;
	int digits = 2;
	int turn = 0;
	TextView[] tv = new TextView[3];
	int[] txtViewId = new int[2];
	float[] tvCursor_x = new float[2];//save cursor position for a text view
	TextView[] tvCreate = new TextView[2];
	Button done;
	GridView gridView;
	int x, y, z, left; // stores the values of the numbers generated
	int etSelect[] = new int[2]; // selects the Edit Text whose value will be
	Animation anim;
	View relativeLayout;
	List<Map<String, String>> numbersList = new ArrayList<Map<String, String>>();
	TextView txt;
	TextView tvSign1, tvSign2;// for signs "+","="
	float gridItems_X[] = new float[10];//save position of the integers(0 to 9) in grid view
	float gridItems_Y[] = new float[10];
	float scrWidth;
	float scrHeight;
	int place = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions);
		relativeLayout = (RelativeLayout) findViewById(R.id.rr);
		getScreenParams();
		initializeViews();
		done.setOnClickListener(this);
		generateQuestion();

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
		tv[etSelect[0]].setText(x + "");
		tv[etSelect[1]].setText(y + "");
		//placing of text view (similar to weights)2 1 2 1 2
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
		tvCreate[Place].setId(44 + Place);// setting ids to 44(for 1st textView),45 for other
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
				animatedStartActivity("Correct");
			}

			else {
				animatedStartActivity("Incorrect");
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
					animate(del_x, del_y, gridItems_X[answer],
							gridItems_Y[answer]);
					startDeleteTextAnimation(answer);
					cursor_x -= TEXT_SIZE;
					answer = -1;
					Place = 0;//next insertion at place 0
				

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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void animatedStartActivity(String result) {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to
		// implement it.
		final Intent intent = new Intent(getApplicationContext(), Result.class);
		// disable default animation for new intent
		Bundle setResult = new Bundle();
		setResult.putString("result", result);
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
		txt = new TextView(Questions.this);
		txt.setOnClickListener(Questions.this);
		txt.setText(i + "");
		txt.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		((RelativeLayout) relativeLayout).addView(txt);
		txt.setVisibility(View.VISIBLE);
		txt.startAnimation(anim);

	}

}
