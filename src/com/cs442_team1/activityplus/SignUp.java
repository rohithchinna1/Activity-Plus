package com.cs442_team1.activityplus;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SignUp extends Activity {

	Button signUpClick;
	EditText uname, email, pass, repass;
	DBAdapter handler;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle signUp) {
		super.onCreate(signUp);
		setContentView(R.layout.signup);

		signUpClick = (Button) findViewById(R.id.signUpClick);
		uname = (EditText) findViewById(R.id.unameSignUp);
		email = (EditText) findViewById(R.id.emailSignUp);
		pass = (EditText) findViewById(R.id.passwordSignUp);
		repass = (EditText) findViewById(R.id.repasswordSignUp);

		signUpClick.setOnClickListener(new OnClickListener() {

			@TargetApi(Build.VERSION_CODES.GINGERBREAD)
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (uname.getText().toString().isEmpty()
						|| email.getText().toString().isEmpty()
						|| pass.getText().toString().isEmpty()
						|| repass.getText().toString().isEmpty()) {
					Toast.makeText(getBaseContext(), "One/More field(s) is/are Empty",
							Toast.LENGTH_LONG).show();
				}

				else {
					String getName = uname.getText().toString();
					String getEmail = email.getText().toString();
					String getPass = pass.getText().toString();
					String getRePass = repass.getText().toString();

					if (getPass.equals(getRePass)) {
						handler = new DBAdapter(getBaseContext());
						handler.open();

						handler.insertData(getName, getEmail,
								getPass, getRePass);
						handler.close();
						Toast.makeText(getBaseContext(),
								"Sign Up Successful", Toast.LENGTH_LONG)
								.show();

						Intent goToSignin = new Intent(SignUp.this,
								SignIn.class);
						startActivity(goToSignin);
						finish();
					} else {
						Toast.makeText(getBaseContext(),
								"Password DOESN'T MATCH",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}
}
