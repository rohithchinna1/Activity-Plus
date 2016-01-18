package com.cs442_team1.activityplus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportPage extends Activity {

	ArrayList<HashMap<String, String>> myValues = new ArrayList<HashMap<String, String>>();
	static final String CAT_NAME = "CategoryName";
	static final String PLACE = "Place";
	static final String DATE = "Date";
	static final String COST = "Cost";
	String SAMPLE_DB_NAME = "firstdatabase1";
	String SAMPLE_TABLE_NAME = "cattable";
	ListView lv1;
	TextView getCateName;
	ArrayList<HashMap<String, String>> data;

	@SuppressLint("SdCardPath")
	protected void onCreate(Bundle report) {
		super.onCreate(report);
		setContentView(R.layout.reportpage);
		data = new ArrayList<HashMap<String, String>>();
		lv1 = (ListView) findViewById(R.id.listView1);
		getCateName = (TextView) findViewById(R.id.categoryNameForReport);

		final String selectedCategory = HomePage.selectedCategory;

		getCateName.setText(selectedCategory);

		try {
			SQLiteDatabase myDb = openOrCreateDatabase(SAMPLE_DB_NAME,
					Context.MODE_PRIVATE, null);

			Cursor cur = myDb.rawQuery("SELECT * FROM " + SAMPLE_TABLE_NAME
					+ " WHERE CategoryName='" + selectedCategory + "'", null);
			if (cur != null) {
				if (cur.moveToFirst()) {
					do {
						HashMap<String, String> map = new HashMap<String, String>();
						String place = cur.getString(cur
								.getColumnIndex("Place"));
						String date = cur.getString(cur.getColumnIndex("Date"));
						double icost = cur
								.getDouble(cur.getColumnIndex("Cost"));
						String cost = String.valueOf(icost);
						map.put(PLACE, place);
						map.put(DATE, date);
						map.put(COST, cost);
						System.out.print(place);
						System.out.print(date);
						System.out.print(cost);
						data.add(map);
					} while (cur.moveToNext());

				}
			}
			cur.close();
		} catch (Exception e) {
		}
		Log.d("Size is:", "" + data.size());

		ReportAdapter adapter = new ReportAdapter(ReportPage.this, data);
		lv1.setAdapter(adapter);
	}
}
