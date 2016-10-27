package com.newthread.ntp_yuyinzhushou;

import com.newthread.ntp_yuyinzhushou.dispatcher.Dispatcher;
import com.newthread.ntp_yuyinzhushou.dispatcher.ServiceDispatcher;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张浩 on 2016/10/18.
 */

public class OpenAppTest {
    @Test
    public void testOpenAppWordBean(){
        List<String> list=new ArrayList<>();
        list.add("打开");
        list.add("qq");
        Dispatcher dispatcher=new ServiceDispatcher();
        dispatcher.mainDispatcher(list);
    }
}
