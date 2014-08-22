package com.example.beacon_dot;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

// to do: 자료를 저장하면서 계속 살아있는 activity 상태롤 만들어야 함.
public class StampNewActivity extends Activity {

	private static final int REQUEST_CODE = 300;
	private String name = null;
	ArrayList<Button> btnList = new ArrayList<Button>();
	Button btnFact = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stamp_new);

		//fix screen shape to portrait
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// handling user name
		Intent intent = getIntent();
		String loginName = intent.getStringExtra(LoginActivity.EXTRA_NAME);		
		String ruleName = intent.getStringExtra(RuleActivity.EXTRA_NAME_RULE);
		String message = null;

		if (loginName.isEmpty()) {
			name = ruleName;
			message = "Welcome, " + ruleName + " !";
		} else {
			name = loginName;
			message = "Welcome, " + loginName + " !";
		}

		TextView textView = (TextView) findViewById(R.id.stamp_welcome);
		textView.setText(message);
		
		// create layout to add buttons
		List<Fact> facts = new FactData().getFacts();
		
		TableRow row1 = (TableRow) findViewById(R.id.row1);
		TableRow row2 = (TableRow) findViewById(R.id.row2);
		TableRow row3 = (TableRow) findViewById(R.id.row3);
		
		// create button view and add on click listener for each button
		for (int i = 0; i < 6; i++) {
			final Fact fact = facts.get(i);
			Button btnFact = new Button(this);
			btnFact.setBackgroundResource(R.drawable.button_effect);
			btnFact.setText(fact.factTitle);
			btnFact.setId(i);
			btnFact.setEnabled(true);
			btnList.add(btnFact);
			
			if (i < 2) {
				row1.addView(btnFact);
			} else if (i >= 2 && i < 4){
				row2.addView(btnFact);
			} else if (i < 6) {
				row3.addView(btnFact);
			}
			
			
			btnFact.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(StampNewActivity.this, FactActivity.class);
					intent.putExtra("factBtnID", fact.id);
					intent.putExtra("factTitle", fact.factTitle);
					intent.putExtra("factImage", fact.image);
					intent.putExtra("factDetail", fact.factDetail);
					intent.putExtra("factQuiz", fact.factQuiz);
					intent.putExtra("factQuizAnswer", fact.factQuizAnswer);
					startActivityForResult(intent, REQUEST_CODE);
				}
					
			});

		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			int ID = data.getIntExtra("factBtnID", 0);
			System.out.println("returned id "+ID);
			System.out.println("button "+ btnList.get(data.getIntExtra("factBtnID", 0)) + " on");
			// combine this code with beacon ranging function so that each button perform depends on certain condition
			btnList.get(data.getIntExtra("factBtnID", 0)).setEnabled(false);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stamp_new, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//		int id = item.getItemId();
		//		if (id == R.id.home) {
		//			finish();
		//		}

		Intent intent = new Intent(StampNewActivity.this, RuleActivity.class);
		intent.putExtra("userName", name);
		startActivity(intent);


		return super.onOptionsItemSelected(item);
	}
}
