package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class TodayDealMoreResponse {

    /**
     * Status : Success
     * Message : product list
     * Data : [{"_id":"602e11404775fa0735d7bf40","product_img":"http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg","product_title":"DOGISTA PET PRODUCTS Dog Rope Leash,Brass","product_price":180,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"602e467ff62e8d2089fba973","product_img":"http://54.212.108.156:3000/api/uploads/cats.jpg","product_title":"Cat Food","product_price":1000,"product_discount":2,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"602e46d8f62e8d2089fba974","product_img":"http://54.212.108.156:3000/api/uploads/cowfood.jpg","product_title":"Cow food","product_price":1000,"product_discount":20,"product_fav":false,"product_rating":4.8,"product_review":232}]
     * product_list_count : 13
     * Code : 200
     */

    private String Status;
    private String Message;
    private int product_list_count;
    private int Code;
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


    public int getProduct_list_count() {
        return product_list_count;
    }

    public void setProduct_list_count(int product_list_count) {
        this.product_list_count = product_list_count;

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

    public static class DataBean  {
        /**
         * _id : 602e11404775fa0735d7bf40
         * product_img : http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg
         * product_title : DOGISTA PET PRODUCTS Dog Rope Leash,Brass
         * product_price : 180
         * product_discount : 0
         * product_fav : false
         * product_rating : 4.8
         * product_review : 232
         */

        private String _id;
        private String product_img;
        private String product_title;
        private int product_price;
        private int product_discount;
        private boolean product_fav;
        private double product_rating;
        private int product_review;


        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;

        }


        public String getProduct_img() {
            return product_img;
        }

        public void setProduct_img(String product_img) {
            this.product_img = product_img;

        }


        public String getProduct_title() {
            return product_title;
        }

        public void setProduct_title(String product_title) {
            this.product_title = product_title;

        }


        public int getProduct_price() {
            return product_price;
        }

        public void setProduct_price(int product_price) {
            this.product_price = product_price;

        }


        public int getProduct_discount() {
            return product_discount;
        }

        public void setProduct_discount(int product_discount) {
            this.product_discount = product_discount;

        }


        public boolean isProduct_fav() {
            return product_fav;
        }

        public void setProduct_fav(boolean product_fav) {
            this.product_fav = product_fav;

        }


        public double getProduct_rating() {
            return product_rating;
        }

        public void setProduct_rating(double product_rating) {
            this.product_rating = product_rating;

        }


        public int getProduct_review() {
            return product_review;
        }

        public void setProduct_review(int product_review) {
            this.product_review = product_review;

        }
    }
}
