package com.scxrh.amb.views.fragment;

import android.content.Intent;
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
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.FinancialProduct;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.presenter.FinProPresenter;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.OnItemClickListener;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.ProgressView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 理财产品
public class FinProFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = FinProFragment.class.getSimpleName();
    @Inject
    FinProPresenter presenter;
    @Bind(R.id.rvList)
    RecyclerView mRecyclerView;
    @Bind(R.id.txtHeader)
    TextView txtHeader;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_finance_product;
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
        txtHeader.setText(getString(R.string.txt_fin_product));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter.loadData();
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
        mRecyclerView.setAdapter(new RecyclerViewAdapter((List<FinancialProduct>)data));
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

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<FinancialProduct> data;
        private OnItemClickListener mOnItemClickListener = (view, position) -> {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)((RecyclerView)view.getParent()).getAdapter();
            FinancialProduct fin = adapter.getItem(position);
            UIData.Item item = new UIData.Item();
            item.setItemId(fin.getItemId());
            item.setContentType("finance");
            item.setImgUrl(fin.getImgUrl());
            Intent intent = new Intent(getActivity(), WindowActivity.class);
            intent.putExtra(Const.KEY_FRAGMENT, DetailFragment.class.getName());
            intent.putExtra(Const.KEY_DATA, item);
            startActivity(intent);
        };

        public RecyclerViewAdapter(List<FinancialProduct> data)
        {
            this.data = data == null ? new ArrayList<>() : data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_fin_product_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position));
            ViewHolder vh = (ViewHolder)holder;
            FinancialProduct fin = getItem(position);
            vh.name.setText(fin.getName());
            vh.description.setText(fin.getDesc());
            String url = AmbApi.END_POINT + fin.getImgUrl();
            vh.imgProduct.setImageURI(Uri.parse(url));
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }

        public FinancialProduct getItem(int position)
        {
            return data.get(position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.description)
        TextView description;
        @Bind(R.id.imgProduct)
        SimpleDraweeView imgProduct;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
