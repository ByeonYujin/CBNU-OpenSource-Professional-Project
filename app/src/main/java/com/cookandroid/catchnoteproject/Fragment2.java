package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    ImageButton[] imgButtons = new ImageButton[5];
    ImageButton[] imgButtons2 = new ImageButton[8];
    Integer[] imgButtonIDs = {R.id.imgbtn1, R.id.imgbtn2, R.id.imgbtn3, R.id.imgbtn4, R.id.imgbtn5};
    Integer[] imgButtonIDs2 = {R.id.imageBtn1, R.id.imageBtn2, R.id.imageBtn3, R.id.imageBtn4, R.id.imageBtn5, R.id.imageBtn6, R.id.imageBtn7, R.id.imageBtn8};
    String[] url1 = {"https://www.myomee.com/sp/exbiDtl.do?exbiNo=1000000235", "https://www.lenovo.com/kr/ko/laptops/c/laptops",
            "https://www.samsung.com/sec/galaxybook/launching/?cid=sec_paid_ppc_naver_notebook_none_brandsearch_other_notebook-20200501_pctitle",
            "http://ruggedkorea.com/html/cs01.php?mode=v&bbs_data=aWR4PTI0NTQmc3RhcnRQYWdlPSZsaXN0Tm89JnRhYmxlPWRoX2Jic19kYXRhJmNvZGU9bm90aWNlJnNlYXJjaF9pdGVtPSZzZWFyY2hfb3JkZXI9||&bgu=view",
            "http://www.msikorea.kr/krDB/msigaming.htm"};
    String[] url2 = {"http://www.compuzone.co.kr/main/main.htm", "https://www.comsclub.com/", "https://www.dicle.co.kr/", "http://dtek.kr/\n",
            "http://www.auction.co.kr/category/category22.html", "http://category.gmarket.co.kr/listview/L100000002.aspx",
            "http://shopping.interpark.com/display/main.do?dispNo=001110&smid1=gnb&smid2=002&smid3=8", "http://www.g9.co.kr/Display/Category/400000157/500000904/600\n" +
            "002748"};

    Intent intent;

    public Fragment2() {

    }

    @Nullable
    @Override
    //프래그먼트 생명주기//
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2,container, false);
        int i;
        for(i=0;i<imgButtonIDs.length;i++){
            imgButtons[i]=(ImageButton) view.findViewById(imgButtonIDs[i]);
        }
        for (i =0; i<imgButtonIDs2.length; i++) {
            imgButtons2[i]=(ImageButton)view.findViewById(imgButtonIDs2[i]);
        }

        for(i=0;i<imgButtonIDs.length;i++){
            final int index;
            index = i;

            //이미지 버튼 웹사이트 연결 -> 모바일 사이트로 변경 예정
            imgButtons[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url1[index]));
                    startActivity(intent);
                }
            });

        }
        for(i=0;i<imgButtonIDs2.length;i++){
            final int index;
            index = i;

            imgButtons2[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url2[index]));
                    startActivity(intent);
                }
            });

        }
        return view;
        //return inflater.inflate(R.layout.fragment2, container, false);

    }



}

