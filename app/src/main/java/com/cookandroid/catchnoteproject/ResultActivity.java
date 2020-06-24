package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.catchnoteproject.db.model.NoteBook;
import com.cookandroid.catchnoteproject.db.service.NoteBookService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ResultActivity extends AppCompatActivity {

    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ListView list;
    private NoteBookService noteBookService;
    private Integer count;
    private int k;
    private Class back;
    private ArrayList<WishList_Item> wishList;
    public int k1;
    public String listkey;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    List<String> listName = new ArrayList<>();
    List<String> listPrice = new ArrayList<>();
    List<String> listSpec = new ArrayList<>();
    List<Integer> listImage = new ArrayList<>();


    @Override
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

        TextView firstOption = (TextView) findViewById(R.id.option1Tv);
        TextView secondOption = (TextView) findViewById(R.id.option2Tv);
        TextView thirdOption = (TextView) findViewById(R.id.option3Tv);
        TextView resultCount = (TextView)findViewById(R.id.countTv);
        ImageView wishlistBtn = (ImageView) findViewById(R.id.wishlistBtn);

        wishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Toast.makeText(getApplicationContext(), "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                } else {
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
                    GoWishList();
                }
            }
        });

        //인텐트로 스피너받아오기
        Intent intent= getIntent();
        String obj = intent.getStringExtra("obj");
        String option1="", option2="", option3="";

        switch(obj) {
            case "notebook":
                option1 = intent.getStringExtra("manufacture");
                option2 = intent.getStringExtra("purpose");
                option3 = intent.getStringExtra("size");
                //상단 Spiner값(선택 옵션) 출력
                firstOption.setText(option1);
                secondOption.setText(option2);
                thirdOption.setText(option3);
                back = SelectOptionActivity.class;
                k=1;
                break;
            case "washer":
                /*option1 = intent.getStringExtra("form");
                option2 = intent.getStringExtra("manufacture");
                option3 = intent.getStringExtra("capacity");*/
                //상단 Spiner값(선택 옵션) 출력
                firstOption.setText(option1);
                secondOption.setText(option2);
                thirdOption.setText(option3);
                back = WasherselectionActivity.class;
                break;
            case "tv":
                /*option1 = intent.getStringExtra("form");
                option2 = intent.getStringExtra("manufacture");*/
                firstOption.setText(option1);
                secondOption.setText(option2);
                back = TvselectionActivity.class;
                break;
            case "refrigerator":
                /*option1 = intent.getStringExtra("form");
                option2 = intent.getStringExtra("manufacture");
                option3 = intent.getStringExtra("capacity");*/
                //상단 Spiner값(선택 옵션) 출력
                firstOption.setText(option1);
                secondOption.setText(option2);
                thirdOption.setText(option3);
                back = RefriselectionActivity.class;
                break;
        }

        //커스텀뷰 세팅
        ViewSet();

        if (k==1) {
            noteBookService = new NoteBookService(getApplicationContext());
            List<NoteBook> noteBooks = noteBookService.getNoteBookList(option1, option2, option3);
            listName = noteBookService.getSubList(option1, option2, option3, 0);
            listPrice = noteBookService.getSubList(option1, option2, option3, 2);
            listSpec = noteBookService.getSubList(option1, option2, option3, 6);

            //리스트 항목 추가
            count = getData();

            //검색된 항목 수 표시
            resultCount.setText("검색 결과 : 총 " + count.toString() + "개");
        }

        /*final ImageButton keepBtn = (ImageButton) findViewById(R.id.beforekeep);
        keepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keepBtn.setImageResource(R.mipmap.ic_keep2);
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(this, back);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void ViewSet() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private Integer getData() {
        count = listName.size();

        for (int i = 0; i < count; i++) {
            // 각 List의 값들을 data 객체에 set
            Data data = new Data();
            data.setTitle(listName.get(i));
            data.setContent("가격 " + listPrice.get(i));
            data.setSpec(listSpec.get(i));
            //data.setProfile();
            data.setResId(R.drawable.ic_launcher_foreground);

            //데이터 추가
            adapter.addItem(data);
        }
        //데이터 저장
        adapter.notifyDataSetChanged();
        return count;
    }


    //팝업창
    public void mOnPopupClick(View v){
        if(k==1) {
            //데이터 담아서 팝업(액티비티) 호출
            recyclerView = findViewById(R.id.recyclerView);
            Integer position = recyclerView.getChildLayoutPosition(v);
            Intent intent = new Intent(this, PopupActivity.class);

            //위시리스트 존재 여부 검사
            if (FirebaseAuth.getInstance().getCurrentUser() == null )
                intent.putExtra("check", 0);
            else{
                checkitem(listName.get(position));
                intent.putExtra("check", k1);
            }
            intent.putExtra("listkey", listkey); // 위시리스트 내 아이템 키값 (존재하지 않을 경우 공백임)


            String nn = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fkr.123rf.com%2Fphoto_46281597_%25EB%2585%25B8%25ED%258A%25B8%25EB%25B6%2581-%25EC%259D%25BC%25EB%259F%25AC%25EC%258A%25A4%25ED%258A%25B8-%25EA%25B3%25A0%25ED%2592%2588%25EC%25A7%2588%25EC%259D%2598-%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580%25EC%2597%2590-%25EA%25B3%25A0%25EB%25A6%25BD-%25EB%2590%259C-%25ED%259D%25B0%25EC%2583%2589.html&psig=AOvVaw26bZA1r6JbxbHl1NGpnw1x&ust=1592908118629000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIjp66ableoCFQAAAAAdAAAAABAD";

            intent.putExtra("model", listName.get(position));
            intent.putExtra("price", listPrice.get(position));
            intent.putExtra("spec", listSpec.get(position));
            intent.putExtra("img","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_KUevtRcjpHg6jJXRuColb_pVbnDLw-kjtnuQjLkm7WCKn08OQ0iv4T9Wx3Y2TC_khTZOKaX8&usqp=CAc"); // 이미지는 보류
            intent.putExtra("category", 0);

            startActivityForResult(intent, 1);
        }
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
                    k1+=1;
                } else{
                    k1=0;
                    listkey="";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("wishlist Check", String.valueOf(databaseError.toException()));
            }
        });
    }

    private void GoWishList() {
        Intent intent = new Intent(this, WishList.class);
        startActivity(intent);
    }

    protected CustomImageProgress onCreateDialog(int id) {
        CustomImageProgress dialog = new CustomImageProgress(ResultActivity.this, R.style.CustomProgressStyle);
        return dialog;
    }
}

