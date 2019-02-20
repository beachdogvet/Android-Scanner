package com.lm.scanner;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "palletdb.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void InitializeDB(Context context) {

        boolean sourceDatabaseFileExists = false;
        String targetDatabasePath = "/data/data/" + context.getPackageName() + "/databases/" + DATABASE_NAME;

        File targetDatabaseFile = new File(targetDatabasePath);
        if (!targetDatabaseFile.exists()) {
            try {
                String[] files = context.getAssets().list("");

                for (String entry : files) {
                    if (entry.startsWith(DATABASE_NAME)) {

                        File targetDatabasedirectory = new File("/data/data/" + context.getPackageName() + "/databases");
                        targetDatabasedirectory.mkdirs();
                        targetDatabaseFile.createNewFile();
                        sourceDatabaseFileExists = true;
                        break;
                    }
                }
                if (sourceDatabaseFileExists) {

                    if (targetDatabaseFile.exists()) {
                        CopyDB(context.getAssets().open(DATABASE_NAME),
                                new FileOutputStream(targetDatabasePath));
                    }
                }

            } catch (IOException e) {
                Log.d("Exception thrown", " InitializeDB() " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public static void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }



    public List<SpinnerItem> GetLocations() {

        SQLiteDatabase db = null;
        List<SpinnerItem> locations = new ArrayList<SpinnerItem>();
        String selectQuery = "SELECT id,locationName from locations order by locationName";

        try {
            db = getWritableDatabase();
            if (db != null) {
                Cursor cursor = db.rawQuery(selectQuery, null);
                //stateLocations.add("Select a State");
                if (cursor.moveToFirst()) {
                    do {
                        SpinnerItem si = new SpinnerItem(cursor.getInt(0), cursor.getString(1));
                        locations.add(si);
                    } while (cursor.moveToNext());

                    if (cursor != null)
                        cursor.close();
                }
            }
        } catch (Exception e) {
            Log.d("Exception thrown", "GetLocations() " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return locations;
    }



    public boolean UpdateLocation(int locationId, String locationName) {

        boolean returnValue = true;

        try {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("locationName", locationName);
            String[] whereArgs = { Long.toString(locationId) };
            db.update("locations", values, " id=?", whereArgs);
            db.close();
        }
        catch (Exception e)
        {
            Log.d("Exception thrown", "UpdateLocation() " + e.getMessage());
            e.printStackTrace();
            returnValue = false;
        }

        return returnValue;
    }


    public boolean AddNewLocation(String newLocationText) {

        boolean returnValue = true;
        try {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("locationName", newLocationText);

            db.insert("locations", null, values);
            db.close();

        }
        catch (Exception e)
        {
            Log.d("Exception thrown", "AddContact() " + e.getMessage());
            e.printStackTrace();
            returnValue = false;
        }

        return returnValue;
    }

    public boolean DeleteLocation(int locationId) {

        boolean returnValue = true;
        try {
            SQLiteDatabase db = getWritableDatabase();
            String[] whereArgs = { Long.toString(locationId) };
            db.delete("locations", "id=?" , whereArgs);
            db.close();
        }
        catch (Exception e)
        {
            Log.d("Exception thrown", "DeleteContact() " + e.getMessage());
            e.printStackTrace();
            returnValue = false;
        }
        return returnValue;
    }



    @Override
    public void onCreate(SQLiteDatabase arg0) { }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {}
}





