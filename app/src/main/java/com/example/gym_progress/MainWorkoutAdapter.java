package com.example.gym_progress;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainWorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder> {

    //--------Variables------------WorkoutHolder
    private List<String> nameData;
    private LayoutInflater mInflater;



    //-------------CONSTRUCTOR-----------------------
    MainWorkoutAdapter(Context context, List<String> nameData) {
        this.mInflater = LayoutInflater.from(context);
        this.nameData = nameData;

    }

    // ----------------RECYCLER ROW / VIEW HOLDER-----------------------------
    @Override
    public WorkoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_recyclerview_row, parent, false);
        return new WorkoutHolder(view);
    }

    // binds the data to the TextView in each row
    // -----THESE CHANGES WILL AFFECT EACH ROW AND ITS DATA-------------
    @Override
    public void onBindViewHolder(WorkoutHolder holder, int position) {
        holder.nameTextView.setText(nameData.get(position));
    }

    @Override
    public int getItemCount() {
        return nameData.size();
    }




}
