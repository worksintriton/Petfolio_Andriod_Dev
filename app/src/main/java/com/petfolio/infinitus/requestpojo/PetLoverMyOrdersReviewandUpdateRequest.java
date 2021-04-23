package com.petfolio.infinitus.requestpojo;

public class PetLoverMyOrdersReviewandUpdateRequest {

    /**
     * order_id : ORDER_1234567890
     * product_id : 1
     * user_feedback : good_work
     * user_rate : 0
     */

    private String order_id;
    private String product_id;
    private String user_feedback;
    private int user_rate;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_feedback() {
        return user_feedback;
    }

    public void setUser_feedback(String user_feedback) {
        this.user_feedback = user_feedback;
    }

    public int getUser_rate() {
        return user_rate;
    }

    public void setUser_rate(int user_rate) {
        this.user_rate = user_rate;
    }
}
