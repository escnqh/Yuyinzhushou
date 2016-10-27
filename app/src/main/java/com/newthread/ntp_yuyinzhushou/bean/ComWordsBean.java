package com.newthread.ntp_yuyinzhushou.bean;


import com.newthread.ntp_yuyinzhushou.wrapper.ComServiceWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/12.
 * 硬编码测试类，暂时代替数据库
 */

public class ComWordsBean {

    public List<String> getInValidWordList() {
        inValidWordList =new ArrayList<>();
        inValidWordList.add("我");
        inValidWordList.add("了");
        inValidWordList.add("你");
        inValidWordList.add("的");
        inValidWordList.add("要");
        inValidWordList.add("想");
        inValidWordList.add("呢");
        inValidWordList.add("吗");
        inValidWordList.add("嘛");
        inValidWordList.add("饭");
        inValidWordList.add("给");
        inValidWordList.add("子");
        return inValidWordList;
    }

    private List<String> inValidWordList;

}
