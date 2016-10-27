package com.newthread.ntp_yuyinzhushou.adapter;

import com.newthread.ntp_yuyinzhushou.dao.WordsDao;
import com.newthread.ntp_yuyinzhushou.factory.AbstractClassInstanceFactory;
import com.newthread.ntp_yuyinzhushou.factory.WordBeanFactory;

/**
 * Created by 张浩 on 2016/10/18.
 *  所有模块的Adapter的父类，用于继承
 */

class ComAdapter{
    private AbstractClassInstanceFactory wordBeanFactory=new WordBeanFactory();

    /**
     * 进行初始化
     * @param tClass 类
     * @return 具体的实例
     */
     WordsDao initialize(Class tClass){
        return (WordsDao) wordBeanFactory.createClassInstance(tClass);
    }
}
