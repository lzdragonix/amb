package com.scxrh.amb.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scxrh.amb.R;

public class MainFragment extends BaseFragment
{
    @Override
    protected View genView(LayoutInflater inflater, ViewGroup container)
    {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
