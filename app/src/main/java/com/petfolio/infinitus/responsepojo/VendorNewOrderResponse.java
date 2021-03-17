package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class VendorNewOrderResponse {


    /**
     * Status : Success
     * Message : Vendor Order Details List
     * Data : [{"_id":"604df4edf2d265475731c69e","order_id":"ITEM-1615721709329","product_name":"DOG FOOD 2","product_quantity":1,"product_price":100,"prodcut_image":"http://54.212.108.156:3000/api/uploads/1615541391131.jpeg","date_of_booking":"14-03-2021 05:05 PM","status":"New","user_cancell_info":"","user_cancell_date":"","vendor_cancell_info":"","vendor_cancell_date":"","vendor_accept_cancel":"","vendor_complete_date":"","vendor_complete_info":""}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * _id : 604df4edf2d265475731c69e
     * order_id : ITEM-1615721709329
     * product_name : DOG FOOD 2
     * product_quantity : 1
     * product_price : 100
     * prodcut_image : http://54.212.108.156:3000/api/uploads/1615541391131.jpeg
     * date_of_booking : 14-03-2021 05:05 PM
     * status : New
     * user_cancell_info :
     * user_cancell_date :
     * vendor_cancell_info :
     * vendor_cancell_date :
     * vendor_accept_cancel :
     * vendor_complete_date :
     * vendor_complete_info :
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
        private String _id;
        private String order_id;
        private String product_name;
        private int product_quantity;
        private double product_price;
        private String prodcut_image;
        private String date_of_booking;
        private String status;
        private String user_cancell_info;
        private String user_cancell_date;
        private String vendor_cancell_info;
        private String vendor_cancell_date;
        private String vendor_accept_cancel;
        private String vendor_complete_date;
        private String vendor_complete_info;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getProduct_quantity() {
            return product_quantity;
        }

        public void setProduct_quantity(int product_quantity) {
            this.product_quantity = product_quantity;
        }

        public double getProduct_price() {
            return product_price;
        }

        public void setProduct_price(double product_price) {
            this.product_price = product_price;
        }

        public String getProdcut_image() {
            return prodcut_image;
        }

        public void setProdcut_image(String prodcut_image) {
            this.prodcut_image = prodcut_image;
        }

        public String getDate_of_booking() {
            return date_of_booking;
        }

        public void setDate_of_booking(String date_of_booking) {
            this.date_of_booking = date_of_booking;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_cancell_info() {
            return user_cancell_info;
        }

        public void setUser_cancell_info(String user_cancell_info) {
            this.user_cancell_info = user_cancell_info;
        }

        public String getUser_cancell_date() {
            return user_cancell_date;
        }

        public void setUser_cancell_date(String user_cancell_date) {
            this.user_cancell_date = user_cancell_date;
        }

        public String getVendor_cancell_info() {
            return vendor_cancell_info;
        }

        public void setVendor_cancell_info(String vendor_cancell_info) {
            this.vendor_cancell_info = vendor_cancell_info;
        }

        public String getVendor_cancell_date() {
            return vendor_cancell_date;
        }

        public void setVendor_cancell_date(String vendor_cancell_date) {
            this.vendor_cancell_date = vendor_cancell_date;
        }

        public String getVendor_accept_cancel() {
            return vendor_accept_cancel;
        }

        public void setVendor_accept_cancel(String vendor_accept_cancel) {
            this.vendor_accept_cancel = vendor_accept_cancel;
        }

        public String getVendor_complete_date() {
            return vendor_complete_date;
        }

        public void setVendor_complete_date(String vendor_complete_date) {
            this.vendor_complete_date = vendor_complete_date;
        }

        public String getVendor_complete_info() {
            return vendor_complete_info;
        }

        public void setVendor_complete_info(String vendor_complete_info) {
            this.vendor_complete_info = vendor_complete_info;
        }
    }
}
