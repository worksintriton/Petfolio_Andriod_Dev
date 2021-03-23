package com.petfolio.infinitus.requestpojo;

import java.util.List;

public class ApplyMultiProdDiscountRequest {


    /**
     * _id : ["604b34a242cb073ec4dfef12","604b34cb42cb073ec4dfef13"]
     * discount_status : false
     * discount_amount : 2
     * discount : 0
     */

    private boolean discount_status;
    private int discount_amount;
    private int discount;
    private List<String> _id;

    public boolean isDiscount_status() {
        return discount_status;
    }

    public void setDiscount_status(boolean discount_status) {
        this.discount_status = discount_status;
    }

    public int getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(int discount_amount) {
        this.discount_amount = discount_amount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public List<String> get_id() {
        return _id;
    }

    public void set_id(List<String> _id) {
        this._id = _id;
    }
}
