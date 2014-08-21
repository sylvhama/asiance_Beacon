package com.example.beacon_dot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FactActivity extends Activity {
	
	private Intent intent = null;
	private String name = null;
//	private RelativeLayout layout = findViewById(id)
//	private RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_fact);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fact);
		
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		

		//get name from LoginActivity
		intent = getIntent();
//		name = intent.getStringExtra("userName");
		
		// get fact data from intent
		String factTitle = intent.getStringExtra("factTitle");
		int factImage = intent.getIntExtra("factImage", 0);
		String factDetail = intent.getStringExtra("factDetail");
		
		// input data to view
		TextView txtView = (TextView) findViewById(R.id.fact_title);
		txtView.setText(factTitle);
		
		txtView = (TextView) findViewById(R.id.fact_detail);
		txtView.setText(factDetail);
		
		ImageView imgView = (ImageView) findViewById(R.id.fact_image);
		imgView.setImageResource(factImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
