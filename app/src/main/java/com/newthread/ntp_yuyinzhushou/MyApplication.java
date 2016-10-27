package com.newthread.ntp_yuyinzhushou;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

/**
 * Created by 张浩 on 2016/10/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        SpeechUtility.createUtility(this,"appid="+getString(R.string.app_id));
        super.onCreate();
    }
}
