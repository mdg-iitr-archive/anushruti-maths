package in.sdslabs.co.anushruti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Result extends Activity {

	String res;
	int x, y, z, left;
	DBHandler db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle getResult = getIntent().getExtras();
		res = getResult.getString("result");
		/*
		 * x = getResult.getInt("x"); y = getResult.getInt("y"); z =
		 * getResult.getInt("z"); left = getResult.getInt("left");
		 */
		setContentView(R.layout.result);
		ListView listView = (ListView) findViewById(R.id.lvStatus);
		db = new DBHandler(this);
		//List<Question> list = db.getAllQuestions();

		int k = db.getQuestionsCount();
		
		String str[] = new String[10];
		int l = 0;
		int i;
		for (i = 1; i <= k; i++) {
			Question q = db.getQuestion(i);
			int m = q.getStatus();
			if (m == 1) {
				str[l] = "Ques: " + i + "  Correct";
				l++;
			} else if (m == 2) {
				str[l] = "Ques: " + i + "  Incorrect";
				l++;
			}
		}
		for (; i <= 10; i++) {
			str[l] = "Question No. " + i;
			l++;
		}
		db.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, str);
		listView.setAdapter(adapter);
		Button switchActivityBtn = (Button) findViewById(R.id.bSwitchActivity);
		switchActivityBtn.setText("Next Question");
		switchActivityBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animatedStartActivity();
			}
		});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		finish();
		super.onStop();
	}

	@Override
	protected void onResume() {
		// animateIn this activity
		ActivitySwitcher.animationIn(findViewById(R.id.container),
				getWindowManager());
		super.onResume();
	}

	private void animatedStartActivity() {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to
		// implement it.
		final Intent intent = new Intent(getApplicationContext(),
				TestScreen.class);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		/*
		 * Bundle setRes = new Bundle(); setRes.putString("res", res);
		 * setRes.putInt("x", x); setRes.putInt("y", y); setRes.putInt("z", z);
		 * setRes.putInt("left", left); intent.putExtras(setRes);
		 */

		ActivitySwitcher.animationOut(findViewById(R.id.container),
				getWindowManager(),
				new ActivitySwitcher.AnimationFinishedListener() {
					@Override
					public void onAnimationFinished() {
						startActivity(intent);
					}
				});
	}
}
