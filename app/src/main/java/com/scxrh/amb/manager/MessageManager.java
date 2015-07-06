package com.scxrh.amb.manager;

import android.content.Context;
import android.content.res.Resources;

import com.scxrh.amb.Const;
import com.scxrh.amb.R;

import java.util.HashMap;
import java.util.Map;

public class MessageManager
{
    private Map<Integer, String> map = new HashMap<>();

    public MessageManager(Context context)
    {
        Resources r = context.getResources();
        map.put(Const.MSG_SERVER_ERROR, r.getString(R.string.msg_server_error));
        map.put(Const.MSG_EMPTY_USER_OR_PASSWORD, r.getString(R.string.msg_empty_user_or_password));
        map.put(Const.MSG_LOGIN, r.getString(R.string.msg_login));
    }

    public String getMessage(int msgId)
    {
        return map.get(msgId);
    }
}
