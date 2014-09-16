package com.example.beacon_dot;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FactActivity extends Activity {

	Integer btnID = null;
	String factAnswer;
	private Button btn1;
	private Button btn2;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fact);

		//fix screen shape to portrait
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//		getActionBar().setDisplayHomeAsUpEnabled(true);

		// ========= get fact data from intent
		Intent intent = getIntent();
		String factTitle = intent.getStringExtra("factTitle");
		String factDetail = intent.getStringExtra("factDetail");
		String answer = intent.getStringExtra("factAnswer");
		factAnswer = answer; 
		final String userAnswer1 = intent.getStringExtra("factQuizAnswer1");
		final String userAnswer2 = intent.getStringExtra("factQuizAnswer2");
		int factBtnID = intent.getIntExtra("factBtnID", 0);
		btnID = factBtnID;
		Boolean rightAnswer = intent.getBooleanExtra("rightAnswer", false);
		//		System.out.println("ID from stamp: "+btnID);

		// ========= create views then write data
		TextView txtView = (TextView) findViewById(R.id.fact_title);
		txtView.setText(factTitle);

		txtView = (TextView) findViewById(R.id.fact_detail);
		txtView.setMovementMethod(new ScrollingMovementMethod());
		txtView.setText(factDetail);

		// put listener on the button		
		btn1 = (Button) findViewById(R.id.btn_answer1);
		btn1.setText(userAnswer1);
		btn2 = (Button) findViewById(R.id.btn_answer2);
		btn2.setText(userAnswer2);

		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				if (userAnswer1.equalsIgnoreCase(factAnswer)) {
					addAnswer(v);
				} else {
					Toast.makeText(FactActivity.this, "Ooops, try again~", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				if (userAnswer2.equalsIgnoreCase(factAnswer)) {
					Toast.makeText(FactActivity.this, "Right!", Toast.LENGTH_SHORT).show();
					addAnswer(v);
				} else {
					Toast.makeText(FactActivity.this, "Ooops, try again!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		// make invisible if user already put right answer
		if (rightAnswer == true) {
			findViewById(R.id.btn_answer1).setVisibility(View.INVISIBLE);
			findViewById(R.id.btn_answer2).setVisibility(View.INVISIBLE);
			txtView.setText(factDetail + " " + answer);
		}
	}
	
	public void addAnswer(View v) {
		RequestTask r = new RequestTask(this);
        String question = Integer.toString(btnID);
        Intent intentGetter = getIntent();
		String id = intentGetter.getStringExtra("id_user");
		r.execute("addAnswer", id, question);		
	}

	//block back button
	//	@Override
	//	public boolean onKeyDown(int keyCode, KeyEvent event) {
	//		switch (keyCode) {
	//		case KeyEvent.KEYCODE_BACK:
	//			return true;
	//		}
	//		return super.onKeyDown(keyCode, event);
	//	}

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
	
	public void afterHttp() {
		Intent intent = new Intent(FactActivity.this, StampNewActivity.class);
		intent.putExtra("factBtnID", btnID);
		intent.putExtra("rightAnswer", true);
		setResult(RESULT_OK, intent);
		finish();
	}
}
