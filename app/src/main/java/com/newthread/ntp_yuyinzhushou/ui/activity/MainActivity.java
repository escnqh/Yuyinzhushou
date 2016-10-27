package com.newthread.ntp_yuyinzhushou.ui.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.lapism.searchview.SearchView;
import com.newthread.ntp_yuyinzhushou.R;
import com.newthread.ntp_yuyinzhushou.ui.fragment.BaseFragment;
import com.newthread.ntp_yuyinzhushou.ui.fragment.ContactFragment;
import com.newthread.ntp_yuyinzhushou.ui.fragment.LocationFragment;
import com.newthread.ntp_yuyinzhushou.ui.fragment.MenuFragment;
import com.newthread.ntp_yuyinzhushou.ui.fragment.SettingFragment;
import com.newthread.ntp_yuyinzhushou.ui.fragment.WebSearchFragment;
import com.newthread.ntp_yuyinzhushou.util.LaunchAppUtil;
import com.newthread.ntp_yuyinzhushou.wrapper.ComServiceWrapper;
import com.newthread.ntp_yuyinzhushou.util.comUtil;
import com.newthread.ntp_yuyinzhushou.util.permissionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity extends BaseActivity implements BaseFragment.FragmentChangeListener{

    private final String TAG=MainActivity.class.getSimpleName();

    private String[] PERMISSION_CONTACTS={
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };

    private RelativeLayout MainView;
    private SearchView searchView;
    FragmentManager fragmentManager;
    Fragment mainFragment,locationFragment,contractFragment,settingFragment,openAppFragment,webSearchFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void init() {
        BaseFragment.setFragmentChangeListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LaunchAppUtil.getAppList(MainActivity.this);
            }
        }).start();

    }


    private void initView() {
        searchView= (SearchView) findViewById(R.id.searchView);
        initSearchView();
        MainView= (RelativeLayout) findViewById(R.id.activity_main);
        initFragment(ComServiceWrapper.HOMEMENU_FRAGMENT,null);
    }


    private void initSearchView(){
//        searchView.setVoice(true);
//        searchView.setOnVoiceClickListener(new SearchView.OnVoiceClickListener() {
//            @Override
//            public void onVoiceClick() {
//                //System.out.println("点击了按钮");
//                beginSpeak();
//            }
//        });
    }


    //初始化Fragment
    private void initFragment(int index,ArrayList<String> list){
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFrgagment(transaction);
        switch (index){
            case ComServiceWrapper.HOMEMENU_FRAGMENT:
                    mainFragment=new MenuFragment();
                    transaction.add(R.id.frame_content,mainFragment);
                    putIntoFragmentMap(ComServiceWrapper.SHOMEMENU_FRAGMENT, ComServiceWrapper.HOMEMENU_FRAGMENT);
                break;

            case ComServiceWrapper.LOCAITON_FRAGMENT:
                    locationFragment=new LocationFragment();
                    SetArgument(list,locationFragment);
                    transaction.add(R.id.frame_content,locationFragment).addToBackStack(ComServiceWrapper.SLOCAITON_FRAGMENT);
                putIntoFragmentMap(ComServiceWrapper.SLOCAITON_FRAGMENT, ComServiceWrapper.LOCAITON_FRAGMENT);
                    break;

            case ComServiceWrapper.CONTACT_FRAGMENT:
                    contractFragment=new ContactFragment();
                    SetArgument(list,contractFragment);
                    transaction.add(R.id.frame_content,contractFragment).addToBackStack(ComServiceWrapper.SCONTACT_FRAGMENT);
                    putIntoFragmentMap(ComServiceWrapper.SCONTACT_FRAGMENT, ComServiceWrapper.CONTACT_FRAGMENT);
                  break;

            case ComServiceWrapper.SETTING_FRAGMENT:
                    settingFragment=new SettingFragment();
                    SetArgument(list,settingFragment);
                    transaction.add(R.id.frame_content,settingFragment).addToBackStack(ComServiceWrapper.SSETTING_FRAGMENT);
                    putIntoFragmentMap(ComServiceWrapper.SSETTING_FRAGMENT, ComServiceWrapper.SETTING_FRAGMENT);
                break;

            case ComServiceWrapper.SEARCH_FRAGMENT:
                webSearchFragment=new WebSearchFragment();
                SetArgument(list,webSearchFragment);
                transaction.add(R.id.frame_content,webSearchFragment).addToBackStack(ComServiceWrapper.SSEARCH_FRAGMENT);
                putIntoFragmentMap(ComServiceWrapper.SSEARCH_FRAGMENT, ComServiceWrapper.SEARCH_FRAGMENT);
                break;


        }
        transaction.commit();
    }


    private void putIntoFragmentMap(String fragString,int fragTag){
        Map<String,Integer> map=new HashMap<>();
        map.put(fragString,fragTag);
        ComServiceWrapper.fragmentMapList.add(map);
    }



    /**
     * 设置参数
     * @param list
     * @param fragment
     */
    private void SetArgument(ArrayList<String> list,Fragment fragment){
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("ResultList",list);
        fragment.setArguments(bundle);
    }


    private void hideFrgagment(FragmentTransaction transaction) {
        if (mainFragment!=null){
            transaction.hide(mainFragment);
        }
        /**
         * 清除fragemnt栈
         */
        for (Map<String,Integer> map: ComServiceWrapper.fragmentMapList){
            for (Map.Entry<String,Integer> entry:map.entrySet()){
                if (!entry.getKey().equals(ComServiceWrapper.SHOMEMENU_FRAGMENT)){
                    fragmentManager.popBackStack(entry.getKey(),POP_BACK_STACK_INCLUSIVE);
                }
            }
        }
    }




    @Override
    public void onFragmentChange(Map<String,Object> map, List<String> arrayList) {
        System.out.println(TAG+map);
        int tag= (int) map.get("tag");
        switch (tag){
            case ComServiceWrapper.NOFRAGMENT:
                Toast.makeText(this,"对不起，我没听懂你说什么！",Toast.LENGTH_SHORT).show();
                return;
            case ComServiceWrapper.LAUNCHAPP_FRAGMENT:
                LaunchAppUtil.LaunchApp(this,map);
                return;
        }
       initFragment(tag,(ArrayList<String>) arrayList);
    }


    /**
     * 初始化权限
     */
    private void initPermission() {
        boolean permissionCheck= permissionUtil.hasPermissions(this,PERMISSION_CONTACTS);
        if (!permissionCheck)
            ActivityCompat.requestPermissions(this,PERMISSION_CONTACTS, comUtil.REQUEST_PERMISSION);
        else
            Toast.makeText(this,"所有权限获得!",Toast.LENGTH_SHORT).show();
    }

    /**
     * 权限的申请的回调
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==comUtil.REQUEST_PERMISSION){
            if (!permissionUtil.verifyPermissions(grantResults)){
                Snackbar.make(MainView, "缺少必要的权限！请点击设置",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("设置", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat
                                        .requestPermissions(MainActivity.this, PERMISSION_CONTACTS,
                                                comUtil.REQUEST_PERMISSION);
                            }
                        }).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPermission();
        // 开放统计 移动数据统计分析
        // FlowerCollector.onResume(this);
        //FlowerCollector.onPageStart(TAG);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 开放统计 移动数据统计分析
        //FlowerCollector.onPageEnd(TAG);
        //FlowerCollector.onPause(this);
    }
}
