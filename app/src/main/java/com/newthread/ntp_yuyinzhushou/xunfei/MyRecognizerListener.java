package com.newthread.ntp_yuyinzhushou.xunfei;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.newthread.ntp_yuyinzhushou.bean.YunYinBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张浩 on 2016/10/12.
 */

public class MyRecognizerListener implements RecognizerListener {

    private final String TAG=MyRecognizerListener.class.getSimpleName();

    private  List<String> VoiceArray=new ArrayList<>();

    public void setGetVoiceResultListener(GetVoiceResultListener getVoiceResultListener) {
        this.getVoiceResultListener = getVoiceResultListener;
    }

    private GetVoiceResultListener getVoiceResultListener;



    public  MyRecognizerListener getRecognizerListener(){
        return new MyRecognizerListener();
    }



    @Override
    public void onBeginOfSpeech() {
        Log.d(TAG,"开始说话");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG,"结束说话");
    }
    @Override
    public void onVolumeChanged(int i, byte[] bytes) {
        Log.d(TAG,"当前音量大小:"+i);
    }
    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
        //每次调用之前后清除一下
        if (VoiceArray.size()>0)
            VoiceArray.clear();
        if (!b){
            Log.d(TAG,"返回结果:"+recognizerResult.getResultString());
            //processResult(recognizerResult.getResultString());
        }

    }

    @Override
    public void onError(SpeechError speechError) {

    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }






    public interface GetVoiceResultListener{
        void getResultList(List<String> list);
    }

}
