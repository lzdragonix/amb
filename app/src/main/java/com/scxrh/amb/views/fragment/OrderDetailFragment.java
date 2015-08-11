package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.DetailItem;
import com.scxrh.amb.model.UserInfo;
import com.scxrh.amb.presenter.OrderDetailPresenter;
import com.scxrh.amb.views.view.OrderDetailView;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 订单
public class OrderDetailFragment extends BaseFragment implements OrderDetailView
{
    public static final String TAG = OrderDetailFragment.class.getSimpleName();
    static final ButterKnife.Setter<TextView, Integer> TEXTCOLOR = (view, value, index) -> view.setTextColor(value);
    static final ButterKnife.Setter<TextView, Integer> BACKGROUND = (view, value, index) -> view.setBackgroundResource(
            value);
    static final ButterKnife.Setter<TextView, View.OnClickListener> ONCLICKLISTENER = (view, value, index) -> {
        view.setOnClickListener(value);
        view.setTag(index);
    };
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.txt_quantity)
    TextView txt_quantity;
    @Bind(R.id.txt_amount)
    TextView txt_amount;
    @Bind(R.id.txt_delivery)
    TextView txt_delivery;
    @Bind(R.id.txt_delivery1)
    TextView txt_delivery1;
    @Bind(R.id.txt_pay)
    TextView txt_pay;
    @Bind(R.id.txt_pay1)
    TextView txt_pay1;
    @Bind(R.id.txt_shr)
    TextView txt_shr;
    @Bind(R.id.txt_dh)
    TextView txt_dh;
    @Bind(R.id.txt_addr)
    TextView txt_addr;
    @Bind(R.id.btnSubmit)
    TextView btnSubmit;
    @Bind(R.id.txt_date)
    EditText txt_date;
    @Bind({ R.id.txt_delivery, R.id.txt_delivery1 })
    List<TextView> deliveries;
    @Bind({ R.id.txt_pay, R.id.txt_pay1 })
    List<TextView> pays;
    @Inject
    OrderDetailPresenter presenter;
    private DetailItem item;
    private int textColorNormal;
    private int textColorTap;
    private int shape = R.drawable.shape_delivery;
    private int shape1 = R.drawable.shape_delivery1;
    private int typeDeliveries = -1;
    private View.OnClickListener listener = v -> changeDeliveries((int)v.getTag());
    private int typePays = -1;
    private View.OnClickListener listener1 = v -> changePays((int)v.getTag());

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        item = getArguments().getParcelable(Const.KEY_DATA);
        textColorNormal = getResources().getColor(R.color.cfbc000);
        textColorTap = getResources().getColor(R.color.cffffff);
        ButterKnife.apply(deliveries, ONCLICKLISTENER, listener);
        ButterKnife.apply(pays, ONCLICKLISTENER, listener1);
        txtHeader.setText("订单");
        String date = Utils.tryParse(new Date(), "yyyy-MM-dd") + " 18:00";
        txt_date.setText(date);
        txt_date.setSelection(date.length());
        name.setText(item.getName());
        String price = String.format("%.2f", Utils.tryParse(item.getPrice(), 0f)) + " 元";
        txt_amount.setText(price);
        presenter.init();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_order_detail;
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
    void submit(View view)
    {
        view.setEnabled(false);
        presenter.submit(item, typeDeliveries, typePays, txt_shr.getText().toString(), txt_dh.getText().toString(),
                         txt_addr.getText().toString());
    }

    public void changeDeliveries(int index)
    {
        ButterKnife.apply(deliveries, TEXTCOLOR, textColorNormal);
        ButterKnife.apply(deliveries, BACKGROUND, shape1);
        TEXTCOLOR.set(deliveries.get(index), textColorTap, index);
        BACKGROUND.set(deliveries.get(index), shape, index);
        typeDeliveries = index;
    }

    public void changePays(int index)
    {
        ButterKnife.apply(pays, TEXTCOLOR, textColorNormal);
        ButterKnife.apply(pays, BACKGROUND, shape1);
        TEXTCOLOR.set(pays.get(index), textColorTap, index);
        BACKGROUND.set(pays.get(index), shape, index);
        typePays = index;
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

    @Override
    public void initView(UserInfo userInfo)
    {
        txt_shr.setText(userInfo.getUserName());
        txt_dh.setText(userInfo.getTelephone());
        txt_addr.setText(userInfo.getAddress());
    }

    @Override
    public void close()
    {
        getActivity().finish();
    }
}
