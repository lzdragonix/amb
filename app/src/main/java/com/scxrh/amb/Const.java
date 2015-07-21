package com.scxrh.amb;

import java.util.regex.Pattern;

public class Const
{
    //some message id
    public static final int MSG_SERVER_ERROR = 0xf2f2;
    public static final int MSG_EMPTY_USER_OR_PASSWORD = MSG_SERVER_ERROR + 1;
    public static final int MSG_LOGIN = MSG_SERVER_ERROR + 2;
    public static final int MSG_MOBILE_ILLEGAL = MSG_SERVER_ERROR + 3;
    public static final int MSG_SUBMITTING = MSG_SERVER_ERROR + 4;
    public static final int MSG_PWD_IS_SHORT = MSG_SERVER_ERROR + 5;
    public static final int MSG_REG_FAILED = MSG_SERVER_ERROR + 6;
    public static final int MSG_INPUT_VERIFY_CODE = MSG_SERVER_ERROR + 7;
    public static final int MSG_AGREE_REG_PROTOCOL = MSG_SERVER_ERROR + 8;
    public static final int MSG_LOADING = MSG_SERVER_ERROR + 9;
    public static final int MSG_LOADING_FAILED = MSG_SERVER_ERROR + 10;
    public static final int MSG_ERROR_PASSWORD = MSG_SERVER_ERROR + 11;
    public static final int MSG_PWD_INCONFORMITY = MSG_SERVER_ERROR + 12;
    //some key
    public static final String KEY_FRAGMENT = "KEY_FRAGMENT";
    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_CODE = "code";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    public static final String KEY_COOKIE = "KEY_COOKIE";
    public static final String KEY_USER_NAME = "KEY_USER_NAME";
    public static final String KEY_CITY = "KEY_CITY";
    public static final String KEY_COMMUNITY = "KEY_COMMUNITY";
    public static final String KEY_AVATAR = "KEY_AVATAR";
    //rest returncode
    public static final String RETURNCODE_0000 = "0000";
    public static final String RETURNCODE_0001 = "0001";
    public static final String RETURNCODE_9999 = "9999";
    //some regex
    public static final Pattern REGEX_EMAIL = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    public static final Pattern REGEX_MOBILE = Pattern.compile("^(1[8|3|5][0-9]|15[0|3|6|7|8|9]|18[2|5|6|8|9])\\d{8}$");
    public static final Pattern REGEX_IDCARD_15 = Pattern.compile(
            "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
    public static final Pattern REGEX_IDCARD_18 = Pattern.compile(
            "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
    //some requestcode
    public static final int REQUESTCODE_CAMERA = 0xf000;
    public static final int REQUESTCODE_CROP = REQUESTCODE_CAMERA + 1;
}