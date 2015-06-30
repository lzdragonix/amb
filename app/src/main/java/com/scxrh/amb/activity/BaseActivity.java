package com.scxrh.amb.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.scxrh.amb.R;

import java.util.List;

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener
{
    protected Context mContext;
    private Toast mToast;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null)
        {
            for (Fragment fragment : fragments)
            {
                fragment.onActivityResult(requestCode, resultCode, intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mContext = this;
        initProgressDialog();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode)
    {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode)
    {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        super.startActivityFromFragment(fragment, intent, requestCode);
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

    @Override
    public void onClick(View v)
    { }

    protected abstract int getLayoutId();

    protected final void setProgressDialog(ProgressDialog pdlg)
    {
        if (pdlg == null) { return; }
        mProgressDialog = pdlg;
    }

    private void initProgressDialog()
    {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(true);
    }

    public void showProgressDialog(final String msg)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mProgressDialog.setMessage(msg);
                if (!mProgressDialog.isShowing())
                {
                    mProgressDialog.show();
                }
            }
        });
    }

    public void closeProgressDialog()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mProgressDialog.dismiss();
            }
        });
    }

    protected final boolean isVisible(View view)
    {
        return view.getVisibility() == View.VISIBLE;
    }

    protected final void setVisible(int id, int visible)
    {
        View v = findViewById(id);
        if (v == null) { return; }
        setVisible(v, visible);
    }

    protected final void setVisible(View v, int visible)
    {
        v.setVisibility(visible);
    }

    protected final void setEnabled(int id, boolean enable)
    {
        View v = findViewById(id);
        if (v == null) { return; }
        setEnabled(v, enable);
    }

    protected final void setEnabled(View v, boolean enable)
    {
        v.setEnabled(enable);
    }

    public final void toast(String msg)
    {
        toast(msg, Gravity.CENTER);
    }

    public final void toast(final String msg, final int gravity)
    {
        if (TextUtils.isEmpty(msg)) { return; }
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (mToast == null)
                {
                    mToast = Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG);
                }
                else
                {
                    mToast.setText(msg);
                }
                mToast.setDuration(Toast.LENGTH_LONG);
                mToast.setGravity(gravity, 0, 0);
                mToast.show();
            }
        });
    }
}
