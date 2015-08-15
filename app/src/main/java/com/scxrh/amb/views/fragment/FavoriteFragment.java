package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.FavoriteItem;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.presenter.FavoritePresenter;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.OnItemClickListener;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.FavoriteView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 收藏
public class FavoriteFragment extends BaseFragment implements FavoriteView
{
    public static final String TAG = FavoriteFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.btnSubmit)
    TextView btnSubmit;
    @Bind(R.id.rvList)
    RecyclerView mRecyclerView;
    @Inject
    FavoritePresenter presenter;
    private CompoundButton.OnCheckedChangeListener mListener = (buttonView, isChecked) -> {
        RecyclerViewAdapter adapter = (RecyclerViewAdapter)mRecyclerView.getAdapter();
        int count = adapter.getItemCount();
        for (int i = 0; i < count; i++)
        {
            adapter.getItem(i).setChecked("0");
        }
        adapter.notifyDataSetChanged();
        if (isChecked)
        {
            int index = Integer.valueOf(buttonView.getTag().toString());
            adapter.getItem(index).setChecked("1");
            adapter.notifyItemChanged(index);
            btnSubmit.setText("删除");
        }
        else
        {
            btnSubmit.setText("取消");
        }
    };

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_favorite;
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
        txtHeader.setText("收藏");
        btnSubmit.setText("编辑");
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        presenter.loadData();
    }

    @OnClick(R.id.btnBack)
    void btnBack()
    {
        getActivity().finish();
    }

    @OnClick(R.id.btnSubmit)
    void modify()
    {
        String btn = btnSubmit.getText().toString();
        if ("编辑".equals(btn))
        {
            if (showCheckBox(true))
            {
                btnSubmit.setText("取消");
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
        else if ("取消".equals(btn))
        {
            btnSubmit.setText("编辑");
            if (showCheckBox(false))
            {
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
        else if ("删除".equals(btn))
        {
            presenter.delFav();
        }
    }

    private boolean showCheckBox(boolean show)
    {
        int count = mRecyclerView.getAdapter().getItemCount();
        if (count == 0) { return false; }
        RecyclerViewAdapter adapter = (RecyclerViewAdapter)mRecyclerView.getAdapter();
        for (int i = 0; i < count; i++)
        {
            FavoriteItem item = adapter.getItem(i);
            item.setShowCheck(show ? "1" : "0");
        }
        return true;
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
        mRecyclerView.setAdapter(new RecyclerViewAdapter((List<FavoriteItem>)data));
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }

    @Override
    public void changeButton(String text)
    {
        btnSubmit.setText(text);
    }

    @Override
    public void close()
    {
        getActivity().finish();
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<FavoriteItem> data;
        private OnItemClickListener mOnItemClickListener = (view, position) -> {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)((RecyclerView)view.getParent()).getAdapter();
            FavoriteItem item = adapter.getItem(position);
            if (!"编辑".equals(btnSubmit.getText().toString()))
            {
                if ("1".equals(item.getShowCheck()))
                {
                    CheckBox box = (CheckBox)view.findViewById(R.id.checkbox);
                    box.setChecked(!box.isChecked());
                }
            }
            else
            {
                UIData.Item uit = new UIData.Item();
                uit.setContentType(item.getContentType());
                uit.setImgUrl(item.getImgUrl());
                uit.setItemId(item.getItemId());
                uit.setItemType(item.getItemType());
                Intent intent = new Intent(getActivity(), WindowActivity.class);
                intent.putExtra(Const.KEY_FRAGMENT, DetailFragment.class.getName());
                intent.putExtra(Const.KEY_DATA, uit);
                intent.putExtra("un-show", true);
                startActivity(intent);
            }
        };

        public RecyclerViewAdapter(List<FavoriteItem> data)
        {
            this.data = data == null ? new ArrayList<>() : data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_favorite_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position));
            ViewHolder vh = (ViewHolder)holder;
            FavoriteItem fav = data.get(position);
            vh.name.setText(fav.getFavoriteName());
            vh.desc.setText(fav.getFavoriteDesc());
            String url = AmbApi.END_POINT + fav.getImgUrl();
            vh.img.setImageURI(Uri.parse(url));
            vh.checkbox.setTag(position);
            if ("1".equals(fav.getShowCheck()))
            {
                vh.checkbox.setVisibility(View.VISIBLE);
            }
            else
            {
                vh.checkbox.setVisibility(View.GONE);
            }
            vh.checkbox.setOnCheckedChangeListener(null);
            vh.checkbox.setChecked("1".equals(fav.getChecked()));
            vh.checkbox.setOnCheckedChangeListener(mListener);
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }

        public FavoriteItem getItem(int position)
        {
            return data.get(position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.desc)
        TextView desc;
        @Bind(R.id.img)
        SimpleDraweeView img;
        @Bind(R.id.checkbox)
        CheckBox checkbox;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
