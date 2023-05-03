package com.example.gym_progress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    //--------Variables------------
    private List<String> nameData, setsData, repsData;
    private LayoutInflater mInflater;

    //-------------CONSTRUCTOR-----------------------
    WorkoutAdapter(Context context, List<String> nameData, List<String> setsData, List<String> repsData) {
        this.mInflater = LayoutInflater.from(context);
        this.nameData = nameData;
        this.setsData = setsData;
        this.repsData = repsData;
    }

    // ----------------RECYCLER ROW / VIEW HOLDER-----------------------------
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    // -----THESE CHANGES WILL AFFECT EACH ROW AND ITS DATA-------------
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(nameData.get(position));
        holder.setsTextView.setText(setsData.get(position));
        holder.repsTextView.setText(repsData.get(position));
    }

    @Override
    public int getItemCount() {
        return nameData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, setsTextView, repsTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recyclerName);
            setsTextView = itemView.findViewById(R.id.recyclerSets);
            repsTextView = itemView.findViewById(R.id.recyclerReps);
        }

    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return nameData.get(id);
    }

}
