package com.example.login;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Random;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {


    private List<Movie> moviesList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("audio");

    MainActivity m = new MainActivity();
    int[] colors;
    Context a;
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


    public MoviesAdapter(List<Movie> moviesList, Context c) {
        this.moviesList = moviesList;

        a = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Movie movie = moviesList.get(position);
        int result = r.nextInt(high-low) + low;
        colors = a.getResources().getIntArray(R.array.color);


        holder.imageView.setBackgroundColor(colors[result]);

        holder.title.setText(movie.getname());

        holder.diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    mediaPlayer = MediaPlayer.create(a, movie.getdieturl());

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
                mediaPlayer = MediaPlayer.create(a, movie.getExcersiseurl());

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
                mediaPlayer = MediaPlayer.create(a, movie.getSymptomsurl());

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
