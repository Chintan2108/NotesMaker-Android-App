package com.example.chintanmaniyar.notesmaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String TAG = "DBHandler";
    private static final String DB_NAME = "notekeeper";
    private static final int DB_VERSION = 1;
    private Context mContext;
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "TITLE TEXT NOT NULL UNIQUE, "
                + "DES TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    public String getRec(SQLiteDatabase db, String title) {
        Cursor cursor = db.query("notes",
                new String[] {"DES"},
                "TITLE = ?",
                new String[] {title},
                null, null, null);

        cursor.moveToFirst();

        return cursor.getString(0);
    }

    public ArrayList<Notes> getDB(SQLiteDatabase db) {
        ArrayList<Notes> notesList = new ArrayList<>();
        Cursor cursor = db.query("notes",
                new String[] {"TITLE", "DES"},
                null, null, null, null,null);

        if(cursor.moveToFirst())
        {
            notesList.add(new Notes(cursor.getString(0), cursor.getString(1)));
        }
        while(cursor.moveToNext()) {
            notesList.add(new Notes(cursor.getString(0), cursor.getString(1)));
        }
        return notesList;
    }

    public boolean addRec(SQLiteDatabase db, String title, String desc) {
        ContentValues row = new ContentValues();
        row.put("TITLE", title);
        row.put("DES", desc);
        Long i = db.insert("notes", null, row);
        Log.d(TAG, "insert done with " + i + " " + title + " " + desc);
        if(i<0)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public void updateRec(SQLiteDatabase db, String title, String newDesc) {
        ContentValues row = new ContentValues();
        String flag = getRec(db, title);
        Log.v(TAG, "duplidate " + flag);
        row.put("DES", newDesc);
        db.update("notes",
                row,
                "TITLE = ?",
                new String[] {title});
        Log.d(TAG, "update done");

    }

    public void deleteRec(SQLiteDatabase db, String title) {
        Log.d(TAG, "inside db delete title: " + title);
        db.delete("notes",
                "TITLE = ?",
                new String[] {title});
        Toast t = Toast.makeText(mContext, "Note removed succesfully", Toast.LENGTH_SHORT);
        t.show();
    }
}
