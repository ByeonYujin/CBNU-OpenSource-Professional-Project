package com.cookandroid.catchnoteproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.catchnoteproject.db.model.NoteBook;
import com.cookandroid.catchnoteproject.db.service.NoteBookService;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ListView list;
    private NoteBookService noteBookService;
    private Integer count;

    List<String> listName = new ArrayList<>();
    List<String> listPrice = new ArrayList<>();
    List<String> listSpec = new ArrayList<>();
    List<Integer> listImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        TextView manufactureOption = (TextView) findViewById(R.id.manufactureTv);
        TextView purposeOption = (TextView) findViewById(R.id.purposeTv);
        TextView sizeOption = (TextView) findViewById(R.id.sizeTv);
        TextView resultCount = (TextView)findViewById(R.id.countTv);

        //인텐트로 스피너받아오기
        Intent intent= getIntent();
        String manufacture = intent.getStringExtra("manufacture");
        String purpose = intent.getStringExtra("purpose");
        String size = intent.getStringExtra("size");


        noteBookService = new NoteBookService(getApplicationContext());
        List<NoteBook> noteBooks = noteBookService.getNoteBookList(manufacture, purpose, size);
        listName = noteBookService.getSubList(manufacture, purpose, size, 0);
        listPrice = noteBookService.getSubList(manufacture, purpose, size, 2);
        listSpec = noteBookService.getSubList(manufacture, purpose, size, 6);


        //list=(ListView)findViewById(R.id.list);

        /*List<String> data = new ArrayList<>();  // 리스트뷰에 넣을 data
        ArrayAdapter<String>adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        list.setAdapter(adapter);
//                리스트뷰 연습
//
        data.add(manufacture);
        data.add(purpose);
        data.add(size);*/

/*        for (NoteBook book : noteBooks) {
//            Log.d("NOTEBOOK INFO : ", book.asString());
//        }

        //db전체 데이터 집어넣기
//        for(NoteBook book : noteBooks) {
//            data.add(book.asString());
//        } */


        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //커스텀뷰 세팅
        ViewSet();

        //상단 Spiner값(선택 옵션) 출력
        manufactureOption.setText(manufacture);
        purposeOption.setText(purpose);
        sizeOption.setText(size);

        //리스트 항목 추가
        count = getData();

        //검색된 항목 수 표시
        resultCount.setText("검색 결과 : 총 "+count.toString()+"개");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                GoMainActivity();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //SelectOptionActivity로 이동
    private void GoMainActivity() {
        Intent intent = new Intent(this, SelectOptionActivity.class);
        startActivity(intent);
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
        //데이터 담아서 팝업(액티비티) 호출
        recyclerView = findViewById(R.id.recyclerView);
        Integer position = recyclerView.getChildLayoutPosition(v);
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("model", listName.get(position));
        intent.putExtra("price", listPrice.get(position));
        intent.putExtra("spec", listSpec.get(position));
        startActivityForResult(intent, 1);
    }
}

