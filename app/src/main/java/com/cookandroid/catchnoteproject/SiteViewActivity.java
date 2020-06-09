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

public class SiteViewActivity extends AppCompatActivity {

    //이미지 버튼, 이미지 버튼 ID,  사이트 URL
    ImageButton[] imgButtons = new ImageButton[8];
    Integer[] imgButtonIDs = {R.id.imageBtn1, R.id.imageBtn2, R.id.imageBtn3, R.id.imageBtn4, R.id.imageBtn5, R.id.imageBtn6, R.id.imageBtn7, R.id.imageBtn8};
    String[] url = {"http://www.compuzone.co.kr/main/main.htm", "https://www.comsclub.com/", "https://www.dicle.co.kr/", "http://dtek.kr/\n",
            "http://www.auction.co.kr/category/category22.html", "http://category.gmarket.co.kr/listview/L100000002.aspx",
    "http://shopping.interpark.com/display/main.do?dispNo=001110&smid1=gnb&smid2=002&smid3=8", "http://www.g9.co.kr/Display/Category/400000157/500000904/600\n" +
            "002748"};
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_view);

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
        intent = new Intent(this, BuyActivity.class);
        startActivity(intent);
    }
}
