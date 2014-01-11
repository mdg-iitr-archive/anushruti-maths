package in.sdslabs.co.anushruti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Result extends Activity {
	
	String res;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle getResult = getIntent().getExtras();
		res = getResult.getString("result");
		setContentView(R.layout.result);

		Button switchActivityBtn = (Button) findViewById(R.id.bSwitchActivity);
		switchActivityBtn.setText(res);
		switchActivityBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animatedStartActivity();
			}
		});
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
				Questions.class);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		Bundle setContent = new Bundle();
		setContent.putString("result", res);
		intent.putExtras(setContent);
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
