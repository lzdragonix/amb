package com.scxrh.amb.views.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.DetailItem;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.presenter.DetailPresenter;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.view.ProgressView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 生活 附近
public class DetailFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = DetailFragment.class.getSimpleName();
    @Inject
    DetailPresenter presenter;
    @Bind(R.id.detailContent)
    FrameLayout detailContent;
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    private UIData.Item item;

    protected int getLayoutId()
    {
        return R.layout.fragment_detail;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        item = getArguments().getParcelable(Const.KEY_DATA);
        presenter.loadData(item);
        switch (item.getContentType())
        {
            case "ad":
                txtHeader.setText("广告详情");
                break;
            case "shop":
                txtHeader.setText("活动");
                break;
            case "product":
                txtHeader.setText("商品详情");
                break;
        }
    }

    @Override
    public void showProgress(String msg)
    {
        showProgressDialog(msg);
    }

    @Override
    public void showError(String msg)
    {
        toast(msg);
    }

    @Override
    public void showData(Object data)
    {
        switch (item.getContentType())
        {
            case "ad":
                showAD(data);
                break;
            case "product":
                showProduct(data);
                break;
        }
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }

    private void showAD(Object data)
    {
        DetailItem di = (DetailItem)data;
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_detail_ad, detailContent, false);
        SimpleDraweeView imgAD = ButterKnife.findById(view, R.id.imgAD);
        String url = AmbApi.END_POINT + di.getImgUrl();
        imgAD.setImageURI(Uri.parse(url));
        TextView txtAdContent = ButterKnife.findById(view, R.id.txtAdContent);
        txtAdContent.setText(di.getText());
        detailContent.addView(view);
    }

    //活动
    private void showShop(Object data)
    {
        DetailItem di = (DetailItem)data;
    }

    private void showProduct(Object data)
    {
        DetailItem di = (DetailItem)data;
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_detail_ad, detailContent, false);
        SimpleDraweeView imgAD = ButterKnife.findById(view, R.id.imgAD);
        String url = AmbApi.END_POINT + di.getImgUrl();
        imgAD.setImageURI(Uri.parse(url));
        TextView txtAdContent = ButterKnife.findById(view, R.id.txtAdContent);
        txtAdContent.setText(di.getText());
        detailContent.addView(view);
    }
}
