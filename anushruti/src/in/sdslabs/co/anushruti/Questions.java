package in.sdslabs.co.anushruti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
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

	// EditText[] et = new EditText[3];
	float cursor_x, cursor_y;
	int answer = -1;
	int digits = 2;
	int turn = 0;
	TextView[] tv = new TextView[3];
	int[] txtViewId = new int[2];
	TextView[] tvCreate = new TextView[2];
	Button done;
	GridView gridView;
	int x, y, z, left; // stores the values of the numbers generated
	int etSelect[] = new int[2]; // selects the Edit Text whose value will be
	Animation anim; // sets
	View linearLayout;
	List<Map<String, String>> numbersList = new ArrayList<Map<String, String>>();
	TextView txt;
	TextView tvSign1, tvSign2;
	float gridItems_X[] = new float[10];
	float gridItems_Y[] = new float[10];
	float scrWidth;
	float scrHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions);
		linearLayout = (RelativeLayout) findViewById(R.id.rr);
		Display display = getWindowManager().getDefaultDisplay();
		scrWidth = display.getWidth();
		scrHeight = display.getHeight();
		initializeViews();
		done.setOnClickListener(this);
		generateQuestion();

	}

	@SuppressLint("NewApi")
	private void generateQuestion() {
		// TODO Auto-generated method stub

		tv[0].setText("");
		tv[1].setText("");
		tv[2].setText("");
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
		tvCreate[turn] = new TextView(this);
		tvCreate[turn].setOnClickListener(this);
		txtViewId[turn] = tvCreate[turn].getId();

		tvCreate[turn].setText(position + "");
		tvCreate[turn].setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		((RelativeLayout) linearLayout).addView(tvCreate[turn]);

		tvCreate[turn].setVisibility(View.VISIBLE);
		cursor_x += tvCreate[turn].getTextSize();
		// anim.setAnimationListener((AnimationListener) this);
		tvCreate[turn].startAnimation(anim);
	}

	private void animate(float lx, float ly, float lcursor_x, float lcursor_y) {
		// TODO Auto-generated method stub

		anim = new TranslateAnimation(lx, lcursor_x, ly, lcursor_y);
		anim.setDuration(1000);
		anim.setFillAfter(true);
		Toast.makeText(this, "" + answer, Toast.LENGTH_LONG).show();

	}

	private HashMap<String, String> createEntry(String key, String name) {
		HashMap<String, String> entry = new HashMap<String, String>();
		entry.put(key, name);
		return entry;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.submit) {

			if (answer == z) {
				Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
				generateQuestion();
			}

			else {
				Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
				tv[left].setText("");
			}

		} else {
			int idDeleted = v.getId();
			if (idDeleted == txtViewId[0]) {
				float del_x = v.getX();
				float del_y = v.getY();
				animate(del_x, del_y, gridItems_X[answer / 10],
						gridItems_Y[answer / 10]);
				startDeleteTextAnimation(answer / 10);
				answer = answer % 10;
				if(!(turn <= 0))
					turn--;
			}
		}
	}

	private void startDeleteTextAnimation(int i) {

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
				txt.setVisibility(View.INVISIBLE);
			}
		});
		txt = new TextView(this);
		txt.setOnClickListener(this);

		txt.setText(i + "");
		txt.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		((RelativeLayout) linearLayout).addView(txt);

		txt.setVisibility(View.VISIBLE);
		cursor_x += txt.getTextSize();
		// anim.setAnimationListener((AnimationListener) this);
		txt.startAnimation(anim);
	}

}
