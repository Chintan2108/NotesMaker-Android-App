 package com.example.chintanmaniyar.notesmaker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {
     private static final String TAG = "MainActivity";
     public static boolean flag = false;
     private DBHandler dbhandler = new DBHandler(this);
     private SQLiteOpenHelper dbhelper = new DBHandler(this);

     //vars
     private ArrayList<Notes> notesList;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         Log.d(TAG, "onCreate started");

         initNotes();
     }

     private void initNotes() {
         Log.d(TAG, "init notes");
         //create notes
         //call database here and populate noteslist
         SQLiteDatabase db = dbhelper.getReadableDatabase();
         notesList = dbhandler.getDB(db);
         initRecyclerView();
     }

     private void initRecyclerView() {
         Log.d(TAG, "recyclerview init");
         RecyclerView recyclerView = findViewById(R.id.recycle);
         MyAdapter adapter = new MyAdapter(this, notesList);
         recyclerView.setAdapter(adapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
     }

     public void onAddNote(View view) {
         flag = true;
         Intent intent = new Intent(this, ShowActivity.class);
         startActivity(intent);
         finish();
     }
 }
