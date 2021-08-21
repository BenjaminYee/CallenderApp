package edu.ucdenver.benjaminyee.todolistapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private ArrayList<Task> taskList;
    private DataManager dataManager;
    private TaskAdapter taskAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialog addTaskDialog = new AddTaskDialog();
                addTaskDialog.show(getSupportFragmentManager(), " ");
            }
        });
        taskList = new ArrayList<Task>();
        dataManager = new DataManager(this);


    }
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }
    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals("")) {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            String m2 = dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, TaskView.class);
            i.putExtra("date", m2);
            startActivity(i);
        }
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
    }
    public void loadData () {
        Cursor cursor = dataManager.selectAll();
        int contactCount = cursor.getCount();
        Log.i("info", String.valueOf(contactCount)+" were added main");

        if (contactCount > 0) {
            taskList.clear();

            while (cursor.moveToNext()) {
                String date = cursor.getString(1);
                String title = cursor.getString(2);
                String time = cursor.getString(3);
                String description = cursor.getString(4);
                String temp = cursor.getString(5);
                Boolean status = true;
                if(temp.equals("false")){
                    status = false;
                }

                Task task = new Task(0,date, title, time, description, status);

                taskList.add(task);
            }
        }
        taskAdapter.notifyDataSetChanged();


    }
}