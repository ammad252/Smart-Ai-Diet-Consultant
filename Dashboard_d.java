package com.example.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class Dashboard_d extends AppCompatActivity {
    private LottieAnimationView lottieAnimationView,aidiet,fb;
    ViewFlipper flipper;
    private FirebaseAuth firebaseAuth;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_d);
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

                if (id == R.id.signUpAdmin) {
        Intent i =new Intent(Dashboard_d.this,CreateNAdmin.class);
        startActivity(i);
        }
        if (id == R.id.feedback) {
            Intent i =new Intent(Dashboard_d.this,Feedback.class);
            startActivity(i);
        }
        if (id == R.id.rf) {
            Intent i =new Intent(Dashboard_d.this, Refresh.class);
            startActivity(i);
        }
        if(id == R.id.about){
            Intent i = new Intent(Dashboard_d.this,about.class);
            startActivity(i);
        }
        if(id == R.id.cnt){
            Intent i = new Intent(Dashboard_d.this,contactUs.class);
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
        lottieAnimationView=findViewById(R.id.post);
        aidiet=findViewById(R.id.Aidiet);
//        nutrition=findViewById(R.id.nutrition);
        fb=findViewById(R.id.feedback);

        aidiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Dashboard_d.this,diet_history.class);
                startActivity(i);
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Dashboard_d.this,Viewfeedback.class);
                startActivity(i);
            }
        });

        flipper=findViewById(R.id.flipper);
        int imgarray[]={R.drawable.histroty,R.drawable.bg,R.drawable.diabtese_text_screen,R.drawable.goal};
        for(int i=0;i<imgarray.length;i++)
            showimage(imgarray[i]);
    }

    private void showimage(int i) {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(i);
        flipper.addView(imageView);
        flipper.setFlipInterval(4000);
        flipper.setAutoStart(true);
        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    public void post(View view) {
        Intent i =new Intent (Dashboard_d.this,adminPostView.class);
        startActivity(i);
    }

//    // Menu list
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menhu,menu);
//        return  true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.signUpAdmin) {
//        Intent i =new Intent(Dashboard_d.this,CreateNAdmin.class);
//        startActivity(i);
//        }
//        if (id == R.id.feedback) {
//            Intent i =new Intent(Dashboard_d.this,Feedback.class);
//            startActivity(i);
//        }
//        if (id == R.id.rf) {
//            Intent i =new Intent(Dashboard_d.this, Refresh.class);
//            startActivity(i);
//        }
//        if(id == R.id.about){
//            Intent i = new Intent(Dashboard_d.this,about.class);
//            startActivity(i);
//        }
//        if(id == R.id.cnt){
//            Intent i = new Intent(Dashboard_d.this,contactUs.class);
//            startActivity(i);
//        }
//        if (id == R.id.logout) {
//            Logout();
//        }
//        return  true;
//}

    private void Logout() {
        //        firebaseAuth.signOut();
        firebaseAuth.getInstance().signOut();
        //--------------------------------------
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("isAdminLogin");
        editor.commit();
        //---------------------------------------
        finish();
    }

    public void nutrition(View view) {
        Intent i =new Intent(Dashboard_d.this,adminDctView.class);
        startActivity(i);
    }
}