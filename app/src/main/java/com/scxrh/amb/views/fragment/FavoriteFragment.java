package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.scxrh.amb.R;

import butterknife.Bind;
import butterknife.OnClick;

// 收藏
public class FavoriteFragment extends BaseFragment
{
    public static final String TAG = FavoriteFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.btnSubmit)
    TextView btnSubmit;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("收藏");
        btnSubmit.setText("编辑");
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }
}
