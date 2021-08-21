package edu.ucdenver.benjaminyee.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TaskView extends AppCompatActivity {
    private ArrayList<Task> taskList;
    private DataManager dataManager;
    private TaskAdapter taskAdapter;
    private RecyclerView taskRecyclerView;
    private String chosenDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        taskList = new ArrayList<Task>();
        dataManager = new DataManager(this);

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskAdapter = new TaskAdapter(this, taskList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        taskRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        taskRecyclerView.setAdapter(taskAdapter);
        chosenDate = init();
    }
    @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }
    public String init(){
        Intent i = getIntent();
        String date = i.getStringExtra("date");
        TextView textViewDate;
        textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(date);
        return date;
    }
    public void loadData () {
        Cursor cursor = dataManager.selectAll();
        int contactCount = cursor.getCount();
        Log.i("info", String.valueOf(contactCount)+" were added");

        if (contactCount > 0) {
            taskList.clear();

            while (cursor.moveToNext()) {
                String test;
                String test2;
                String[] temp1;
                String[] temp2;
                String date = cursor.getString(1);
                String title = cursor.getString(2);
                String time = cursor.getString(3);
                String description = cursor.getString(4);
                String temp = cursor.getString(5);
                Boolean status = true;
                if(temp.equals("false")) status = false;

                Task task = new Task(0,date, title, time, description, status);
                temp1 = date.split("/");
                int Check = temp1.length;
                if (Check == 3){
                    if(temp1[0].length() == 1){
                        temp1[0]= "0"+temp1[0];
                    }
                    temp1[2] = temp1[2].substring(temp1[2].length() - 2);
                    test = temp1[0]+temp1[1]+temp1[2];
                }
                else{
                    test = date;
                }
                temp2 = chosenDate.split(" ");
                temp2[2] = temp2[2].substring(temp2[2].length() - 2);
                String temp3 = temp2[1];
                switch (temp3){
                    case "January":
                        temp2[1] = "01";
                        break;
                    case "February":
                        temp2[1] = "02";
                        break;
                    case "March":
                        temp2[1] = "03";
                        break;
                    case "April":
                        temp2[1] = "04";
                        break;
                    case "May":
                        temp2[1] = "05";
                        break;
                    case "June":
                        temp2[1] = "06";
                        break;
                    case "July":
                        temp2[1] = "07";
                        break;
                    case "August":
                        temp2[1] = "08";
                        break;
                    case "September":
                        temp2[1] = "09";
                        break;
                    case "October":
                        temp2[1] = String.valueOf(10);
                        break;
                    case "November":
                        temp2[1] = String.valueOf(11);
                        break;
                    default:
                        temp2[1] = String.valueOf(12);
                }
                test2 = temp2[1]+temp2[0]+temp2[2];
                if(test.equals(test2)) {
                    taskList.add(task);
                }
            }
        }
        taskAdapter.notifyDataSetChanged();
    }
    public void showTask(int taskToShow){
        ViewTaskDialog viewTaskDialog = new ViewTaskDialog();
        viewTaskDialog.sendSelectedTask(taskList.get(taskToShow));
        viewTaskDialog.show(getSupportFragmentManager(),"");
    }
    public void deleteTask(Task task){
        String delTitle = task.getTitle();
        taskList.remove(task);
        dataManager.Delete(delTitle);
        loadData();
    }
    public void addNewTask(Task task){
        String status;
        String date = task.getDate();
        String title = task.getTitle();
        String time = task.getTime();
        String description = task.getDescription();
        Boolean temp = task.getStatus();
        if(!temp){
            status = "false";
        }
        else{
            status = "true";
        }
        dataManager.insert(date,title,time,description,status);
        loadData();
    }
}