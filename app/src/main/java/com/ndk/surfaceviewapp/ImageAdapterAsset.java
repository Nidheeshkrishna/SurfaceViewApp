package com.ndk.surfaceviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ImageAdapterAsset extends  RecyclerView.Adapter<ImageAdapterAsset.MyViewHolder> {
    private List<ImgesAssetLoan> moviesList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        TextView txt_count;
        FloatingActionButton fab_close_btn;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.imageview);
            fab_close_btn=(FloatingActionButton)view.findViewById(R.id.fab_close_btn);
            txt_count=(TextView)view.findViewById(R.id.txt_count);
            //genre = (TextView) view.findViewById(R.id.genre);
            // year = (TextView) view.findViewById(R.id.year);
        }

    }


    public ImageAdapterAsset(Context context, List<ImgesAssetLoan> moviesList) {
        this.moviesList = moviesList;
        this.context=context;
    }



    @NonNull
    @Override
    public ImageAdapterAsset.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_row_asset, parent, false);

        return new ImageAdapterAsset.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageAdapterAsset.MyViewHolder holder, final int position) {

        ImgesAssetLoan movie = moviesList.get(position);
        holder.img.setImageBitmap(movie.getImage());
        int count=moviesList.size();

        holder.txt_count.setText(""+(position+1));

        holder.fab_close_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                //moviesList.remove(moviesList.get(position));
                removeAt(position);


                //notifyDataSetChanged();



            }
        });



    }
    //holder.genre.setText(movie.getGenre());
    //holder.year.setText(movie.getYear());


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void removeAt(int position) {
        moviesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, moviesList.size());
    }
}
