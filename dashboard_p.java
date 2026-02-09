package com.example.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class dashboard_p extends AppCompatActivity {
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    ImageButton imageView0;
    ViewFlipper flipper;
    private LottieAnimationView lottieAnimationView;
    private FirebaseAuth firebaseAuth;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navi);
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
             int id = menuItem.getItemId();

        if (id == R.id.feedback) {
                         Intent i = new Intent(dashboard_p.this,Feedback.class);
            startActivity(i);
        }

                if(id == R.id.dashboard){
                    Intent i = new Intent(dashboard_p.this,dashboard_p.class);
                    startActivity(i);
                }
                if(id == R.id.history){
                    Intent i = new Intent(dashboard_p.this,diet_history.class);
                    startActivity(i);
                }
                if(id == R.id.rf){
                    Intent i = new Intent(dashboard_p.this,Refresh.class);
                    startActivity(i);
                }
        if(id == R.id.about){
            Intent i = new Intent(dashboard_p.this,about.class);
            startActivity(i);
        }
        if(id == R.id.cnt){
            Intent i = new Intent(dashboard_p.this,contactUs.class);
            startActivity(i);
        }
        if (id == R.id.logout) {
            Logout();
        }

         drawerLayout.closeDrawer(GravityCompat.START);
         return  true;
         }
          });


        firebaseAuth = FirebaseAuth.getInstance();
        flipper=findViewById(R.id.flipper);
        int imgarray[]={R.drawable.histroty,R.drawable.bg,R.drawable.diabtese_text_screen,R.drawable.goal,R.drawable.food1,R.drawable.food2};
        for(int i=0;i<imgarray.length;i++)
            showimage(imgarray[i]);
        lottieAnimationView=findViewById(R.id.post);
        imageView0 = findViewById(R.id.diet);
        imageView1 = findViewById(R.id.nutrition);
        imageView2 = findViewById(R.id.diethistory);
        imageView3 = findViewById(R.id.post);
        imageView4 = findViewById(R.id.profile);
        imageView5 = findViewById(R.id.feedback);

        imageView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(dashboard_p.this,Dietform.class);
                startActivity(intent1);
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent i =new Intent(dashboard_p.this,doctorview.class);
             startActivity(i);
        }
    });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_p.this,diet_history.class);
                startActivity(intent);
            }
        });

        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_p.this,Viewpost.class);
                startActivity(intent);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_p.this,user_profile.class);
                startActivity(intent);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_p.this,Feedback.class);
                startActivity(intent);
            }
        });

    }

    public void showimage(int img){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(img);
        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);
        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    //================Menu Item
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.sidebar,menu);
//        return  true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.feedback) {
//            Intent i = new Intent(dashboard_p.this,Feedback.class);
//            startActivity(i);
//        }
//        if (id == R.id.healthy) {
//            Intent i = new Intent(dashboard_p.this,Viewpost.class);
//            startActivity(i);
//        }
//        if (id == R.id.rf) {
//            Intent i =new Intent(dashboard_p.this, Refresh.class);
//            startActivity(i);
//        }
//        if(id == R.id.about){
//            Intent i = new Intent(dashboard_p.this,about.class);
//            startActivity(i);
//        }
//        if(id == R.id.cnt){
//            Intent i = new Intent(dashboard_p.this,contactUs.class);
//            startActivity(i);
//        }
//        if (id == R.id.logout) {
//            Logout();
//        }
//    return  true;
//    }

    private void Logout() {
//        firebaseAuth.signOut();
        firebaseAuth.getInstance().signOut();
        //--------------------------------------
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("isUserLogin");
        editor.commit();
        //---------------------------------------
        finish();
    }


}