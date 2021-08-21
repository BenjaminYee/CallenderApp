package edu.ucdenver.benjaminyee.todolistapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;


class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private LocalDate selectedDate;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        selectedDate = LocalDate.now();
        selectedDate = LocalDate.now().minusDays(selectedDate.getDayOfMonth() - 1);
        DayOfWeek week = selectedDate.getDayOfWeek();
        int offset;
        switch (week) {
            case MONDAY:
                offset = 0;
                break;
            case TUESDAY:
                offset = 1;
                break;
            case WEDNESDAY:
                offset = 2;
                break;
            case THURSDAY:
                offset = 3;
                break;
            case FRIDAY:
                offset = 4;
                break;
            case SATURDAY:
                offset = 5;
                break;
            case SUNDAY:
                offset = 6;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + week);
        }
        selectedDate = LocalDate.now();
        int date = selectedDate.getDayOfMonth() + offset;
            if(position == date){
                holder.calView.setBackgroundColor(Color.GRAY);
            }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface  OnItemListener {
        void onItemClick(int position, String dayText);
    }
}
