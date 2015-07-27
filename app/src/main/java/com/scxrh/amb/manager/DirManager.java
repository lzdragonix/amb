package com.scxrh.amb.manager;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

public class DirManager
{
    public static final String PATH_APP_ROOT = "amb";
    public static final String PATH_LOG = "log";

    public static String getPath(String dir)
    {
        String path;
        if (TextUtils.isEmpty(dir))
        {
            path = addPath(Environment.getExternalStorageDirectory().getPath(), PATH_APP_ROOT);
        }
        else
        {
            path = addPath(getPath(null), dir);
        }
        return checkDir(path);
    }

    //检查目录是否存在，如果不存在则创建，并返回目录路径
    private static String checkDir(String path)
    {
        File file = new File(path);
        if (!file.exists()) { file.mkdirs(); }
        return path;
    }

    //组装路径
    private static String addPath(String parent, String child)
    {
        return String.format("%s/%s", parent, child);
    }

    public static String getPath()
    {
        return getPath(null);
    }
}
