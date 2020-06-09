package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EventViewActivity extends AppCompatActivity {

    ImageButton[] imgButtons = new ImageButton[5];
    Integer[] imgButtonIDs = {R.id.imgbtn1, R.id.imgbtn2, R.id.imgbtn3, R.id.imgbtn4, R.id.imgbtn5};
    String[] url = {"https://www.myomee.com/sp/exbiDtl.do?exbiNo=1000000235", "https://www.lenovo.com/kr/ko/laptops/c/laptops",
    "https://www.samsung.com/sec/galaxybook/launching/?cid=sec_paid_ppc_naver_notebook_none_brandsearch_other_notebook-20200501_pctitle",
    "http://ruggedkorea.com/html/cs01.php?mode=v&bbs_data=aWR4PTI0NTQmc3RhcnRQYWdlPSZsaXN0Tm89JnRhYmxlPWRoX2Jic19kYXRhJmNvZGU9bm90aWNlJnNlYXJjaF9pdGVtPSZzZWFyY2hfb3JkZXI9||&bgu=view",
    "http://www.msikorea.kr/krDB/msigaming.htm"};
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        int i;
        for (i =0; i<imgButtonIDs.length; i++) {
            imgButtons[i]=(ImageButton) findViewById(imgButtonIDs[i]);
        }

        for (i=0; i<imgButtonIDs.length; i++) {
            final int index;
            index = i;

            //이미지 버튼 웹사이트 연결 -> 모바일 사이트로 변경 예정
            imgButtons[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url[index]));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                GoMBuyActivity();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //BuyActivity로 이동
    private void GoMBuyActivity(){
        Intent intent = new Intent(this, BuyActivity.class);
        startActivity(intent);
    }
}
