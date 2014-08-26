package com.example.beacon_dot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.BeaconManager.RangingListener;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.BeaconManager.MonitoringListener;
import com.example.beacon_dot.R.id;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.drm.DrmStore.RightsStatus;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

// to do: 자료를 저장하면서 계속 살아있는 activity 상태롤 만들어야 함.
public class StampNewActivity extends Activity {

	/*Insert here the UUID of your beacon
	 * You can find it using applications such as iBeacon Locate (on google play)
	 */
	//	ActionMode mActionMode = null;
	//	public static final String CLASS_NAME = "StampNewActivity";
	int detection1=0, detection2=0, detection3=0;
	final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
	final Region ALL_ESTIMOTE_BEACONS = new Region("CreativeRoom", ESTIMOTE_PROXIMITY_UUID, null, null);
	protected static final String TAG = "EstimoteiBeacon";
	private static final int NOTIFICATION_ID = 123;
	public int flagRegion = 0;
	BeaconManager beaconManager=null;
	NotificationManager notificationManager = null;
	// beacon part above

	private static final int REQUEST_CODE = 300;
	private String name = null;
	ArrayList<Button> btnList = new ArrayList<Button>();
	Button btnFact = null;
	int closeFlag = 0;
	boolean answered = false;

	// create layout to add buttons
	List<Fact> facts = new FactData().getFacts();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stamp_new);

		//=========== beacon manager 
		beaconManager = new BeaconManager(this);
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		//===========

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

		LinearLayout layout = (LinearLayout) findViewById(R.id.stamp_layout);


		final LinearLayout factLayout = (LinearLayout) findViewById(R.id.fact_layout);
		final TextView factQuizText = (TextView) findViewById(R.id.fact_quiz);
		final EditText factQuizEdit = (EditText) findViewById(R.id.fact_answer);
		final Button factQuizBtn = (Button) findViewById(R.id.btn_answer);

		// create button view and add on click listener for each button
		for (int i = 0; i < 6; i++) {
			final Fact fact = facts.get(i);
			final Button btnFact = new Button(this);
			btnFact.setBackgroundResource(R.drawable.button_effect);
			btnFact.setText(fact.factTitle);
			btnFact.setId(i);
			btnFact.setWidth(70);
			btnFact.setEnabled(false);
			btnList.add(btnFact);

			layout.addView(btnFact);
			LinearLayout.LayoutParams para = (LayoutParams) btnFact.getLayoutParams();
			para.setMargins(55, 5, 55, 5);
			btnFact.setLayoutParams(para);

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
					intent.putExtra("rightAnswer", fact.rightAnswer);
					startActivityForResult(intent, REQUEST_CODE);

				}

			});

		}

		//=========================
		//set scanning action for beacon manager


		beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(3), 0);

		// check bluetooth function is on or not
		if (!beaconManager.isBluetoothEnabled()) {
			alertbox("Alert", "Please, activate your bluetooth");
		}

		//add listener to beacon manager starting ranging beacons
		beaconManager.setMonitoringListener(new MonitoringListener() {

			@Override
			public void onEnteredRegion(Region arg0, List<Beacon> arg1) {
				if (flagRegion < 1) {
					Toast.makeText(getApplicationContext(), "In the zone, let's find quizzes", Toast.LENGTH_LONG).show();
				}
				//					TextView txtAccessControl = (TextView)findViewById(R.id.txtAccessControl);
				//					txtAccessControl.setText("Dear " + name + "\r\n\r\nWelcome to our store!");

				// ---range for beacons---
				try {
					beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
				} catch (RemoteException e) {
					Log.e(TAG, "Cannot start ranging", e);
				}
			}

			// ---stop ranging for beacons---
			@Override
			public void onExitedRegion(Region arg0) {
				/*Reset	this closeFlag, when enter region again and it is "IMMEDIATE" it sends again to the cloud a message*/
				flagRegion = 0; 
				Toast.makeText(getApplicationContext(), "Out of the zone, see you next time!", Toast.LENGTH_LONG).show();

				try {
					beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
				} catch (RemoteException e) {
					Log.e(TAG, e.getLocalizedMessage());
				}


			}
		});



		beaconManager.setRangingListener(new RangingListener() {

			@Override
			public void onBeaconsDiscovered(Region arg0, List<Beacon> beacons) {
				if (beacons != null && !beacons.isEmpty()) {
					Beacon beacon = beacons.get(0);
					//					if(String.valueOf(Utils.proximityFromAccuracy(Utils.computeAccuracy(beacon))) == "IMMEDIATE" && flagRegion == 0) {
					//						flagRegion++;
					//						postNotification(name + ", this beacon is notified");
					//					}

					if(String.valueOf(Utils.proximityFromAccuracy(Utils.computeAccuracy(beacon))) == "IMMEDIATE") {
						selectQuiz( beacon.getMinor() );
					}
				}
			}
		});
		//=========================
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			//			int ID = data.getIntExtra("factBtnID", 0);
			//			System.out.println("returned id "+ID);
			//			System.out.println("button "+ btnList.get(data.getIntExtra("factBtnID", 0)) + " on");

			// combine this code with beacon ranging function so that each button perform depends on certain condition
			btnList.get(data.getIntExtra("factBtnID", 0)).setEnabled(true);
			Fact fact = facts.get(data.getIntExtra("factBtnID", 0));
			fact.setRightAnswer(true);


		}

	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (closeFlag < 1) {
				Toast.makeText(this, "Push again to close this app", Toast.LENGTH_LONG).show();
				closeFlag ++;
				return true;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
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

		Intent intent = new Intent(StampNewActivity.this, RuleActivity.class);
		intent.putExtra("userName", name);
		startActivity(intent);


		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		notificationManager.cancel(NOTIFICATION_ID);
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startMonitoring(ALL_ESTIMOTE_BEACONS);
				} catch (RemoteException e) {
					Log.d(TAG, "Error while starting monitoring");
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		notificationManager.cancel(NOTIFICATION_ID);
		beaconManager.disconnect();
	}

	//method to select quiz depends on each beacon
	private void selectQuiz(int Minor){
		Intent intent = new Intent(StampNewActivity.this, FactActivity.class);
		List<Fact> facts = new FactData().getFacts();
		Fact fact = null;
		
		switch(Minor) {

		case 16770:
			// add intent and start that intent to start proper activity
			//			Toast.makeText(this, Minor + " beacon detected", Toast.LENGTH_SHORT).show();

			if (detection1 < 1) {
				fact = facts.get(0);
				intent.putExtra("factBtnID", fact.id);
				intent.putExtra("factTitle", fact.factTitle);
				intent.putExtra("factImage", fact.image);
				intent.putExtra("factDetail", fact.factDetail);
				intent.putExtra("factQuiz", fact.factQuiz);
				intent.putExtra("factQuizAnswer", fact.factQuizAnswer);
				detection1 ++;
				startActivityForResult(intent, REQUEST_CODE);
			}

			break;
		case 3801:
			// add intent and start that intent to start proper activity
			//			Toast.makeText(this, Minor + " beacon detected", Toast.LENGTH_SHORT).show();
			if (detection2 < 1) {
				fact = facts.get(1);
				intent.putExtra("factBtnID", fact.id);
				intent.putExtra("factTitle", fact.factTitle);
				intent.putExtra("factImage", fact.image);
				intent.putExtra("factDetail", fact.factDetail);
				intent.putExtra("factQuiz", fact.factQuiz);
				intent.putExtra("factQuizAnswer", fact.factQuizAnswer);
				detection2 ++;
				startActivityForResult(intent, REQUEST_CODE);
			}
			break;
		case 12968:
			// add intent and start that intent to start proper activity
			//			Toast.makeText(this, Minor + " beacon detected", Toast.LENGTH_SHORT).show();
			if (detection3 < 1) {
				fact = facts.get(2);
				intent.putExtra("factBtnID", fact.id);
				intent.putExtra("factTitle", fact.factTitle);
				intent.putExtra("factImage", fact.image);
				intent.putExtra("factDetail", fact.factDetail);
				intent.putExtra("factQuiz", fact.factQuiz);
				intent.putExtra("factQuizAnswer", fact.factQuizAnswer);
				detection3++;
				startActivityForResult(intent, REQUEST_CODE);
			}
			break;
		default:
			//consider default action if needed
		}

	}

	private void postNotification(String msg) {
		Intent notifyIntent = new Intent(this, FactActivity.class);

		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification notification = new Notification.Builder(this)
		//		.setSmallIcon(R.drawable.polo)
		.setContentTitle("Asiance Beacon").setContentText(msg)
		.setAutoCancel(true).setContentIntent(pendingIntent).build();
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notificationManager.notify(NOTIFICATION_ID, notification);
	}

	// ---helper method to determine if the app is in the foreground---
	//	public static boolean isAppInForeground(Context context) {
	//		//		Toast.makeText(context,"debug toast", Toast.LENGTH_SHORT).show();
	//		List<RunningTaskInfo> task = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
	//		if (task.isEmpty()) {
	//			return false;
	//		}
	//		return task.get(0).topActivity.getPackageName().equalsIgnoreCase(context.getPackageName());
	//	}

	//create alert box for bluetooth function check
	public void alertbox(String title, String mymessage) {
		new AlertDialog.Builder(this)
		.setMessage(mymessage)
		.setTitle(title)
		.setCancelable(true)
		.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				finish();
			}
		}).show();
	}


}
