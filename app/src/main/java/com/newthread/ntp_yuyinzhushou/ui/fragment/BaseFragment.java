package com.newthread.ntp_yuyinzhushou.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.newthread.ntp_yuyinzhushou.R;
import com.newthread.ntp_yuyinzhushou.dispatcher.Dispatcher;
import com.newthread.ntp_yuyinzhushou.dispatcher.ServiceDispatcher;
import com.newthread.ntp_yuyinzhushou.ui.view.RippleBackground;
import com.newthread.ntp_yuyinzhushou.util.SpeechRecognizerUtil;
import com.newthread.ntp_yuyinzhushou.wrapper.ComServiceWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/17.
 */

public class BaseFragment extends Fragment implements RecognizerListener {
    private static final String TAG=BaseFragment.class.getSimpleName();
    SpeechRecognizer recognizer;
    boolean isSpeak=false;
    RippleBackground rippleBackground;
    FloatingActionButton floatingActionButton;
    TextView Listener_Tv;
    List<String> VoiceArray=new ArrayList<>();
    FragmentVoiceResultListener fragmentVoiceResultListener;
    static FragmentChangeListener fragmentChangeListener;
    Vibrator vibrator;

    /**
     * 从Activity中获取控件
     */
    protected void initView(){
        floatingActionButton= (FloatingActionButton) getActivity().findViewById(R.id.voice_fab);
        rippleBackground= (RippleBackground) getActivity().findViewById(R.id.content);
        Listener_Tv= (TextView) getActivity().findViewById(R.id.listening_tv);
        if (rippleBackground.isRippleAnimationRunning()){
            rippleBackground.stopRippleAnimation();
            Listener_Tv.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化科大讯飞
     */
    protected void init(){
        vibrator= (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        recognizer= SpeechRecognizerUtil.getRecognizer(getContext());
        SpeechRecognizerUtil.initSpeechRecognizer(recognizer);
    }


    /**
     *开始讲话
     */
    protected void beginSpeak(){
        vibrator.vibrate(100);
        int ret=recognizer.startListening(this);
        switch (ret){
            case ErrorCode.ERROR_NO_NETWORK:
                Toast.makeText(getContext(),"请检查网络设置!"+ret,Toast.LENGTH_SHORT).show();
                break;
            case ErrorCode.ERROR_NETWORK_TIMEOUT:
                Toast.makeText(getContext(),"连接超时！"+ret,Toast.LENGTH_SHORT).show();
                break;
        }
    }



    /**
     * MenuFragment回调到MainActivity
     * @param list
     */
    protected void setFragmentChange(List<String> list){
        Map<String,Object> map=ProgressResult(list);
        if (fragmentChangeListener!=null)
            fragmentChangeListener.onFragmentChange(map,list);

    }



    /**
     * 处理返回结果
     * @param list 返回结果
     * @return map
     */
    protected Map<String,Object> ProgressResult(List<String> list){
        Map<String,Object> map=new HashMap<>();
        int tag= ComServiceWrapper.NOFRAGMENT;
        String keyWord="";

        Dispatcher dispatcher=new ServiceDispatcher();
        Map<String,Object> BestAdapterMap=dispatcher.mainDispatcher(list);


        if (BestAdapterMap!=null){
            tag= (int) BestAdapterMap.get("tag");
            keyWord= (String) BestAdapterMap.get("KeyWord");
        }
        map.put("tag",tag);
        map.put("ResultList",list);
        map.put("KeyWord",keyWord);
        Log.d(TAG,"实际的map"+map);
        return map;
    }




    @Override
    public void onVolumeChanged(int i, byte[] bytes) {
        if (isSpeak){
            rippleBackground.startRippleAnimation();
        }
    }

    @Override
    public void onBeginOfSpeech() {
        isSpeak=true;
        if (Listener_Tv.getVisibility()== View.GONE)
            Listener_Tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEndOfSpeech() {
        isSpeak=false;
        if (Listener_Tv.getVisibility()==View.VISIBLE)
            Listener_Tv.setVisibility(View.GONE);
        rippleBackground.stopRippleAnimation();
    }

    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
        if (VoiceArray.size()>0)//最好不用全局变量！！！
            VoiceArray.clear();
        if (!b){
            VoiceArray=SpeechRecognizerUtil.processResult(recognizerResult.getResultString());//返回的数据
            if (fragmentVoiceResultListener!=null) fragmentVoiceResultListener.onResult(VoiceArray);

        }
    }

    @Override
    public void onError(SpeechError speechError) {
        int error=speechError.getErrorCode();
        switch (error){
            case ErrorCode.ERROR_NO_NETWORK:
                Toast.makeText(getContext(),"请检查网络设置!"+String.valueOf(error),Toast.LENGTH_SHORT).show();
                break;
            case ErrorCode.ERROR_NETWORK_TIMEOUT:
                Toast.makeText(getContext(),"连接超时！"+error,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }

    public  interface FragmentChangeListener{
        void onFragmentChange(Map<String,Object> map,List<String> arrayList);
    }

    public interface FragmentVoiceResultListener{
        void onResult(List<String> list);
    }

    public static void setFragmentChangeListener(FragmentChangeListener ChangeListener) {
        fragmentChangeListener = ChangeListener;
    }


    public void setFragmentVoiceResultListener(FragmentVoiceResultListener fragmentVoiceResultListener) {
        this.fragmentVoiceResultListener = fragmentVoiceResultListener;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recognizer!=null){
            recognizer.cancel();
            recognizer.destroy();
        }
    }

}
