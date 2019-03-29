package com.dong.easy.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2019/3/16.
 */
public class AppInstallReceiver extends BroadcastReceiver {

    private final String TAG = "AppInstallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        PackageManager manager = context.getPackageManager();
        String action = intent.getAction();

        Uri data = intent.getData();
        String packageName = "";
        if (data != null) {
            packageName = data.getSchemeSpecificPart();
        }

        Log.i(TAG, "-- onReceive action=" + action + " | package=" + packageName);

        if (TextUtils.equals(action, Intent.ACTION_PACKAGE_ADDED)) {
            Log.i(TAG, "安装成功-- " + packageName);
        } else if (TextUtils.equals(action, Intent.ACTION_PACKAGE_REMOVED)) {
            Log.i(TAG, "卸载成功-- " + packageName);
        } else if (TextUtils.equals(action, Intent.ACTION_PACKAGE_REPLACED)) {
            Log.i(TAG, "替换成功-- " + packageName);
        }
    }

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
        intentFilter.addDataScheme("package");
        return intentFilter;
    }

    public void register(Context context) {
        if (context != null) {
            context.registerReceiver(this, getIntentFilter());
        }
    }

    public void unregister(Context context) {
        if (context != null) {
            context.unregisterReceiver(this);
        }
    }

}
