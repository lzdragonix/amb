package com.scxrh.amb.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.R;
import com.scxrh.amb.component.DaggerSelCityComponent;
import com.scxrh.amb.module.ActivityModule;
import com.scxrh.amb.module.SelCityModule;
import com.scxrh.amb.presenter.SelCityPresenter;
import com.scxrh.amb.view.iview.SelCityView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//
public class SelCityFragment extends BaseFragment implements SelCityView
{
    public static final String TAG = SelCityFragment.class.getSimpleName();
    @Inject
    SelCityPresenter presenter;
    @Bind(R.id.rvList)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter = new RecyclerView.Adapter<ViewHolder>()
    {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_city_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            holder.name.setText(presenter.getItem(position).optString("name"));
        }

        @Override
        public int getItemCount()
        {
            return presenter.getCount();
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        presenter.loadData();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_selcity;
    }

    @Override
    protected void injectDependencies()
    {
        DaggerSelCityComponent.builder().appComponent(App.get(getActivity()).getComponent())
                              .activityModule(new ActivityModule(getActivity())).selCityModule(new SelCityModule(this))
                              .build().inject(this);
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
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
    public void showData()
    {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.txtName)
        TextView name;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
