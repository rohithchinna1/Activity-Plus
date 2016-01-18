package com.cs442_team1.activityplus;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends Activity {

	Button getPass, goToSignIn;
	EditText frgtPassEmail;

	protected void onCreate(Bundle frgtPass) {
		super.onCreate(frgtPass);
		setContentView(R.layout.forgotpassword);

		getPass = (Button) findViewById(R.id.getPassDB);
		goToSignIn = (Button) findViewById(R.id.goToSignIn);
		frgtPassEmail = (EditText) findViewById(R.id.frgtPassEmail);
		//final TextView passwordFromDB = (TextView) findViewById(R.id.passwordFromDB);
		//final TextView tv1 = (TextView) findViewById(R.id.tv1);

		getPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String enteredEmail = frgtPassEmail.getText().toString();

				if (enteredEmail.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Enter email first!", Toast.LENGTH_LONG).show();
				} else {
					DBAdapter forEmail = new DBAdapter(ForgotPassword.this);
					forEmail.open();
					String getPasswordFromDB;
					getPasswordFromDB = "";

					if (forEmail.ForgotPass(enteredEmail)) {
						Cursor cursor = forEmail.fetchPassword(enteredEmail);
						if (cursor.moveToFirst()) {
							do {
								getPasswordFromDB = cursor.getString(cursor
										.getColumnIndex(DBAdapter.PASSWORD));
							} while (cursor.moveToNext());
						
							Intent chooser = null;
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setData(Uri.parse("mailto:"));
							String[] to = {enteredEmail};
							i.putExtra(Intent.EXTRA_EMAIL, to);
							i.putExtra(Intent.EXTRA_SUBJECT, "This is your password" );
							i.putExtra(Intent.EXTRA_TEXT, getPasswordFromDB);
							i.setType("message/rfc822");
							chooser = Intent.createChooser(i, "Send Mail");
							startActivity(chooser);
							
						}
					} else {
						Toast.makeText(ForgotPassword.this, "Invalid EMAIL ID",
								Toast.LENGTH_LONG).show();
					}
					forEmail.close();
					
					//tv1.setText("Your password has been sent");
					goToSignIn.setVisibility(View.VISIBLE);
					
					//tv1.setText("Your password is:");
					//passwordFromDB.setText(getPasswordFromDB);
					
				}
			}
		});

		goToSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent signInPage = new Intent(ForgotPassword.this,
						SignIn.class);
				startActivity(signInPage);
				finish();
			}
		});

	}
}