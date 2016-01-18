package com.cs442_team1.activityplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
        public class ImageDB extends SQLiteOpenHelper {    
                static final String DATABASE_NAME="myimage1.db";
                private static final int SCHEMA_VERSION=1;
                static final String TABLE_NAME="Image1";
               
                public ImageDB(Context context) {
                        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
                        }
                @Override
                public void onCreate(SQLiteDatabase db) {
                        // TODO Auto-generated method stub                     
                        db.execSQL("CREATE TABLE Image1(_id INTEGER PRIMARY KEY AUTOINCREMENT,category VARCHAR,imageblob BLOB);");
                }
                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                }              
                public Cursor getAll() {                               
                        return(getReadableDatabase().rawQuery("SELECT imageblob FROM Image1",null));
                }    
                
                public Cursor getCategory() {                               
                    return(getReadableDatabase().rawQuery("SELECT category FROM Image1",null));
            }    
                public void insert(String categories,byte[] bytes)
                {
                        ContentValues cv = new ContentValues();
                        cv.put("category", categories);
                        cv.put("imageblob",bytes);
                        Log.e("inserted", "inserted");
                        getWritableDatabase().insert("Image1","imageblob",cv);
                       
                }
                public byte[] getImage(Cursor c)
                  {
                          return(c.getBlob(1));
                  }
        }