package com.cs442_team1.activityplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LimitDB extends SQLiteOpenHelper {
	static final String DATABASE_NAME = "mylimit.db";
	private static final int SCHEMA_VERSION = 2;
	static final String TABLE_NAME = "Mylimit";

	public LimitDB(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("CREATE TABLE Mylimit(_id INTEGER PRIMARY KEY AUTOINCREMENT,limits INTEGER not null);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public Cursor getAll() {                               
        return(getReadableDatabase().rawQuery("SELECT limits FROM Mylimit",null));
}  

	public void insert(int limits) {
		ContentValues cv = new ContentValues();
		cv.put("limits", limits);
		Log.e("inserted", "inserted");
		getWritableDatabase().insert("Mylimit", "limits", cv);

	}
	
	public void updateRow(int limits)
	{
		ContentValues cv = new ContentValues();
		cv.put("limits", limits);
		Log.e("updated", "updated");
		getWritableDatabase().update("Mylimit", cv, "_id "+"="+1,null);
		
	}

}