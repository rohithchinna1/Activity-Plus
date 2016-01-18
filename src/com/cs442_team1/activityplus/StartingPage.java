package com.cs442_team1.activityplus;

import java.io.File;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StartingPage extends Activity {
	
	private static boolean doesDatabaseExist(ContextWrapper context, String dbname) {
	    File dbFile = context.getDatabasePath(dbname);
	    return dbFile.exists();
	}
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.starting);
	
	
	
	Button add = (Button) findViewById(R.id.add);
	Button cont = (Button) findViewById(R.id.cont);
	
	add.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent goToHpmePage = new Intent(StartingPage.this,
					AddNewCategory.class);
			startActivity(goToHpmePage);

		}
	});

	cont.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			
			 if(doesDatabaseExist(getApplication(), ImageDB.DATABASE_NAME)){
		    	Intent goToHpmePage = new Intent(StartingPage.this,
						HomePage.class);
				startActivity(goToHpmePage);
				finish();
			 }
			 
			 else
			 {
				 Toast.makeText(getApplication(), "There are no categories present", Toast.LENGTH_SHORT).show();
			 }
		    
			
			

		}
	});
}
}
