package com.scxrh.amb.common;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class RxBus
{
    private Map<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> Observable<T> register(@NonNull Object tag, @NonNull Class<T> clazz)
    {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList)
        {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }
        Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        return subject;
    }

    public void unregister(@NonNull Object tag, @NonNull Observable observable)
    {
        List<Subject> subjects = subjectMapper.get(tag);
        if (subjects != null)
        {
            subjectMapper.remove(tag);
        }
    }

    public void post(@NonNull Object content)
    {
        post(content.getClass().getName(), content);
    }

    @SuppressWarnings("unchecked")
    public void post(@NonNull Object tag, @NonNull Object content)
    {
        List<Subject> subjectList = subjectMapper.get(tag);
        for (Subject subject : subjectList)
        {
            subject.onNext(content);
        }
    }
}
