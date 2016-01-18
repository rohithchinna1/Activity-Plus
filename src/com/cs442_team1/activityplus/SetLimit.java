package com.cs442_team1.activityplus;

import java.io.File;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetLimit extends Activity {

	LimitDB help = new LimitDB(this);

	int limit = 0;
	double Total = 0;
	double b;
	Cursor c;
	TextView tv, tv1, tv2;

	private static boolean doesDatabaseExist(ContextWrapper context,
			String dbname) {
		File dbFile = context.getDatabasePath(dbname);
		return dbFile.exists();
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.setlimit);

		if (doesDatabaseExist(getApplication(),
				AddDataInCategory.SAMPLE_DB_NAME)) {
			SQLiteDatabase myDb = openOrCreateDatabase(
					AddDataInCategory.SAMPLE_DB_NAME, Context.MODE_PRIVATE,
					null);
			c = myDb.rawQuery("SELECT " + AddDataInCategory.COST + " FROM "
					+ AddDataInCategory.SAMPLE_TABLE_NAME, null);

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						Total = Total + c.getDouble(c.getColumnIndex("Cost"));

					} while (c.moveToNext());
				}
			}

		}

		if (doesDatabaseExist(getApplication(), LimitDB.DATABASE_NAME)) {
			c = help.getAll();
			c.moveToFirst();
			limit = c.getInt(c.getColumnIndex("limits"));
		}

		tv = (TextView) findViewById(R.id.oldlimit);
		tv.setText("$" + limit);

		tv2 = (TextView) findViewById(R.id.cspendings);
		tv2.setText(String.format("$%.2f", Total));
		tv1 = (TextView) findViewById(R.id.rbalance);
		if (limit <= 0) {
			b = 0;
			tv1.setText("Limit not set");

		} else {
			b = limit - Total;
			tv1.setText(String.format("$%.2f", b));
		}

		Button save = (Button) findViewById(R.id.savelimit);
		save.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText et = (EditText) findViewById(R.id.enteredlimit);
				String str = et.getText().toString();

				if (isInteger(str) && Integer.parseInt(str) >= 0) {
					limit = Integer.parseInt(str);
					if (doesDatabaseExist(getApplication(),
							LimitDB.DATABASE_NAME)) {
						help.updateRow(limit);
						Toast.makeText(getApplication(),
								"Limit has been updated", Toast.LENGTH_SHORT)
								.show();
					} else {
						help.insert(limit);
						Toast.makeText(getApplication(), "Limit has been set",
								Toast.LENGTH_SHORT).show();
					}
					Intent i = new Intent(SetLimit.this, HomePage.class);
					startActivity(i);
					finish();
				} else {
					if (str.isEmpty()) {
						Toast.makeText(getApplication(), "Enter the limit",
								Toast.LENGTH_LONG).show();
					}

					else if (isInteger(str) && Integer.parseInt(str) < 0) {

						Toast.makeText(getApplication(),
								"Limit cannot be negative ", Toast.LENGTH_LONG)
								.show();
					}

					else {
						Toast.makeText(getApplication(), "Invalid number",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}

}
