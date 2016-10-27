package com.newthread.ntp_yuyinzhushou.dispatcher;

import android.util.Log;

import com.newthread.ntp_yuyinzhushou.adapter.ComServiceAdapter;
import com.newthread.ntp_yuyinzhushou.adapter.LocationAdapter;
import com.newthread.ntp_yuyinzhushou.adapter.OpenAppAdapter;
import com.newthread.ntp_yuyinzhushou.adapter.WebSearchAdapter;
import com.newthread.ntp_yuyinzhushou.factory.AbstractClassInstanceFactory;
import com.newthread.ntp_yuyinzhushou.factory.AdapterFactory;
import com.newthread.ntp_yuyinzhushou.wrapper.ComServiceWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/18.
 * 主控制类
 */

public class ServiceDispatcher implements Dispatcher{

    private final static String TAG=ServiceDispatcher.class.getSimpleName();

    private AbstractClassInstanceFactory adapterFactory =new AdapterFactory();
    private List<String> listIn;
    private List<Map<String,Object>> AllMaps=new ArrayList<>();

    /**
     * 进行初始化
     * @param list 科大讯飞返回的list
     */
    private void init(List<String> list){
        listIn=new ArrayList<>();
        listIn.addAll(list);
    }


    @Override
    public void DoMatchAdapter(Class c) {
        ComServiceAdapter adapter= (ComServiceAdapter) adapterFactory.createClassInstance(c);
        adapter.initWordData();
        double percent=adapter.MatchService(listIn);
        int tag=adapter.GetServiceTag();
        String keyWord=adapter.GetServiceKeyWords();
        Log.d(TAG,c.getSimpleName()+"匹配百分比:"+percent);
        if (tag!= ComServiceWrapper.NOFRAGMENT){
            Log.d(TAG,c.getSimpleName()+"匹配成功！FragmentTag="+tag);
            Map<String,Object> map=new HashMap<>();
            map.put("percent",percent);
            map.put("tag",tag);
            map.put("KeyWord",keyWord);
            AllMaps.add(map);
        }
        else
            Log.d(TAG,"匹配失败！");
        Log.d(TAG,"-------------------------------------");
    }



    @Override
    public Map<String,Object> FindBestAdapter() {
        if (AllMaps.size()>0){
            double max = 0.0;
            int position = 0;
            for (int i = 0; i < AllMaps.size(); i++) {
                double realMax = (double) AllMaps.get(i).get("percent");
                if (realMax > max) {
                    max = realMax;
                    position = i;
                }
            }
           Log.d(TAG,"符合匹配的map：" + AllMaps);
            Map<String, Object> map = AllMaps.get(position);
           Log.d (TAG,"最大值map：" + map);
            return map;
        }else
            return  null;
    }

    @Override
    public Map<String,Object> mainDispatcher(List<String> list) {
        init(list);
        DoMatchAdapter(LocationAdapter.class);
        DoMatchAdapter(OpenAppAdapter.class);
        DoMatchAdapter(WebSearchAdapter.class);
        return  FindBestAdapter();
    }

}
