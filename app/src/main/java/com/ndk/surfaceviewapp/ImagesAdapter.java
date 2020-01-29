package com.ndk.surfaceviewapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesAdapter extends  RecyclerView.Adapter<ImagesAdapter.MyViewHolder>{

private List<ImagesCaptured> moviesList;
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


    public ImagesAdapter(Context context, List<ImagesCaptured> moviesList) {
        this.moviesList = moviesList;
        this.context=context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ImagesCaptured movie = moviesList.get(position);

        //Picasso.get().load(movie.getImage()).into(holder.img);
       // holder.img.setRotation(l);


        //Imgae Rotaion code////////////////////////////////////////
        Bitmap yourSelectedImage= movie.getImage();
        Matrix mat = new Matrix();
        mat.postRotate((90)); //degree how much you rotate i rotate 270
        Bitmap bMapRotate=Bitmap.createBitmap(yourSelectedImage, 0,0,yourSelectedImage.getWidth(),yourSelectedImage.getHeight(), mat, true);
        holder.img.setImageBitmap(bMapRotate);
        holder.txt_count.setText(""+(position+1));
        holder.fab_close_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                    removeAt(position);
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
