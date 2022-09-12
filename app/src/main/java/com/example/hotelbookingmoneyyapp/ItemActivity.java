package com.example.hotelbookingmoneyyapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    Animation from_left, from_right, from_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ImageView second_back_arrow = findViewById(R.id.second_back_arrow);
        ImageView second_arrow_up = findViewById(R.id.seconf_arrow_up);
        TextView Name = findViewById(R.id.second_title);
        TextView Des = findViewById(R.id.second_subtitle);
        TextView more_details = findViewById(R.id.more_details);
        RatingBar ratingBar = findViewById(R.id.second_ratingbar);

        Name.setText(getIntent().getStringExtra("NAME"));
        Des.setText(getIntent().getStringExtra("DES"));
        int stars = 0;
        try {
            stars = Integer.parseInt(getIntent().getStringExtra("STARS"));
        } catch (Exception e) {
            ratingBar.setRating(0);
        }

        ratingBar.setRating(stars);

        second_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //Hide status bar and navigation bar at the bottom
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        this.getWindow().getDecorView().setSystemUiVisibility(

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );


        //Load Animations
        from_left = AnimationUtils.loadAnimation(this, R.anim.anim_from_left);
        from_right = AnimationUtils.loadAnimation(this, R.anim.anim_from_right);
        from_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);



        //Set Animations
        second_back_arrow.setAnimation(from_left);
        Name.setAnimation(from_right);
        Des.setAnimation(from_right);
        ratingBar.setAnimation(from_left);
        second_arrow_up.setAnimation(from_bottom);
        more_details.setAnimation(from_bottom);



        second_arrow_up.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemActivity.this, Reviews.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(second_arrow_up, "background_image_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ItemActivity.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });
    }
}