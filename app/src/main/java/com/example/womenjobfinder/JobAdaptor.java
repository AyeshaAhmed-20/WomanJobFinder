package com.example.womenjobfinder;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        JobModel job = getItem(position);

        if (job != null) {
            TextView titleTextView = convertView.findViewById(android.R.id.text1);
            TextView descriptionTextView = convertView.findViewById(android.R.id.text2);

            titleTextView.setText(job.getTitle());
            descriptionTextView.setText(job.getSalary());
        }

        return convertView;
    }
}
