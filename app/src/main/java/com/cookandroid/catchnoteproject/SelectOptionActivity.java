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

public class SelectOptionActivity extends AppCompatActivity {

    private String manufacture,purpose, size;
    private Spinner spinner2,spinner4,spinner5;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);

        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner4 = (Spinner)findViewById(R.id.spinner4);
        spinner5 = (Spinner)findViewById(R.id.spinner5);
        ImageView wishlistBtn = (ImageView) findViewById(R.id.wishlistBtn);

        wishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Toast.makeText(getApplicationContext(), "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    showDialog(1);
                    Thread thread1 = new Thread(new Runnable() {
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
                    thread1.start();
                    GoWishList();
                }
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manufacture = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                purpose = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            }

        }
    };

    protected CustomImageProgress onCreateDialog(int id) {
        CustomImageProgress dialog = new CustomImageProgress(SelectOptionActivity.this, R.style.CustomProgressStyle);
        return dialog;
    }

    //ResultActivity로 이동
    private void GoResultActivity(){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("manufacture",manufacture);
        intent.putExtra("purpose",purpose);
        intent.putExtra("size",size);
        intent.putExtra("obj", "notebook");
        startActivity(intent);
    }

    //NotebookActivity로 이동
    private void GoNotebookActivity(){
        Intent intent = new Intent(this, NotebookActivity.class);
        startActivity(intent);
    }

    private void GoWishList() {
        Intent intent = new Intent(this, WishList.class);
        startActivity(intent);
    }
}
