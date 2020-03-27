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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ece.soccert.R;
import com.ece.soccert.database.DatabaseHelper;
import com.ece.soccert.database.model.Result;
import com.ece.soccert.utils.MyDividerItemDecoration;
import com.ece.soccert.utils.RecyclerTouchListener;

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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
        createNote();
        toggleEmptyResults();
        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                deleteResult(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        return root;
    }

    /**
     * Inserting new note in db
     * and refreshing the list
     */
    private void createNote() {
        // inserting note in db and getting
        // newly inserted note id
       long id = db.insertResult(new String[]{"PSG", "OL"}, new Integer[]{2, 1});

        // get the newly inserted note from db
        Result r = db.getResult(id);

        if (r != null) {
            // adding new note to array list at 0 position
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

        // removing the note from the list
        resultsList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyResults();
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
