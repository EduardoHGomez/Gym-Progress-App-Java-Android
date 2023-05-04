package com.example.gym_progress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainWorkoutAdapter extends RecyclerView.Adapter<MainWorkoutAdapter.ViewHolder> {

    //--------Variables------------
    private List<String> nameData;
    private LayoutInflater mInflater;

    //-------------CONSTRUCTOR-----------------------
    MainWorkoutAdapter(Context context, List<String> nameData) {
        this.mInflater = LayoutInflater.from(context);
        this.nameData = nameData;
    }

    // ----------------RECYCLER ROW / VIEW HOLDER-----------------------------
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    // -----THESE CHANGES WILL AFFECT EACH ROW AND ITS DATA-------------
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(nameData.get(position));
    }

    @Override
    public int getItemCount() {
        return nameData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.main_recycler_workoutName);

        }

    }

}
