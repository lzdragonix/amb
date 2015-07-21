package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.scxrh.amb.R;

import butterknife.Bind;
import butterknife.OnClick;

// 订单
public class OrderFragment extends BaseFragment
{
    public static final String TAG = OrderFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("订单中心");
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_order;
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }
}
