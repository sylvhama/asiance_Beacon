package com.example.beacon_dot;

import java.util.List;

import com.example.beacon_dot.R.id;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class StampActivity extends ActionBarActivity {

	public final static String EXTRA_NAME_STAMP = "com.example.beacon_dot.NAME";
	private String welcome = "Welcome, ";
	private String game = "! Let's play game!";

	//get name from LoginActivity
	//Intent intent = null;
	private String name = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_stamp);

		RelativeLayout relativelayout = new RelativeLayout(this);
		ScrollView scroll = new ScrollView(this);
		
		
		RelativeLayout.LayoutParams relativePrams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		relativelayout.setLayoutParams(relativePrams);

		// get user name from login activity
		Intent intent = getIntent();
		String loginName = intent.getStringExtra(LoginActivity.EXTRA_NAME);
		name = loginName;
		//Intent ruleName = intent.getStringExtra(RuleActivity.EXTRA_NAME_RULE);
		String message = welcome + loginName + game;

		//create TextView and add parameters for this object
		TextView textView = new TextView(this);
		textView.setId(0);
		textView.setTextSize(30);
		textView.setText(message);
		RelativeLayout.LayoutParams textPrams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		textView.setLayoutParams(textPrams);
		relativelayout.addView(textView);

		List<Fact> facts = new FactData().getFacts();
		//		LinearLayout layout = (LinearLayout) findViewById(R.id.stamp_linearLayout);
		//		LinearLayout.LayoutParams LinearBtnPrams = new LinearLayout.LayoutParams(
		//				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


		int id=1;

		for (final Fact fact : facts) {
			// create button and change button image based on certain condition
			// create array of buttons to connect with each facts
			//Button btnFact1 = (Button) findViewById(R.id.button_fact1);
			//btnFact1.setBackgroundResource(R.drawable.button_effect);
			// to do: need condition for this part
			
			RelativeLayout.LayoutParams btnPrams = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			btnPrams.addRule(RelativeLayout.BELOW, id-1);
			btnPrams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			
			
			
			
			Button button = new Button(this);
			button.setId(id);
			button.setBackgroundResource(R.drawable.approved_stamp);
			button.setActivated(false);
			button.setLayoutParams(btnPrams);
			relativelayout.addView(button);
			


			//event listener for stamp button
			button.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(StampActivity.this, FactActivity.class);
					intent.putExtra("factTitle", fact.factTitle);
					intent.putExtra("imageSource", fact.image);
					intent.putExtra("factDetail", fact.factDetail);
					startActivity(intent);
				}
			});
			
			id ++;
		}
		scroll.addView(relativelayout);
		setContentView(scroll);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//		int id = item.getItemId();
		//		if (id == R.id.action_settings) {
		//			return true;
		//		}
		switch (item.getItemId()) {
		case R.id.action_rule:
			Intent intent = new Intent(StampActivity.this, RuleActivity.class);
			intent.putExtra(EXTRA_NAME_STAMP, name);
			startActivity(intent);
			break;
		case R.id.action_stamp:
			return true;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}