package com.cs442_team1.activityplus;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddNewCategory extends Activity {

	private static boolean doesDatabaseExist(ContextWrapper context,
			String dbname) {
		File dbFile = context.getDatabasePath(dbname);
		return dbFile.exists();
	}

	EditText enteredCategoryName;
	Button getFromCam, getFromSD, okSave, cancel;
	ImageView getImage;
	private String selectedImagePath;
	ImageDB help = new ImageDB(this);
	// CategoryDetailsAdapter cd = new CategoryDetailsAdapter(this);
	private String st = "";

	protected void onCreate(Bundle addNewCategory) {
		super.onCreate(addNewCategory);
		setContentView(R.layout.addnewcategory);

		enteredCategoryName = (EditText) findViewById(R.id.enteredCategoryName);
		getFromCam = (Button) findViewById(R.id.getFromCam);
		getFromSD = (Button) findViewById(R.id.getFromSD);
		okSave = (Button) findViewById(R.id.okSave);
		cancel = (Button) findViewById(R.id.cancel);

		getImage = (ImageView) findViewById(R.id.imageView1);

		getFromCam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);

			}
		});
		getFromSD.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"), 1);

			}
		});

		okSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				st = enteredCategoryName.getText().toString();
				if (st.equals("")) {

					Toast.makeText(getApplication(), "Enter Category Name",
							Toast.LENGTH_LONG).show();

				} else {

					if (doesDatabaseExist(getApplication(),
							ImageDB.DATABASE_NAME)) {
						HomePage.hp.finish();
					}
					BitmapDrawable bm = (BitmapDrawable) getImage.getDrawable();
					Bitmap image = bm.getBitmap();
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					image.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] byteArray = stream.toByteArray();
					help.insert(st, byteArray);
					Log.d("inserted", "inserted");

					Intent intent = new Intent(AddNewCategory.this,
							HomePage.class);
					startActivityForResult(intent, 1888);
					finish();

				}

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(AddNewCategory.this, HomePage.class);
				startActivity(i);
				finish();

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				Bitmap image = (Bitmap) data.getExtras().get("data");
				getImage.setImageBitmap(image);

			} else if (requestCode == 1) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				// Bitmap bmp = BitmapFactory.decodeFile(selectedImagePath);
				getImage.setImageURI(selectedImageUri);
				// getImage.setImageBitmap(bmp);

			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
