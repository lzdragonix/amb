package com.scxrh.amb.views.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.R;
import com.scxrh.amb.injector.component.DaggerMvpComponent;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.model.City;
import com.scxrh.amb.presenter.PerInfoPresenter;
import com.scxrh.amb.views.activity.WindowActivity;
import com.scxrh.amb.views.view.PerInfoView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

// 个人信息
public class PerInfoFragment extends BaseFragment implements PerInfoView
{
    public static final String TAG = PerInfoFragment.class.getSimpleName();
    @Bind(R.id.txtHeader)
    TextView txtHeader;
    @Bind(R.id.btnSubmit)
    View btnSubmit;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.txtCommunity)
    TextView txtCommunity;
    @Bind(R.id.txtCity)
    TextView txtCity;
    @Bind(R.id.imgAvatar)
    SimpleDraweeView imgAvatar;
    @Inject
    PerInfoPresenter presenter;

    @Override
    public void setName(String name)
    {
        if (TextUtils.isEmpty(name)) { return; }
        this.name.setText(name);
        this.name.setSelection(name.length());
    }

    @Override
    public void changeCity(City city)
    {
        txtCity.setText(city.getName());
    }

    @Override
    public void changeCommunity(City city)
    {
        String name = city.getName();
        txtCommunity.setText(TextUtils.isEmpty(name) ? "请选择" : name);
    }

    @Override
    public void changeAvatar(String path)
    {
        if (TextUtils.isEmpty(path)) { return; }
        imgAvatar.setImageURI(Uri.parse(path));
    }

    @Override
    public void showLogin()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, LoginFragment.class.getName());
        startActivity(intent);
    }

    @Override
    public void close()
    {
        getActivity().finish();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtHeader.setText("个人信息");
        presenter.initView();
        presenter.loadData();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK) { return; }
        if (requestCode == Const.REQUESTCODE_CAMERA)
        {
            Uri uri = presenter.getUriFile();
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 120);
            intent.putExtra("outputY", 120);
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            getActivity().startActivityForResult(intent, Const.REQUESTCODE_CROP);
        }
        else if (requestCode == Const.REQUESTCODE_CROP)
        {
            presenter.modifyAvatar();
        }
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_per_info;
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
        getActivity().finish();
    }

    @OnClick(R.id.rlAvatar)
    void avatar()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, presenter.getUriFile());
        try
        {
            getActivity().startActivityForResult(intent, Const.REQUESTCODE_CAMERA);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.txtCity)
    void selectCity()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, SelCityFragment.class.getName());
        startActivity(intent);
    }

    @OnClick(R.id.txtCommunity)
    void selectCommunity()
    {
        Intent intent = new Intent(getActivity(), WindowActivity.class);
        intent.putExtra(Const.KEY_FRAGMENT, SelCityFragment.class.getName());
        intent.putExtra("type", "community");
        startActivity(intent);
    }

    @OnClick(R.id.btnSubmit)
    void submit()
    {
        presenter.modifyName(name.getText().toString());
        presenter.submit();
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
    public void showData(Object data)
    { }

    @Override
    public void finish()
    {
        closeProgressDialog();
        btnSubmit.setEnabled(true);
    }
}
