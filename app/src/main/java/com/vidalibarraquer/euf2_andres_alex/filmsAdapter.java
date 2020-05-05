package com.vidalibarraquer.euf2_andres_alex;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class filmsAdapter extends RecyclerView.Adapter<filmsAdapter.MyViewHolder> {

    private final ArrayList<HashMap<String, String>> DataSet;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public ImageView coverImage;

        public MyViewHolder(View v) {
            super(v);

            titleText = (TextView) v.findViewById(R.id.titleText);
            coverImage = (ImageView) v.findViewById(R.id.coverImage);
        }

    }

    public filmsAdapter(MainActivity context, ArrayList<HashMap<String,String>> myDataSet){
        // Let's get the data from outside
        DataSet = myDataSet;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creates the view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.film_list_element, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String,String> Film = DataSet.get(position);
        holder.titleText.setText(Film.get("title"));
        holder.coverImage.setImageBitmap(null);
        new DownloadImageTask(holder.coverImage).execute((Film.get("cover")));
    }

    @Override
    public int getItemCount() {
        return DataSet.size();
    }
}
