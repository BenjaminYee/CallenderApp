package edu.ucdenver.benjaminyee.todolistapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ViewTaskDialog extends DialogFragment {
    //Declare variables to hold references to text fields
    private TextView viewTextDate;
    private TextView viewTextTitle;
    private TextView viewTextTime;
    private TextView viewTextTextMultiLine;

    private RadioButton doneRadioButton;
    private RadioButton notDoneRadioButton;
    private Button backButton;
    private Button deleteButton;
    private Boolean status;

    private Task task;

    public ViewTaskDialog(){}
    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_view_task,null);

        //find references to views on the form
        viewTextDate = dialogView.findViewById(R.id.viewTextDate);
        viewTextTitle = dialogView.findViewById(R.id.viewTextTitle);
        viewTextTime = dialogView.findViewById(R.id.viewTextTime);
        viewTextTextMultiLine = dialogView.findViewById(R.id.viewTextTextMultiLine);
        doneRadioButton = dialogView.findViewById(R.id.doneRadioButton);
        notDoneRadioButton = dialogView.findViewById(R.id.notDoneRadioButton);
        backButton = dialogView.findViewById(R.id.backButton);
        deleteButton = dialogView.findViewById(R.id.deleteButton);

        viewTextDate.setText(task.getDate());
        viewTextTitle.setText(task.getTitle());
        viewTextTime.setText(task.getTime());
        viewTextTextMultiLine.setText(task.getDescription());
        if(task.getStatus()){
          doneRadioButton.setChecked(true);
        }
        else{
            notDoneRadioButton.setChecked(true);
        }
        builder.setView(dialogView).setMessage("");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskView callingActivity = (TaskView) getActivity();
                callingActivity.deleteTask(task);
                String date = viewTextDate.getText().toString();
                String title = viewTextTitle.getText().toString();
                String time = viewTextTime.getText().toString();
                String description = viewTextTextMultiLine.getText().toString();
                if(doneRadioButton.isChecked()){
                    status = true;
                }
                else{
                    status = false;
                }
                Task task = new Task (0,date,title,time,description,status);
                callingActivity.addNewTask(task);
                dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskView callingActivity = (TaskView) getActivity();
                callingActivity.deleteTask(task);
                dismiss();
            }
        });
        return builder.create();
    }
    public void sendSelectedTask(Task task){this.task = task;}
}
