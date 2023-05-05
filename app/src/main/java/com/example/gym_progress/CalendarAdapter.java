package com.example.gym_progress;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    private int month, year;
    Context contextMain;

    //---------------Constructor--------------------
    public CalendarAdapter(ArrayList<String> daysOfMonth, int month, int year) {
        this.daysOfMonth = daysOfMonth;
        this.month = month;
        this.year = year;
    }


    //------------------CalendarViewHolder--------------
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        contextMain = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        return new CalendarViewHolder(view);
    }

    //------------------onBindViewHolder--------------
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        String dayOfMonth;
        dayOfMonth = daysOfMonth.get(position);
        if (position < 42 && !dayOfMonth.equals("")) {
            holder.dayOfMonth.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_corner));
            int colorToSet = calculateColor(position, month, year);
            int colorBackground = 0;
            if (colorToSet < 10) colorBackground = R.color.empty;
            else if (colorToSet > 10 && colorToSet < 100) colorBackground = R.color.green1;
            else if (colorToSet >= 100 && colorToSet < 300) colorBackground = R.color.green2;
            else if (colorToSet >= 300 && colorToSet < 500) colorBackground = R.color.green3;
            else if (colorToSet >= 500 && colorToSet < 800) colorBackground = R.color.green4;

            holder.dayOfMonth.getBackground().setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), colorBackground), PorterDuff.Mode.SRC_IN);
        }
    }

    private int calculateColor(int position, int month, int year) {
        int total = 0;
        int reps = 0;
        int sets = 0;
        DatabaseWorkout myDB = new DatabaseWorkout(contextMain);
        Cursor cursor = myDB.readAllDayData(position, month, year);
        if(cursor.getCount() == 0){
            Toast.makeText(contextMain, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                sets = cursor.getInt(0);
                reps = cursor.getInt(1);
                total += sets*reps;
            }
        }

        return total;
    }

    //-------------getItemCount--------------------
    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }



}
