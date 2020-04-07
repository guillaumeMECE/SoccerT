package com.ece.soccert.ui.maps;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ece.soccert.R;
import com.ece.soccert.database.DatabaseHelper;
import com.ece.soccert.database.model.Result;
import com.ece.soccert.ui.highlights.HighlightAdapter;
import com.ece.soccert.ui.highlights.HighlightFragment;
import com.ece.soccert.ui.highlights.HighlightsViewModel;
import com.ece.soccert.ui.notifications.NotificationsViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private static final String ARG_SECTION_NUMBER = "section_number";
   // private mapsViewModel mapsViewModel;
   private GoogleMap mMap;
    private int idResult;
    private DatabaseHelper db;
    private Result result;

    public static MapsFragment newInstance(int index, int idResult) {
        MapsFragment fragment = new MapsFragment();
        Bundle bundle = new Bundle();
        Log.d("ID", "PlaceHolderFragMaps NEWINSTANCE: id = "+idResult);
        bundle.putInt(ARG_SECTION_NUMBER, idResult);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //HighlightsViewModel pageViewModel = ViewModelProviders.of(this).get(HighlightsViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Log.d("ID", "PlaceHolderFragHighlight ONCREATE: id = "+index);
        idResult = index;
        //pageViewModel.setIndex(index);
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
        View root = inflater.inflate(R.layout.activity_maps, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        db = new DatabaseHelper(getActivity());
        result = db.getResult(idResult);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        return root;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(result.getLatitude(), result.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));
    }
}