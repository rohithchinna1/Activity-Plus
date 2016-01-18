package com.cs442_team1.activityplus;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SignIn extends Activity {

	Button signInClick, forgotPassword;
	EditText unameSignIn, passSignIn;

	private static boolean doesDatabaseExist(ContextWrapper context,
			String dbname) {
		File dbFile = context.getDatabasePath(dbname);
		return dbFile.exists();
	}

	protected void onCreate(Bundle signIn) {
		super.onCreate(signIn);
		setContentView(R.layout.signin);

		signInClick = (Button) findViewById(R.id.signInClick);
		forgotPassword = (Button) findViewById(R.id.forgotPassword);
		unameSignIn = (EditText) findViewById(R.id.unameSignIn);
		passSignIn = (EditText) findViewById(R.id.passSignIn);

		signInClick.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (unameSignIn.getText().toString().isEmpty()
						|| passSignIn.getText().toString().isEmpty()) {
					Toast.makeText(getBaseContext(),
							"Please Enter the Details", Toast.LENGTH_LONG)
							.show();
				}

				else {
					String getSignInUname = unameSignIn.getText().toString();
					String getSignInPass = passSignIn.getText().toString();

					try {
						if (getSignInUname.length() > 0
								&& getSignInPass.length() > 0) {
							DBAdapter dbUser = new DBAdapter(SignIn.this);
							dbUser.open();

							if (dbUser.Login(getSignInUname, getSignInPass)) {

								if (doesDatabaseExist(getApplication(),
										ImageDB.DATABASE_NAME)) {
									Intent goToHpmePage = new Intent(
											SignIn.this, HomePage.class);
									startActivity(goToHpmePage);
									finish();
								}

								else {
									Intent i = new Intent(SignIn.this,
											AddNewCategory.class);
									startActivity(i);
									finish();
								}

							} else {
								Toast.makeText(SignIn.this,
										"Invalid Username/Password",
										Toast.LENGTH_LONG).show();
							}
							dbUser.close();
						}

					} catch (Exception e) {
						
					}

				}

			}
		});

		forgotPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent gotToFrgtPass = new Intent(SignIn.this,
						ForgotPassword.class);
				startActivity(gotToFrgtPass);

			}
		});

	}
}
