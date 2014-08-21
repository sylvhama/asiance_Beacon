package com.example.beacon_dot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RuleActivity extends Activity {

	public final static String EXTRA_NAME_RULE = "com.example.beacon_dot.NAME";	

	Intent intent = null;
	private String name = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		//get name from LoginActivity
		intent = getIntent();
		name = intent.getStringExtra("userName");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rule, menu);
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
		Intent intent = null;

		intent = new Intent(this, StampNewActivity.class);
		intent.putExtra(EXTRA_NAME_RULE, name);
		startActivity(intent);
		finish();

		return super.onOptionsItemSelected(item);
	}
}
