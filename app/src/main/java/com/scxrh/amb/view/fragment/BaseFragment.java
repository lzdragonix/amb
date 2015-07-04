package com.scxrh.amb.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
        int layoutRes = getLayoutId();
        if (layoutRes == 0)
        {
            throw new IllegalArgumentException("getLayoutId() returned 0, which is not allowed. " +
                                               "If you don't want to use getLayoutId() but implement your own view for this " +
                                               "fragment manually, then you have to override onCreateView();");
        }
        else
        {
            mLayout = inflater.inflate(layoutRes, container, false);
            return mLayout;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
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

    protected void beforeContentView()
    { }

    protected abstract int getLayoutId();
}
