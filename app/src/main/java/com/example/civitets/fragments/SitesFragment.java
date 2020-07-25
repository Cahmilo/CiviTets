package com.example.civitets.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.civitets.R;
import com.example.civitets.adapters.SitesAdapter;
import com.example.civitets.clases.Sites;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SitesFragment extends Fragment {

    public int id;
    public View miView;
    private LinearLayoutManager layoutManager;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    RecyclerView recyclerViewPlaces;
    private Sites sites;
    private SitesAdapter adaptador;
    private ArrayList<Sites> arraySites;
    private TextView txtNoFound;
    float zoomLevel = 11.0f;

    public SitesFragment() {
    }

    public static SitesFragment newInstance() {
        return new SitesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s) {
        miView = inflater.inflate(R.layout.fragment_sites, container, false);
        txtNoFound = (TextView) miView.findViewById(R.id.txt_nofound);
        recyclerViewPlaces = (RecyclerView) miView.findViewById(R.id.rview_places);

        //get Data the MainActivity
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            arraySites = (ArrayList<Sites>) extras.getSerializable("arraySites");
        }

        if (arraySites.size() == 0) {
            recyclerViewPlaces.setVisibility(View.GONE);
            txtNoFound.setVisibility(View.VISIBLE);
        } else {

            if (mapFragment == null) {
                mapFragment = SupportMapFragment.newInstance();
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {

                        LatLng latLng = null;
                        for (int i = 0; i < arraySites.size(); i++) {
                            latLng = new LatLng(arraySites.get(i).getUser_lat(), arraySites.get(i).getUser_lng());
                            googleMap.addMarker(new MarkerOptions().position(latLng)
                                    .title(arraySites.get(i).getName()));

                        }
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
                    }
                });
            }
            getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

            recyclerViewPlaces.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerViewPlaces.setLayoutManager(new LinearLayoutManager(miView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewPlaces.setItemAnimator(new DefaultItemAnimator());
            adaptador = new SitesAdapter(arraySites);
            recyclerViewPlaces.setAdapter(adaptador);

        }

        return miView;
    }

}
