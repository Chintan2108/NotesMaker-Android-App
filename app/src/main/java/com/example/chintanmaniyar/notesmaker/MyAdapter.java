package com.example.chintanmaniyar.notesmaker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private static final String TAG = "MyAdapter";

    private List<Notes> notesList;
    private Context mContext;

    public MyAdapter(Context mContext, List<Notes> notesList) {
        this.notesList = notesList;
        this.mContext = mContext;
        Log.d(TAG, "constructor");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.d(TAG, "viewBinder called");
        Notes note = notesList.get(i);
        final String title = note.getTitle();
        final String desc = note.getDesc();
        Log.v(TAG, "Recycler populate index : " + i);
        Log.v(TAG, "title : " + title);
        Log.v(TAG, "desc : " + desc);
        if(desc.length() > 32){
            Log.v(TAG, desc.substring(0,31) + "...");
            viewHolder.description.setText(desc.substring(0,31) + "...");
        }
        else
        {
            viewHolder.description.setText(desc);
        }
        if(title.length() > 20){
            Log.v(TAG, title.substring(0,19) + "...");
            viewHolder.title.setText(Integer.toString(i+1) + ". " + title.substring(0,19) + "...");
        }
        else
        {
            viewHolder.title.setText(Integer.toString(i+1) + ". " + title);
        }


        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent
                Intent intent = new Intent(v.getContext(), ShowActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("desc", desc);
                Log.d(TAG, "clicked on note");
                MainActivity.flag = false;
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
