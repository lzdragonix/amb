package com.scxrh.amb.manager;

import android.os.Environment;

import java.io.File;

public class DirManager
{
    public static final String PATH_APP_ROOT = "amb";

    public String getPath(String dir)
    {
        String root = addPath(Environment.getExternalStorageDirectory().getPath(), PATH_APP_ROOT);
        return checkDir(root);
    }

    public String getPath()
    {
        return getPath(null);
    }

    //检查目录是否存在，如果不存在则创建，并返回目录路径
    private String checkDir(String path)
    {
        File file = new File(path);
        if (!file.exists()) { file.mkdirs(); }
        return path;
    }

    //组装路径
    private String addPath(String parent, String child)
    {
        return String.format("%s/%s", parent, child);
    }
}
