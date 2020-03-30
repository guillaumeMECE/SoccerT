package com.ece.soccert.ui.highlights;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ece.soccert.R;
import com.ece.soccert.database.model.Result;
import com.ece.soccert.database.model.Step;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.MyViewHolder> {

    private Context context;
    private List<Step> stepsList;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView timestamp;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            timestamp = view.findViewById(R.id.time);
        }
    }


    HighlightAdapter(Context context, List<Step> stepsList) {
        this.context = context;
        this.stepsList = stepsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * Apply value to the layout
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Step step = stepsList.get(position);
        holder.name.setText(step.getName());
        if (step.getTeam() == 0){
            holder.name.setGravity(Gravity.START);
        }else if(step.getTeam()==1){
            holder.name.setGravity(Gravity.END);
        }


        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(step.getTimestamp()));
    }

    /**
     * Number of item in recyclerview
     * @return
     */
    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d("TAG", "formatDate: "+dateStr);
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm:ss");
            return fmtOut.format(date);
        } catch (ParseException ignored) {

        }

        return "";
    }
}
