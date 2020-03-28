package com.ece.soccert.ui.results;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ece.soccert.R;
import com.ece.soccert.database.DatabaseHelper;
import com.ece.soccert.database.model.Result;
import com.ece.soccert.utils.MyDividerItemDecoration;
import com.ece.soccert.utils.RecyclerTouchListener;
import com.ece.soccert.view.StatsActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultsFragment extends Fragment {

    private ResultsAdapter mAdapter;
    private DatabaseHelper db;
    private List<Result> resultsList = new ArrayList<>();
    private TextView noResultsView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ResultsViewModel resultsViewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_results, container, false);
        noResultsView = root.findViewById(R.id.text_home);
        resultsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                noResultsView.setText(s);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        db = new DatabaseHelper(getActivity());
        resultsList.addAll(db.getAllResults());
        mAdapter = new ResultsAdapter(getActivity(), resultsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext());
        Log.d("TAG", "onCreateView: "+Objects.requireNonNull(getActivity()).getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
       // createResult();
        toggleEmptyResults();

        /**
          On long press on RecyclerView item, open alert dialog
          with options to choose
          Edit and Delete
          */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Result result = resultsList.get(position);
                Intent intent = new Intent(getActivity(), StatsActivity.class);
                intent.putExtra("Result", result);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {showActionsDialog(position); }
        }));
        return root;
    }

    /**
     * Opens dialog with Delete options
     * Delete
     */
    private void showActionsDialog(final int position) {
        new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
                .setTitle(R.string.deleteResultAlertBox)
                .setPositiveButton(R.string.deleteResultAlertBoxBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            deleteResult(position);
                    }
                })
                .show();
    }

    /**
     * Inserting new result in db
     * and refreshing the list
     */
    private void createResult() {
        // inserting result in db and getting
        // newly inserted result id
       long id = db.insertResult(new String[]{"PSG", "OL"}, new int[]{2, 1});

        // get the newly inserted result from db
        Result r = db.getResult(id);

        if (r != null) {
            // adding new result to array list at 0 position
            resultsList.add(0, r);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

            toggleEmptyResults();
        }
    }

    /**
     * Deleting note from SQLite and removing the
     * item from the list by its position
     */
    private void deleteResult(int position) {
        // deleting the note from db
        db.deleteResult(resultsList.get(position));

        // removing the result from the list
        resultsList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyResults();
    }

    private void toggleEmptyResults() {

        if (db.getResultsCount() > 0) {
            noResultsView.setVisibility(View.GONE);
        } else {
            noResultsView.setVisibility(View.VISIBLE);
        }
    }
}
