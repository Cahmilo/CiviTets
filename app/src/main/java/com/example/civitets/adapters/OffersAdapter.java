package com.example.civitets.adapters;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.civitets.R;
import com.example.civitets.clases.Offers;
import java.io.InputStream;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.TitularesViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
     private ArrayList<Offers> datos;

    public static class TitularesViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtInfo;
        private ImageView imgvSale;


        public TitularesViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txt_sale_name);
            txtInfo = (TextView) itemView.findViewById(R.id.txt_sale_info);
            imgvSale = (ImageView) itemView.findViewById(R.id.imgv_sale);

        }

        public void bindTitular(Offers t) {

            txtName.setText(t.getName().replace("\"", ""));
            txtInfo.setText(t.getInfo().replace("\"", ""));
            String image = t.getImagen().replace("\"", "");

            //call Asyntask
            new DownloadImageTask(imgvSale)
                    .execute(image);
        }
    }

    public OffersAdapter(ArrayList<Offers> datos) {
        this.datos = datos;
    }

    @Override
    public OffersAdapter.TitularesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_offers, viewGroup, false);
        itemView.setOnClickListener(this);
        OffersAdapter.TitularesViewHolder tvh = new OffersAdapter.TitularesViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(final OffersAdapter.TitularesViewHolder viewHolder, int pos) {
        Offers item = datos.get(pos);
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

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
