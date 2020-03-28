package com.ece.soccert.ui.highlights;

import android.os.Bundle;
import android.util.Log;
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
import com.ece.soccert.database.model.Step;
import com.ece.soccert.ui.results.ResultsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaceholderFragmentHighlight extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private HighlightsViewModel pageViewModel;
    private HighlightAdapter mAdapter;
    private DatabaseHelper db;
    private List<Step> stepsList = new ArrayList<>();
    private int idResult;

    public static PlaceholderFragmentHighlight newInstance(int index,int idResult) {
        PlaceholderFragmentHighlight fragment = new PlaceholderFragmentHighlight();
        Bundle bundle = new Bundle();
        Log.d("ID", "PlaceHolderFragHighlight NEWINSTANCE: id = "+idResult);
        bundle.putInt(ARG_SECTION_NUMBER, idResult);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(HighlightsViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Log.d("ID", "PlaceHolderFragHighlight ONCREATE: id = "+index);
        idResult = index;
        pageViewModel.setIndex(index);
    }
   /* ResultsViewModel resultsViewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
    View root = inflater.inflate(R.layout.fragment_results, container, false);
    noResultsView = root.findViewById(R.id.text_home);
        resultsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            noResultsView.setText(s);
        }
    });*/
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_highlights, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("ID", "PlaceHolderFragHighlight ONCHANGED: id = "+Objects.requireNonNull(s));
                idResult = Integer.parseInt(Objects.requireNonNull(s));
            }
        });
        Log.d("ID", "PlaceHolderFragHighlight ONCREATEVIEW: id = "+idResult);
        //getmIndex
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_highlight);
        db = new DatabaseHelper(getActivity());
        stepsList.addAll(db.getStepHistory(idResult));
        mAdapter = new HighlightAdapter(getActivity(), stepsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
        return root;
    }
}