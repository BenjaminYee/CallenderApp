package edu.ucdenver.benjaminyee.todolistapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;

public class AddTaskDialog extends DialogFragment {
    //Variables to store task
    private EditText dateEditText;
    private EditText titleEditText;
    private EditText timeEditText;
    private EditText descriptionEditText;
    //references to the buttons
    private Button buttonSave;
    private Button buttonClear;
    private Button buttonBack;
    public AddTaskDialog(){
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.add_task,null);
        //finds the references on the form
        dateEditText = dialogView.findViewById(R.id.editTextDate);
        titleEditText = dialogView.findViewById(R.id.editTextTitle);
        timeEditText = dialogView.findViewById(R.id.editTextTime);
        descriptionEditText = dialogView.findViewById(R.id.editTextTextMultiLine);

        buttonSave = dialogView.findViewById(R.id.saveButton);
        buttonClear = dialogView.findViewById(R.id.clearButton);
        buttonBack= dialogView.findViewById(R.id.backButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateEditText.getText().toString();
                String title = titleEditText.getText().toString();
                String time = timeEditText.getText().toString();
                String description = descriptionEditText.getText().toString();


                Task task = new Task(0,date, title, time, description, false);

                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.addNewTask(task);
                dismiss();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateEditText.setText("");
                titleEditText.setText("");
                timeEditText.setText("");
                descriptionEditText.setText("");
            }
        });
        builder.setView(dialogView);
        return builder.create();
    }
}
