package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class TvselectionActivity extends AppCompatActivity {

    private String manufacture, size;
    private Spinner option1,option2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvselection);


        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        option1 = (Spinner)findViewById(R.id.spinner2);
        option2 = (Spinner)findViewById(R.id.spinner4);

        ImageView wishlistBtn = (ImageView) findViewById(R.id.wishlistBtn);
        wishlistBtn.setOnClickListener(OnClickListener);

        option1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manufacture = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        option2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                size = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(OnClickListener);
        wishlistBtn.setOnClickListener(OnClickListener);

    }

    View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.searchBtn:
                    showDialog(1);
                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            TimerTask task=new TimerTask() {
                                @Override
                                public void run() {
                                    removeDialog(1);
                                }
                            };
                            Timer timer=new Timer();
                            timer.schedule(task,1000);
                        }
                    });
                    thread.start();
                    GoResultActivity();
                    break;
                case R.id.wishlistBtn:
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        Toast.makeText(getApplicationContext(), "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else {
                        showDialog(1);
                        Thread thread2 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //1초 후 다이얼로그 닫기
                                TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        removeDialog(1);
                                    }
                                };
                                Timer timer = new Timer();
                                timer.schedule(task, 1000);
                            }
                        });
                        thread2.start();
                        GoWishList();
                        break;
                    }
            }

        }
    };

    protected CustomImageProgress onCreateDialog(int id) {
        CustomImageProgress dialog = new CustomImageProgress(TvselectionActivity.this, R.style.CustomProgressStyle);
        return dialog;
    }

    //ResultActivity로 이동
    private void GoResultActivity(){
        Intent intent = new Intent(this, TvResult.class);
        //인텐트로 스피너보내기
        String sp1 = option1.getSelectedItem().toString();
        String sp2 = option2.getSelectedItem().toString();



        intent.putExtra("in1",sp1);
        intent.putExtra("in2",sp2);

        startActivity(intent);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                GoMainActivity();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void GoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    private void GoWishList() {
        Intent intent = new Intent(this, WishList.class);
        startActivity(intent);
    }

}
