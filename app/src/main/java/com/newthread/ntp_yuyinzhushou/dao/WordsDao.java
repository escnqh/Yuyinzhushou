package com.newthread.ntp_yuyinzhushou.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/12.
 * 获取数据信息
 */

public interface WordsDao {
    /**
     * 返回废词
     * @return list
     */
    List<String> getInvalidWords();

    /**
     * 返回API关键词
     * @return
     */
    Map<String,String> getServiceKeyWords();


    /**
     * 返回service对应的tag
     * @return
     */
    Map<String,Integer> getServiceTagMap();



}
