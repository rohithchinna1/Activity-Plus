package com.cs442_team1.activityplus;

import java.io.File;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;

public class Boxee extends Activity {

	private static boolean doesDatabaseExist(ContextWrapper context,
			String dbname) {
		File dbFile = context.getDatabasePath(dbname);
		return dbFile.exists();
	}

	@Override
	protected void onCreate(Bundle Splash) {
		// TODO Auto-generated method stub
		super.onCreate(Splash);
		setContentView(R.layout.boxee);
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2500);
					if (doesDatabaseExist(getApplication(),
							DBAdapter.DATABASE_NAME)) {
						Intent openStartingPoint = new Intent(Boxee.this,
								SignIn.class);
						startActivity(openStartingPoint);
						finish();
					} else {
						Intent openStartingPoint = new Intent(Boxee.this,
								SignUp.class);
						startActivity(openStartingPoint);
						finish();
					}
				} catch (Exception e) {
				} finally {
					finish();
				}
			}
		};
		timer.start();
	}
}
