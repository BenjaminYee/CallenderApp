package edu.ucdenver.benjaminyee.todolistapp;

public class Task {
    public boolean getStatus;
    //Fields for Task class
    private int id;
    private String date;
    private String description;
    private String title;
    private String time;
    private Boolean status;

    public Task(int id, String date, String title, String time, String description, Boolean status){
        this.id = id;
        this.date = date;
        this.title = title;
        this.time = time;
        this.description = description;
        this.status = status;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Boolean getStatus(){
        return status;
    }
    public void setStatus(Boolean status){
        this.status = status;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}
