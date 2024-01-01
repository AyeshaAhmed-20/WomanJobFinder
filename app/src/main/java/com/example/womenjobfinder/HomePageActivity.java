
package com.example.womenjobfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends AppCompatActivity {

    private ListView listView;
    private SearchView searchView;
    ArrayList<JobModel> dataModalArrayList;
    private TextView textView;
    private ImageView myJobButton,logout;
    private FirebaseFirestore db;

    private JobAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        searchView=findViewById(R.id.searchView);
        listView = findViewById(R.id.jobListView);
        textView = findViewById(R.id.textViewWelcome);
        myJobButton = findViewById(R.id.myJobs);
        logout = findViewById(R.id.logoutButton);

        dataModalArrayList = new ArrayList<>();

        myJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, AppliedJobsActivity.class);

                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                finish();
            }
        });

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
                                d.getId();

                                // after getting this list we are passing
                                // that list to our object class.
                                //textView.setText(d.toString());
                                JobModel dataModal = d.toObject(JobModel.class);
                                dataModal.setId(d.getId());
                                // after getting data from Firebase we are
                                // storing that data in our array list
                                dataModalArrayList.add(dataModal);

                                //Toast.makeText(HomePageActivity.this, dataModal.getId(), Toast.LENGTH_SHORT).show();

                            }
                            // after that we are passing our array list to our adapter class.
                            adapter = new JobAdaptor(HomePageActivity.this, dataModalArrayList);

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
                        Toast.makeText(HomePageActivity.this, "Fail to load data.", Toast.LENGTH_SHORT).show();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobModel selectedJob = adapter.getItem(position);

                // Open a new activity with the selected job details
                Intent intent = new Intent(HomePageActivity.this, JobDetailView.class);
                intent.putExtra("job", selectedJob);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}