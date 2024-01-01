package com.example.womenjobfinder;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class JobModel implements Parcelable {
    private String title,description,city,country,date,salary,postedBy,email,phone,company,qualification;

    public JobModel() {
    }

    protected JobModel(Parcel in) {
        title = in.readString();
        description = in.readString();
        city = in.readString();
        country = in.readString();
        date = in.readString();
        salary = in.readString();
        postedBy = in.readString();
        email = in.readString();
        phone = in.readString();
        company = in.readString();
        qualification = in.readString();
    }

    public static final Creator<JobModel> CREATOR = new Creator<JobModel>() {
        @Override
        public JobModel createFromParcel(Parcel in) {
            return new JobModel(in);
        }

        @Override
        public JobModel[] newArray(int size) {
            return new JobModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public JobModel(String title, String description, String city, String country, String date, String salary, String postedBy, String email, String phone, String company, String qualification) {
        this.title = title;
        this.description = description;
        this.city = city;
        this.country = country;
        this.date = date;
        this.salary = salary;
        this.postedBy = postedBy;
        this.email = email;
        this.phone = phone;
        this.company = company;
        this.qualification = qualification;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(date);
        dest.writeString(salary);
        dest.writeString(postedBy);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(company);
        dest.writeString(qualification);
    }
}