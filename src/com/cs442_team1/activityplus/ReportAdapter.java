package com.cs442_team1.activityplus;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

class ViewHolder {
	TextView place, date, cost;
}

public class ReportAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ReportAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listviewreport, null);

			holder.place = (TextView) convertView.findViewById(R.id.place);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.cost = (TextView) convertView.findViewById(R.id.cost);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HashMap<String, String> getValues = new HashMap<String, String>();
		getValues = data.get(position);

		// Setting all values in listview
		holder.place.setText(getValues.get(ReportPage.PLACE));
		holder.date.setText(getValues.get(ReportPage.DATE));
		holder.cost.setText("$"+getValues.get(ReportPage.COST));

		new HashMap<String, String>();
		data.get(position);
		return convertView;
	}

}
