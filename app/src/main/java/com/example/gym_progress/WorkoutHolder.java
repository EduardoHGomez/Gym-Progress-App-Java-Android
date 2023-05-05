package com.example.gym_progress;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WorkoutHolder extends RecyclerView.ViewHolder{
    public TextView nameTextView;
    public Button addButton, deleteButton;

    public WorkoutHolder(View itemView, MainWorkoutAdapter.OnItemClickListener listener) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.main_recycler_workoutName);
        addButton = itemView.findViewById(R.id.add_button_workout);
        deleteButton = itemView.findViewById(R.id.delete_button_workout);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemDelete(position);
                    }
                }
            }
        });

    }

    public String getNameAtIndex(){
        return (String) nameTextView.getText();
    }

}