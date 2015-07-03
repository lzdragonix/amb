package com.scxrh.amb.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scxrh.amb.R;

//推荐
public class RecommendFragment extends BaseFragment
{
    public static final String TAG = RecommendFragment.class.getSimpleName();

    @Override
    protected View genView(LayoutInflater inflater, ViewGroup container)
    {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }
}
