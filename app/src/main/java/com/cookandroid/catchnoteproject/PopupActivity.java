package com.cookandroid.catchnoteproject;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class PopupActivity extends Activity {

    TextView modelTv;
    TextView priceTv;
    TextView specTv;
    ImageButton shareImageButton;
    ImageButton copyImageButton;
    ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_activity);

        //UI 객체생성
        modelTv = (TextView)findViewById(R.id.model);
        priceTv = (TextView)findViewById(R.id.price);
        specTv = (TextView) findViewById(R.id.spec);
        shareImageButton = (ImageButton) findViewById(R.id.shareBtn);
        copyImageButton = (ImageButton) findViewById(R.id.copyBtn);
        itemImage = (ImageView) findViewById(R.id.itemImage);

        //데이터 가져오기
        Intent intent = getIntent();
        String model = intent.getStringExtra("model");
        String price = intent.getStringExtra("price");
        String spec = intent.getStringExtra("spec");
        String img = intent.getStringExtra("img");

        modelTv.setText(model);
        priceTv.setText(price);
        specTv.setText(spec);
        Glide.with(this)
                .load(img)
                .into(this.itemImage);


        shareImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Sharing_intent = new Intent(Intent.ACTION_SEND);
                Sharing_intent.setType("text/plain");

                String Test_Message = modelTv.getText().toString();

                Sharing_intent.putExtra(Intent.EXTRA_TEXT, Test_Message);

                Intent Sharing = Intent.createChooser(Sharing_intent, "공유하기");
                startActivity(Sharing);
            }
        });

        copyImageButton.setOnTouchListener(new View.OnTouchListener(){   //터치 이벤트 리스너 등록(누를때)

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction()==MotionEvent.ACTION_DOWN){ //눌렀을 때 동작

                    //클립보드 사용 코드
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("모델명",modelTv.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);

                    //복사가 되었다면 토스트메시지 노출
                    Toast.makeText(getApplicationContext(),"클립보드에 복사되었습니다.",Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}