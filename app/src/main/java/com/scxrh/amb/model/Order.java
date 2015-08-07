package com.scxrh.amb.model;

import java.util.ArrayList;
import java.util.List;

public class Order
{
    private String orderId;
    private String orderDate;
    private String shopId;
    private String shopName;
    private String orderName;
    private String orderThumbnail;
    private String orderState;
    private List<OrderItem> orderItems = new ArrayList<>();

    public String getOrderName()
    {
        return orderName;
    }

    public void setOrderName(String orderName)
    {
        this.orderName = orderName;
    }

    public String getOrderThumbnail()
    {
        return orderThumbnail;
    }

    public void setOrderThumbnail(String orderThumbnail)
    {
        this.orderThumbnail = orderThumbnail;
    }

    public String getOrderState()
    {
        return orderState;
    }

    public void setOrderState(String orderState)
    {
        this.orderState = orderState;
    }

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(String orderDate)
    {
        this.orderDate = orderDate;
    }

    public String getShopId()
    {
        return shopId;
    }

    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public List<OrderItem> getOrderItems()
    {
        return orderItems;
    }

    public void addOrderItems(List<OrderItem> orderItems)
    {
        this.orderItems.clear();
        this.orderItems.addAll(orderItems);
    }

    public class OrderItem
    {
        private String orderItemId;
        private String productItemId;
        private String name;
        private String thumbnail;
        private String amount;
        private String price;
        private String totalPrice;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getThumbnail()
        {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail)
        {
            this.thumbnail = thumbnail;
        }

        public String getOrderItemId()
        {
            return orderItemId;
        }

        public void setOrderItemId(String orderItemId)
        {
            this.orderItemId = orderItemId;
        }

        public String getProductItemId()
        {
            return productItemId;
        }

        public void setProductItemId(String productItemId)
        {
            this.productItemId = productItemId;
        }

        public String getAmount()
        {
            return amount;
        }

        public void setAmount(String amount)
        {
            this.amount = amount;
        }

        public String getPrice()
        {
            return price;
        }

        public void setPrice(String price)
        {
            this.price = price;
        }

        public String getTotalPrice()
        {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice)
        {
            this.totalPrice = totalPrice;
        }
    }
}
