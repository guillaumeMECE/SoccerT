package com.ece.soccert.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ece.soccert.R;
import com.ece.soccert.database.DatabaseHelper;
import com.ece.soccert.database.model.Result;

import java.util.Objects;

public class DashboardFragment extends Fragment {

    private Button startEndMatch;
    private DatabaseHelper db;
    private long idResult;
    private Result actual_match;
    DashboardViewModel dashboardViewModel;
    int[] yellow;
    int[] red;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        db = new DatabaseHelper(getActivity());
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final TextView textViewScores = root.findViewById(R.id.scores);
        dashboardViewModel.getScores().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewScores.setText(s);
            }
        });

        final TextView textViewTeam1 = root.findViewById(R.id.team1);
        dashboardViewModel.getmT1().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewTeam1.setText(s);
            }
        });

        final TextView textViewTeam2 = root.findViewById(R.id.team2);
        dashboardViewModel.getmT2().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewTeam2.setText(s);
            }
        });

        final TextView textViewYellow = root.findViewById(R.id.yellow_nb);
        dashboardViewModel.getYellow().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewYellow.setText(s);
            }
        });

        final TextView textViewRed = root.findViewById(R.id.red_nb);
        dashboardViewModel.getRed().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewRed.setText(s);
            }
        });
        yellow= new int[]{0, 0};
        red= new int[]{0, 0};

        startEndMatch = root.findViewById(R.id.start_end_match);
        startEndMatch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("TAG", "onClick: TEST : "+startEndMatch.getText().equals(getText(R.string.startMatch)));
                if (startEndMatch.getText().equals(getText(R.string.startMatch))){
                    startEndMatch.setText(R.string.endMatch);
                    startEndMatch.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorRed));
                    idResult = db.insertResult(new String[]{"USA", "FR"}, new int[]{0, 0});
                    actual_match = db.getResult(idResult);
                    dashboardViewModel.setTeams(actual_match.getTeams());
                    db.insertStep((int) idResult,"START",2);
                }else{
                    startEndMatch.setText(R.string.startMatch);
                    startEndMatch.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorPrimary));
                    db.insertStep((int) idResult,"END",2);
                }
            }
        });

        Button addBtnGoalT1 = root.findViewById(R.id.add_btn_goal_t1);
        addBtnGoalT1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.insertStep((int) idResult,"GOAL",0);
                actual_match.setScores(new int[]{actual_match.getScores()[0] + 1, actual_match.getScores()[1]});
                db.updateResult(actual_match);
                dashboardViewModel.setScores(actual_match.getScores());
            }
        });

        Button addBtnGoalT2 = root.findViewById(R.id.add_btn_goal_t2);
        addBtnGoalT2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.insertStep((int) idResult,"GOAL",1);
                actual_match.setScores(new int[]{actual_match.getScores()[0], actual_match.getScores()[1] + 1});
                db.updateResult(actual_match);
                dashboardViewModel.setScores(actual_match.getScores());
            }
        });

        Button addBtnYellowT1 = root.findViewById(R.id.add_btn_yellow_t1);
        addBtnYellowT1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.insertStep((int) idResult,"YELLOW CARD",0);
                yellow[0]=yellow[0]+1;
                dashboardViewModel.setYellow(yellow);
            }
        });

        Button addBtnYellowT2 = root.findViewById(R.id.add_btn_yellow_t2);
        addBtnYellowT2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.insertStep((int) idResult,"YELLOW CARD",1);
                yellow[1]=yellow[1]+1;
                dashboardViewModel.setYellow(yellow);
            }
        });

        Button addBtnRedT1 = root.findViewById(R.id.add_btn_red_t1);
        addBtnRedT1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.insertStep((int) idResult,"RED CARD",0);
                red[0]=red[0]+1;
                dashboardViewModel.setRed(red);
            }
        });

        Button addBtnRedT2 = root.findViewById(R.id.add_btn_red_t2);
        addBtnRedT2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.insertStep((int) idResult,"RED CARD",1);
                red[1]=red[1]+1;
                dashboardViewModel.setRed(red);
            }
        });
        
        return root;
    }

    /**
     * Automaticaly end the match if it wasn't made manually
     */
    @Override
    public void onStop() {
        super.onStop();
        if(startEndMatch.getText().equals(getText(R.string.endMatch))){
            db.insertStep((int) idResult,"END",2);
        }
    }
}
