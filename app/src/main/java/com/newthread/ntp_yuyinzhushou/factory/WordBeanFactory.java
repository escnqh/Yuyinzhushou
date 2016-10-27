package com.newthread.ntp_yuyinzhushou.factory;

import com.newthread.ntp_yuyinzhushou.adapter.ComServiceAdapter;
import com.newthread.ntp_yuyinzhushou.dao.WordsDao;

/**
 * Created by 张浩 on 2016/10/18.
 * 词语表工厂
 */

public class WordBeanFactory extends AbstractClassInstanceFactory{
    @Override
    public <T> T createClassInstance(Class<T> tClass) {
        WordsDao wordsDao=null;
        try {
            wordsDao= (WordsDao) Class.forName(tClass.getName()).newInstance();
        }catch (Exception e){
            System.out.println("单词表产生错误！");
        }
        return (T) wordsDao;
    }
}
