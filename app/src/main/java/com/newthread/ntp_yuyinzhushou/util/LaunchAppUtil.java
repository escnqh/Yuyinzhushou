package com.newthread.ntp_yuyinzhushou.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/19.
 */

public class LaunchAppUtil {

    private static Map<String,Intent> AppNameIntentMap=new HashMap<>();



    /**
     * 获取系统应用信息列表
     */
    public static void getAppList(AppCompatActivity activity) {
        PackageManager packageManager = activity.getPackageManager();
        List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo:list){
            String name=applicationInfo.loadLabel(packageManager).toString();
            String packageName=applicationInfo.packageName;
            Intent intent=packageManager.getLaunchIntentForPackage(packageName);
            AppNameIntentMap.put(name,intent);
            //String intentName=packageManager.getLaunchIntentForPackage(packageName).toString();
        }
    }


    /**
     * 通过app名字获取启动App的Intent
     * @param name
     * @return
     */
    private static Intent getIntentLaunchApp(String name){
        for (Map.Entry<String,Intent> entry:AppNameIntentMap.entrySet()){
            if (entry.getKey().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }


    /**
     * 启动app
     * @param context 上下文
     * @param map 返回值
     */
    public static void LaunchApp(Context context,Map<String,Object> map){
        String name="";
        name=(String) map.get("KeyWord");
        if (name.isEmpty())  Toast.makeText(context,"对不起，我没听懂你说什么！",Toast.LENGTH_SHORT).show();
        Intent intent=getIntentLaunchApp(name);
        if (intent!=null)
            context.startActivity(intent);
        else
            Toast.makeText(context,"对不起，您的手机没有安装此应用！",Toast.LENGTH_SHORT).show();
    }



}
