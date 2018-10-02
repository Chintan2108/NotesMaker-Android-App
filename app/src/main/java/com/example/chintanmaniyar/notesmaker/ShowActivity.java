package com.example.chintanmaniyar.notesmaker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ShowActivity extends AppCompatActivity {
    private static final String TAG = "ShowActivity";
    private DBHandler dbhandler = new DBHandler(this);
    private SQLiteOpenHelper dbhelper = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        if(MainActivity.flag) {
            Button delete =  findViewById(R.id.delete);
            delete.setVisibility(View.INVISIBLE);
            Button save = findViewById(R.id.save);
            ConstraintLayout.LayoutParams save_params = (ConstraintLayout.LayoutParams) save.getLayoutParams();
            save_params.leftMargin = 180;
            save.setLayoutParams(save_params);
            //set save button to centre here
            //save.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        }
        else
        {
            Intent intent = getIntent();
            TextView Title = findViewById(R.id.title);
            TextView Desc = findViewById(R.id.desc);
            Title.setText(intent.getStringExtra("title"));
            Desc.setText(intent.getStringExtra("desc"));
        }
    }

    public void onSave(View view) {
        TextView Title = findViewById(R.id.title);
        TextView Desc = findViewById(R.id.desc);
        String newTitle = Title.getText().toString();
        String newDesc = Desc.getText().toString();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Toast t;
        Log.v(TAG, "onSaveFlag = " + MainActivity.flag);
        if(newTitle.isEmpty()){
            t = Toast.makeText(this, "Note cannot be saved without title", Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            if(MainActivity.flag) {
                if(dbhandler.addRec(db, newTitle, newDesc)){
                    t = Toast.makeText(this, "Note created Successfully", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(this, MainActivity.class);
                    t.show();
                    startActivity(intent);
                    finish();
                }
                else{
                    t = Toast.makeText(this, "Note with same title already exists", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
            else
            {
                Intent oldIntent = getIntent();
                String oldTitle = oldIntent.getStringExtra("title");
                if(!oldTitle.equals(newTitle)){
                    if(!dbhandler.getRec(db, newTitle).isEmpty()) {
                        t = Toast.makeText(this, "Note with same title already exists", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
                else{
                    dbhandler.updateRec(db, newTitle, newDesc);
                    t = Toast.makeText(this, "Note updated succesfully", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(this, MainActivity.class);
                    t.show();
                    startActivity(intent);
                    finish();
                }
            }
        }


    }

    public void onDelete(View view) {
        TextView Title = findViewById(R.id.title);
        String title = Title.getText().toString();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Log.d(TAG, "delete title" + title);
        dbhandler.deleteRec(db, title);
        Toast t =Toast.makeText(this, "Note removed succesfully", Toast.LENGTH_SHORT);
        Intent intent = new Intent(this, MainActivity.class);
        t.show();
        startActivity(intent);
        finish();

    }
}
