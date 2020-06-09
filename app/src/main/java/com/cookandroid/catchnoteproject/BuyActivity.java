package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;

public class BuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_notebook);
        
        Button siteBtn = (Button)findViewById(R.id.siteBtn);
        Button eventBtn = (Button)findViewById(R.id.eventBtn);

        siteBtn.setOnClickListener(OnClickListener);
        eventBtn.setOnClickListener(OnClickListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                GoNotebookActivity();
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
                case R.id.siteBtn:
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
                    GoSiteViewActivity();
                    break;
                case R.id.eventBtn:
                    showDialog(1);
                    Thread thread1=new Thread(new Runnable() {
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
                    thread1.start();
                    GoEventViewOptionActivity();
                    break;
            }
        }
    };

    protected CustomImageProgress onCreateDialog(int id) {
        CustomImageProgress dialog = new CustomImageProgress(BuyActivity.this, R.style.CustomProgressStyle);
        return dialog;
    }

    //SiteViewActivity로 이동
    private void GoSiteViewActivity(){
        Intent intent = new Intent(this, SiteViewActivity.class);
        startActivity(intent);
    }

    //EventViewActivity로 이동
    private void GoEventViewOptionActivity(){
        Intent intent = new Intent(this, EventViewActivity.class);
        startActivity(intent);
    }

    //NotebookActivity로 이동
    private void GoNotebookActivity(){
        Intent intent = new Intent(this, NotebookActivity.class);
        startActivity(intent);
    }
}

