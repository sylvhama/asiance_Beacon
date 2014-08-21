package com.example.beacon_dot;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

// to do: 자료를 저장하면서 계속 살아있는 activity 상태롤 만들어야 함.
public class StampNewActivity extends Activity {

	private String name = null;
	private FactData factData;

//	int[] btnID = {
//			R.id.stamp1, R.id.stamp2, R.id.stamp3, R.id.stamp4, R.id.stamp5, R.id.stamp6 
//	};


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
		
		
		List<Fact> facts = new FactData().getFacts();
		LinearLayout layout1 = (LinearLayout) findViewById(R.id.row1Layout);
		LinearLayout layout2 = (LinearLayout) findViewById(R.id.row2Layout);
		LinearLayout layout3 = (LinearLayout) findViewById(R.id.row3Layout);
		
		// create button view and add on click listener for each button
		for (int i = 0; i < 2; i++) {
			final Fact fact = facts.get(i);
			Button btnFact = new Button(this);
			btnFact.setText(fact.factTitle);
			layout1.addView(btnFact);
			
			btnFact.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(StampNewActivity.this, FactActivity.class);
					intent.putExtra("factTitle", fact.factTitle);
					intent.putExtra("factImage", fact.image);
					intent.putExtra("factDetail", fact.factDetail);
					startActivity(intent);
				}
					
			});

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
