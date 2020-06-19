package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WishList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<WishList_Item> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser mAuth;
    private String userid;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList= new ArrayList<WishList_Item>(); // User 객체를 담을 어레이 리스트 (어댑터 쪽으로)

        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        userid = mAuth.getUid();

        databaseReference = database.getReference("users").child(userid).child("wishlist"); //DB테이블연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스 의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 list추출

                    WishList_Item keepItem = snapshot.getValue(WishList_Item.class);
                    arrayList.add(keepItem);
                }
                adapter.notifyDataSetChanged();
            }

            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        TextView textView = (TextView) findViewById(R.id.option2Tv);
        TextView count = (TextView) findViewById(R.id.countTv);

        textView.setText(mAuth.getEmail()+"님의 찜 목록");
        count.setText(" ");

        adapter = new WishListAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); //리사클러뷰에 어댑터 연결

    }

    //팝업창
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        recyclerView = findViewById(R.id.recyclerView);
        Integer position = recyclerView.getChildLayoutPosition(v);
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("model", arrayList.get(position).getId());
        intent.putExtra("price", String.valueOf(arrayList.get(position).getMoney()));
        intent.putExtra("spec", arrayList.get(position).getSpec());
        intent.putExtra("img", arrayList.get(position).getProfile());
        startActivityForResult(intent, 1);
    }

}
