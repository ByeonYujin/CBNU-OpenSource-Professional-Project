package com.cookandroid.catchnoteproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Fragment1 extends Fragment {

    public Fragment1() {

    }

    @Nullable
    @Override
    //프래그먼트 생명주기//
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment1,container,false);

    }
}
