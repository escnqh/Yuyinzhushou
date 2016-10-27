package com.newthread.ntp_yuyinzhushou.bean;

import com.newthread.ntp_yuyinzhushou.dao.WordsDao;
import com.newthread.ntp_yuyinzhushou.wrapper.ComServiceWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/18.
 * 打开App应用的ServiceBean
 */

public class OpenAppWordBean extends ComWordsBean implements WordsDao {

    @Override
    public List<String> getInvalidWords() {
        List<String> invalidList=new ArrayList<>();
        invalidList.addAll(getInValidWordList());
        invalidList.add("打开应用废词1");
        invalidList.add("打开应用废词2");
        invalidList.add("打开应用废词3");
        invalidList.add("打开应用废词4");
        invalidList.add("打开应用废词5");
        invalidList.add("打开应用废词6");
        return invalidList;
    }

    @Override
    public Map<String, String> getServiceKeyWords() {
        Map<String, String> ServiceKeyWordMap = new HashMap<>();
        ServiceKeyWordMap.put("打开","LaunchApp");
        ServiceKeyWordMap.put("安装","DownLoadApp");
        ServiceKeyWordMap.put("下载","DownLoadApp");
        return ServiceKeyWordMap;
    }

    @Override
    public Map<String, Integer> getServiceTagMap() {
        Map<String,Integer> map=new HashMap<>();
        map.put("LaunchApp", ComServiceWrapper.LAUNCHAPP_FRAGMENT);
        map.put("DownLoadApp",ComServiceWrapper.DOWNLOADAPP_FRAGMENT);
        return map;
    }
}
