package com.example.gym_progress;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;

    public CalendarAdapter(ArrayList<String> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }


    //---------TEST----
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        return new CalendarViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        String dayOfMonth;
        dayOfMonth = daysOfMonth.get(position);
        if (position < 42 && !dayOfMonth.equals("")) {
            holder.dayOfMonth.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_corner));
            holder.dayOfMonth.getBackground().setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }



}
