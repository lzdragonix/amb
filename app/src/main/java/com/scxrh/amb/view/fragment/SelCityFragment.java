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
    private static final int VIEWTYPE_CONTENT = 0;
    private static final int VIEWTYPE_INDEX = 1;
    @Inject
    SelCityPresenter presenter;
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.rvList)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>()
    {
        OnItemClickListener mOnItemClickListener = new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                String a= presenter.getItem(position).getName();
            }
        };

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view;
            if (viewType == VIEWTYPE_INDEX)
            {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_city_item_index, parent, false);
                return new ViewHolderIndex(view);
            }
            else
            {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_city_item, parent, false);
                return new ViewHolderContent(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            if (holder instanceof ViewHolderIndex)
            {
                ((ViewHolderIndex)holder).index.setText(presenter.getItem(position).getName());
            }
            else if (holder instanceof ViewHolderContent)
            {
                int i = position + 1;
                if (i < presenter.getCount() - 1 && "0".equals(presenter.getItem(i).getId()))
                {
                    ((ViewHolderContent)holder).divider.setVisibility(View.INVISIBLE);
                }
                else
                {
                    ((ViewHolderContent)holder).divider.setVisibility(View.VISIBLE);
                }
                ((ViewHolderContent)holder).name.setText(presenter.getItem(position).getName());
            }
        }

        @Override
        public int getItemViewType(int position)
        {
            return "0".equals(presenter.getItem(position).getId()) ? VIEWTYPE_INDEX : VIEWTYPE_CONTENT;
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
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        txtHeader.setText(getString(R.string.txt_select_city));
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
        DaggerSelCityComponent.builder().appComponent(App.getAppComponent())
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

    interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    class ViewHolderContent extends RecyclerView.ViewHolder
    {
        @Bind(R.id.txtName)
        TextView name;
        @Bind(R.id.divider)
        View divider;

        public ViewHolderContent(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderIndex extends RecyclerView.ViewHolder
    {
        @Bind(R.id.txtIndex)
        TextView index;

        public ViewHolderIndex(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
