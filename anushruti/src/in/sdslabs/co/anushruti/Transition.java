package in.sdslabs.co.anushruti;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Transition extends Activity implements OnClickListener{

	private static final String LOG_TAG = "debugger";
	Button btn;
	private float xCurrentPos, yCurrentPos;
	//private TextView txt;
	View linearLayout;
	int i=60;
	int x=50,y=50;
	TextView txt,txt2;
	Animation anim;
	//LinearLayout linearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transition);
		linearLayout=(LinearLayout)findViewById(R.id.info);
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);

		anim= new TranslateAnimation(50, 100,50,100); 
		anim.setDuration(1000); 
		anim.setFillAfter(true); 
		
		//txt = (TextView) findViewById(R.id.textView1); 
	
		//xCurrentPos = txt.getLeft(); 
		//yCurrentPos = txt.getTop(); 
		//txt.setVisibility(View.GONE);

	}

	

	@Override
	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		//anim.setFillEnabled(true); 
		anim.setAnimationListener(new AnimationListener() {

		    @Override
		    public void onAnimationStart(Animation arg0) {}

		    @Override
		    public void onAnimationRepeat(Animation arg0) {}

		    @Override
		    public void onAnimationEnd(Animation arg0) {
		    	
		    }
		});
		txt= new TextView (Transition.this);
		txt.setText(i+"");
		 txt.setLayoutParams(new LayoutParams(
		            LayoutParams.MATCH_PARENT,
		            LayoutParams.WRAP_CONTENT));

		    ((LinearLayout)linearLayout).addView(txt);
		
		txt.setVisibility(View.VISIBLE);
		// anim.setAnimationListener((AnimationListener) this);
		txt.startAnimation(anim);
		
				
		
	}

	
}
