package com.vidalibarraquer.euf2_andres_alex;

import android.content.Context;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;


public class filmsAdapter extends RecyclerView.Adapter<filmsAdapter.MyViewHolder> {

    private ArrayList<HashMap<String, String>> DataSet;
    Context context;
    private ItemClickListener mClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView titleText;
        public ImageView coverImage;


        public MyViewHolder(View v) {
            super(v);
            this.titleText = (TextView) v.findViewById(R.id.titleText);
            this.coverImage = (ImageView) v.findViewById(R.id.coverImage);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            HashMap<String, String> Film = DataSet.get(getPosition());
            Toast.makeText(context, Film.get("title"), Toast.LENGTH_SHORT).show();
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);


            return true;
        }
    }

    public filmsAdapter(MainActivity context, ArrayList<HashMap<String, String>> myDataSet) {
        // Let's get the data from outside
        DataSet = myDataSet;

        this.context = context;
    }

    public void setItems(ArrayList<HashMap<String, String>> myDataSet) {
        DataSet = myDataSet;
    }

    @NonNull
    @Override
    public filmsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creates the view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_list_element, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, String> Film = DataSet.get(position);
        holder.titleText.setText(Film.get("title"));
        Glide.with(holder.coverImage.getContext())
                .load(Film.get("cover"))
                .into(holder.coverImage);
    }

    @Override
    public int getItemCount() {
        return DataSet.size();
    }

    // Permet clicsk
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Interfícies per implementar events click
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
