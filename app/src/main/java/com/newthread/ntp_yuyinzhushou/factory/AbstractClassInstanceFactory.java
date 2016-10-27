package com.newthread.ntp_yuyinzhushou.factory;

import com.newthread.ntp_yuyinzhushou.adapter.ComServiceAdapter;
import com.newthread.ntp_yuyinzhushou.dao.WordsDao;

/**
 * Created by 张浩 on 2016/10/18.
 * 词表工厂的抽象类
 */

public abstract class AbstractClassInstanceFactory {
    public abstract <T> T createClassInstance(Class<T> tClass);
}
