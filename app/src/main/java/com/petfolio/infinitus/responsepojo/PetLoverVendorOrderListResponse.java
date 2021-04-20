package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class PetLoverVendorOrderListResponse {

    /**
     * Status : Success
     * Message : Vendor Order Grouped
     * Data : [{"p_order_id":"ORDER-1618833408047","p_user_id":"603e27792c2b43125f8cb802","p_shipping_address":"60797c16a20ca32d2668a30c","p_payment_id":"pay_H0gNJn2CM7xjO1","p_vendor_id":"604866a50b3a487571a1c568","p_order_product_count":2,"p_order_price":7200,"p_order_image":"http://54.212.108.156:3000/api/uploads/1615541391131.jpeg","p_order_booked_on":"19-04-2021 12:05 PM","p_order_status":"New","p_order_text":"Food Products"},{"p_order_id":"ORDER-1618839010942","p_user_id":"603e27792c2b43125f8cb802","p_shipping_address":"60797c16a20ca32d2668a30c","p_payment_id":"pay_H0gNJn2CM7xjO1","p_vendor_id":"604866a50b3a487571a1c568","p_order_product_count":2,"p_order_price":7200,"p_order_image":"http://54.212.108.156:3000/api/uploads/1615541391131.jpeg","p_order_booked_on":"19-04-2021 12:05 PM","p_order_status":"New","p_order_text":"Food Products"},{"p_order_id":"ORDER-1618839299981","p_user_id":"603e27792c2b43125f8cb802","p_shipping_address":"60797c16a20ca32d2668a30c","p_payment_id":"pay_H0gNJn2CM7xjO1","p_vendor_id":"604866a50b3a487571a1c568","p_order_product_count":2,"p_order_price":7200,"p_order_image":"http://54.212.108.156:3000/api/uploads/1615541391131.jpeg","p_order_booked_on":"19-04-2021 12:05 PM","p_order_status":"New","p_order_text":"Food Products"}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * p_order_id : ORDER-1618833408047
     * p_user_id : 603e27792c2b43125f8cb802
     * p_shipping_address : 60797c16a20ca32d2668a30c
     * p_payment_id : pay_H0gNJn2CM7xjO1
     * p_vendor_id : 604866a50b3a487571a1c568
     * p_order_product_count : 2
     * p_order_price : 7200
     * p_order_image : http://54.212.108.156:3000/api/uploads/1615541391131.jpeg
     * p_order_booked_on : 19-04-2021 12:05 PM
     * p_order_status : New
     * p_order_text : Food Products
     */

    private List<DataBean> Data;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String p_order_id;
        private String p_user_id;
        private String p_shipping_address;
        private String p_payment_id;
        private String p_vendor_id;
        private int p_order_product_count;
        private int p_order_price;
        private String p_order_image;
        private String p_order_booked_on;
        private String p_order_status;
        private String p_order_text;

        public String getP_order_id() {
            return p_order_id;
        }

        public void setP_order_id(String p_order_id) {
            this.p_order_id = p_order_id;
        }

        public String getP_user_id() {
            return p_user_id;
        }

        public void setP_user_id(String p_user_id) {
            this.p_user_id = p_user_id;
        }

        public String getP_shipping_address() {
            return p_shipping_address;
        }

        public void setP_shipping_address(String p_shipping_address) {
            this.p_shipping_address = p_shipping_address;
        }

        public String getP_payment_id() {
            return p_payment_id;
        }

        public void setP_payment_id(String p_payment_id) {
            this.p_payment_id = p_payment_id;
        }

        public String getP_vendor_id() {
            return p_vendor_id;
        }

        public void setP_vendor_id(String p_vendor_id) {
            this.p_vendor_id = p_vendor_id;
        }

        public int getP_order_product_count() {
            return p_order_product_count;
        }

        public void setP_order_product_count(int p_order_product_count) {
            this.p_order_product_count = p_order_product_count;
        }

        public int getP_order_price() {
            return p_order_price;
        }

        public void setP_order_price(int p_order_price) {
            this.p_order_price = p_order_price;
        }

        public String getP_order_image() {
            return p_order_image;
        }

        public void setP_order_image(String p_order_image) {
            this.p_order_image = p_order_image;
        }

        public String getP_order_booked_on() {
            return p_order_booked_on;
        }

        public void setP_order_booked_on(String p_order_booked_on) {
            this.p_order_booked_on = p_order_booked_on;
        }

        public String getP_order_status() {
            return p_order_status;
        }

        public void setP_order_status(String p_order_status) {
            this.p_order_status = p_order_status;
        }

        public String getP_order_text() {
            return p_order_text;
        }

        public void setP_order_text(String p_order_text) {
            this.p_order_text = p_order_text;
        }
    }
}
