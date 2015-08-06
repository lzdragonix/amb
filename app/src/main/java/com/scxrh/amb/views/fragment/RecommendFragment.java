package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.adapter.ADPagerAdapter;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.presenter.RecomPresenter;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.OnItemClickListener;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.ProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//推荐
public class RecommendFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = RecommendFragment.class.getSimpleName();
    @Inject
    RecomPresenter presenter;
    @Bind(R.id.vpAD)
    ViewPager vpAD;
    @Bind(R.id.rvShop)
    RecyclerView rvShop;
    @Bind(R.id.rvBuy)
    RecyclerView rvBuy;
    @Bind(R.id.rvLottery)
    RecyclerView rvLottery;
    @Bind(R.id.rvDiscount)
    RecyclerView rvDiscount;
    @Bind(R.id.rvFinance)
    RecyclerView rvFinance;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_recommend;
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
        rvShop.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        rvBuy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvLottery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvDiscount.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvFinance.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        presenter.loadData();
    }

    @OnClick(R.id.btnManager)
    void btnManager()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, ManagerFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.btnFinPro)
    void btnFinPro()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, FinProFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.btnBank)
    void btnBank()
    {
        presenter.goFinTab();
    }

    @OnClick(R.id.btnFav)
    void btnFav()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, FavoriteFragment.class.getName());
        startActivity(intent);
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
        Map<String, UIData> uis = (Map<String, UIData>)data;
        vpAD.setAdapter(new ADPagerAdapter(getActivity(), uis.get("ad").getItems(), R.layout.layout_image_item_ad));
        rvShop.setAdapter(new RecyclerViewAdapter(uis.get("hot").getItems()));
        rvBuy.setAdapter(new RecyclerViewAdapter(uis.get("buy").getItems()));
        rvLottery.setAdapter(new RecyclerViewAdapter(uis.get("lottery").getItems()));
        rvDiscount.setAdapter(new RecyclerViewAdapter(uis.get("discount").getItems()));
        rvFinance.setAdapter(new RecyclerViewAdapter(uis.get("finance").getItems()));
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }

    private void showDetail(UIData.Item item)
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, DetailFragment.class.getName());
        intent.putExtra(Const.KEY_DATA, item);
        startActivity(intent);
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<UIData.Item> data;
        private OnItemClickListener mOnItemClickListener = (view, position) -> {
            UIData.Item item = ((RecyclerViewAdapter)((RecyclerView)view.getParent()).getAdapter()).getItem(position);
            showDetail(item);
        };

        public RecyclerViewAdapter(List<UIData.Item> data)
        {
            this.data = data == null ? new ArrayList<>() : data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_image_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position));
            ViewHolder vh = (ViewHolder)holder;
            String url = AmbApi.END_POINT + data.get(position).getImgUrl();
            vh.imgItem.setImageURI(Uri.parse(url));
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }

        public UIData.Item getItem(int position)
        {
            return data.get(position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.imgItem)
        ImageView imgItem;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
