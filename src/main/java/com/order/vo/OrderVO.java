package com.order.vo;


public class OrderVO {


    public OrderVO() {
    }

    public OrderVO(String boutiqueId, String orderId, Double totalOrderPrice) {
        this.orderId = orderId;
        this.totalOrderPrice = totalOrderPrice;
        this.boutiqueId = boutiqueId;
    }

    private String boutiqueId;

    private String orderId;

    private Double totalOrderPrice;

    private Double comissionValue;

    public String getBoutiqueId() {
        return boutiqueId;
    }

    public void setBoutiqueId(String boutiqueId) {
        this.boutiqueId = boutiqueId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(Double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public Double getComissionValue() {
        return comissionValue;
    }

    public void setComissionValue(Double comissionValue) {
        this.comissionValue = comissionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderVO orderVO = (OrderVO) o;

        if (!boutiqueId.equals(orderVO.boutiqueId)) return false;
        return orderId.equals(orderVO.orderId);

    }

    @Override
    public int hashCode() {
        int result = boutiqueId.hashCode();
        result = 31 * result + orderId.hashCode();
        return result;
    }
}
