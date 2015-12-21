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
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.Points;
import com.scxrh.amb.presenter.PointsPresenter;
import com.scxrh.amb.views.view.PointsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 积分
public class PointsFragment extends BaseFragment implements PointsView
{
    public static final String TAG = PointsFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Inject
    PointsPresenter presenter;
    @Bind(R.id.rvList)
    RecyclerView mRecyclerView;
    @Bind(R.id.sum_points)
    TextView sum_points;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_points;
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
        txtHeader.setText("积分中心");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter.loadData();
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
        mRecyclerView.setAdapter(new RecyclerViewAdapter((List<Points>)data));
        sum_points.setText("" + presenter.sumPoint());
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
        private List<Points> data;

        public RecyclerViewAdapter(List<Points> data)
        {
            this.data = data == null ? new ArrayList<>() : data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_points_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            ViewHolder vh = (ViewHolder)holder;
            Points point = data.get(position);
            vh.desc.setText(point.getDesc());
            vh.date.setText(point.getDate());
            vh.point.setText(point.getPoint());
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.desc)
        TextView desc;
        @Bind(R.id.point)
        TextView point;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
