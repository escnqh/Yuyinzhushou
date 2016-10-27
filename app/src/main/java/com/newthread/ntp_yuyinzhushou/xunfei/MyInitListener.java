package com.newthread.ntp_yuyinzhushou.xunfei;

import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;

/**
 * Created by 张浩 on 2016/10/12.
 */

public class MyInitListener implements InitListener {

    public static MyInitListener getInitListener(){
        return new MyInitListener();
    }

    private String TAG=MyInitListener.class.getSimpleName();
    @Override
    public void onInit(int i) {
        Log.d(TAG, "SpeechRecognizer init() code = " + i);
        if (i != ErrorCode.SUCCESS) {
            Log.d("初始化失败，错误码：" , String.valueOf(i));
        }
    }
}
