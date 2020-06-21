package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class RefrigeratorResult extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Refrigerator> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<WishList_Item> wishList;
    public int k;
    public String listkey;

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

        Intent intent2 =new Intent(getIntent());

        final String t= intent2.getStringExtra("in3");
        final String m = intent2.getStringExtra("in4");
        final String e = intent2.getStringExtra("in5");

        TextView firstOption = (TextView) findViewById(R.id.option1Tv);
        TextView secondOption = (TextView) findViewById(R.id.option2Tv);
        TextView thirdOption = (TextView) findViewById(R.id.option3Tv);

        firstOption.setText(t);
        secondOption.setText(m);
        thirdOption.setText(e);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
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

                    String test = refrigerator.getType();

                    String test2 = refrigerator.getManufacturer();
                    String test3 = refrigerator.getGrade();


                    if((t.equals(test))&&(m.equals(test2))&&(e.equals(test3))) {
                        Log.d(test,test);
                        arrayList.add(refrigerator); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보내준비
                    }

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

    //팝업창
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        recyclerView = findViewById(R.id.recyclerView);
        Integer position = recyclerView.getChildLayoutPosition(v);
        Intent intent = new Intent(this, PopupActivity.class);

        //위시리스트 존재 여부 검사
        if (FirebaseAuth.getInstance().getCurrentUser() == null )
            intent.putExtra("check", 0);
        else{
            checkitem(arrayList.get(position).getId());
            intent.putExtra("check", k);
        }
        intent.putExtra("listkey", listkey); // 위시리스트 내 아이템 키값 (존재하지 않을 경우 공백임)

        intent.putExtra("model", arrayList.get(position).getId());
        intent.putExtra("price", arrayList.get(position).getMoney());
        intent.putExtra("spec", arrayList.get(position).getSpec());
        intent.putExtra("img", arrayList.get(position).getProfile());
        intent.putExtra("category", 3);

        startActivityForResult(intent, 1);
    }

    private void checkitem(final String model){

        wishList= new ArrayList<WishList_Item>();

        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wishlist"); //DB테이블연결

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스 의 데이터를 받아오는 곳
                wishList.clear(); // 기존 배열리스트가 존재하지않게 초기화

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 list추출

                    WishList_Item item = snapshot.getValue(WishList_Item.class);
                    if((model.equals(item.getId()))) {
                        wishList.add(item); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보내준비
                        listkey = snapshot.getKey();
                        //keepImageBtn.setImageResource(R.drawable.ic_filledheart);
                    }
                }
                adapter.notifyDataSetChanged();

                if (wishList.isEmpty() == false){
                    k+=1;
                } else{
                    k=0;
                    listkey="";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("wishlist Check", String.valueOf(databaseError.toException()));
            }
        });
    }

}
