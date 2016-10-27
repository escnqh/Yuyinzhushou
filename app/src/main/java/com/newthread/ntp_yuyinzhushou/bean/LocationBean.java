package com.newthread.ntp_yuyinzhushou.bean;

import com.newthread.ntp_yuyinzhushou.dao.WordsDao;
import com.newthread.ntp_yuyinzhushou.wrapper.ComServiceWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MoChan on 2016/10/20.
 */

public class LocationBean extends ComWordsBean implements WordsDao {

    @Override
    public List<String> getInvalidWords() {
        List<String> invalidWordList = new ArrayList<>();
        invalidWordList.addAll(getInValidWordList());
        invalidWordList.add("高德");
        invalidWordList.add("百度");
        invalidWordList.add("东西");
        return invalidWordList;
    }

    @Override
    public Map<String, String> getServiceKeyWords() {
        Map<String, String> keyWordMap = new HashMap<>();
        keyWordMap.put("地图", "MapOpen");
        keyWordMap.put("附近", "MapOpen");
        keyWordMap.put("周边", "MapOpen");
        keyWordMap.put("周围", "MapOpen");
        keyWordMap.put("导航", "MapOpen");
        keyWordMap.put("去", "MapSearchAction");
        keyWordMap.put("到", "MapSearchAction");
        keyWordMap.put("吃", "MapSearchAction");
        keyWordMap.put("吃饭", "MapSearchAction");
        keyWordMap.put("喝", "MapSearchAction");
        keyWordMap.put("喝水", "MapSearchAction");
        keyWordMap.put("饿", "MapSearchAction");
        keyWordMap.put("渴", "MapSearchAction");
        keyWordMap.put("口渴", "MapSearchAction");
        keyWordMap.put("住", "MapSearchAction");
        keyWordMap.put("住宿", "MapSearchAction");
        keyWordMap.put("病", "MapSearchAction");
        keyWordMap.put("看病", "MapSearchAction");
        keyWordMap.put("吃药", "MapSearchAction");
        keyWordMap.put("买药", "MapSearchAction");
        keyWordMap.put("上班", "MapSearchAction");
        keyWordMap.put("上学", "MapSearchAction");
        keyWordMap.put("回家", "MapSearchAction");
        keyWordMap.put("游戏", "MapSearchAction");
        keyWordMap.put("打牌", "MapSearchAction");
        keyWordMap.put("参观", "MapSearchAction");
        keyWordMap.put("游玩", "MapSearchAction");
        keyWordMap.put("游览", "MapSearchAction");
        keyWordMap.put("存钱", "MapSearchAction");
        keyWordMap.put("取钱", "MapSearchAction");
        keyWordMap.put("取钱", "MapSearchAction");
        keyWordMap.put("买", "MapSearchAction");
        keyWordMap.put("买菜", "MapSearchAction");
        keyWordMap.put("购物", "MapSearchAction");
        keyWordMap.put("逛街", "MapSearchAction");
        keyWordMap.put("运动", "MapSearchAction");
        keyWordMap.put("健身", "MapSearchAction");
        keyWordMap.put("游泳", "MapSearchAction");
        keyWordMap.put("打球", "MapSearchAction");
        keyWordMap.put("踢球", "MapSearchAction");
        keyWordMap.put("健身", "MapSearchAction");
        keyWordMap.put("骑车", "MapSearchAction");
        keyWordMap.put("骑行", "MapSearchAction");
        keyWordMap.put("跑步", "MapSearchAction");
        keyWordMap.put("散步", "MapSearchAction");
        keyWordMap.put("跳舞", "MapSearchAction");
        keyWordMap.put("唱歌", "MapSearchAction");
        keyWordMap.put("喝酒", "MapSearchAction");
        keyWordMap.put("上网", "MapSearchAction");
        keyWordMap.put("按摩", "MapSearchAction");
        keyWordMap.put("足疗", "MapSearchAction");
        keyWordMap.put("桑拿", "MapSearchAction");
        keyWordMap.put("汗蒸", "MapSearchAction");
        keyWordMap.put("理发", "MapSearchAction");
        keyWordMap.put("剪发", "MapSearchAction");
        keyWordMap.put("洗头", "MapSearchAction");
        keyWordMap.put("洗发", "MapSearchAction");
        keyWordMap.put("剪头", "MapSearchAction");
        keyWordMap.put("剃头", "MapSearchAction");
        keyWordMap.put("美容", "MapSearchAction");
        keyWordMap.put("美发", "MapSearchAction");
        keyWordMap.put("寄", "MapSearchAction");
        keyWordMap.put("邮", "MapSearchAction");
        keyWordMap.put("洗手间", "MapSearchAction");
        keyWordMap.put("如厕", "MapSearchAction");
        keyWordMap.put("入厕", "MapSearchAction");
        keyWordMap.put("大号", "MapSearchAction");
        keyWordMap.put("小号", "MapSearchAction");
        keyWordMap.put("大解", "MapSearchAction");
        keyWordMap.put("小解", "MapSearchAction");
        keyWordMap.put("看书", "MapSearchAction");
        keyWordMap.put("餐厅", "MapSearchNoun");
        keyWordMap.put("餐馆", "MapSearchNoun");
        keyWordMap.put("饭店", "MapSearchNoun");
        keyWordMap.put("饭馆", "MapSearchNoun");
        keyWordMap.put("小吃", "MapSearchNoun");
        keyWordMap.put("馆子", "MapSearchNoun");
        keyWordMap.put("饭", "MapSearchNoun");
        keyWordMap.put("市场", "MapSearchNoun");
        keyWordMap.put("超市", "MapSearchNoun");
        keyWordMap.put("商场", "MapSearchNoun");
        keyWordMap.put("商城", "MapSearchNoun");
        keyWordMap.put("菜市场", "MapSearchNoun");
        keyWordMap.put("步行街", "MapSearchNoun");
        keyWordMap.put("百货", "MapSearchNoun");
        keyWordMap.put("酒店", "MapSearchNoun");
        keyWordMap.put("宾馆", "MapSearchNoun");
        keyWordMap.put("旅馆", "MapSearchNoun");
        keyWordMap.put("旅店", "MapSearchNoun");
        keyWordMap.put("客栈", "MapSearchNoun");
        keyWordMap.put("招待所", "MapSearchNoun");
        keyWordMap.put("钟点房", "MapSearchNoun");
        keyWordMap.put("公寓", "MapSearchNoun");
        keyWordMap.put("小区", "MapSearchNoun");
        keyWordMap.put("银行", "MapSearchNoun");
        keyWordMap.put("邮局", "MapSearchNoun");
        keyWordMap.put("邮政", "MapSearchNoun");
        keyWordMap.put("快递", "MapSearchNoun");
        keyWordMap.put("顺丰", "MapSearchNoun");
        keyWordMap.put("圆通", "MapSearchNoun");
        keyWordMap.put("申通", "MapSearchNoun");
        keyWordMap.put("中通", "MapSearchNoun");
        keyWordMap.put("韵达", "MapSearchNoun");
        keyWordMap.put("菜鸟", "MapSearchNoun");
        keyWordMap.put("驿站", "MapSearchNoun");
        keyWordMap.put("体育馆", "MapSearchNoun");
        keyWordMap.put("游泳馆", "MapSearchNoun");
        keyWordMap.put("体育场", "MapSearchNoun");
        keyWordMap.put("篮球场", "MapSearchNoun");
        keyWordMap.put("篮球", "MapSearchNoun");
        keyWordMap.put("足球场", "MapSearchNoun");
        keyWordMap.put("足球", "MapSearchNoun");
        keyWordMap.put("田径场", "MapSearchNoun");
        keyWordMap.put("网球场", "MapSearchNoun");
        keyWordMap.put("网球", "MapSearchNoun");
        keyWordMap.put("羽毛球场", "MapSearchNoun");
        keyWordMap.put("羽毛球馆", "MapSearchNoun");
        keyWordMap.put("羽毛球", "MapSearchNoun");
        keyWordMap.put("台球馆", "MapSearchNoun");
        keyWordMap.put("台球", "MapSearchNoun");
        keyWordMap.put("桌球馆", "MapSearchNoun");
        keyWordMap.put("桌球", "MapSearchNoun");
        keyWordMap.put("乒乓球台", "MapSearchNoun");
        keyWordMap.put("乒乓球馆", "MapSearchNoun");
        keyWordMap.put("乒乓球场", "MapSearchNoun");
        keyWordMap.put("乒乓球室", "MapSearchNoun");
        keyWordMap.put("乒乓球", "MapSearchNoun");
        keyWordMap.put("药店", "MapSearchNoun");
        keyWordMap.put("药房", "MapSearchNoun");
        keyWordMap.put("医院", "MapSearchNoun");
        keyWordMap.put("诊所", "MapSearchNoun");
        keyWordMap.put("门诊", "MapSearchNoun");
        keyWordMap.put("医务室", "MapSearchNoun");
        keyWordMap.put("医务所", "MapSearchNoun");
        keyWordMap.put("感冒", "MapSearchNoun");
        keyWordMap.put("发烧", "MapSearchNoun");
        keyWordMap.put("发热", "MapSearchNoun");
        keyWordMap.put("头疼", "MapSearchNoun");
        keyWordMap.put("头痛", "MapSearchNoun");
        keyWordMap.put("发炎", "MapSearchNoun");
        keyWordMap.put("骨折", "MapSearchNoun");
        keyWordMap.put("疼", "MapSearchNoun");
        keyWordMap.put("痛", "MapSearchNoun");
        keyWordMap.put("胃痛", "MapSearchNoun");
        keyWordMap.put("受伤", "MapSearchNoun");
        keyWordMap.put("中暑", "MapSearchNoun");
        keyWordMap.put("公园", "MapSearchNoun");
        keyWordMap.put("游乐场", "MapSearchNoun");
        keyWordMap.put("游乐园", "MapSearchNoun");
        keyWordMap.put("景点", "MapSearchNoun");
        keyWordMap.put("风景", "MapSearchNoun");
        keyWordMap.put("名胜", "MapSearchNoun");
        keyWordMap.put("古迹", "MapSearchNoun");
        keyWordMap.put("博物馆", "MapSearchNoun");
        keyWordMap.put("动物园", "MapSearchNoun");
        keyWordMap.put("停车场", "MapSearchNoun");
        keyWordMap.put("理发店", "MapSearchNoun");
        keyWordMap.put("美发店", "MapSearchNoun");
        keyWordMap.put("美容店", "MapSearchNoun");
        keyWordMap.put("网吧", "MapSearchNoun");
        keyWordMap.put("网咖", "MapSearchNoun");
        keyWordMap.put("歌厅", "MapSearchNoun");
        keyWordMap.put("舞厅", "MapSearchNoun");
        keyWordMap.put("迪厅", "MapSearchNoun");
        keyWordMap.put("迪吧", "MapSearchNoun");
        keyWordMap.put("KTV", "MapSearchNoun");
        keyWordMap.put("卡拉OK", "MapSearchNoun");
        keyWordMap.put("歌舞厅", "MapSearchNoun");
        keyWordMap.put("酒吧", "MapSearchNoun");
        keyWordMap.put("清吧", "MapSearchNoun");
        keyWordMap.put("厕所", "MapSearchNoun");
        keyWordMap.put("公厕", "MapSearchNoun");
        return keyWordMap;
    }

    @Override
    public Map<String, Integer> getServiceTagMap() {
        Map<String, Integer> tagMap = new HashMap<>();
        tagMap.put("MapOpen", ComServiceWrapper.LOCAITON_FRAGMENT);
        tagMap.put("MapSearchAction", ComServiceWrapper.LOCAITON_FRAGMENT);
        tagMap.put("MapSearchNoun", ComServiceWrapper.LOCAITON_FRAGMENT);
        return tagMap;
    }

}
