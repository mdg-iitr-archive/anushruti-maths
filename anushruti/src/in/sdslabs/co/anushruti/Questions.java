package in.sdslabs.co.anushruti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Questions extends Activity implements OnClickListener {

	EditText[] et = new EditText[3];
	Button done;
	GridView gridView;
	int x, y, z, left; // stores the values of the numbers generated
	int etSelect[] = new int[2]; // selects the Edit Text whose value will be
									// sets
	
	List<Map<String,String>> numbersList = new ArrayList<Map<String,String>>();

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions);
		initializeViews();
		done.setOnClickListener(this);

		generateQuestion();

	}

	private void generateQuestion() {
		// TODO Auto-generated method stub
		
		et[0].setText("");
		et[1].setText("");
		et[2].setText("");
		Random a = new Random();
		Random b = new Random();
		Random c = new Random();
		x = Math.abs((a.nextInt(100)));
		y = Math.abs((b.nextInt(100)));
		etSelect[0] = (Math.abs(c.nextInt(3)));
		etSelect[1] = (etSelect[0] + 1) % 3;
		left = (etSelect[0] + 2) % 3;

		Arrays.sort(etSelect);

		if(x>y)
		{
			z = x;
			x = y;
			y = z;
		}
		et[etSelect[0]].setText(x + "");
		et[etSelect[1]].setText(y + "");

		z = y + x;
		if (etSelect[1] == 2)
			z = y - x;
	}

	private void initializeViews() {
		// TODO Auto-generated method stub
		et[0] = (EditText) findViewById(R.id.editText1);
		et[1] = (EditText) findViewById(R.id.editText2);
		et[2] = (EditText) findViewById(R.id.editText3);
		done = (Button) findViewById(R.id.submit);
		gridView = (GridView) findViewById(R.id.gridView);
		for(int i=0;i<=9;i++)
			numbersList.add(createEntry("number", "" + i));
		gridView.setAdapter(new SimpleAdapter(this,numbersList, android.R.layout.simple_list_item_1, new String[] {"number"}, new int[] {android.R.id.text1}));
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

			String input = et[left].getText().toString();
			int ans;
			if(!input.contentEquals("")) ans = Integer.parseInt(input);
			else
				ans = -1;
			if (ans == z) {
				Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
				generateQuestion();
			}

			else
			{
				Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
				et[left].setText("");
			}
				
		}
	}

}
