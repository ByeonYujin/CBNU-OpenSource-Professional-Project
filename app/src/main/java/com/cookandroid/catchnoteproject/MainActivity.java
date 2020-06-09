package com.cookandroid.catchnoteproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.createDataBase();
//            helper.testTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);


        ImageButton nbImgbtn = (ImageButton) findViewById(R.id.nbbtn);
        ImageButton elecsImgbtn = (ImageButton) findViewById(R.id.elecbtn);

        nbImgbtn.setOnClickListener(OnClickListener);
        elecsImgbtn.setOnClickListener(OnClickListener);
    }

    //뒤로 가기 버튼 누르면 앱 종료
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
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
                case R.id.elecbtn:
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
                    GoElecselectOptionActivity();
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

    //SelectOptionActivity로 이동
    private void GoElecselectOptionActivity() {
        Intent intent = new Intent(this, ElecselectionActivity.class);
        startActivity(intent);
    }

}
