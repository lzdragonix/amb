package com.scxrh.amb.views.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.City;
import com.scxrh.amb.presenter.SelCityPresenter;
import com.scxrh.amb.views.OnItemClickListener;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.ProgressView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//
public class SelCityFragment extends BaseFragment implements ProgressView
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
    @Bind(R.id.etxSearch)
    EditText etxSearch;
    private boolean isComm = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        isComm = !TextUtils.isEmpty(getArguments().getString("type"));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(LinearLayoutManager.VERTICAL));
        if (isComm)
        {
            txtHeader.setText(getString(R.string.txt_select_community));
            presenter.loadCommunity();
        }
        else
        {
            txtHeader.setText(getString(R.string.txt_select_city));
            presenter.loadCity();
        }
        etxSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                presenter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            { }
        });
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_selcity;
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
        getActivity().setResult(Activity.RESULT_CANCELED);
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
        mRecyclerView.setAdapter(new RecyclerViewAdapter((List<City>)data));
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<City> data;
        OnItemClickListener mOnItemClickListener = (view, position) -> {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)((RecyclerView)view.getParent()).getAdapter();
            City city = adapter.getItem(position);
            if ("0".equals(city.getId())) { return; }
            if (isComm) { presenter.changeCommunity(city); }
            else
            {
                presenter.changeCity(city);
                presenter.changeCommunity(new City());
                Intent intent = new Intent(getActivity(), WindowActivity.class);
                intent.putExtra(Const.KEY_FRAGMENT, SelCityFragment.class.getName());
                intent.putExtra("type", "community");
                getActivity().startActivity(intent);
            }
            getActivity().finish();
        };

        public RecyclerViewAdapter(List<City> data)
        {
            this.data = data == null ? new ArrayList<>() : data;
        }

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
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position));
            if (holder instanceof ViewHolderIndex)
            {
                ((ViewHolderIndex)holder).index.setText(data.get(position).getName());
            }
            else if (holder instanceof ViewHolderContent)
            {
                int i = position + 1;
                if (i < data.size() - 1 && "0".equals(data.get(i).getId()))
                {
                    ((ViewHolderContent)holder).divider.setVisibility(View.INVISIBLE);
                }
                else
                {
                    ((ViewHolderContent)holder).divider.setVisibility(View.VISIBLE);
                }
                ((ViewHolderContent)holder).name.setText(data.get(position).getName());
            }
        }

        @Override
        public int getItemViewType(int position)
        {
            return "0".equals(data.get(position).getId()) ? VIEWTYPE_INDEX : VIEWTYPE_CONTENT;
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }

        public City getItem(int position)
        {
            return data.get(position);
        }
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
