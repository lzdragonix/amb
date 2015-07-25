package com.scxrh.amb.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class AppInfo
{
    private Map<String, List<Subject>> subjectMapper = new ConcurrentHashMap<>();
    private City city = new City();
    private City community = new City();
    private String name = "";
    private String avatar = "";

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
        notify("avatar", avatar);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        notify("name", name);
    }

    public City getCommunity()
    {
        return community;
    }

    public void setCommunity(City community)
    {
        this.community = community;
        notify("community", community);
    }

    public City getCity()
    {
        return city;
    }

    public void setCity(City city)
    {
        this.city = city;
        notify("city", city);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    public <T> Observable<T> observable(@NonNull String name, @NonNull Object observe, @NonNull Class<T> clazz)
    {
        String key = genKey(name, observe);
        List<Subject> subjectList = subjectMapper.get(key);
        if (null == subjectList)
        {
            subjectList = new ArrayList<>();
            subjectMapper.put(key, subjectList);
        }
        Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        return subject;
    }

    public void unobservable(Object observe)
    {
        String hash = String.valueOf(observe.hashCode());
        Set<String> keys = subjectMapper.keySet();
        for (String key : keys)
        {
            if (!key.contains(hash)) { continue; }
            subjectMapper.remove(key);
        }
    }

    @SuppressWarnings("unchecked")
    private void notify(@NonNull String name, @NonNull Object content)
    {
        Set<String> keys = subjectMapper.keySet();
        for (String key : keys)
        {
            if (!key.contains(name)) { continue; }
            List<Subject> subjectList = subjectMapper.get(key);
            for (Subject subject : subjectList)
            {
                subject.onNext(content);
            }
        }
    }

    private String genKey(String name, Object observe)
    {
        return name + "-" + observe.hashCode();
    }
}
