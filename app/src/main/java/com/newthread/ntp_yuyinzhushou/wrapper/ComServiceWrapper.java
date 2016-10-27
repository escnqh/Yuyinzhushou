package com.newthread.ntp_yuyinzhushou.wrapper;

import com.newthread.ntp_yuyinzhushou.ui.fragment.MenuFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张浩 on 2016/10/14.
 */

public class ComServiceWrapper {

    public static final List<Map<String,Integer>> fragmentMapList=new ArrayList<>();

    /**
     * 获取呈现在用户前的fragment tag
     * @return
     */
    public static int GetCurrentFragmentTag(){
        int tag=NOFRAGMENT;
        for (Map.Entry<String,Integer> entry:fragmentMapList.get(fragmentMapList.size()-1).entrySet()){
            tag=entry.getValue();
        }
        return tag;
    }


    /**
     * 清除当前的List
     */
    public static void ClearCurrentFragmentList(){
        for (int  i=1;i<fragmentMapList.size();i++){
            fragmentMapList.remove(i);
        }
    }

    public static final int APPLOADFINISH=200;//app类加载完成
    public static final int NOFRAGMENT=404;//无fragment
    public static final int HOMEMENU_FRAGMENT = 100;//主页
    public static final int LOCAITON_FRAGMENT = 101;//定位
    public static final int CONTACT_FRAGMENT = 102;//联系人
    public static final int SETTING_FRAGMENT = 103;//设置
    public static final int SEARCH_FRAGMENT=104;//搜索
    public static final int  LAUNCHAPP_FRAGMENT=105;//打开app
    public static final int  DOWNLOADAPP_FRAGMENT=106;//下载app

    public static final String SHOMEMENU_FRAGMENT = "home";
    public static final String SLOCAITON_FRAGMENT = "locaiton";
    public static final String SCONTACT_FRAGMENT = "contact";
    public static final String SSETTING_FRAGMENT = "setting";
    public static final String SSEARCH_FRAGMENT="search";
    public static final String  SLAUNCHAPP_FRAGMENT="launch";
    public static final String  SDOWNLOADAPP_FRAGMENT="download";


}
