package com.scxrh.amb.common;

import android.util.Log;

import com.scxrh.amb.manager.DirManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger
{
    //日志是否写文件开关，当为debug版时输出'd'级别的日志，release版设为false
    private static Boolean LOG_SWITCH = true;
    //输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static char LOG_TYPE = 'v';
    //本类输出的日志文件名称
    private static String LOG_FILENAME = "Log.txt";
    //日志的输出格式
    private static SimpleDateFormat mLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    //日志文件格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");

    public static void w(String tag, String text)
    {
        log(tag, text, 'w', null);
    }

    public static void e(String tag, String text)
    {
        log(tag, text, 'e', null);
    }

    public static void d(String tag, String text)
    {
        log(tag, text, 'd', null);
    }

    public static void i(String tag, String text)
    {
        log(tag, text, 'i', null);
    }

    public static void v(String tag, String text)
    {
        log(tag, text, 'v', null);
    }

    public static void w(String tag, String msg, Throwable tr)
    {
        log(tag, msg, 'w', tr);
    }

    public static void e(String tag, String msg, Throwable tr)
    {
        log(tag, msg, 'e', tr);
    }

    public static void d(String tag, String msg, Throwable tr)
    {
        log(tag, msg, 'd', tr);
    }

    public static void i(String tag, String msg, Throwable tr)
    {
        log(tag, msg, 'i', tr);
    }

    public static void v(String tag, String msg, Throwable tr)
    {
        log(tag, msg, 'v', tr);
    }

    public static void setLogSwitch(boolean log)
    {
        LOG_SWITCH = log;
    }

    private static void log(String tag, String msg, char level, Throwable tr)
    {
        if ('e' == level && ('e' == LOG_TYPE || 'v' == LOG_TYPE))
        {
            //输出错误信息
            if (tr == null) { Log.e(tag, msg); }
            else { Log.e(tag, msg, tr); }
        }
        else if ('w' == level && ('w' == LOG_TYPE || 'v' == LOG_TYPE))
        {
            if (tr == null) { Log.w(tag, msg); }
            else { Log.w(tag, msg, tr); }
        }
        else if ('d' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE))
        {
            if (tr == null) { Log.d(tag, msg); }
            else { Log.d(tag, msg, tr); }
        }
        else if ('i' == level && ('i' == LOG_TYPE || 'v' == LOG_TYPE))
        {
            if (tr == null) { Log.i(tag, msg); }
            else { Log.i(tag, msg, tr); }
        }
        else
        {
            if (tr == null) { Log.v(tag, msg); }
            else { Log.v(tag, msg, tr); }
        }
        if ((LOG_SWITCH && 'd' == level) || 'e' == level)
        {
            if (tr == null) { writeLogToFile(String.valueOf(level), tag, msg); }
            else { writeLogToFile(String.valueOf(level), tag + " " + msg, tr); }
        }
    }

    //新建或打开日志文件
    private static void writeLogToFile(String mylogtype, String tag, String text)
    {
        if (!Utils.hasSDCard()) { return; }
        Date nowtime = new Date();
        String needWriteFile = logfile.format(nowtime);
        String needWriteMessage = mLogSdf.format(nowtime) + "    " + mylogtype + "    " + tag + "    " + text;
        String pathLog = DirManager.getPath(DirManager.PATH_LOG);
        File file = new File(pathLog);
        if (!file.exists() && !file.mkdirs()) { return; }
        file = new File(pathLog, needWriteFile + "-" + LOG_FILENAME);
        FileWriter filerWriter = null;
        BufferedWriter bufWriter = null;
        try
        {
            filerWriter = new FileWriter(file, true);
            bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (bufWriter != null) { bufWriter.close(); }
                if (filerWriter != null) { filerWriter.close(); }
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
    }

    //记录日志
    private static void writeLogToFile(String level, String tag, Throwable ex)
    {
        StringBuilder sb = new StringBuilder();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null)
        {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        writeLogToFile(level, tag + ":\n", sb.toString() + "\n");
    }
}
