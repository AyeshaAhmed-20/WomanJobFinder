package com.example.womenjobfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class JobDetailView extends AppCompatActivity {
    //JobModel jobModel = (JobModel) getIntent().getSerializableExtra("job");
    //JobModel jobModel = getIntent().getExtras().getParcelable("job");

    private Button buttonApply;
    private TextView titleText,salaryText,descriptionText,cityText,companyText,dateText,emailText,phoneText,postedByText;
    private TextView qualificationText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_view);

        titleText = findViewById(R.id.textViewTitle);
        salaryText = findViewById(R.id.textViewSalary);
        descriptionText = findViewById(R.id.textViewDescription);
        cityText = findViewById(R.id.textViewLocation);
        buttonApply = findViewById(R.id.buttonApply);
        dateText = findViewById(R.id.textViewDate);
        companyText = findViewById(R.id.textViewCompany);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);
        postedByText = findViewById(R.id.postedText);
        qualificationText = findViewById(R.id.textViewQualification);

        JobModel jobModel = getIntent().getParcelableExtra("job");

        titleText.setText(jobModel.getTitle());
        salaryText.setText("PKR"+jobModel.getSalary());
        descriptionText.setText(jobModel.getDescription());
        cityText.setText(jobModel.getCity()+", "+jobModel.getCountry());
        dateText.setText(jobModel.getDate());
        companyText.setText(jobModel.getCompany());
        emailText.setText(jobModel.getEmail());
        phoneText.setText(jobModel.getPhone());
        postedByText.setText(jobModel.getPostedBy());
        qualificationText.setText(jobModel.getQualification());
    }
}