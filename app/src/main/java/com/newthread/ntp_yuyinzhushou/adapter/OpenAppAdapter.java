package com.newthread.ntp_yuyinzhushou.adapter;

import com.newthread.ntp_yuyinzhushou.bean.OpenAppWordBean;
import com.newthread.ntp_yuyinzhushou.dao.WordsDao;
import com.newthread.ntp_yuyinzhushou.wrapper.ComServiceWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/18.
 */

public class OpenAppAdapter extends ComAdapter implements ComServiceAdapter{
    private WordsDao wordsDao;
    private List<String> invalidWordsList;
    private Map<String,String> ServiceKeyWords;
    private Map<String,Integer> ServiceTagMap;
    private List<String> keyWordFind;//找到的关键词
    private int Tag= ComServiceWrapper.NOFRAGMENT;//默认的tag  菜单
    private String keyWord="";

    @Override
    public void initWordData() {
        wordsDao=initialize(OpenAppWordBean.class);
        invalidWordsList=wordsDao.getInvalidWords();
        ServiceKeyWords=wordsDao.getServiceKeyWords();
        ServiceTagMap=wordsDao.getServiceTagMap();
        keyWordFind=new ArrayList<>();
    }

    @Override
    public double MatchService(List<String> listIn) {
        //废词移除
        listIn.removeAll(invalidWordsList);
        System.out.println("进入的关键词："+listIn);
        //关键词搜索
        for (int i=0;i<listIn.size();i++){
            for (Map.Entry<String,String> entry:ServiceKeyWords.entrySet()){
                if (entry.getKey().equals(listIn.get(i))){
                    keyWordFind.add(entry.getValue());
                }
            }
        }
        //获取关键词
        keyWord=getOpenKeyWord(listIn);


        System.out.println("匹配的service："+keyWordFind);
        float size=keyWordFind.size();

        if (size==0){
            return 0;//无匹配，相似度0
        }else if (size>=1){
            for (Map.Entry<String,Integer> entry:ServiceTagMap.entrySet()){
                if (entry.getKey().equals(keyWordFind.get(0))){
                    Tag=entry.getValue();
                }
            }
            return (1/size)*100;
        } else
            return  0;
    }

    @Override
    public int GetServiceTag() {
        return Tag;
    }

    @Override
    public String GetServiceKeyWords() {
        return keyWord;
    }


    private String getOpenKeyWord(List<String> listIn){
        StringBuilder builder=new StringBuilder();
        List<String> list=new ArrayList<>();
        for (Map.Entry<String,String> entry:ServiceKeyWords.entrySet())
        list.add(entry.getKey());
        listIn.removeAll(list);
        for (String s:listIn)
            builder.append(s);
        System.out.println(listIn);
        return builder.toString();
    }






}
