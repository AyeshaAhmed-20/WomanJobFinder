package com.example.womenjobfinder;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AppliedJobsActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<ResumeModel> dataModalArrayList;
    private FirebaseFirestore db;

    private AppliedJobAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_jobs);

        listView = findViewById(R.id.myJobListView);

        dataModalArrayList = new ArrayList<>();



        db = FirebaseFirestore.getInstance();

        db.collection("resumes").whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                d.getId();

                                // after getting this list we are passing
                                // that list to our object class.
                                //textView.setText(d.toString());
                                ResumeModel dataModal = d.toObject(ResumeModel.class);

                                dataModalArrayList.add(dataModal);

                                //Toast.makeText(AppliedJobsActivity.this, dataModal.getUrl(), Toast.LENGTH_SHORT).show();

                            }
                            // after that we are passing our array list to our adapter class.
                            adapter = new AppliedJobAdaptor(AppliedJobsActivity.this, dataModalArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            listView.setAdapter(adapter);
                            //listView.setAdapter(jobAdapter);
                        }
                        else{
                            //textView.setText("not empty");
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(AppliedJobsActivity.this, "Fail to load data.", Toast.LENGTH_SHORT).show();
                    }
                });



    }
}