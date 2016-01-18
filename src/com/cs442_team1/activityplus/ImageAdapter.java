package com.cs442_team1.activityplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ImageAdapter extends BaseAdapter {

	private Context context;
	Bitmap[] categoriesName;
	String[] n;
	ImageView imageView;

	public ImageAdapter(Context context, String[] names, Bitmap[] array) {
		this.context = context;
		this.n = names;
		this.categoriesName = array;

	}

	@SuppressLint({ "InflateParams", "NewApi" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.griditem, null);

			// set value into textview

			TextView textView = (TextView) gridView.findViewById(R.id.itemText);
			textView.setText(n[position]);

			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.imageView1);

			imageView.setImageBitmap(categoriesName[position]);

		} else {
			gridView = (View) convertView;
		}

		return gridView;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categoriesName.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
