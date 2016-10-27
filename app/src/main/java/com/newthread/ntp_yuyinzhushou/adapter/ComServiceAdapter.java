package com.newthread.ntp_yuyinzhushou.adapter;

import java.util.List;

/**
 * Created by 张浩 on 2016/10/18.
 * 所有功能的适配器接口
 */
public interface ComServiceAdapter{
    /**
     * 进行数据的初始化
     */
    void initWordData();

    /**
     * 处理句式，进行匹配，返回一个相似度的值
     * @param list 输入的分词List
     * @return 匹配值百分比
     */
    double MatchService(List<String> list);

    /**
     * 获取匹配成功之后的服务相应的tag
     * @return fragment tag
     */
    int GetServiceTag();


    /**
     * 获取最终与操作相关的关键词
     * @return keyword
     */
    String GetServiceKeyWords();

}
