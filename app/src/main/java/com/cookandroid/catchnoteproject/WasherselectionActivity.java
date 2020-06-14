package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WasherselectionActivity extends AppCompatActivity {

    //이승우가 하는부분




    private String form,manufacture, capacity;
    private Spinner option1,option2, option3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washerselection);





        option1 = (Spinner)findViewById(R.id.spinner4);
        option2 = (Spinner)findViewById(R.id.spinner2);
        option3 = (Spinner)findViewById(R.id.spinner5);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        option1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                form = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        option2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manufacture = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        option3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                capacity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(OnClickListener);

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
            }

        }
    };

    protected CustomImageProgress onCreateDialog(int id) {
        CustomImageProgress dialog = new CustomImageProgress(WasherselectionActivity.this, R.style.CustomProgressStyle);
        return dialog;
    }

    //ResultActivity로 이동
    private void GoResultActivity(){
        Intent intent = new Intent(this, WashingResult.class);
       String op1 = option1.getSelectedItem().toString();
       String op2 = option2.getSelectedItem().toString();
       String op3 = option3.getSelectedItem().toString();

       intent.putExtra("op1",op1);
       intent.putExtra("op2",op2);
       intent.putExtra("op3",op3);

        startActivity(intent);
    }

    //NotebookActivity로 이동
    private void GoNotebookActivity(){
        Intent intent = new Intent(this, NotebookActivity.class);
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


}
