package com.cs442_team1.activityplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	public static final String ROW_ID = "id";
	public static final String U_NAME = "uname";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String RE_PASSWORD = "repassword";
	//public static final String FAV_COLOR = "favcolor";
	//public static final String MY_IMAGE = "myimage";
	public static final String TABLE_NAME = "userdata";
	public static final String DATABASE_NAME = "firstdatabase.sqlite";
	public static final int DATABASE_VERSION = 2;

	public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS userdata (id INTEGER PRIMARY KEY AUTOINCREMENT, uname VARCHAR not null, email VARCHAR not null, password VARCHAR not null, repassword VARCHAR not null);";

	DataBaseHelper dbhelper;
	SQLiteDatabase db;
	Context context;

	public DBAdapter(Context context) {
		this.context = context;
		dbhelper = new DataBaseHelper(context);
	}

	private static class DataBaseHelper extends SQLiteOpenHelper {

		public DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

			try {
				db.execSQL(TABLE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);

		}

	}

	public DBAdapter open() {
		db = dbhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbhelper.close();
	}

	public long insertData(String name, String email, String password,
			String repassword) {
		ContentValues conval = new ContentValues();
		conval.put(U_NAME, name);
		conval.put(EMAIL, email);
		conval.put(PASSWORD, password);
		conval.put(RE_PASSWORD, repassword);
		return db.insertOrThrow(TABLE_NAME, null, conval);
	}

	// public Cursor returnData()
	// {
	// return db.query(TABLE_NAME, new String[] {U_NAME, EMAIL, PASSWORD,
	// RE_PASSWORD}, null, null, null, null, null);
	// }

	public boolean Login(String uname, String password) throws SQLException {
		Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " WHERE uname=? AND password=?", new String[] { uname,
				password });
		if (mCursor != null) {
			if (mCursor.getCount() > 0) {
				return true;
			}
		}
		return false;
	}

	public boolean ForgotPass(String email) throws SQLException {
		Cursor searchEmail = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " WHERE email=?", new String[] { email });
		if (searchEmail != null) {
			if (searchEmail.getCount() > 0) {
				return true;
			}
		}
		return false;
	}

	public Cursor fetchPassword(String email) {
		return db.rawQuery("SELECT password FROM userdata WHERE email=?",
				new String[] { email });
	}

}