package asiance.anniversary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.BeaconManager.MonitoringListener;
import com.estimote.sdk.BeaconManager.RangingListener;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

public class StampNewActivity extends Activity {

	/*Insert here the UUID of your beacon
	 * You can find it using applications such as iBeacon Locate (on google play)
	 */
	//	ActionMode mActionMode = null;
	//	public static final String CLASS_NAME = "StampNewActivity";
	final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
	final Region ALL_ESTIMOTE_BEACONS = new Region("CreativeRoom", ESTIMOTE_PROXIMITY_UUID, null, null);
	protected static final String TAG = "EstimoteiBeacon";
	private static final int NOTIFICATION_ID = 123;
	private static String ID_USER;
	public int flagRegion = 0;
	BeaconManager beaconManager=null;
	NotificationManager notificationManager = null;
	// beacon part above

	private static final int REQUEST_CODE = 300;
	private String name = null;
	ArrayList<Button> btnList = new ArrayList<Button>();
	Button btnFact = null;
	int closeActivityFlag = 0;

	// create layout to add buttons
	List<Fact> facts = new FactData().getFacts();
	Fact fact = new Fact();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stamp_new);

		//=========== beacon manager 
		beaconManager = new BeaconManager(this);
		//=========== beacon manager

		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// fix screen shape to portrait
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// handling user name
		Intent intent = getIntent();
		String loginName = intent.getStringExtra(LoginActivity.EXTRA_NAME);		
		ID_USER = intent.getStringExtra(LoginActivity.EXTRA_ID);	
		String ruleName = intent.getStringExtra(RuleActivity.EXTRA_NAME_RULE);
		String message = "Find one of those places!";

		TextView textView = (TextView) findViewById(R.id.stamp_welcome);
		textView.setTextSize(20);
		textView.setText(message);

		LinearLayout layout = (LinearLayout) findViewById(R.id.stamp_layout);

		// create button view and add on click listener for each button
		for (int i = 0; i < 3; i++) {
			fact = facts.get(i);
			final Button btnFact = new Button(this);
			btnFact.setBackgroundResource(R.drawable.button_asiance);
			btnFact.setText(fact.factTitle);
			btnFact.setId(i);
			btnFact.setWidth(70);
			btnFact.setEnabled(false);
			btnFact.setTextColor(Color.parseColor("white"));
			btnList.add(btnFact);

			layout.addView(btnFact);
			LinearLayout.LayoutParams para = (LayoutParams) btnFact.getLayoutParams();
			para.setMargins(55, 5, 55, 5);
			btnFact.setLayoutParams(para);

			btnFact.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// stop ranging service before activity launched
					try {
						beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					// method to create FactActivity when user push the button
					createFactActivityByButton(btnFact);
				}
			});
		}

		//========================= set scanning action for beacon manager
		beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(3), 0);

		// check bluetooth function is on or not
		if (!beaconManager.isBluetoothEnabled()) {
			alertbox("Alert", "Please, activate your bluetooth");
		}

		//add listener to beacon manager starting ranging beacons
		beaconManager.setMonitoringListener(new MonitoringListener() {

			@Override
			public void onEnteredRegion(Region arg0, List<Beacon> arg1) {
				//if (flagRegion < 1) {
					//Toast.makeText(getApplicationContext(), "In the zone, let's find quizzes", Toast.LENGTH_LONG).show();
				//}
				// by Adrian
				// TextView txtAccessControl = (TextView)findViewById(R.id.txtAccessControl);
				// txtAccessControl.setText("Dear " + name + "\r\n\r\nWelcome to our store!");

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
				/*Reset	this closeActivityFlag, when enter region again and it is "IMMEDIATE" it sends again to the cloud a message*/
				flagRegion = 0; 
				Toast.makeText(getApplicationContext(), "Out of the zone, you are too far!", Toast.LENGTH_LONG).show();
				changeColor("UNKNOWN", btnList.get(0), facts.get(0).isRightAnswer());
				changeColor("UNKNOWN", btnList.get(1), facts.get(1).isRightAnswer());
				changeColor("UNKNOWN", btnList.get(2), facts.get(2).isRightAnswer());

				//try {
					//beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
				//} catch (RemoteException e) {
					//Log.e(TAG, e.getLocalizedMessage());
				//}
			}
		});

		beaconManager.setRangingListener(new RangingListener() {

			@Override
			public void onBeaconsDiscovered(Region arg0, List<Beacon> beacons) {
				Log.i(TAG, "Ranged beacons: " + beacons);
				if (beacons != null && !beacons.isEmpty()) {
					Beacon beacon = beacons.get(0);										
					
					if(String.valueOf(Utils.proximityFromAccuracy(Utils.computeAccuracy(beacon))) == "IMMEDIATE" && flagRegion == 0) {
						flagRegion++;
						postNotification(name + ", this beacon is notified");
					}
					
					// generate LogCat message to see beacon detection status
					Log.i(TAG, beacon.getMinor()+" FactID(RangingListener): "+facts.get(0)+"-"+facts.get(0).isRightAnswer());
					Log.i(TAG, beacon.getMinor()+" FactID(RangingListener): "+facts.get(1)+"-"+facts.get(1).isRightAnswer());
					Log.i(TAG, beacon.getMinor()+" FactID(RangingListener): "+facts.get(2)+"-"+facts.get(2).isRightAnswer());
					
					//for (int i = 0; i < beacons.size(); i++) {
					//	Log.i(TAG,"XLOL" + beacons.get(i).toString() + " BTN " + btnList.get(i).getText().toString() + " DISTANCE " + Utils.proximityFromAccuracy(Utils.computeAccuracy(beacons.get(i))));
					//	changeColor(String.valueOf(Utils.proximityFromAccuracy(Utils.computeAccuracy(beacons.get(i)))), btnList.get(i), facts.get(i).isRightAnswer());
					//}	
					
					switch(beacon.getMinor()) {
					case 16770:
						changeColor(String.valueOf(Utils.proximityFromAccuracy(Utils.computeAccuracy(beacon))), btnList.get(0), facts.get(0).isRightAnswer());
						changeColor("UNKNOWN", btnList.get(1), facts.get(1).isRightAnswer());
						changeColor("UNKNOWN", btnList.get(2), facts.get(2).isRightAnswer());
						break;
					case 3801:
						changeColor(String.valueOf(Utils.proximityFromAccuracy(Utils.computeAccuracy(beacon))), btnList.get(1), facts.get(1).isRightAnswer());
						changeColor("UNKNOWN", btnList.get(0), facts.get(0).isRightAnswer());
						changeColor("UNKNOWN", btnList.get(2), facts.get(2).isRightAnswer());
						break;
					case 12968:
						changeColor(String.valueOf(Utils.proximityFromAccuracy(Utils.computeAccuracy(beacon))), btnList.get(2), facts.get(2).isRightAnswer());
						changeColor("UNKNOWN", btnList.get(0), facts.get(0).isRightAnswer());
						changeColor("UNKNOWN", btnList.get(1), facts.get(1).isRightAnswer());
						break;
//					default:
					}
					
					

					if(String.valueOf(Utils.proximityFromAccuracy(Utils.computeAccuracy(beacon))) == "IMMEDIATE") {
						try {
							beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
							selectQuiz( beacon.getMinor() );
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		//========================= set scanning action for beacon manager
	}
	
	private void changeColor(String distance, Button btn, boolean answer) {
		if (!answer) {
			if (distance == "UNKNOWN") {
				btn.setBackgroundResource(R.drawable.button_asiance);
			}
			else if (distance == "IMMEDIATE") {
				btn.setBackgroundResource(R.drawable.button_green);
			}
			else if (distance == "NEAR") {
				btn.setBackgroundResource(R.drawable.button_yellow);
			}
			else if (distance == "FAR") {
				btn.setBackgroundResource(R.drawable.button_red);
			}	
		} else {
			btn.setBackgroundResource(R.drawable.button_asiance);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

			// if user entered correct answer at FactActivity, corresponding button will be enabled 
			btnList.get(data.getIntExtra("factBtnID", 0)).setEnabled(true);
			facts.get(data.getIntExtra("factBtnID", 0)).setRightAnswer(true);
			Log.i(TAG, "FactID: "+facts.get(data.getIntExtra("factBtnID", 0))+"-"+facts.get(data.getIntExtra("factBtnID", 0)).isRightAnswer());
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (closeActivityFlag < 1) {
				Toast.makeText(this, "Push again to close this app", Toast.LENGTH_SHORT).show();
				closeActivityFlag ++;
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

	//method for binding quiz and beacon
	private void selectQuiz(int Minor ) throws RemoteException{

		//double check to stop ranging service
		beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);

		Intent intent = new Intent(StampNewActivity.this, FactActivity.class);
		
		// generate LogCat message to see rightAnswer flag has been set properly 
		Log.i(TAG, "FactID(selectQuiz): "+facts.get(0)+"-"+facts.get(0).isRightAnswer());
		Log.i(TAG, "FactID(selectQuiz): "+facts.get(1)+"-"+facts.get(1).isRightAnswer());
		Log.i(TAG, "FactID(selectQuiz): "+facts.get(2)+"-"+facts.get(2).isRightAnswer());
		
		// add extra data and start activity with intent		
		switch(Minor) {
		case 16770:
			createFactActivityByBeacon(intent,0,Minor);
			break;
		case 3801:
			createFactActivityByBeacon(intent,1,Minor);
			break;
		case 12968:
			createFactActivityByBeacon(intent,2,Minor);
			break;
//		default:
		}

	}

	private void createFactActivityByBeacon(Intent intent, int factID, int Minor) {
		//Toast.makeText(this, Minor + " beacon detected", Toast.LENGTH_SHORT).show();
		fact = facts.get(factID);
		if (fact.isRightAnswer() == false) {
			intent.putExtra("factBtnID", fact.id);
			intent.putExtra("factTitle", fact.factTitle);
			intent.putExtra("factImage", fact.image);
			intent.putExtra("factDetail", fact.factDetail);
			intent.putExtra("factAnswer", fact.answer);
			intent.putExtra("factQuizAnswer1", fact.factQuizAnswer1);
			intent.putExtra("factQuizAnswer2", fact.factQuizAnswer2);
			intent.putExtra("id_user", ID_USER);
			startActivityForResult(intent, REQUEST_CODE);
		} else {
			Toast.makeText(this, "You already have solved "+fact.factTitle, Toast.LENGTH_SHORT).show();
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

	private void createFactActivityByButton(final Button btnFact) {
		// each button's ID is the same value with their index number in facts list 
		fact = facts.get(btnFact.getId());
		btnFact.setText(fact.factTitle);
		Intent intent = new Intent(StampNewActivity.this, FactActivity.class);
		intent.putExtra("factBtnID", fact.id);
		intent.putExtra("factTitle", fact.factTitle);
		intent.putExtra("factImage", fact.image);
		intent.putExtra("factDetail", fact.factDetail);
		intent.putExtra("factAnswer", fact.answer);
		intent.putExtra("factQuizAnswer1", fact.factQuizAnswer1);
		intent.putExtra("factQuizAnswer2", fact.factQuizAnswer2);
		intent.putExtra("rightAnswer", fact.rightAnswer);
		intent.putExtra("id_user", ID_USER);
		startActivityForResult(intent, REQUEST_CODE);
	}


}
