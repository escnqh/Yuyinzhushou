package com.newthread.ntp_yuyinzhushou.factory;

import com.newthread.ntp_yuyinzhushou.adapter.ComServiceAdapter;
import com.newthread.ntp_yuyinzhushou.dao.WordsDao;

/**
 * Created by 张浩 on 2016/10/18.
 * 适配器生产工厂
 */

public class AdapterFactory extends AbstractClassInstanceFactory{
    @Override
    public <T> T createClassInstance(Class<T> tClass) {
        ComServiceAdapter adapter=null;
        try {
            adapter= (ComServiceAdapter) Class.forName(tClass.getName()).newInstance();
        }catch (Exception e){
            System.out.println("功能适配器生产出现错误！");
        }
        return (T) adapter;
    }
}
