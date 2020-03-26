package com.ece.soccert.ui.results;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ece.soccert.R;
import com.ece.soccert.database.model.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//package info.androidhive.sqlite.view;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder> {

    private Context context;
    private List<Result> resultsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView team1;
        public TextView team2;
        public TextView scores;
        //public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            team1 = view.findViewById(R.id.team1);
            scores = view.findViewById(R.id.scores);
            team2 = view.findViewById(R.id.team2);
           // timestamp = view.findViewById(R.id.timestamp);
        }
    }


    public ResultsAdapter(Context context, List<Result> resultsList) {
        this.context = context;
        this.resultsList = resultsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Result result = resultsList.get(position);
        holder.team1.setText(result.getTeams()[0]);
        holder.scores.setText(result.getScores()[0]+" - "+result.getScores()[1]);
        holder.team2.setText(result.getTeams()[1]);

        // Displaying dot from HTML character code
        //holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        //holder.timestamp.setText(formatDate(result.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d",Locale.FRANCE);
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
