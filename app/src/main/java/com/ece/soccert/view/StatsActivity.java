package com.ece.soccert.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ece.soccert.R;
import com.ece.soccert.database.model.Result;
import com.ece.soccert.utils.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class StatsActivity extends AppCompatActivity {

    private TextView team1;
    private TextView team2;
    private TextView scores;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_tabs);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        Result result = intent.getParcelableExtra("Result");
        Log.d("TAG_intent", "onCreate: "+ Objects.requireNonNull(result).getTeams()[0]);

        // Apply change to top card
        updateUI(result);

    }

    private void updateUI(Result result) {
        team1 = findViewById(R.id.team1);
        team1.setText(result.getTeams()[0]);
        team2 = findViewById(R.id.team2);
        team2.setText(result.getTeams()[1]);
        scores = findViewById(R.id.scores);
        scores.setText(result.getScores()[0]+" - "+result.getScores()[1]);
        date = findViewById(R.id.date);
        date.setText(formatDate(result.getTimestamp()));
    }

    /**
     * Formatting timestamp to `MMM d - HH:mm` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21 - 00:42
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d("TAG", "formatDate: "+dateStr);
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d - HH:mm");
            return fmtOut.format(Objects.requireNonNull(date));
        } catch (ParseException ignored) {

        }

        return "";
    }

}

