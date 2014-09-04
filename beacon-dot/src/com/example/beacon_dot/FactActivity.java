package com.example.beacon_dot;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

public class FactActivity extends Activity {

	Integer btnID = null;
	String factAnswer;

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
		int factImage = intent.getIntExtra("factImage", 0);
		String factDetail = intent.getStringExtra("factDetail");
		String factQuiz = intent.getStringExtra("factQuiz");
		String answer = intent.getStringExtra("factQuizAnswer");
		factAnswer = answer; 
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

		ImageView imgView = (ImageView) findViewById(R.id.fact_image);
		imgView.setImageResource(factImage);



		txtView = (TextView) findViewById(R.id.fact_quiz);
		txtView.setText(factQuiz);

		final EditText editText = (EditText) findViewById(R.id.fact_answer);
		// define max input length
		editText.addTextChangedListener(new TextWatcher() {
			String previous = "";
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				previous = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
				if(editText.getLineCount() > 1) {
					editText.setText(previous);
					editText.setSelection(editText.length());
				}
			}
		});

		// put listener on the button
		Button btn = (Button) findViewById(R.id.btn_answer);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {				
				String answer = editText.getText().toString();
				if (answer.equalsIgnoreCase(factAnswer)) {
					addAnswer();
				} else {
					Toast.makeText(FactActivity.this, "Ooops, try again~", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		// make invisible if user already put right answer
		if (rightAnswer == true) {
			findViewById(R.id.fact_quiz).setVisibility(View.INVISIBLE);
			findViewById(R.id.fact_answer).setVisibility(View.INVISIBLE);
			findViewById(R.id.btn_answer).setVisibility(View.INVISIBLE);
		}
	}
	
	public void addAnswer() {
		RequestTask r = new RequestTask();
        String res = "Nothing";
        String question = Integer.toString(btnID);
        Intent intentGetter = getIntent();
		String id = intentGetter.getStringExtra("id_user");
  		try {
  			res = r.execute("addAnswer", id, question).get();
  		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			res = "InterruptedException";
			e.printStackTrace();
			Toast.makeText(FactActivity.this, res, Toast.LENGTH_SHORT).show();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			res = "ExecutionException";
			e.printStackTrace();
			Toast.makeText(FactActivity.this, res, Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent(FactActivity.this, StampNewActivity.class);
		intent.putExtra("factBtnID", btnID);
		intent.putExtra("rightAnswer", true);
		setResult(RESULT_OK, intent);
		finish();
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
}
