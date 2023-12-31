
package com.example.womenjobfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends AppCompatActivity {

    private ListView listView;
    ArrayList<JobModel> dataModalArrayList;
    private TextView textView;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        listView = findViewById(R.id.jobListView);
        textView = findViewById(R.id.textViewWelcome);

        dataModalArrayList = new ArrayList<>();



        db = FirebaseFirestore.getInstance();

        db.collection("jobs")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                //textView.setText(d.toString());
                                JobModel dataModal = d.toObject(JobModel.class);

                                // after getting data from Firebase we are
                                // storing that data in our array list
                                dataModalArrayList.add(dataModal);
                            }
                            // after that we are passing our array list to our adapter class.
                            JobAdaptor adapter = new JobAdaptor(HomePageActivity.this, dataModalArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            listView.setAdapter(adapter);
                            //listView.setAdapter(jobAdapter);
                        }
                        else{
                            textView.setText("not empty");
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(HomePageActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });

       /* db.collection("jobs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<JobModel> jobs = new ArrayList<>();
                            textView.setText("success");
                            *//*for (QueryDocumentSnapshot document : task.getResult()) {
                                JobModel job = document.toObject(JobModel.class);
                                jobs.add(job);
                            }*//*
                            //jobAdapter.addAll(jobs);
                        } else {
                            textView.setText("error");
                            Log.d("myTag", "This is my message");
                            // Handle errors
                        }
                    }
                });*/
    }
}