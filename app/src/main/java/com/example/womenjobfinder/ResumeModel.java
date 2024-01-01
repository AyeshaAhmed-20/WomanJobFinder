package com.example.womenjobfinder;

public class ResumeModel {
    public ResumeModel() {
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ResumeModel(String jobId, String userId, String url) {
        this.jobId = jobId;
        this.userId = userId;
        this.url = url;
        this.date = date;
        this.title = title;
    }

    private String jobId,userId,url,date,title;
}
