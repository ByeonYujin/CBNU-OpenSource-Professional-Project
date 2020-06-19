package com.cookandroid.catchnoteproject;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class PopupActivity extends Activity {

    TextView modelTv;
    TextView priceTv;
    TextView specTv;
    ImageButton shareImageButton;
    ImageButton copyImageButton;
    ImageView itemImage;
    ImageButton keepImageBtn;
    private int k;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser mAuth;
    private String key;

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
        keepImageBtn = (ImageButton) findViewById(R.id.keepBtn);

        //데이터 가져오기
        Intent intent = getIntent();
        final String model = intent.getStringExtra("model");
        final String spec = intent.getStringExtra("spec");
        final String img = intent.getStringExtra("img");
        final int category = intent.getIntExtra("category", 0);
        final String price;

        if (category > 0) {
            int priceint = intent.getIntExtra("price", 0);

            DecimalFormat myFormatter = new DecimalFormat("###,###");
            String formattedStringPrice = myFormatter.format(priceint);
            price = formattedStringPrice+'원';
        }
        else {
            final String pricestr = intent.getStringExtra("price");
            price = pricestr;
        }

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


        //찜기능
        keepImageBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //회원만 이용 가능
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        Toast.makeText(getApplicationContext(), "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //user 아이디 받고 db연동
                        mAuth = FirebaseAuth.getInstance().getCurrentUser();
                        key = mAuth.getUid();

                        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
                        databaseReference = database.getReference("users").child(key).child("wishlist"); //DB테이블연결

                        if (k==0) {
                            keepImageBtn.setImageResource(R.drawable.ic_filledheart); //하트 모양 바꾸기
                            additem(model, price, spec, img, category); // users/userid/keep_list에 추가
                            Toast.makeText(getApplicationContext(), "찜목록에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            k++;
                        }
                        else {
                            k--;
                            deleteitem(); // db에서 삭제
                            keepImageBtn.setImageResource(R.drawable.ic_emptyheart);
                            Toast.makeText(getApplicationContext(), "찜목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
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

    private void additem(String model, String price, String spec, String img, int category){

        HashMap<String, Object> item = new HashMap<>();
        item.put("id", model);
        item.put("money", price);
        item.put("profile", img);
        item.put("spec", spec);

        databaseReference = databaseReference.push();
        databaseReference.setValue(item);
    }

    private void deleteitem(){
        databaseReference.setValue(null);
        databaseReference = database.getReference("users").child(key).child("keep_list");
    }
}