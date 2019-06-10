package com.example.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    final Context context = this;

    private String mLanguageCode = "hi";
    private MediaPlayer mediaPlayer;
    private TextView toolBarBtn;
    private SwipeRefreshLayout pullToRefresh;
    private MoviesAdapter moviesAd;

    private static final int REQUEST_CODE_READ_EXTERNAL_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieList.clear();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        mediaPlayer = new MediaPlayer();
        setSupportActionBar(toolbar);



        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieList.clear();
                onResume();
                pullToRefresh.setRefreshing(false);
            }
        });

        toolBarBtn = (TextView)findViewById(R.id.toolbarbtn);
        toolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),
                        "Button in ToolBar clicked",
                        Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

        toolBarBtn = (TextView) findViewById(R.id.online);
        toolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
            }
        });



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(movieList, this);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        recyclerView.setAdapter(mAdapter);

        //recyclerView.

    }

    public void checkConnection(){
            if(isOnline()){
                startActivity(new Intent(MainActivity.this,online.class));
                //Toast.makeText(MainActivity.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            }
        }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onResume(){
        super.onResume();

        prepareMovieData();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == 1){

            if(resultCode == RESULT_OK){

                //the selected audio.
                Uri uri = data.getData();
                Toast.makeText(MainActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Prepares sample data to provide data set to adapter
     */
    private void prepareMovieData() {

        Movie movie = new Movie(R.string.pregnancy,R.raw.pregnancydiet,R.raw.pregnancyexercise,R.raw.pregnancysymptoms);
        movieList.add(movie);
        movie = new Movie(R.string.bp,R.raw.bpdiet,R.raw.bpexercise,R.raw.bpsymptoms);
        movieList.add(movie);
        movie = new Movie(R.string.diabetes,R.raw.diabetesdiet,R.raw.diabetesexercise,R.raw.diabetessymptoms);
        movieList.add(movie);
        movie = new Movie(R.string.malaria,R.raw.malariadiet,0,R.raw.maleriasymptoms);
        movieList.add(movie);
        movie = new Movie(R.string.thyroid,R.raw.thioridediet,R.raw.thiordeexercise,R.raw.thioridesymptoms);
        movieList.add(movie);

        Log.e("AYAt","Name"+movieList.get(0).getname()+"Diet"+movieList.get(1).getdieturl());

        mAdapter.notifyDataSetChanged();
    }

}
