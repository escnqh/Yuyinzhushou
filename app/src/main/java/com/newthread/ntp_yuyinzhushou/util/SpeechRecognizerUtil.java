package com.newthread.ntp_yuyinzhushou.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.newthread.ntp_yuyinzhushou.bean.YunYinBean;
import com.newthread.ntp_yuyinzhushou.xunfei.MyInitListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张浩 on 2016/10/12.
 */

public class SpeechRecognizerUtil {


    private String TAG=SpeechRecognizerUtil.class.getSimpleName();

    // SpeechConstant.TYPE_CLOUD;
    public static void initSpeechRecognizer(SpeechRecognizer speechRecognizer){
        //识别方式：云端方式
        speechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
        //返回结果
        speechRecognizer.setParameter(SpeechConstant.RESULT_TYPE,"json");
        //设置为中文语言
        speechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        speechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        speechRecognizer.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        speechRecognizer.setParameter(SpeechConstant.VAD_EOS, "2000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        speechRecognizer.setParameter(SpeechConstant.ASR_PTT, "0");
    }

    /**
     * 获取SpeechRecognizer对象
     * @param context 上下文
     * @return SpeechRecognizer
     */
    public static SpeechRecognizer getRecognizer(Context context){
        return SpeechRecognizer.createRecognizer(context, MyInitListener.getInitListener());
    }



    /**
     * 返回经过json解析后的文本
     * @param result json串
     * @return list<String>
     */
    public static List<String> processResult(String result){
        List<String> VoiceArray=new ArrayList<>();
        Gson gson=new Gson();
        YunYinBean yunYinBean=gson.fromJson(result,YunYinBean.class);
        List<YunYinBean.WsBean> list=yunYinBean.getWs();
        for (YunYinBean.WsBean wsBean:list){
            VoiceArray.add(wsBean.getCw().get(0).getW());
        }
        return VoiceArray;
    }

}
