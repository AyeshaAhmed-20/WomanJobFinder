package com.example.womenjobfinder;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

// JobAdapter.java
public class JobAdaptor extends ArrayAdapter<JobModel> {

    public JobAdaptor(Context context, List<JobModel> jobs) {
        super(context, 0, jobs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.job_list_tile, parent, false);
        }



        JobModel job = getItem(position);

        if (job != null) {
            TextView titleTextView = convertView.findViewById(R.id.title);
            TextView descriptionTextView = convertView.findViewById(R.id.salary);

            titleTextView.setText(job.getTitle());
            descriptionTextView.setText(job.getCompany());
        }

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.

                *//*Intent intent = new Intent(getApp, HomePageActivity.class);
                startActivity(intent);*//*
            }
        });*/

        return convertView;
    }
}
