package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;


public class ElecselectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elecselectionoption);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageButton washerImgBtn = (ImageButton) findViewById(R.id.washerImgBtn);
        ImageButton tvImgBtn = (ImageButton) findViewById(R.id.tvImgBtn);
        ImageButton rfImgBtn = (ImageButton) findViewById(R.id.rfImgBtn);

        washerImgBtn.setOnClickListener(OnClickListener);
        tvImgBtn.setOnClickListener(OnClickListener);
        rfImgBtn.setOnClickListener(OnClickListener);
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

    //버튼 클릭 시 화면 전환
    View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.washerImgBtn:
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
                    GoWasherselectionActivity();
                    break;
                case R.id.tvImgBtn:
                    showDialog(1);
                    Thread thread2 = new Thread(new Runnable() {
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
                    thread2.start();
                    GoTVselectionActivity();
                    break;
                case R.id.rfImgBtn:
                    showDialog(1);
                    Thread thread3 = new Thread(new Runnable() {
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
                    thread3.start();
                    GoRefriselectionActivity();
                    break;
            }
        }
    };

    protected CustomImageProgress onCreateDialog(int id) {
        CustomImageProgress dialog = new CustomImageProgress(ElecselectionActivity.this, R.style.CustomProgressStyle);
        return dialog;
    }

    //MainActivity로 이동
    private void GoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void GoWasherselectionActivity(){
        Intent intent = new Intent(this, WasherselectionActivity.class);
        startActivity(intent);
    }
    private void GoTVselectionActivity(){
        Intent intent = new Intent(this, TvselectionActivity.class);
        startActivity(intent);
    }
    private void GoRefriselectionActivity(){
        Intent intent = new Intent(this, RefriselectionActivity.class);
        startActivity(intent);
    }
}
