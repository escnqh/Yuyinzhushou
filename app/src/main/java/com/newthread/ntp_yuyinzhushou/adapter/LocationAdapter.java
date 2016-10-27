package com.newthread.ntp_yuyinzhushou.adapter;

import com.newthread.ntp_yuyinzhushou.bean.LocationBean;
import com.newthread.ntp_yuyinzhushou.dao.WordsDao;
import com.newthread.ntp_yuyinzhushou.wrapper.ComServiceWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MoChan on 2016/10/24.
 */

public class LocationAdapter extends ComAdapter implements ComServiceAdapter {

    private WordsDao wordsDao;
    private List<String> keyWordFind;
    private List<String> invalidWordsList;
    private Map<String, String> ServiceKeyWords;
    private Map<String, Integer> ServiceTagMap;
    private int Tag = ComServiceWrapper.LOCAITON_FRAGMENT;

    @Override
    public void initWordData() {
        wordsDao = initialize(LocationBean.class);
        invalidWordsList = wordsDao.getInvalidWords();
        ServiceKeyWords = wordsDao.getServiceKeyWords();
        ServiceTagMap = wordsDao.getServiceTagMap();
        keyWordFind = new ArrayList<>();
    }

    @Override
    public double MatchService(List<String> listIn) {
        listIn.removeAll(invalidWordsList);
        System.out.println("进入的关键词：" + listIn);
        for (int i = 0; i < listIn.size(); i++) {
            for (Map.Entry<String, String> entry : ServiceKeyWords.entrySet()) {
                if (entry.getKey().equals(listIn.get(i))) {
                    keyWordFind.add(entry.getValue());
                }
            }
        }
        System.out.println("匹配的service：" + keyWordFind);
        float size = keyWordFind.size();
        if (size == 0) {
            return 0;//无匹配，相似度0
        } else if (size >= 1) {
            for (Map.Entry<String, Integer> entry : ServiceTagMap.entrySet()) {
                if (entry.getKey().equals(keyWordFind.get(0))) {
                    Tag = entry.getValue();
                }
            }
            System.out.println("匹配值："+(1 * size) * 100);
            return (1 * size) * 100;
        } else {
            return 0;
        }
    }

    @Override
    public int GetServiceTag() {
        return Tag;
    }

    @Override
    public String GetServiceKeyWords() {
        return null;
    }
}
