package com.example.womenjobfinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ListView listView;
    private SearchView searchView;
    ArrayList<JobModel> dataModalArrayList;
    private TextView textView;
    private ImageView myJobButton,logout;
    private FirebaseFirestore db;

    private JobAdaptor adapter;

    private View view;

    String[] cities ={
            "Islamabad", "Rawalpindi", "Karachi", "Lahore",  "Faisalabad",
            "Multan", "Peshawar", "Quetta", "Sialkot", "Gujranwala"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null)
        {
            view=inflater.inflate(R.layout.fragment_home, container,false);
        }
        else
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

        searchView=view.findViewById(R.id.searchView);
        listView = view.findViewById(R.id.jobListView);
        textView = view.findViewById(R.id.textViewWelcome);
        myJobButton = view.findViewById(R.id.myJobs);
        logout = view.findViewById(R.id.logoutButton);

        dataModalArrayList = new ArrayList<>();

        myJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppliedJobsActivity.class);

                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
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
                            adapter = new JobAdaptor(getActivity(), dataModalArrayList);

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
                        Toast.makeText(getActivity(), "Fail to load data.", Toast.LENGTH_SHORT).show();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobModel selectedJob = adapter.getItem(position);

                // Open a new activity with the selected job details
                Intent intent = new Intent(getActivity(), JobDetailView.class);
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
                db.collection("jobs")
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    adapter.clear();
                                    dataModalArrayList.clear();
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
                                        if(dataModal.getTitle().toLowerCase().contains(newText.toLowerCase())){
                                            dataModalArrayList.add(dataModal);
                                        }


                                        //Toast.makeText(HomePageActivity.this, dataModal.getId(), Toast.LENGTH_SHORT).show();

                                    }
                                    // after that we are passing our array list to our adapter class.
                                    adapter = new JobAdaptor(getActivity(), dataModalArrayList);

                                    // after passing this array list to our adapter
                                    // class we are setting our adapter to our list view.
                                    listView.setAdapter(adapter);
                                    //listView.setAdapter(jobAdapter);
                                }
                            }

                        });
                //adapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }
}