package com.cs442_team1.activityplus;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint({ "NewApi", "SdCardPath" })
public class AddDataInCategory extends Activity implements LocationListener {

	private LocationManager locationManager = null;
	private String provider;

	double lat;
	double lng;
	EditText place;
	Cursor c;
	double Total = 0;
	double limit = 0;
	final Context context = this;

	static String COST = "Cost";
	static String SAMPLE_DB_NAME = "firstdatabase1";
	static String SAMPLE_TABLE_NAME = "cattable";

	LimitDB lm = new LimitDB(this);

	private static boolean doesDatabaseExist(ContextWrapper context,
			String dbname) {
		File dbFile = context.getDatabasePath(dbname);
		return dbFile.exists();
	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@SuppressLint("NewApi")
	protected void onCreate(Bundle addData) {
		super.onCreate(addData);
		setContentView(R.layout.adddataincategory);

		if (doesDatabaseExist(getApplication(), SAMPLE_DB_NAME)) {
			SQLiteDatabase myDb = openOrCreateDatabase(SAMPLE_DB_NAME,
					Context.MODE_PRIVATE, null);
			c = myDb.rawQuery("SELECT " + COST + " FROM " + SAMPLE_TABLE_NAME,
					null);

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						Total = Total + c.getDouble(c.getColumnIndex("Cost"));

					} while (c.moveToNext());
				}
			}

			Log.d("total", "" + Total);
		}

		if (doesDatabaseExist(getApplication(), LimitDB.DATABASE_NAME)) {
			c = lm.getAll();
			c.moveToFirst();
			limit = c.getDouble(c.getColumnIndex("limits"));
		}

		Log.d("limit", "" + limit);

		Button getDate = (Button) findViewById(R.id.getDate);
		Button getPlace = (Button) findViewById(R.id.getPlace);
		Button getTime = (Button) findViewById(R.id.getTime);
		Button enterDetails = (Button) findViewById(R.id.enterDetails);
		Button getAllDetails = (Button) findViewById(R.id.getAllDetails);

		final EditText date = (EditText) findViewById(R.id.date);
		place = (EditText) findViewById(R.id.place);
		final EditText time = (EditText) findViewById(R.id.time);
		final EditText cost = (EditText) findViewById(R.id.cost);

		final TextView getCategoryName = (TextView) findViewById(R.id.getCategoryName);

		Intent getName = getIntent();
		final String selectedCategory = getName.getExtras().getString(
				"selectedcategory");

		getCategoryName.setText(selectedCategory);

		getDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String currentDateString = DateFormat.getDateInstance().format(
						new Date());

				// textView is the TextView view that should display it
				date.setText(currentDateString);

			}
		});

		getTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String currentTimeString = DateFormat.getTimeInstance().format(
						new Date());

				time.setText(currentTimeString);

			}
		});

		getPlace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				// Define the criteria how to select the locatioin provider ->
				// use
				// default
				// Criteria criteria = new Criteria();
				// provider = locationManager.getBestProvider(criteria, false);
				// Location location =
				// locationManager.getLastKnownLocation(provider);
				provider = LocationManager.NETWORK_PROVIDER;
				Location location = locationManager
						.getLastKnownLocation(provider);

				if (location != null) {
					System.out.println("Provider " + provider
							+ " has been selected.");
					onLocationChanged(location);

				} else {

					Toast.makeText(getApplication(), "Location not avilable",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		enterDetails.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (date.getText().toString().isEmpty()
						|| place.getText().toString().isEmpty()
						|| time.getText().toString().isEmpty()
						|| cost.getText().toString().isEmpty()
						|| !(isDouble(cost.getText().toString()))) {
					if (date.getText().toString().isEmpty()
							|| place.getText().toString().isEmpty()
							|| time.getText().toString().isEmpty()
							|| cost.getText().toString().isEmpty()) {
						Toast.makeText(getBaseContext(),
								"All the fields are required",
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(getBaseContext(), "Invalid cost value",
								Toast.LENGTH_SHORT).show();
					}
				}

				else if (doesDatabaseExist(getApplication(),
						LimitDB.DATABASE_NAME)
						&& Double.parseDouble(cost.getText().toString())
								+ Total > limit) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);

					// set title
					// alertDialogBuilder.setTitle("You have exceeded the limit");

					// set dialog message
					alertDialogBuilder
							.setMessage(
									"You have exceeded the limit. Do you want to reset the limit?")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {

											Intent i = new Intent(
													AddDataInCategory.this,
													SetLimit.class);
											startActivity(i);
											finish();
										}
									})
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {

											dialog.cancel();
										}
									});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				}

				else {
					String getDate = date.getText().toString();
					String getPlace = place.getText().toString();
					String getTime = time.getText().toString();
					String getCost = cost.getText().toString();
					double icost = Double.parseDouble(getCost);
					SAMPLE_DB_NAME = "firstdatabase1";
					SQLiteDatabase myDb = openOrCreateDatabase(SAMPLE_DB_NAME,
							Context.MODE_PRIVATE, null);

					String SAMPLE_TABLE_NAME = "cattable";
					myDb.execSQL("CREATE TABLE IF NOT EXISTS "
							+ SAMPLE_TABLE_NAME
							+ " (id INTEGER primary key autoincrement,CategoryName VARCHAR, Date VARCHAR, Time VARCHAR, Place VARCHAR, Cost INTEGER);");

					ContentValues newValues1 = new ContentValues();

					newValues1.put("CategoryName", selectedCategory);
					newValues1.put("Date", getDate);
					newValues1.put("Time", getTime);
					newValues1.put("Place", getPlace);
					newValues1.put("Cost", icost);
					myDb.insert(SAMPLE_TABLE_NAME, null, newValues1);
					myDb.close();
					Intent goToReportPage = new Intent(AddDataInCategory.this,
							ReportPage.class);
					startActivity(goToReportPage);
					finish();

				}
			}
		});

		getAllDetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent goToReportPage = new Intent(AddDataInCategory.this,
						ReportPage.class);
				startActivity(goToReportPage);
			}
		});

	}

	/*
	 * @Override protected void onResume() { super.onResume();
	 * locationManager.requestLocationUpdates(provider, 400, 1, this); }
	 * 
	 * @Override protected void onPause() { super.onPause();
	 * locationManager.removeUpdates(this); }
	 */

	@Override
	public void onLocationChanged(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();

		// Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		// StringBuilder builder = new StringBuilder(); try { List<Address>
		// address = geoCoder.getFromLocation(lat, lng, 1); int maxLines =
		// address.get(0).getMaxAddressLineIndex(); for (int i = 0; i <
		// maxLines; i++) { String addressStr =
		// address.get(0).getAddressLine(i); builder.append(addressStr);
		// builder.append(" "); }
		//
		// String finalAddress = builder.toString(); // This is the complete //
		// address.
		//
		// // latituteField.setText(String.valueOf(lat)); //
		// longitudeField.setText(String.valueOf(lng));
		// place.setText(finalAddress); // This will display the final address.
		//
		// } catch (IOException e) { } catch (NullPointerException e) { }

		List<Address> addresses;
		Geocoder coder = null;
		StringBuilder builder = new StringBuilder();
		String myAddress = "";
		coder = new Geocoder(AddDataInCategory.this, Locale.ENGLISH);

		try {
			addresses = coder.getFromLocation(lat, lng, 1);

			for (int index = 0; index < addresses.size(); index++) {
				Address address = addresses.get(index);
				builder.append("" + address.getAddressLine(index) + "\n");
			}
			myAddress = builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		place.setText(myAddress);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}