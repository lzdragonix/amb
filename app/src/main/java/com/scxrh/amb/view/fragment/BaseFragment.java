package com.scxrh.amb.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scxrh.amb.view.activity.BaseActivity;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment
{
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int layoutId = getLayoutId();
        if (layoutId == 0)
        {
            throw new IllegalArgumentException("getLayoutId() returned 0, which is not allowed. " +
                                               "If you don't want to use getLayoutId() but implement your own view " +
                                               "for this fragment manually, then you have to override onCreateView();");
        }
        else
        {
            return inflater.inflate(layoutId, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        injectDependencies();
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected final void toast(String msg)
    {
        toast(msg, Gravity.CENTER);
    }

    protected final void toast(String msg, int gravity)
    {
        if (isAdded())
        {
            ((BaseActivity)getActivity()).toast(msg, gravity);
        }
    }

    protected final void showProgressDialog(String msg)
    {
        if (isAdded() && !TextUtils.isEmpty(msg))
        {
            ((BaseActivity)getActivity()).showProgressDialog(msg);
        }
    }

    protected final void closeProgressDialog()
    {
        if (isAdded())
        {
            ((BaseActivity)getActivity()).closeProgressDialog();
        }
    }

    protected void beforeContentView()
    { }

    protected abstract int getLayoutId();

    /**
     * This method will be called from {@link #onViewCreated(View, Bundle)} and this is the right place to
     * inject
     * dependencies (i.e. by using dagger)
     */
    protected void injectDependencies()
    { }
}
