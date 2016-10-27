package com.newthread.ntp_yuyinzhushou.dispatcher;

import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/19.
 */

public interface Dispatcher{
    /**
     * 进行Adapter的匹配
     * @param c adapter类名
     */
    void DoMatchAdapter(Class c);

    /**
     * 找到匹配度最高的adapter
     * @return map
     */
    Map<String,Object> FindBestAdapter();


    /**
     * 进行匹配
     * @param list 输入的分词列表
     * @return map{percent=100，tag=100}
     */
    Map<String,Object>  mainDispatcher(List<String> list);
}
