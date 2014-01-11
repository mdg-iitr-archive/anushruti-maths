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

	// EditText[] et = new EditText[3];
	int TEXT_SIZE=30;
	int Place = 0;// next insert animation
	float cursor_x, cursor_y;
	int answer = -1;
	int digits = 2;
	int turn = 0;
	TextView[] tv = new TextView[3];
	int[] txtViewId = new int[2];
	float[] tvCursor_x = new float[2];
	TextView[] tvCreate = new TextView[2];
	Button done;
	GridView gridView;
	int x, y, z, left; // stores the values of the numbers generated
	int etSelect[] = new int[2]; // selects the Edit Text whose value will be
	Animation anim; 
	View linearLayout;
	List<Map<String, String>> numbersList = new ArrayList<Map<String, String>>();
	TextView txt;
	TextView tvSign1, tvSign2;
	float gridItems_X[] = new float[10];
	float gridItems_Y[] = new float[10];
	float scrWidth;
	float scrHeight;
	int place = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions);
		linearLayout = (RelativeLayout) findViewById(R.id.rr);
		Display display = getWindowManager().getDefaultDisplay();
		scrWidth = display.getWidth();
		// scrHeight = display.getHeight();
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
				// Toast.makeText(Questions.this, "" + answer,
				// Toast.LENGTH_LONG)
				// .show();
				// tvCreate[Place-1].setClickable(true);
			}
		});
		tvCreate[Place] = new TextView(this);
		tvCreate[Place].setOnClickListener(this);
		// txtViewId[turn] = tvCreate[turn].getId();
		tvCreate[Place].setId(44 + Place);
		tvCursor_x[Place] = cursor_x;

		tvCreate[Place].setText(position + "");
		int j=44+Place;
		tvCreate[Place].setTextSize(TEXT_SIZE);
		/*Toast.makeText(Questions.this, "id inserted: " + j,
				Toast.LENGTH_LONG).show();*/
		tvCreate[Place].setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tvCreate[Place].setClickable(true);
		// tvCreate[Place].setFocusableInTouchMode(false);
		((RelativeLayout) linearLayout).addView(tvCreate[Place]);
		// tvCreate[Place].setWidth(50);
		tvCreate[Place].setVisibility(View.VISIBLE);
		//cursor_x += tvCreate[Place].getTextSize();
		cursor_x += TEXT_SIZE;
		// anim.setAnimationListener((AnimationListener) this);
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
				Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
				generateQuestion();
			}

			else {
				Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
				tv[left].setText("");
			}

		} else {
			/*Toast.makeText(Questions.this, "id clicked  " + v.getId(),
					Toast.LENGTH_LONG).show();
			Toast.makeText(Questions.this, "  tvCreate[0]=" +tvCreate[0].getId()+"\n tvCreate[1]="+tvCreate[1].getId(),
					Toast.LENGTH_LONG).show();*/
			
			int id_num;
			float del_x;
			int digit;
			float del_y = cursor_y;
			int idDeleted = v.getId();
			if (idDeleted == 44) {
				id_num = 0;
				/*Toast.makeText(Questions.this, "id deleted: " + idDeleted,
						Toast.LENGTH_LONG).show();*/
				del_x = tvCursor_x[id_num];
				if (answer / 10 == 0)// single digit like 5_
				{
					((ViewGroup) linearLayout).removeView(v);
					animate(del_x, del_y, gridItems_X[answer],
							gridItems_Y[answer]);
					/*new Handler().postDelayed(new Runnable() {
					    public void run() {
					        v.clearAnimation();
					        //Extra work goes here
					        txt.setVisibility(View.GONE);
					    }
					}, anim.getDuration());*/
					startDeleteTextAnimation(answer);
					 
					// cursor repositioning ... no text shift required
					/*txt = new TextView(this);
					txt.setText(answer + "");*/
					cursor_x -= TEXT_SIZE;

					answer = -1;
					Place = 0;
				}

			} else if (idDeleted == 45) {
				id_num = 1;
				del_x = tvCursor_x[id_num];
				/*Toast.makeText(Questions.this, "id deleted: " + idDeleted,
						Toast.LENGTH_LONG).show();*/
				/*
				 * if(answer/10==0)// single digit like _6 { animate(del_x,
				 * del_y, gridItems_X[answer], gridItems_Y[answer]);
				 * startDeleteTextAnimation(answer); answer = -1; } else//two
				 * digit eg 54
				 */
					cursor_x -= TEXT_SIZE;
					
					((ViewGroup) linearLayout).removeView(v);
					animate(del_x, del_y, gridItems_X[answer % 10],
							gridItems_Y[answer % 10]);
					/*new Handler().postDelayed(new Runnable() {
					    public void run() {
					        v.clearAnimation();
					        //Extra work goes here
					        txt.setVisibility(View.GONE);
					    }
					}, anim.getDuration());*/
					
					startDeleteTextAnimation(answer % 10);
					answer /= 10;
					Place = 0;
				
			}
			if (!(turn <= 0))
				turn--;
		}

	}


	private class myTextView extends TextView{

		
		public myTextView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		
		@Override
		protected void onAnimationEnd() {
			// TODO Auto-generated method stub
			super.onAnimationEnd();
			setVisibility(View.GONE);
		}
		
		
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
		//myTextView txt;
		txt = new TextView(Questions.this);
		txt.setOnClickListener(Questions.this);

		txt.setText(i + "");
		txt.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		((RelativeLayout) linearLayout).addView(txt);

		txt.setVisibility(View.VISIBLE);

		
		// cursor_x -= txt.getTextSize();
		// anim.setAnimationListener((AnimationListener) this);
		txt.startAnimation(anim);

		
	
		
		
	}

}
