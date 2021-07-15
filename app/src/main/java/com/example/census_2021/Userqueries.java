package com.example.census_2021;

public class Userqueries {
    String qtitle;
    String query;
    String status;
    String feedback;
    String Name;

    public String getQtitle() {
        return qtitle;
    }

    public void setQtitle(String qtitle) {
        this.qtitle = qtitle;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Userqueries()
    {

    }
    public Userqueries(String qtitle, String query, String status, String feedback) {
        this.qtitle = qtitle;
        this.query = query;
        this.status= status;
        this.feedback=feedback;
    }

    public String getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getTitle() {
        return qtitle;
    }
    public void setTitle(String qtitle) {
        this.qtitle = qtitle;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public String getQuery() {
        return query;
    }


}