package com.example.beacon_dot;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.BeaconManager.MonitoringListener;

import android.app.Activity;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StampActivity extends Activity {

	//copy and paste from MainActivity.java
	private String userID = "Your Name";

	//List<Item> itemList;
	//DBActivity dbApp;
	//ArrayAdapter<Item> lvArrayAdapter;
	ActionMode mActionMode = null;
	public static final String CLASS_NAME = "MainActivity";

	/*Insert here the UUID of your beacon
	 * You can find it using applications such as iBeacon Locate (on google play)
	 */
	private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";

	private static final Region ALL_ESTIMOTE_BEACONS = new Region("Asiance", ESTIMOTE_PROXIMITY_UUID, null, null);

	protected static final String TAG = "EstimoteiBeacon";

	private static final int NOTIFICATION_ID = 123;

	public int flagRegion = 0;

	BeaconManager beaconManager;
	NotificationManager notificationManager;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stamp);

		// get user name from login activity
		String uName = getIntent().getStringExtra("username");
		if (!uName.equals("")) {
			userID=uName;		
		}

		//create & run background the beacon instance. update beacon signal every 10 second.
		beaconManager = new BeaconManager(this);
		beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(10), 0);

		// alert if bluetooth device is not activated
		if (!beaconManager.isBluetoothEnabled()) {
			alertbox("Alert", "Please, activate your bluetooth");
		}

		// create listener for beacons
		beaconManager.setMonitoringListener(
				new MonitoringListener() {
					@Override
					public void onEnteredRegion(Region region, List<Beacon> arg1) {
						if (isAppInForeground(getApplicationContext())) {
							Toast.makeText(getApplicationContext(), "You entered the party", Toast.LENGTH_LONG).show();

							TextView txtAccessControl = (TextView)findViewById(R.id.txtAccessControl);
							txtAccessControl.setText("Dear " + userID + "\r\n\r\nWelcome to our PARTY!");

							// ---range for beacons---
							try {
								beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
							} catch (RemoteException e) {
								Log.e(TAG, "Cannot start ranging", e);
							}
						}
					}

					@Override
					public void onExitedRegion(Region region) {
						/* Reset	this flag, when enter region again and it is "IMMEDIATE"
						 * it sends again to the cloud a message*/

						flagRegion = 0; 

						if (isAppInForeground(getApplicationContext())) {
							Toast.makeText(getApplicationContext(), "You exited the party", Toast.LENGTH_LONG).show();

							/*TextView txtAccessControl = (TextView)findViewById(R.id.txtAccessControl);
							txtAccessControl.setText("See you next time!");*/
						} 

						// ---stop ranging for beacons---
						try {
							beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
						} catch (RemoteException e) {
							Log.e(TAG, e.getLocalizedMessage());
						}
					}
				}
		);
		
		beaconManager.setRangingListener(
				new BeaconManager.RangingListener() { 
					public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
						//Log.d(TAG, "Ranged beacons: " + beacons);
						runOnUiThread(
								new Runnable() {									
									@Override
									public void run() {
										if (beacons.size() > 0) {
											Beacon beaconA = null;											
											beaconA = beacons.get(0);
											
											/*detect beacon and get minor integer number
											 * to-do: call asiance facts activity and let that activity handle displaying facts page*/ 
											if(String.valueOf(Utils.proximityFromAccuracy(Utils.computeAccuracy(beaconA))) == "IMMEDIATE"){
												displayAsianceFact( beaconA.getMinor() );
											}
										}
									}
								}
						);
					};
				}
		);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}
	
	/*set actions for each beacons
	 * to-do: put in to asiance fact activity java file and modify action*/
	private void displayAsianceFact(int Minor){

		switch(Minor) {

		case 16770:
			setAsianceFact("polo1", "SANGHON KIM JERSEY FOR LACOSTE L!VE");
			break;
		case 3801:
			setAsianceFact("polo2", "SANGHON KIM POLO FOR LACOSTE L!VE");
			break;
		case 12968:
			setAsianceFact("polo3", "SANGHON KIM TEE-SHIRT FOR LACOSTE L!VE");
			break;
		default:
			//setProduct("polo1", "SANGHON KIM JERSEY FOR LACOSTE L!VE");
		}
	}
	
	/*create view object depends on the result of selectProduct method
	 * to-do: put in to asiance fact activity java file and modify action*/ 
	private void setAsianceFact(String productPicURL, String productName){

		TextView txtAccessControl = (TextView)findViewById(R.id.txtAccessControl);
		txtAccessControl.setText(productName);

		ImageView imageView = (ImageView) findViewById(R.id.productPic);
		imageView.setImageResource(getResources().getIdentifier(productPicURL, "drawable", getPackageName()));
	}

	// ---helper method to determine if the app runs on foreground---
	public static boolean isAppInForeground(Context context) {
		List<RunningTaskInfo> task = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
		if (task.isEmpty()) {
			return false;
		}
		return task.get(0).topActivity.getPackageName().equalsIgnoreCase(context.getPackageName());
	}

	// void alert box method to create an alert
	public void alertbox(String title, String mymessage) {
		new AlertDialog.Builder(this)
		.setMessage(mymessage)
		.setTitle(title)
		.setCancelable(true)
		.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// ((AlertDialog)dialog).getButton(whichButton).setVisibility(View.INVISIBLE);
				finish();
			}
		}).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stamp, menu);
		return true;
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_stamp,
					container, false);
			return rootView;
		}
	}

}
