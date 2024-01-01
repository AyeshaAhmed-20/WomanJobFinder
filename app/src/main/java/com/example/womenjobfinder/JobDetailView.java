package com.example.womenjobfinder;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class JobDetailView extends AppCompatActivity {
    //JobModel jobModel = (JobModel) getIntent().getSerializableExtra("job");
    //JobModel jobModel = getIntent().getExtras().getParcelable("job");

    private Button buttonApply;
    private TextView titleText,salaryText,descriptionText,cityText,companyText,dateText,emailText,phoneText,postedByText;
    private TextView qualificationText;

    Uri imageuri = null;
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

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore.getInstance().collection("resumes")
                        .whereEqualTo("jobId",jobModel.getId())
                        .whereEqualTo("userId",FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            if (!document.isEmpty()) {
                                Toast.makeText(JobDetailView.this, "You have already applied for this job", Toast.LENGTH_SHORT).show();

                            } else {
                                Intent galleryIntent = new Intent();
                                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                                // We will be redirected to choose pdf
                                galleryIntent.setType("application/pdf");
                                startActivityForResult(galleryIntent, 1);

                            }
                        } else {
                            Toast.makeText(JobDetailView.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "Failed with: ", task.getException());
                        }
                    }
                });



            }
        });
    }
    ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            //Toast.makeText(JobDetailView.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            //Toast.makeText(JobDetailView.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        myurl = uri.toString();
                        //SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                        Map<String, Object> posts = new HashMap<>();
                        String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        JobModel jobModel = getIntent().getParcelableExtra("job");
                        posts.put("url", myurl);
                        posts.put("jobId", jobModel.getId());
                        posts.put("userId", userId);
                        posts.put("date", date);
                        posts.put("title", jobModel.getTitle());
                        FirebaseFirestore.getInstance().collection("resumes").add(posts);
                        Toast.makeText(JobDetailView.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(JobDetailView.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}