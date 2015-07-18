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
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.SalesManager;
import com.scxrh.amb.model.SysInfo;
import com.scxrh.amb.presenter.ManagerPresenter;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.view.ProgressView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 客户经理
public class ManagerFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = ManagerFragment.class.getSimpleName();
    @Bind(R.id.rvList)
    RecyclerView mRecyclerView;
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Inject
    ManagerPresenter presenter;
    @Inject
    SysInfo sysInfo;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_manager;
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
        txtHeader.setText(getString(R.string.txt_manager));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter.loadData(sysInfo.getCommunity().getId());
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
    public void showError(String msg)
    {
        toast(msg);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void showData(List<?> data)
    {
        mRecyclerView.setAdapter(new RecyclerViewAdapter((List<SalesManager>)data));
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<SalesManager> data;

        public RecyclerViewAdapter(List<SalesManager> data)
        {
            this.data = data == null ? new ArrayList<>() : data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_salemanager_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            ViewHolder vh = (ViewHolder)holder;
            SalesManager sm = data.get(position);
            vh.name.setText(sm.getName());
            vh.description.setText(sm.getDesc());
            vh.phone.setText(sm.getTelephone());
            String url = AmbApi.END_POINT + sm.getPhotoUrl();
            vh.photo.setImageURI(Uri.parse(url));
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
        @Bind(R.id.phone)
        TextView phone;
        @Bind(R.id.photo)
        SimpleDraweeView photo;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
