package com.example.womenjobfinder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

// JobAdapter.java
public class AppliedJobAdaptor extends ArrayAdapter<ResumeModel> {

    public AppliedJobAdaptor(Context context, List<ResumeModel> jobs) {
        super(context, 0, jobs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.applied_job_list_tile, parent, false);
        }



        ResumeModel job = getItem(position);

        if (job != null) {
            TextView titleTextView = convertView.findViewById(R.id.title);
            TextView descriptionTextView = convertView.findViewById(R.id.appliedOn);
            Button viewResume = convertView.findViewById(R.id.viewResume);

            titleTextView.setText(job.getTitle());
            descriptionTextView.setText(job.getDate());
            View finalConvertView = convertView;
            viewResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse( "http://docs.google.com/viewer?url=" + job.getUrl()), "text/html");

                    finalConvertView.getContext().startActivity(intent);



                }
            });

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

   /* @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterPattern = constraint.toString().toLowerCase().trim();
                List<JobModel> filteredList = new ArrayList<>();
                for (JobModel item : originalData) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData.clear();
                filteredData.addAll((List<JobModel>) results.values);
                notifyDataSetChanged();
            }
        };
    }*/
}
