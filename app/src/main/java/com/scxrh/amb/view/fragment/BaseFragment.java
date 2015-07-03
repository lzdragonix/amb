package com.scxrh.amb.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scxrh.amb.view.activity.BaseActivity;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements View.OnClickListener
{
    private View mLayout;
    private Handler mHandler = new Handler();
    private Thread mUiThread;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mUiThread = Thread.currentThread();
        mLayout = genView(inflater, container);
        ButterKnife.bind(this, getLayout());
        return mLayout;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected final View getLayout()
    {
        return mLayout;
    }

    protected final View findViewById(int id)
    {
        return mLayout != null ? mLayout.findViewById(id) : null;
    }

    protected final void setOnClickListener(View v)
    {
        v.setOnClickListener(this);
    }

    protected final void setOnClickListener(int id)
    {
        View v = findViewById(id);
        if (v == null) { return; }
        setOnClickListener(v);
    }

    protected final void runOnUiThread(Runnable action)
    {
        if (Thread.currentThread() != mUiThread)
        {
            mHandler.post(action);
        }
        else
        {
            action.run();
        }
    }

    protected final void toast(String msg)
    {
        toast(msg, Gravity.CENTER);
    }

    protected final void toast(String msg, int gravity)
    {
        if (isAdded() && !TextUtils.isEmpty(msg))
        {
            ((BaseActivity)getActivity()).toast(msg, gravity);
        }
    }

    @Override
    public void onClick(View v)
    { }

    protected abstract View genView(LayoutInflater inflater, ViewGroup container);
}
