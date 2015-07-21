package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.views.activity.WindowActivity;

import butterknife.Bind;
import butterknife.OnClick;

// 设置
public class SettingsFragment extends BaseFragment
{
    public static final String TAG = SettingsFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.txtCache)
    TextView txtCache;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_settings;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("设置");
        txtCache.setText(String.format("%.2f", Math.random()) + " M");
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }

    @OnClick(R.id.rlModifyPwd)
    void modifyPwd()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, ModifyPwdFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.rlCleanCache)
    void cleanCache()
    {
        txtCache.setText("");
    }
}
