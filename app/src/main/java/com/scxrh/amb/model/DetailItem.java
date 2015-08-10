package com.scxrh.amb.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailItem implements Parcelable
{
    public static final Parcelable.Creator<DetailItem> CREATOR = new Parcelable.Creator<DetailItem>()
    {
        public DetailItem createFromParcel(Parcel source) {return new DetailItem(source);}

        public DetailItem[] newArray(int size) {return new DetailItem[size];}
    };
    private String imgUrl;
    private String shopDesc;
    private String shopLogo;
    private String shopName;
    private String telephone;
    private String text;
    private String name;
    private String price;
    private String thumbImgUrl;
    private String desc;
    private String itemId;
    private String amount;

    public DetailItem() { }

    protected DetailItem(Parcel in)
    {
        this.imgUrl = in.readString();
        this.shopDesc = in.readString();
        this.shopLogo = in.readString();
        this.shopName = in.readString();
        this.telephone = in.readString();
        this.text = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.thumbImgUrl = in.readString();
        this.desc = in.readString();
        this.itemId = in.readString();
        this.amount = in.readString();
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getThumbImgUrl()
    {
        return thumbImgUrl;
    }

    public void setThumbImgUrl(String thumbImgUrl)
    {
        this.thumbImgUrl = thumbImgUrl;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getItemId()
    {
        return itemId;
    }

    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getShopDesc()
    {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc)
    {
        this.shopDesc = shopDesc;
    }

    public String getShopLogo()
    {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo)
    {
        this.shopLogo = shopLogo;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.imgUrl);
        dest.writeString(this.shopDesc);
        dest.writeString(this.shopLogo);
        dest.writeString(this.shopName);
        dest.writeString(this.telephone);
        dest.writeString(this.text);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.thumbImgUrl);
        dest.writeString(this.desc);
        dest.writeString(this.itemId);
        dest.writeString(this.amount);
    }
}
