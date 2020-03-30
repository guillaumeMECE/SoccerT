package com.ece.soccert.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ece.soccert.R;
import com.ece.soccert.database.DatabaseHelper;
import com.ece.soccert.database.model.Result;

public class StatsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private StatsViewModel pageViewModel;
    private DatabaseHelper db;
    private int idResult;


    public static StatsFragment newInstance(int index, int idResult) {
        StatsFragment fragment = new StatsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, idResult);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(StatsViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            idResult = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        db = new DatabaseHelper(getActivity());

        Result result = db.getResult(idResult);
        final TextView textViewTeam1 = root.findViewById(R.id.team1);
        textViewTeam1.setText(result.getTeams()[0]);
        final TextView textViewTeam2 = root.findViewById(R.id.team2);
        textViewTeam2.setText(result.getTeams()[1]);

        int[] yellow = db.getStepYellow(idResult);
        int[] red= db.getStepRed(idResult);

        final TextView textViewYellow1 = root.findViewById(R.id.yellow1);
        textViewYellow1.setText(String.valueOf(yellow[0]));
        final TextView textViewYellow2 = root.findViewById(R.id.yellow2);
        textViewYellow2.setText(String.valueOf(yellow[1]));

        final TextView textViewRed1 = root.findViewById(R.id.red1);
        textViewRed1.setText(String.valueOf(red[0]));
        final TextView textViewRed2 = root.findViewById(R.id.red2);
        textViewRed2.setText(String.valueOf(red[1]));

        return root;
    }
}