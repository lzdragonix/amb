package com.scxrh.amb.model;

import com.scxrh.amb.common.Utils;

public class UserInfo
{
    private String address;
    private String comunityId;
    private String email;
    private String point;
    private String telephone;
    private String userId;
    private String userName;

    public String getAddress()
    {
        return Utils.replaceNull(address);
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getComunityId()
    {
        return Utils.replaceNull(comunityId);
    }

    public void setComunityId(String comunityId)
    {
        this.comunityId = comunityId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPoint()
    {
        return point;
    }

    public void setPoint(String point)
    {
        this.point = point;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}
