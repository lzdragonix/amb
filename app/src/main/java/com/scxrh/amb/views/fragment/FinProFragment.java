package com.scxrh.amb.views.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.FinancialProduct;
import com.scxrh.amb.presenter.FinProPresenter;
import com.scxrh.amb.views.view.ProgressView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//
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
        presenter.loadData("402883474e4336c2014e43499c43000c", "");
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
    @SuppressWarnings("unchecked")
    public void showData(List<?> data)
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
            ViewHolder vh = (ViewHolder)holder;
            FinancialProduct fin = data.get(position);
            vh.name.setText(fin.getName());
            vh.description.setText(fin.getDescription());
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
        @Bind(R.id.description)
        TextView description;
        @Bind(R.id.imgProduct)
        ImageView imgProduct;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
