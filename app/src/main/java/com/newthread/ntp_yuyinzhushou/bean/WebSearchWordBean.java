package com.newthread.ntp_yuyinzhushou.bean;

import com.newthread.ntp_yuyinzhushou.dao.WordsDao;
import com.newthread.ntp_yuyinzhushou.wrapper.ComServiceWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/18.
 * 网页搜索ServiceBean
 */

public class WebSearchWordBean extends ComWordsBean implements WordsDao{
    @Override
    public List<String> getInvalidWords() {
        return getInValidWordList();
    }

    @Override
    public Map<String, String> getServiceKeyWords() {
        Map<String,String> ServiceKeyWordMap=new HashMap<>();
//        ServiceKeyWordMap.put("","WebSearch");
        ServiceKeyWordMap.put("搜索","WebSearch");
        return ServiceKeyWordMap;
    }

    @Override
    public Map<String, Integer> getServiceTagMap() {
        Map<String,Integer> map=new HashMap<>();
        map.put("WebSearch", ComServiceWrapper.SEARCH_FRAGMENT);
        return map;
    }
}
