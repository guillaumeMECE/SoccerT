package com.ece.soccert.ui.results;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ece.soccert.R;
import com.ece.soccert.database.DatabaseHelper;
import com.ece.soccert.database.model.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment {

    private ResultsAdapter mAdapter;
    private ResultsViewModel resultsViewModel;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private List<Result> resultsList = new ArrayList<>();
    private TextView noResultsView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resultsViewModel =
                ViewModelProviders.of(this).get(ResultsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_results, container, false);
        noResultsView = root.findViewById(R.id.text_home);
        resultsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                noResultsView.setText(s);
            }
        });
        recyclerView = root.findViewById(R.id.recycler_view);
        db = new DatabaseHelper(getActivity());
        resultsList.addAll(db.getAllResults());
        mAdapter = new ResultsAdapter(getActivity(), resultsList);
        recyclerView.setAdapter(mAdapter);
        toggleEmptyResults();

        return root;
    }

    private void toggleEmptyResults() {
        // you can check notesList.size() > 0

        if (db.getResultsCount() > 0) {
            noResultsView.setVisibility(View.GONE);
        } else {
            noResultsView.setVisibility(View.VISIBLE);
        }
    }
}
