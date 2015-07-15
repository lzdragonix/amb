package com.scxrh.amb.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class SysInfo
{
    private Map<String, List<Subject>> subjectMapper = new ConcurrentHashMap<>();
    private City city = new City();
    private City community = new City();

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

    @SuppressWarnings("unchecked")
    public <T> Observable<T> observable(@NonNull String name, @NonNull Class<T> clazz)
    {
        List<Subject> subjectList = subjectMapper.get(name);
        if (null == subjectList)
        {
            subjectList = new ArrayList<>();
            subjectMapper.put(name, subjectList);
        }
        Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        return subject;
    }

    public <T> void unobservable(@NonNull String name, @NonNull Observable observable)
    {
        List<Subject> subjects = subjectMapper.get(name);
        if (subjects != null)
        {
            subjectMapper.remove(name);
        }
    }

    @SuppressWarnings("unchecked")
    private void notify(@NonNull String name, @NonNull Object content)
    {
        List<Subject> subjectList = subjectMapper.get(name);
        for (Subject subject : subjectList)
        {
            subject.onNext(content);
        }
    }
}
