package com.scxrh.amb.views.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.DetailItem;
import com.scxrh.amb.model.FinancialProduct;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.presenter.DetailPresenter;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.view.DetailView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 详细信息
public class DetailFragment extends BaseFragment implements DetailView
{
    public static final String TAG = DetailFragment.class.getSimpleName();
    @Inject
    DetailPresenter presenter;
    @Bind(R.id.detailContent)
    FrameLayout detailContent;
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.shouc)
    ImageView shouc;
    private UIData.Item item;
    private double mPrice;

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
        boolean unshow = getArguments().getBoolean("un-show", false);
        if (unshow || "finance".equals(item.getContentType()) || presenter.hasFav(item))
        {
            shouc.setVisibility(View.GONE);
        }
        presenter.loadData(item);
        switch (item.getContentType())
        {
            case "ad":
            case "ad2":
                txtHeader.setText("广告详情");
                break;
            case "shop":
                txtHeader.setText("商户");
                break;
            case "product":
                txtHeader.setText("商品详情");
                break;
            case "finance":
                txtHeader.setText("移动金融");
                break;
            case "lottery":
                txtHeader.setText("火爆抽奖");
                break;
        }
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
    {
        switch (item.getContentType())
        {
            case "ad":
            case "ad2":
                showAD(data);
                break;
            case "product":
                showProduct(data);
                break;
            case "finance":
                showFinance(data);
                break;
            case "lottery":
                showLottery(data);
                break;
            case "shop":
                showShop(data);
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

    @OnClick(R.id.shouc)
    void favorite()
    {
        presenter.favorite(item.getItemId(), item.getContentType());
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

    private void showProduct(Object data)
    {
        DetailItem di = (DetailItem)data;
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_detail_product, detailContent, false);
        SimpleDraweeView img = ButterKnife.findById(view, R.id.img);
        String url = AmbApi.END_POINT + di.getImgUrl();
        img.setImageURI(Uri.parse(url));
        TextView name = ButterKnife.findById(view, R.id.name);
        name.setText(di.getName());
        TextView desc = ButterKnife.findById(view, R.id.desc);
        desc.setText(di.getDesc());
        TextView price = ButterKnife.findById(view, R.id.price);
        price.setText("￥" + String.format("%.2f", Utils.tryParse(di.getPrice(), 0f)));
        detailContent.addView(view);
        ButterKnife.findById(view, R.id.btnBuy).setOnClickListener(v -> presenter.buy(di));
        TextView txt_num = ButterKnife.findById(view, R.id.txt_num);
        TextView txt_price = ButterKnife.findById(view, R.id.txt_price);
        txt_price.setText("￥ " + String.format("%.2f", Utils.tryParse(di.getPrice(), 0f)));
        mPrice = Utils.tryParse(txt_price.getText().toString().replace("￥ ", ""), 0d);
        ButterKnife.findById(view, R.id.btn_add).setOnClickListener(v -> {
            int i = Utils.tryParse(txt_num.getText().toString(), 0);
            i++;
            String s = String.valueOf(i);
            String p = String.format("%.2f", mPrice * i);
            txt_num.setText(s);
            txt_price.setText("￥ " + p);
            di.setPrice(p);
            di.setAmount(s);
        });
        ButterKnife.findById(view, R.id.btn_sub).setOnClickListener(v -> {
            int i = Utils.tryParse(txt_num.getText().toString(), 0);
            if (i > 1)
            {
                i--;
                String s = String.valueOf(i);
                String p = String.format("%.2f", mPrice * i);
                txt_num.setText(s);
                txt_price.setText("￥ " + p);
                di.setPrice(p);
                di.setAmount(s);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void showFinance(Object data)
    {
        FinancialProduct di = ((List<FinancialProduct>)data).get(0);
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_detail_finance, detailContent, false);
        SimpleDraweeView img = ButterKnife.findById(view, R.id.img);
        String url = AmbApi.END_POINT + di.getImgUrl();
        img.setImageURI(Uri.parse(url));
        TextView name = ButterKnife.findById(view, R.id.name);
        name.setText(di.getName());
        TextView desc = ButterKnife.findById(view, R.id.desc);
        desc.setText(di.getDesc());
        TextView detail = ButterKnife.findById(view, R.id.detail);
        detail.setText(di.getDetail());
        detailContent.addView(view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showLottery(Object data)
    {
        String userId = data == null ? "" : data.toString();
        String url = AmbApi.END_POINT + item.getItemId() + userId;
        WebView webView = new WebView(getActivity());
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        detailContent.addView(webView);
    }

    private void showShop(Object data)
    {
        DetailItem di = (DetailItem)data;
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_detail_shop, detailContent, false);
        SimpleDraweeView img = ButterKnife.findById(view, R.id.img);
        String url = AmbApi.END_POINT + di.getImgUrl();
        img.setImageURI(Uri.parse(url));
        TextView name = ButterKnife.findById(view, R.id.name);
        name.setText(di.getName());
        TextView desc = ButterKnife.findById(view, R.id.desc);
        desc.setText(di.getDesc());
        detailContent.addView(view);
    }

    @Override
    public void hideFavButton()
    {
        shouc.setVisibility(View.GONE);
    }
}
