package com.example.vinoth.blogreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinoth.blogreader.model.BlogPost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BlogPostDetailActivity extends AppCompatActivity {

    DatabaseReference reference;
    private ImageView imageView;
    private TextView title;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_post_detail);
         imageView=(ImageView)findViewById(R.id.ivblogimag);
         title=(TextView)findViewById(R.id.tvblogtitle);
         desc=(TextView)findViewById(R.id.tvblogdesc);
        String key = getIntent().getExtras().getString("BLOG_KEY");
        reference= FirebaseDatabase.getInstance().getReference("datas");
        reference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BlogPost blogPost=dataSnapshot.getValue(BlogPost.class);
                imageView.setImageResource(R.drawable.batman);
                String dourl = blogPost.getUrl();
                title.setText(blogPost.getBlogtitle());
                desc.setText(blogPost.getDescription());
                Picasso.with(BlogPostDetailActivity.this).load(dourl).placeholder(R.drawable.batman).fit().into(imageView);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
