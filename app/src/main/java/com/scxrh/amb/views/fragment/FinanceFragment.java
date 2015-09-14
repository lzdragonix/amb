package com.scxrh.amb.views.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.common.Utils;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.BankInfo;
import com.scxrh.amb.model.FinancialProduct;
import com.scxrh.amb.model.SalesManager;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.presenter.FinancePresenter;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.FinanceView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

// 社区金融
public class FinanceFragment extends BaseFragment implements FinanceView
{
    public static final String TAG = FinanceFragment.class.getSimpleName();
    @Inject
    FinancePresenter presenter;
    @Bind(R.id.bankLogo)
    SimpleDraweeView bankLogo;
    @Bind(R.id.bankName)
    TextView bankName;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.vpManager)
    ViewPager vpManager;
    @Bind(R.id.productContent)
    LinearLayout productContent;
    @Bind(R.id.ll_dots)
    LinearLayout llDots;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_finance;
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
        vpManager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            { }

            @Override
            public void onPageSelected(int position)
            {
                changeDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            { }
        });
        presenter.init();
        presenter.loadBank();
        presenter.loadManager();
        presenter.loadProduct();
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
        List<BankInfo> list = (List<BankInfo>)data;
        if (list.size() > 0)
        {
            BankInfo bank = list.get(0);
            String url = AmbApi.END_POINT + bank.getBankLogo();
            bankLogo.setImageURI(Uri.parse(url));
            bankName.setText(bank.getBankName());
            address.setText(bank.getAddress());
        }
    }

    @Override
    public void finish()
    {
        closeProgressDialog();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void showManager(Object data)
    {
        List<SalesManager> list = (List<SalesManager>)data;
        vpManager.setAdapter(new ManagerPagerAdapter(list));
        //dots
        llDots.removeAllViews();
        int count = vpManager.getAdapter().getCount();
        for (int i = 0; i < count; i++)
        {
            ImageView img = new ImageView(getActivity());
            img.setBackgroundResource(R.drawable.selector_dots);
            img.setEnabled(false);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            int margin = Utils.dpToPx(5);
            param.setMargins(margin, margin, margin, margin);
            llDots.addView(img, param);
        }
        changeDot(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void showProduct(Object data)
    {
        List<FinancialProduct> list = (List<FinancialProduct>)data;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        productContent.removeAllViews();
        for (FinancialProduct product : list)
        {
            View view = inflater.inflate(R.layout.layout_fin_product_item, productContent, false);
            SimpleDraweeView imgProduct = ButterKnife.findById(view, R.id.imgProduct);
            TextView name = ButterKnife.findById(view, R.id.name);
            TextView description = ButterKnife.findById(view, R.id.description);
            String url = AmbApi.END_POINT + product.getImgUrl();
            imgProduct.setImageURI(Uri.parse(url));
            name.setText(product.getName());
            description.setText(product.getDesc());
            view.setOnClickListener(v -> {
                UIData.Item item = new UIData.Item();
                item.setItemId(product.getItemId());
                item.setContentType("finance");
                item.setImgUrl(product.getImgUrl());
                Intent intent = new Intent(getActivity(), WindowActivity.class);
                intent.putExtra(Const.KEY_FRAGMENT, DetailFragment.class.getName());
                intent.putExtra(Const.KEY_DATA, item);
                startActivity(intent);
            });
            productContent.addView(view);
        }
    }

    private void changeDot(int index)
    {
        int count = llDots.getChildCount();
        if (index >= count) { return; }
        for (int i = 0; i < count; i++)
        {
            llDots.getChildAt(i).setEnabled(false);
        }
        llDots.getChildAt(index).setEnabled(true);
    }

    private class ManagerPagerAdapter extends PagerAdapter
    {
        private List<SalesManager> data;
        private View[] pages;

        public ManagerPagerAdapter(List<SalesManager> data)
        {
            this.data = data == null ? new ArrayList<>() : data;
            pages = new View[this.data.size()];
        }

        @Override
        public int getCount()
        {
            return pages.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View view = pages[position];
            if (view == null)
            {
                view = getActivity().getLayoutInflater().inflate(R.layout.layout_salemanager_item, container, false);
                pages[position] = view;
                SalesManager manager = data.get(position);
                SimpleDraweeView photo = ButterKnife.findById(view, R.id.photo);
                TextView name = ButterKnife.findById(view, R.id.name);
                TextView description = ButterKnife.findById(view, R.id.description);
                TextView phone = ButterKnife.findById(view, R.id.phone);
                String url = AmbApi.END_POINT + manager.getPhotoUrl();
                photo.setImageURI(Uri.parse(url));
                name.setText(manager.getName());
                description.setText(manager.getDesc());
                phone.setText(manager.getTelephone());
                phone.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + ((TextView)v).getText().toString()));
                    startActivity(intent);
                });
            }
            if (view.getParent() != null)
            {
                ((ViewGroup)view.getParent()).removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView(pages[position]);
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }
    }
}
