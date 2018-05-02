package com.kalbenutritionals.kalcaremobie.ViewModel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Robert on 21/02/2018.
 */

public class VMItems extends RecyclerView.ViewHolder {
    String guiid;
    String productCategory;
    String itemCode;
    String itemName;
    String itemBrand;
    String itemGroup;
    String desc;
    String barcode;
    int qty;
    double price;
    double discount;
    double totalPrice;
    double taxAmount;
    double netPrice;
    String basePoint;
    String bonusPoint;
    int intCampagn;

    public VMItems(View itemView) {
        super(itemView);
    }

    public int getIntCampagn() {
        return intCampagn;
    }

    public void setIntCampagn(int intCampagn) {
        this.intCampagn = intCampagn;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getGuiid() {
        return guiid;
    }

    public void setGuiid(String guiid) {
        this.guiid = guiid;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public String getBasePoint() {
        return basePoint;
    }

    public void setBasePoint(String basePoint) {
        this.basePoint = basePoint;
    }

    public String getBonusPoint() {
        return bonusPoint;
    }

    public void setBonusPoint(String bonusPoint) {
        this.bonusPoint = bonusPoint;
    }
}
