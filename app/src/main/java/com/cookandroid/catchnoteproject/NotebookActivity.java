
package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class NotebookActivity extends AppCompatActivity {

    Button btn1,btn2,btn3;
    Spinner spinner2,spinner4,spinner5;
    private String manufacture,purpose, size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);

        btn1=(Button)findViewById(R.id.recommendBtn); //노트북 추천받기
        btn2=(Button)findViewById(R.id.buyBtn); //노트북 사러가기
        btn3=(Button)findViewById(R.id.searchBtn); //검색 버튼
        ImageView wishlistBtn = (ImageView) findViewById(R.id.wishlistBtn);

        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner4 = (Spinner)findViewById(R.id.spinner4);
        spinner5 = (Spinner)findViewById(R.id.spinner5);

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



        btn1.setOnClickListener(OnClickListener);
        btn2.setOnClickListener(OnClickListener);
        btn3.setOnClickListener(OnClickListener);
        wishlistBtn.setOnClickListener(OnClickListener);

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
            switch (v.getId()) {
                case R.id.recommendBtn:
                    showDialog(1);
                    Thread thread = new Thread(new Runnable() {
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
                    thread.start();
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    Fragment1 fragment1 = new Fragment1();
                    transaction1.replace(R.id.frame, fragment1);
                    transaction1.commit();
                    btn3.setVisibility(View.VISIBLE); //화면 전환 시 버튼 보이게
                    break;
                case R.id.buyBtn:
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
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    Fragment2 fragment2 = new Fragment2();
                    transaction2.replace(R.id.frame, fragment2);
                    transaction2.commit();
                    btn3.setVisibility(View.INVISIBLE); //화면 전환 시 버튼 안보이게
                    break;
                case R.id.searchBtn:
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
                    GoResultActivity();
                    break;
                case R.id.wishlistBtn:
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        Toast.makeText(getApplicationContext(), "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        showDialog(1);
                        Thread thread3 = new Thread(new Runnable() {
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
                        thread3.start();
                        GoWishList();
                        break;
                    }
            }
        }
    };


    protected CustomImageProgress onCreateDialog(int id) {
        CustomImageProgress dialog = new CustomImageProgress(NotebookActivity.this, R.style.CustomProgressStyle);
        return dialog;
    }



    private void GoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void GoWishList() {
        Intent intent = new Intent(this, WishList.class);
        startActivity(intent);
    }

    private void GoResultActivity(){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("manufacture",manufacture);
        intent.putExtra("purpose",purpose);
        intent.putExtra("size",size);
        intent.putExtra("obj", "notebook");
        startActivity(intent);
    }
}