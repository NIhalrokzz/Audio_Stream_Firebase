package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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



public class online extends AppCompatActivity {


    private List<Audio> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AudioAdapter mAdapter;
    private FloatingActionButton fab;
    final Context context = this;
    private MediaPlayer mediaPlayer;
    private TextView toolBarBtn;
    private SwipeRefreshLayout pullToRefresh;

    private static final int REQUEST_CODE_READ_EXTERNAL_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        movieList.clear();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        mediaPlayer = new MediaPlayer();
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(online.this,Main2Activity.class));
            }
        });

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
                startActivity(new Intent(online.this,LoginActivity.class));
            }
        });

        toolBarBtn = (TextView) findViewById(R.id.offline);
        toolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(online.this,MainActivity.class));
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new AudioAdapter(movieList, this);

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
                Toast.makeText(online.this,uri.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Prepares sample data to provide data set to adapter
     */
    private void prepareMovieData() {
        // movieList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("audio");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Audio audio = dataSnapshot1.getValue(Audio.class);
                    movieList.add(audio);
                }
                mAdapter = new AudioAdapter(movieList,online.this);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                Log.e("AYA","Name"+movieList.get(0).getname()+"Diet"+movieList.get(0).getdieturl());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        mAdapter.notifyDataSetChanged();
    }

    public void pause() {
        mediaPlayer.release();
    }
}
