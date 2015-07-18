package com.scxrh.amb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 7/18/15.
 */
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

    public class Item
    {
        private String contentType;
        private String imgUrl;
        private String itemId;
        private String itemType;

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
    }
}
