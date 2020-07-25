package com.example.civitets.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.civitets.R;
import com.example.civitets.clases.Sites;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class SitesAdapter extends RecyclerView.Adapter<SitesAdapter.TitularesViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Sites> datos;

    public static class TitularesViewHolder extends RecyclerView.ViewHolder {

        private TextView txtSite;


        public TitularesViewHolder(View itemView) {
            super(itemView);
            txtSite = (TextView) itemView.findViewById(R.id.txt_site);
        }

        public void bindTitular(Sites t) {
            txtSite.setText(t.getName().replace("\"", ""));
        }
    }

    public SitesAdapter(ArrayList<Sites> datos) {
        this.datos = datos;
    }

    @Override
    public SitesAdapter.TitularesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_sites, viewGroup, false);
        itemView.setOnClickListener(this);
        SitesAdapter.TitularesViewHolder tvh = new SitesAdapter.TitularesViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(final SitesAdapter.TitularesViewHolder viewHolder, int pos) {
        Sites item = datos.get(pos);
        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }

}
