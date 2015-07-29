package com.scxrh.amb.model;

public class FavoriteItem
{
    private String favoriteId;
    private String favoriteName;
    private String favoriteDesc;
    private String itemId;
    private String itemType;
    private String imgUrl;
    private String contentType;
    private String showCheck;
    private String checked;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        FavoriteItem item = (FavoriteItem)o;
        return favoriteId.equals(item.favoriteId);
    }

    @Override
    public int hashCode()
    {
        return favoriteId.hashCode();
    }

    public String getChecked()
    {
        return checked;
    }

    public void setChecked(String checked)
    {
        this.checked = checked;
    }

    public String getShowCheck()
    {
        return showCheck;
    }

    public void setShowCheck(String showCheck)
    {
        this.showCheck = showCheck;
    }

    public String getFavoriteId()
    {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId)
    {
        this.favoriteId = favoriteId;
    }

    public String getFavoriteName()
    {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName)
    {
        this.favoriteName = favoriteName;
    }

    public String getFavoriteDesc()
    {
        return favoriteDesc;
    }

    public void setFavoriteDesc(String favoriteDesc)
    {
        this.favoriteDesc = favoriteDesc;
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

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
}
