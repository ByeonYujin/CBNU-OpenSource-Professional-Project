package com.cookandroid.catchnoteproject;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RefrigeratorResult extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Refrigerator> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        // recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList= new ArrayList<Refrigerator>(); // User 객체를 담을 어레이 리스트 (어댑터 쪽으로)

        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("Refrigerator"); //DB테이블연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스 의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {   //반복문으로 데이터 list추출
                    Refrigerator refrigerator = snapshot.getValue(Refrigerator.class); // 만들어뒀던 티비 객체에 데이터 담음
                    arrayList.add(refrigerator); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보내준비

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생시
                Log.e("Tv", String.valueOf(databaseError.toException()));
            }
        });

        adapter = new RefrigeratorAdapter(arrayList,this);
        recyclerView.setAdapter(adapter); //리사클러뷰에 어댑터 연결



    }

}
