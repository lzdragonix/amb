package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.Order;
import com.scxrh.amb.presenter.OrderPresenter;
import com.scxrh.amb.views.view.OrderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 订单
public class OrderFragment extends BaseFragment implements OrderView
{
    public static final String TAG = OrderFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Inject
    OrderPresenter presenter;
    @Bind(R.id.rvList)
    RecyclerView mRecyclerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("订单中心");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter.loadData();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_order;
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
    @SuppressWarnings("unchecked")
    public void showData(Object data)
    {
        mRecyclerView.setAdapter(new RecyclerViewAdapter((List<Order>)data));
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }

    @Override
    public void close()
    {
        getActivity().finish();
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<Order> data;

        public RecyclerViewAdapter(List<Order> data)
        {
            this.data = data == null ? new ArrayList<>() : data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_order_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            ViewHolder vh = (ViewHolder)holder;
            Order order = data.get(position);
            vh.name.setText(order.getShopName());
            vh.desc.setText(order.getShopName());
            vh.state.setText("未处理");
            Order.OrderItem orderItem = order.getOrderItems().get(0);
            vh.price.setText("￥ " + String.format("%.2f", Utils.tryParse(orderItem.getPrice(), 0f)));
            vh.amount.setText(orderItem.getAmount());
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.desc)
        TextView desc;
        @Bind(R.id.state)
        TextView state;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.amount)
        TextView amount;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
