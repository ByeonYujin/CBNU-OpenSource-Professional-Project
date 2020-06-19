package com.cookandroid.catchnoteproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
        }*/

        helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.createDataBase();
//            helper.testTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);


        Button nbbtn = (Button) findViewById(R.id.nbbtn);
        Button wasbtn = (Button) findViewById(R.id.wasbtn);
        Button refbtn = (Button) findViewById(R.id.refbtn);
        Button tvbtn = (Button) findViewById(R.id.tvbtn);
        TextView logoutTv = (TextView) findViewById(R.id.logoutTv);
        Button keepbtn = (Button) findViewById(R.id.keeplist);

        nbbtn.setOnClickListener(OnClickListener);
        wasbtn.setOnClickListener(OnClickListener);
        refbtn.setOnClickListener(OnClickListener);
        tvbtn.setOnClickListener(OnClickListener);
        logoutTv.setOnClickListener(OnClickListener);
        keepbtn.setOnClickListener(OnClickListener);
    }

    //버튼 클릭 시 화면 전환
    View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nbbtn:
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
                    GoNoteBookActivity();
                    break;
                case R.id.wasbtn:
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
                    GoWasherselectionnActivity();
                    break;
                case R.id.tvbtn :
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
                    GoTvselectionActivity();
                    break;
                case R.id.refbtn :
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
                    GoRefriselectionActivity();
                    break;
                case R.id.keeplist:
                    showDialog(1);
                    Thread thread4 = new Thread(new Runnable() {
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
                    thread4.start();
                    GoKeepList();
                    break;
                case R.id.logoutTv:
                    FirebaseAuth.getInstance().signOut();
                    showToast("로그아웃 되었습니다.");
                    startLoginActivity();
                    break;
            }
        }
    };


    protected CustomImageProgress onCreateDialog(int id) {
        CustomImageProgress dialog = new CustomImageProgress(MainActivity.this, R.style.CustomProgressStyle);
        return dialog;
    }

    //BuyActivity로 이동
    private void GoNoteBookActivity() {
        Intent intent = new Intent(this, NotebookActivity.class);
        startActivity(intent);
    }

    private void GoElecselectOptionActivity() {
        Intent intent = new Intent(this, ElecselectionActivity.class);
        startActivity(intent);
    }

    private void GoTvselectionActivity() {
        Intent intent = new Intent(this, TvselectionActivity.class);
        startActivity(intent);
    }

    private void GoRefriselectionActivity() {
        Intent intent = new Intent(this, RefriselectionActivity.class);
        startActivity(intent);
    }

    private void GoWasherselectionnActivity() {
        Intent intent = new Intent(this, WasherselectionActivity.class);
        startActivity(intent);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void GoKeepList() {
        Intent intent = new Intent(this, WishList.class);
        startActivity(intent);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}