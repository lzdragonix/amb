package com.scxrh.amb;

import java.util.regex.Pattern;

public class Const
{
    //some message id
    public static final int MSG_SERVER_ERROR = 0xf2f2;
    public static final int MSG_EMPTY_USER_OR_PASSWORD = MSG_SERVER_ERROR + 1;
    public static final int MSG_LOGIN = MSG_SERVER_ERROR + 2;
    public static final int MSG_MOBILE_ILLEGAL = MSG_SERVER_ERROR + 3;
    //some url
    public static final String URL_BASE = "http://113.106.63.129:8489/windforce/m/";
    public static final String URL_LOGIN = URL_BASE + "mlogin.action";
    public static final String URL_USER_REGISTER = URL_BASE + "user_register.action";
    public static final String URL_SMS_SEND = URL_BASE + "sms_send.action";
    //some key
    public static final String KEY_FRAGMENT = "KEY_FRAGMENT";
    public static final String KEY_CODE = "code";
    public static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    public static final String KEY_SERVER = "KEY_SERVER";
    //rest returncode
    public static final String RETURNCODE_0000 = "0000";
    public static final String RETURNCODE_0001 = "0001";
    //some regex
    public static final Pattern REGEX_EMAIL = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    public static final Pattern REGEX_MOBILE = Pattern.compile("^(1[8|3|5][0-9]|15[0|3|6|7|8|9]|18[2|5|6|8|9])\\d{8}$");
    public static final Pattern REGEX_IDCARD_15 = Pattern
            .compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
    public static final Pattern REGEX_IDCARD_18 = Pattern
            .compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
}