package com.scxrh.amb.views.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.Order;
import com.scxrh.amb.presenter.OrderPresenter;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.view.OrderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 订单中心
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

    private String parseState(String state)
    {
        switch (state)
        {
            case "1":
                return "未发货";
            case "2":
                return "已发货";
            case "3":
                return "已完成";
            case "4":
                return "已取消";
            case "5":
                return "已过期";
            case "6":
                return "已退款";
            case "7":
                return "已退货";
            default:
                return "未支付";
        }
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
            Order.OrderItem orderItem = order.getOrderItems().get(0);
            vh.name.setText(order.getShopName());
            vh.desc.setText(order.getOrderName());
            vh.state.setText(parseState(order.getOrderState()));
            vh.price.setText("￥ " + String.format("%.2f", Utils.tryParse(orderItem.getPrice(), 0f)));
            vh.amount.setText("× " + orderItem.getAmount());
            String url = AmbApi.END_POINT + order.getOrderThumbnail();
            vh.img.setImageURI(Uri.parse(url));
            vh.confirm.setOnClickListener(v -> presenter.confirm(order.getOrderId()));
            if ("0".equals(order.getOrderState()))
            {
                vh.confirm.setVisibility(View.VISIBLE);
                vh.line.setVisibility(View.VISIBLE);
            }
            else
            {
                vh.confirm.setVisibility(View.GONE);
                vh.line.setVisibility(View.GONE);
            }
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
        @Bind(R.id.confirm)
        TextView confirm;
        @Bind(R.id.line)
        View line;
        @Bind(R.id.img)
        SimpleDraweeView img;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
