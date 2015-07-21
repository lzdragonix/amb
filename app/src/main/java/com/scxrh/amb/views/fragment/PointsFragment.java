package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.scxrh.amb.R;

import butterknife.Bind;
import butterknife.OnClick;

// 积分
public class PointsFragment extends BaseFragment
{
    public static final String TAG = PointsFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_points;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("积分中心");
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }
}
