package com.petfolio.infinitus.requestpojo;

import java.util.List;

public class VendorOrderBookingCreateRequest {

    /**
     * user_id : 603e27792c2b43125f8cb802
     * product_data : [{"_id":"6046fa59cb48ca0b68cda50c","user_id":"603e27792c2b43125f8cb802","product_id":{"breed_type":["602d1c20562e0916bc9b3218"],"pet_type":["602d1c6b562e0916bc9b321d"],"age":[3],"product_img":["http://54.212.108.156:3000/api/uploads/1614075552394.jpg"],"_id":"6034d6a5888af7628e7e17d4","user_id":"602a2061b3c2dd2c152d77d8","cat_id":"5fec14a5ea832e2e73c1fc79","cost":1000,"threshould":"100","product_name":"Cat Dinner","product_discription":"This cat  food","discount":10,"related":"","count":0,"status":"true","verification_status":"Not Verified","date_and_time":"Tue Feb 23 2021 15:49:15 GMT+0530 (India Standard Time)","mobile_type":"Admin","delete_status":true,"fav_status":false,"today_deal":true,"updatedAt":"2021-03-08T09:15:24.812Z","createdAt":"2021-02-23T10:19:17.691Z","__v":0},"product_count":7,"updatedAt":"2021-03-09T06:10:04.116Z","createdAt":"2021-03-09T04:32:25.151Z","__v":0},{"_id":"60471192760fff2968288bbd","user_id":"603e27792c2b43125f8cb802","product_id":{"breed_type":["602d1c17562e0916bc9b3217"],"pet_type":["602d1c6b562e0916bc9b321d"],"age":[3],"product_img":["http://54.212.108.156:3000/api/uploads/1614075490400.jpg"],"_id":"6034d66598fa826140f6a3a3","user_id":"602a2061b3c2dd2c152d77d8","cat_id":"5fec14a5ea832e2e73c1fc79","cost":40000,"threshould":"100","product_name":"CAT Lunch","product_discription":"This is cat lunch","discount":40,"related":"","count":0,"status":"true","verification_status":"Not Verified","date_and_time":"Tue Feb 23 2021 15:48:14 GMT+0530 (India Standard Time)","mobile_type":"Admin","delete_status":true,"fav_status":false,"today_deal":true,"updatedAt":"2021-03-08T09:15:22.710Z","createdAt":"2021-02-23T10:18:13.989Z","__v":0},"product_count":1,"updatedAt":"2021-03-09T06:11:30.904Z","createdAt":"2021-03-09T06:11:30.904Z","__v":0}]
     * prodouct_total : 47000
     * shipping_charge : 0
     * discount_price : 0
     * grand_total : 0
     */

    private String user_id;
    private int prodouct_total;
    private int shipping_charge;
    private int discount_price;
    private int grand_total;
    private List<ProductDataBean> product_data;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;

    }


    public int getProdouct_total() {
        return prodouct_total;
    }

    public void setProdouct_total(int prodouct_total) {
        this.prodouct_total = prodouct_total;
    }


    public int getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(int shipping_charge) {
        this.shipping_charge = shipping_charge;

    }


    public int getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(int discount_price) {
        this.discount_price = discount_price;

    }


    public int getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(int grand_total) {
        this.grand_total = grand_total;

    }


    public List<ProductDataBean> getProduct_data() {
        return product_data;
    }

    public void setProduct_data(List<ProductDataBean> product_data) {
        this.product_data = product_data;

    }

    public static class ProductDataBean  {
        /**
         * _id : 6046fa59cb48ca0b68cda50c
         * user_id : 603e27792c2b43125f8cb802
         * product_id : {"breed_type":["602d1c20562e0916bc9b3218"],"pet_type":["602d1c6b562e0916bc9b321d"],"age":[3],"product_img":["http://54.212.108.156:3000/api/uploads/1614075552394.jpg"],"_id":"6034d6a5888af7628e7e17d4","user_id":"602a2061b3c2dd2c152d77d8","cat_id":"5fec14a5ea832e2e73c1fc79","cost":1000,"threshould":"100","product_name":"Cat Dinner","product_discription":"This cat  food","discount":10,"related":"","count":0,"status":"true","verification_status":"Not Verified","date_and_time":"Tue Feb 23 2021 15:49:15 GMT+0530 (India Standard Time)","mobile_type":"Admin","delete_status":true,"fav_status":false,"today_deal":true,"updatedAt":"2021-03-08T09:15:24.812Z","createdAt":"2021-02-23T10:19:17.691Z","__v":0}
         * product_count : 7
         * updatedAt : 2021-03-09T06:10:04.116Z
         * createdAt : 2021-03-09T04:32:25.151Z
         * __v : 0
         */

        private String _id;
        private String user_id;
        private ProductIdBean product_id;
        private int product_count;
        private String updatedAt;
        private String createdAt;
        private int __v;


        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;

        }


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;

        }

        public ProductIdBean getProduct_id() {
            return product_id;
        }

        public void setProduct_id(ProductIdBean product_id) {
            this.product_id = product_id;

        }


        public int getProduct_count() {
            return product_count;
        }

        public void setProduct_count(int product_count) {
            this.product_count = product_count;

        }


        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;

        }


        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;

        }


        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public static class ProductIdBean  {
            /**
             * breed_type : ["602d1c20562e0916bc9b3218"]
             * pet_type : ["602d1c6b562e0916bc9b321d"]
             * age : [3]
             * product_img : ["http://54.212.108.156:3000/api/uploads/1614075552394.jpg"]
             * _id : 6034d6a5888af7628e7e17d4
             * user_id : 602a2061b3c2dd2c152d77d8
             * cat_id : 5fec14a5ea832e2e73c1fc79
             * cost : 1000
             * threshould : 100
             * product_name : Cat Dinner
             * product_discription : This cat  food
             * discount : 10
             * related :
             * count : 0
             * status : true
             * verification_status : Not Verified
             * date_and_time : Tue Feb 23 2021 15:49:15 GMT+0530 (India Standard Time)
             * mobile_type : Admin
             * delete_status : true
             * fav_status : false
             * today_deal : true
             * updatedAt : 2021-03-08T09:15:24.812Z
             * createdAt : 2021-02-23T10:19:17.691Z
             * __v : 0
             */

            private String _id;
            private String user_id;
            private String cat_id;
            private int cost;
            private String threshould;
            private String product_name;
            private String product_discription;
            private int discount;
            private String related;
            private int count;
            private String status;
            private String verification_status;
            private String date_and_time;
            private String mobile_type;
            private boolean delete_status;
            private boolean fav_status;
            private boolean today_deal;
            private String updatedAt;
            private String createdAt;
            private int __v;
            private List<String> breed_type;
            private List<String> pet_type;
            private List<Integer> age;
            private List<String> product_img;


            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;

            }


            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;

            }


            public String getCat_id() {
                return cat_id;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;

            }


            public int getCost() {
                return cost;
            }

            public void setCost(int cost) {
                this.cost = cost;

            }


            public String getThreshould() {
                return threshould;
            }

            public void setThreshould(String threshould) {
                this.threshould = threshould;

            }


            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;

            }


            public String getProduct_discription() {
                return product_discription;
            }

            public void setProduct_discription(String product_discription) {
                this.product_discription = product_discription;

            }


            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;

            }


            public String getRelated() {
                return related;
            }

            public void setRelated(String related) {
                this.related = related;

            }


            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;

            }


            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;

            }


            public String getVerification_status() {
                return verification_status;
            }

            public void setVerification_status(String verification_status) {
                this.verification_status = verification_status;

            }

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }

            public String getMobile_type() {
                return mobile_type;
            }

            public void setMobile_type(String mobile_type) {
                this.mobile_type = mobile_type;

            }


            public boolean isDelete_status() {
                return delete_status;
            }

            public void setDelete_status(boolean delete_status) {
                this.delete_status = delete_status;
            }


            public boolean isFav_status() {
                return fav_status;
            }

            public void setFav_status(boolean fav_status) {
                this.fav_status = fav_status;

            }


            public boolean isToday_deal() {
                return today_deal;
            }

            public void setToday_deal(boolean today_deal) {
                this.today_deal = today_deal;
            }


            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }


            public List<String> getBreed_type() {
                return breed_type;
            }

            public void setBreed_type(List<String> breed_type) {
                this.breed_type = breed_type;

            }


            public List<String> getPet_type() {
                return pet_type;
            }

            public void setPet_type(List<String> pet_type) {
                this.pet_type = pet_type;
            }


            public List<Integer> getAge() {
                return age;
            }

            public void setAge(List<Integer> age) {
                this.age = age;
            }


            public List<String> getProduct_img() {
                return product_img;
            }

            public void setProduct_img(List<String> product_img) {
                this.product_img = product_img;
            }
        }
    }
}
