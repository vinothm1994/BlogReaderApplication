package com.example.vinoth.blogreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinoth.blogreader.model.BlogPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mBlogRecycler;
    private DatabaseReference databaseReference;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(getApplication(),BlogpostActivity.class));
            }
        });
        mBlogRecycler=(RecyclerView)findViewById(R.id.recyclerview);
        mBlogRecycler.setHasFixedSize(true);
        //mBlogRecycler.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mBlogRecycler.setLayoutManager(mLayoutManager);
       // mBlogRecycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mBlogRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference dbRef = databaseReference.child("datas");
        FirebaseRecyclerAdapter<BlogPost,BlogViewHolder> adapter=new FirebaseRecyclerAdapter<BlogPost, BlogViewHolder>(
                BlogPost.class,
                R.layout.model_layot,
                BlogViewHolder.class,
                dbRef
        ) {


            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, BlogPost model, int position) {
                final String blogpostkey=getRef(position).getKey();
                viewHolder.setTitle(model.getBlogtitle());
                viewHolder.setImage(model.getUrl());

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent intent=new Intent(MainActivity.this,BlogPostDetailActivity.class);
                        intent.putExtra("BLOG_KEY",blogpostkey);
                        startActivity(intent);
                    }
                });

            }
        };

        mBlogRecycler.setAdapter(adapter);


    }

    public static  class BlogViewHolder extends RecyclerView.ViewHolder{

        View mview;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setTitle(String title){
            TextView textView=(TextView)mview.findViewById(R.id.tvtitle);
            textView.setText(title);

        }
        public void setImage(String urlv){
            Log.i("vinothmaadhu", "setImage: " +urlv );
            ImageView imageView=(ImageView)mview.findViewById(R.id.ivmodel);
            Picasso.with(mview.getContext())
                    .load(urlv).resize(600,400)
                    .centerCrop().placeholder(R.drawable.batman)
                    .into(imageView);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
