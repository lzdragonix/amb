package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.adapter.ADPagerAdapter;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.presenter.LiveMSPresenter;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.OnItemClickListener;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.ProgressView;
import com.scxrh.amb.widget.loopager.LoopViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

// 生活 美食
public class LiveMSFragment extends BaseFragment implements ProgressView
{
    public static final String TAG = LiveMSFragment.class.getSimpleName();
    @Inject
    LiveMSPresenter presenter;
    @Bind(R.id.vpAD)
    LoopViewPager vpAD;
    @Bind(R.id.rvList)
    RecyclerView mRecyclerView;
    private int page;
    private CountDownTimer timer = new CountDownTimer(3000000, 10000)
    {
        @Override
        public void onTick(long millisUntilFinished)
        {
            vpAD.setCurrentItem(page, true);
            page++;
        }

        @Override
        public void onFinish()
        { }
    };
    private List<UIData.Item> items = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        int mIndex = getArguments().getInt("index");
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        presenter.loadData(mIndex);
    }

    @Override
    public void onDestroy()
    {
        timer.cancel();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_live_ms;
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
        initPagerData(uis.get("ad").getItems());
        vpAD.setAdapter(new ADPagerAdapter(getActivity(), items, R.layout.layout_image_item_ad1, false));
        vpAD.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            { }

            @Override
            public void onPageSelected(int position)
            {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            { }
        });
        mRecyclerView.setAdapter(new RecyclerViewAdapter(uis.get("content").getItems()));
        timer.start();
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }

    private void initPagerData(List<UIData.Item> data)
    {
        if (data.size() == 0) { return; }
        items.addAll(data);
        if (items.size() < 3) { initPagerData(data); }
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_image_item1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position));
            ViewHolder vh = (ViewHolder)holder;
            UIData.Item item = getItem(position);
            String url = AmbApi.END_POINT + item.getImgUrl();
            vh.img.setImageURI(Uri.parse(url));
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)vh.img.getLayoutParams();
            if ((position % 2) == 1) { params.gravity = Gravity.RIGHT; }
            else { params.gravity = Gravity.LEFT; }
            vh.img.setLayoutParams(params);
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
        @Bind(R.id.img)
        ImageView img;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
