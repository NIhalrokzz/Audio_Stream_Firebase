package com.example.login;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {
    private List<Audio> moviesList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("audio");

    online m = new online();
    Context a;
    int[] colors;
    MediaPlayer mediaPlayer;
    Random r = new Random();
    int low = 0;
    int high = 4;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public Button diet,exercise,symptoms,pause;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            diet = (Button) view.findViewById(R.id.diet);
            exercise = (Button) view.findViewById(R.id.exercise);
            symptoms = (Button) view.findViewById(R.id.symptom);
            pause = (Button) view.findViewById(R.id.pause);
            imageView = (ImageView) view.findViewById(R.id.imageview);
        }
    }


    public AudioAdapter(List<Audio> moviesList, Context c) {
        this.moviesList = moviesList;

        a = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new AudioAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Audio movie = moviesList.get(position);

        int result = r.nextInt(high-low) + low;
        colors = a.getResources().getIntArray(R.array.color);

        holder.imageView.setBackgroundColor(colors[result]);
        Log.e("Diet",moviesList.get(position).getdieturl());
        holder.title.setText(movie.getname());

        holder.diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Diet",moviesList.get(position).getdieturl());
                mediaPlayer = MediaPlayer.create(a, Uri.parse(moviesList.get(position).getdieturl()));
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });

            }
        });
        holder.exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(a, Uri.parse(moviesList.get(position).getexerciseurl()));
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
            }
        });
        holder.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.release();
            }
        });
        holder.symptoms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(a, Uri.parse(moviesList.get(position).getsymptomsurl()));
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


}
