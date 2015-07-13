package com.scxrh.amb.model;

public class City
{
    private String id;
    private String name;
    private String pinyin;
    private String py;
    private int hot;

    public int getHot()
    {
        return hot;
    }

    public void setHot(int hot)
    {
        this.hot = hot;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPinyin()
    {
        return pinyin;
    }

    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }

    public String getPy()
    {
        return py;
    }

    public void setPy(String py)
    {
        this.py = py;
    }
}
