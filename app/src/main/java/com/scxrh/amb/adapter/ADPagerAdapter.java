package com.scxrh.amb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.rest.AmbApi;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.fragment.DetailFragment;

import java.util.List;

public class ADPagerAdapter extends PagerAdapter
{
    private List<UIData.Item> data;
    private View[] pages;
    private Activity context;

    public ADPagerAdapter(Activity context, List<UIData.Item> data)
    {
        this.context = context;
        this.data = data;
        pages = new View[data.size()];
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
            view = context.getLayoutInflater().inflate(R.layout.layout_image_item_ad, container, false);
            pages[position] = view;
            String url = AmbApi.END_POINT + data.get(position).getImgUrl();
            SimpleDraweeView img = (SimpleDraweeView)view.findViewById(R.id.imgItem);
            img.setImageURI(Uri.parse(url));
            view.setOnClickListener(v -> {
                UIData.Item item = data.get(position);
                Intent intent = new Intent(context, WindowActivity.class);
                intent.putExtra(Const.KEY_FRAGMENT, DetailFragment.class.getName());
                intent.putExtra(Const.KEY_DATA, item);
                context.startActivity(intent);
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
