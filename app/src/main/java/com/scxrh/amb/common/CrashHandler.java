package com.scxrh.amb.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Debug;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements UncaughtExceptionHandler
{
    private static final String TAG = CrashHandler.class.getSimpleName();
    //CrashHandler实例
    private static volatile CrashHandler instance = null;
    //系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    //程序的Context对象  
    private Context mContext;

    private CrashHandler(Context context)
    {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 初始化CrashHandler单例
     */
    public static void initInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (CrashHandler.class)
            {
                if (instance == null)
                {
                    instance = new CrashHandler(context);
                }
            }
        }
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex)
    {
        if (!handleException(ex) && mDefaultHandler != null)
        {
            //如果用户没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);
        }
        else
        {
            Logger.d(TAG, "Exception:ThreadId=" + thread.getId() + ";ThreadName=" + thread.getName() +
                       ";AppId=" +
                       Process.myPid());
            if (Looper.myLooper() == Looper.getMainLooper())
            {
                //如果异常出现在主线程，提示并退出程序
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        Looper.prepare();
                        Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }.start();
                try
                {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e)
                {
                    Log.e(TAG, "error : ", e);
                }
                //退出程序
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
        }
    }

    private boolean handleException(Throwable ex)
    {
        //如果是调试状态则不生成异常文件，让系统默认的异常处理器来处理
        if (Debug.isDebuggerConnected() || ex == null) { return false; }
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件   
        Logger.e(TAG, "uncaughtException", ex);
        return true;
    }

    private Map<String, String> collectDeviceInfo(Context ctx)
    {
        Map<String, String> info = new HashMap<String, String>();
        try
        {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null)
            {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                info.put("versionName", versionName);
                info.put("versionCode", versionCode);
            }
        }
        catch (NameNotFoundException e)
        {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields)
        {
            try
            {
                field.setAccessible(true);
                info.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            }
            catch (Exception e)
            {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
        return info;
    }
}
