package com.cs442_team1.activityplus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class StartingPoint extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button signUp = (Button) findViewById(R.id.signup);
		Button signIn = (Button) findViewById(R.id.signin);

		signUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent goToSingUp = new Intent(StartingPoint.this, SignUp.class);
				startActivity(goToSingUp);
			}
		});

		signIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent goToSingIn = new Intent(StartingPoint.this, SignIn.class);
				startActivity(goToSingIn);
			}
		});

	}
}
