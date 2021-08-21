package edu.ucdenver.benjaminyee.todolistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ListItemHolder> {
    TaskView taskView;
    private ArrayList<Task> taskList;

    public TaskAdapter (TaskView taskView, ArrayList<Task> taskList){
        this.taskView = taskView;
        this.taskList = taskList;
    }
    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);

        return new ListItemHolder(listItem);
    }
    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position){
        Task task = taskList.get(position);
        holder.textViewTitle.setText(task.getTitle());
        holder.tVTime.setText(task.getTime());
        if(task.getStatus() == true){
            holder.textViewStatus.setText("done");
        }
        else{
            holder.textViewStatus.setText("not done");
        }
    }
    @Override
    public int getItemCount() { return taskList.size(); }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewTitle;
        private TextView textViewStatus;
        private TextView tVTime;

        public ListItemHolder(View view) {
            super(view);

            textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewStatus = view.findViewById(R.id.textViewStatus);
            tVTime = view.findViewById(R.id.tVTime);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            taskView.showTask(getAdapterPosition());
        }
    }
}
