package com.cs442_team1.activityplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectView extends Activity {

	

	protected void onCreate(Bundle selectView) {
		super.onCreate(selectView);
		setContentView(R.layout.selectview);
		
		Button getReport;

		
		getReport = (Button) findViewById(R.id.getReport);

		
		getReport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goToReportPage = new Intent(SelectView.this,
						ReportPage.class);
				startActivity(goToReportPage);
			}
		});

	}
}
