package com.scxrh.amb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UIData
{
    private String locationId;
    private String locationName;
    private List<Item> items = new ArrayList<>();

    public String getLocationId()
    {
        return locationId;
    }

    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }

    public static class Item implements Parcelable
    {
        public static final Creator<Item> CREATOR = new Creator<Item>()
        {
            public Item createFromParcel(Parcel source) {return new Item(source);}

            public Item[] newArray(int size) {return new Item[size];}
        };
        private String contentType;
        private String imgUrl;
        private String itemId;
        private String itemType;

        public Item() { }

        protected Item(Parcel in)
        {
            this.contentType = in.readString();
            this.imgUrl = in.readString();
            this.itemId = in.readString();
            this.itemType = in.readString();
        }

        public String getContentType()
        {
            return contentType;
        }

        public void setContentType(String contentType)
        {
            this.contentType = contentType;
        }

        public String getImgUrl()
        {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl)
        {
            this.imgUrl = imgUrl;
        }

        public String getItemId()
        {
            return itemId;
        }

        public void setItemId(String itemId)
        {
            this.itemId = itemId;
        }

        public String getItemType()
        {
            return itemType;
        }

        public void setItemType(String itemType)
        {
            this.itemType = itemType;
        }

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeString(this.contentType);
            dest.writeString(this.imgUrl);
            dest.writeString(this.itemId);
            dest.writeString(this.itemType);
        }
    }
}
