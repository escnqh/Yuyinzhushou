package com.newthread.ntp_yuyinzhushou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newthread.ntp_yuyinzhushou.R;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 张浩 on 2016/10/13.
 */

public class MenuFragment extends BaseFragment implements BaseFragment.FragmentVoiceResultListener {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.main_menu,container,false);
        initView();
        init();
        return view;
    }


    @Override
    protected void initView() {
        super.initView();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("我是在菜单中！！！！");
                beginSpeak();
            }
        });
    }


    @Override
    protected void init() {
        super.init();
        setFragmentVoiceResultListener(this);//注册Listener
    }


    @Override
    public void onResult(List<String> list) {
           setFragmentChange(list);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("MenuFragment被销毁了");
    }


}
