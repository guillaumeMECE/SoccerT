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

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Button startEndMatch;
    private DatabaseHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        db = new DatabaseHelper(getActivity());
        final TextView textView = root.findViewById(R.id.text_dashboard);
       // startendmatch = root.findViewById(R.id.startEndMatch);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        startEndMatch = root.findViewById(R.id.start_end_match);
        startEndMatch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("TAG", "onClick: TEST : "+startEndMatch.getText().equals(getText(R.string.startMatch)));
                if (startEndMatch.getText().equals(getText(R.string.startMatch))){
                    startEndMatch.setText(R.string.endMatch);
                    startEndMatch.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                    long idResult = db.insertResult(new String[]{"USA", "FR"}, new int[]{3, 7});
                    db.insertStep((int) idResult,"START",2);
                }else{
                    startEndMatch.setText(R.string.startMatch);
                    startEndMatch.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                }
            }
        });
        return root;
    }

}
