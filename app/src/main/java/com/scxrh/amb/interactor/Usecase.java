package com.scxrh.amb.interactor;

import rx.Observable;

public interface Usecase<T>
{
    Observable<T> execute();
}
