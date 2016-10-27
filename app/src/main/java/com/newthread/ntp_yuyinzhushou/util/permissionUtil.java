package com.newthread.ntp_yuyinzhushou.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by 张浩 on 2016/10/12.
 */

public class permissionUtil {
    /**
     * 检查所有app拥有的危险权限
     * @param permissions
     * @return
     */
    public static boolean hasPermissions(Context context,String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查返回结果
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
