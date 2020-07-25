package com.example.civitets.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.civitets.R;
import com.example.civitets.adapters.OffersAdapter;
import com.example.civitets.clases.Offers;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OffersFragment extends Fragment {

    public int id;
    public View miView;
    RecyclerView recyclerViewOffers;
    private LinearLayoutManager layoutManager;
    private Offers offers;
    private OffersAdapter adaptador;
    private ArrayList<Offers> arrayOffers;
    private TextView txtNoOffers;

    public OffersFragment() {
    }

    public OffersFragment(int id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s) {

        miView= inflater.inflate(R.layout.fragment_offers, container, false);
        txtNoOffers = (TextView) miView.findViewById(R.id.no_found_offers);
        recyclerViewOffers = (RecyclerView) miView.findViewById(R.id.rview_offers);

        //get Data the MainActivity
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null)
        {
            arrayOffers = (ArrayList<Offers>) extras.getSerializable("arrayOffers");
        }

        if(arrayOffers.size()==0){
            txtNoOffers.setVisibility(View.VISIBLE);
            recyclerViewOffers.setVisibility(View.GONE);
        } else{
            recyclerViewOffers.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerViewOffers.setLayoutManager(new LinearLayoutManager(miView.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerViewOffers.setItemAnimator(new DefaultItemAnimator());

            adaptador = new OffersAdapter(arrayOffers);
            recyclerViewOffers.setAdapter(adaptador);
        }

        return miView;
    }

}

