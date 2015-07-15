package com.scxrh.amb.views.fragment;

import android.content.Intent;

import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.views.activity.WindowActivity;

import butterknife.OnClick;

//推荐
public class RecommendFragment extends BaseFragment
{
    public static final String TAG = RecommendFragment.class.getSimpleName();

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_recommend;
    }

    @OnClick(R.id.btnManager)
    void btnManager()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, ManagerFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.btnFinPro)
    void btnFinPro()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, FinProFragment.class.getName());
        startActivity(intent);
    }
}
