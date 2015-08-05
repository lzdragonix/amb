package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.UserInfo;
import com.scxrh.amb.presenter.SuggPresenter;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.SuggView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 投诉建议
public class SuggestionFragment extends BaseFragment implements SuggView
{
    public static final String TAG = SuggestionFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.btnSubmit)
    TextView btnSubmit;
    @Bind(R.id.txtContent)
    TextView txtContent;
    @Bind(R.id.txtUserName)
    TextView txtUserName;
    @Bind(R.id.txtPhone)
    TextView txtPhone;
    @Inject
    SuggPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("投诉建议");
        presenter.init();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_suggestion;
    }

    @Override
    protected void injectDependencies()
    {
        DaggerMvpComponent.builder()
                          .appComponent(App.getAppComponent())
                          .activityModule(new ActivityModule(getActivity()))
                          .mvpModule(new MvpModule(this))
                          .build()
                          .inject(this);
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }

    @OnClick(R.id.btnSubmit)
    void submit()
    {
        btnSubmit.setEnabled(false);
        presenter.submit(txtUserName.getText().toString(), txtPhone.getText().toString(),
                         txtContent.getText().toString());
    }

    @Override
    public void close()
    {
        getActivity().finish();
    }

    @Override
    public void showLogin()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, LoginFragment.class.getName());
        startActivity(intent);
    }

    @Override
    public void initView(UserInfo userInfo)
    {
        txtUserName.setText(userInfo.getUserName());
        txtPhone.setText(userInfo.getTelephone());
    }

    @Override
    public void showProgress(String msg)
    {
        showProgressDialog(msg);
    }

    @Override
    public void showMessage(String msg)
    {
        toast(msg);
    }

    @Override
    public void showData(Object data)
    { }

    @Override
    public void finish()
    {
        btnSubmit.setEnabled(true);
        closeProgressDialog();
    }
}
