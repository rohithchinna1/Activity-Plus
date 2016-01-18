package com.cs442_team1.activityplus;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class HomePage extends Activity {

	Button addNew;
	GridView categories;
	Bitmap[] array;
	String[] names;
	static String str = "";
	static String selectedCategory;
    public static Activity hp;
	protected void onCreate(Bundle homePage) {
		super.onCreate(homePage);
		setContentView(R.layout.homepage);
		hp =this;

		ImageDB help = new ImageDB(this);
		Cursor c = help.getAll();
		int i = 0;
		if (c.getCount() > 0) {
			array = new Bitmap[c.getCount()];
			c.moveToFirst();
			while (c.isAfterLast() == false) {

				byte[] bytes = c.getBlob(c.getColumnIndex("imageblob"));

				array[i] = BitmapFactory
						.decodeByteArray(bytes, 0, bytes.length);
				c.moveToNext();
				i++;
			}
			Log.e("Bitmap length", "" + array.length);
		}

		int j = 0;
		c = help.getCategory();
		if (j < array.length) {
			names = new String[array.length];
			while (c.moveToNext()) {

				names[j] = c.getString(c.getColumnIndex("category"));

				j++;
			}
			Log.e("category length", "" + names.length);

		}

		categories = (GridView) findViewById(R.id.gridView1);

		categories.setAdapter(new ImageAdapter(this, names, array));

		categories.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				selectedCategory = (String) ((TextView) v
						.findViewById(R.id.itemText)).getText();

				Intent goToAddDetails = new Intent(getApplicationContext(),
						AddDataInCategory.class);
				goToAddDetails.putExtra("selectedcategory", selectedCategory);
				startActivity(goToAddDetails);

			}
		});

		addNew = (Button) findViewById(R.id.addNew);
		addNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goToAddNewCatPage = new Intent(HomePage.this,
						AddNewCategory.class);
				startActivity(goToAddNewCatPage);

			}
		});

		Button setlimit = (Button) findViewById(R.id.setlimit);
		setlimit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomePage.this, SetLimit.class);
				startActivity(i);

			}
		});
	}
}