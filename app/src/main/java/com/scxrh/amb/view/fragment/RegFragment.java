package com.scxrh.amb.view.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scxrh.amb.R;
import com.scxrh.amb.view.iview.RegView;

import butterknife.Bind;
import butterknife.OnClick;

// 注册
public class RegFragment extends BaseFragment implements RegView
{
    public static final String TAG = RegFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.txt1)
    TextView txt1;
    @Bind(R.id.btnReg)
    View btnReg;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("注册");
        txt1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_reg;
    }

    @Override
    protected void injectDependencies()
    {
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }

    @OnClick(R.id.btnReg)
    void reg()
    {
    }
}
