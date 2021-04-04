package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class PetTypeListResponse {

    /**
     * Status : Success
     * Message : PET type Details
     * Data : {"usertypedata":[{"_id":"602d1bf4562e0916bc9b3215","pet_type_title":"Dog","date_and_time":"2/17/2021, 7:06:51 PM","delete_status":false,"updatedAt":"2021-02-17T13:36:52.141Z","createdAt":"2021-02-17T13:36:52.141Z","__v":0},{"_id":"602d1c6b562e0916bc9b321d","pet_type_title":"Cat","date_and_time":"2/17/2021, 7:08:50 PM","delete_status":false,"updatedAt":"2021-02-17T13:38:51.432Z","createdAt":"2021-02-17T13:38:51.432Z","__v":0},{"_id":"602d1cf4562e0916bc9b3224","pet_type_title":"Parrot","date_and_time":"2/17/2021, 7:11:08 PM","delete_status":false,"updatedAt":"2021-02-17T13:41:08.956Z","createdAt":"2021-02-17T13:41:08.956Z","__v":0},{"_id":"602d1d73562e0916bc9b322a","pet_type_title":"Pigs","date_and_time":"2/17/2021, 7:13:15 PM","delete_status":false,"updatedAt":"2021-02-17T13:43:15.527Z","createdAt":"2021-02-17T13:43:15.527Z","__v":0},{"_id":"602d1dd5562e0916bc9b3232","pet_type_title":"Fish","date_and_time":"2/17/2021, 7:14:52 PM","delete_status":false,"updatedAt":"2021-02-17T13:44:53.219Z","createdAt":"2021-02-17T13:44:53.219Z","__v":0},{"_id":"602d1f52562e0916bc9b323d","pet_type_title":"Horse","date_and_time":"2/17/2021, 7:21:14 PM","delete_status":false,"updatedAt":"2021-02-17T13:51:14.862Z","createdAt":"2021-02-17T13:51:14.862Z","__v":0}],"product_categories":[{"_id":"5fec1424ea832e2e73c1fc78","img_path":"http://52.25.163.13:3000/api/uploads/template%20(2).jpg","product_cate":"Dog  Food","img_index":0,"show_status":true,"date_and_time":"Fri Feb 19 2021 11:06:01 GMT+0530 (India Standard Time)","delete_status":true,"updatedAt":"2021-02-19T05:36:00.643Z","createdAt":"2020-12-30T05:46:12.099Z","__v":0},{"_id":"5fec14a5ea832e2e73c1fc79","img_path":"http://52.25.163.13:3000/api/uploads/template%20(3).jpg","product_cate":"Cat Food","img_index":0,"show_status":true,"date_and_time":"Thu Feb 18 2021 15:05:46 GMT+0530 (India Standard Time)","delete_status":true,"updatedAt":"2021-02-18T09:35:47.194Z","createdAt":"2020-12-30T05:48:21.363Z","__v":0},{"_id":"5fec1573ea832e2e73c1fc7a","img_path":"http://52.25.163.13:3000/api/uploads/template%20(4).jpg","product_cate":"Cow food","img_index":0,"show_status":true,"date_and_time":"Thu Feb 18 2021 15:05:55 GMT+0530 (India Standard Time)","delete_status":true,"updatedAt":"2021-02-18T09:35:55.761Z","createdAt":"2020-12-30T05:51:47.787Z","__v":0},{"_id":"5fec22eeea832e2e73c1fc7b","img_path":"http://52.25.163.13:3000/api/uploads/template%20(5).jpg","product_cate":"Bird Food","img_index":0,"show_status":true,"date_and_time":"Thu Feb 18 2021 15:06:04 GMT+0530 (India Standard Time)","delete_status":true,"updatedAt":"2021-02-18T09:36:04.572Z","createdAt":"2020-12-30T06:49:18.019Z","__v":0},{"_id":"602ccc8518da357d363da7cb","img_path":"http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg","product_cate":"PET COLLARS & LEASHES","img_index":0,"show_status":true,"date_and_time":"2/17/2021, 1:27:57 PM","delete_status":false,"updatedAt":"2021-02-17T07:57:57.193Z","createdAt":"2021-02-17T07:57:57.193Z","__v":0}]}
     * Code : 200
     */

    private String Status;
    private String Message;
    private DataBean Data;
    private int Code;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public static class DataBean {
        /**
         * _id : 602d1bf4562e0916bc9b3215
         * pet_type_title : Dog
         * date_and_time : 2/17/2021, 7:06:51 PM
         * delete_status : false
         * updatedAt : 2021-02-17T13:36:52.141Z
         * createdAt : 2021-02-17T13:36:52.141Z
         * __v : 0
         */

        private List<UsertypedataBean> usertypedata;
        /**
         * _id : 5fec1424ea832e2e73c1fc78
         * img_path : http://52.25.163.13:3000/api/uploads/template%20(2).jpg
         * product_cate : Dog  Food
         * img_index : 0
         * show_status : true
         * date_and_time : Fri Feb 19 2021 11:06:01 GMT+0530 (India Standard Time)
         * delete_status : true
         * updatedAt : 2021-02-19T05:36:00.643Z
         * createdAt : 2020-12-30T05:46:12.099Z
         * __v : 0
         */

        private List<ProductCategoriesBean> product_categories;

        public List<UsertypedataBean> getUsertypedata() {
            return usertypedata;
        }

        public void setUsertypedata(List<UsertypedataBean> usertypedata) {
            this.usertypedata = usertypedata;
        }

        public List<ProductCategoriesBean> getProduct_categories() {
            return product_categories;
        }

        public void setProduct_categories(List<ProductCategoriesBean> product_categories) {
            this.product_categories = product_categories;
        }

        public static class UsertypedataBean {
            private String _id;
            private String pet_type_title;
            private String date_and_time;
            private boolean delete_status;
            private String updatedAt;
            private String createdAt;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getPet_type_title() {
                return pet_type_title;
            }

            public void setPet_type_title(String pet_type_title) {
                this.pet_type_title = pet_type_title;
            }

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }

            public boolean isDelete_status() {
                return delete_status;
            }

            public void setDelete_status(boolean delete_status) {
                this.delete_status = delete_status;
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
        }

        public static class ProductCategoriesBean {
            private String _id;
            private String img_path;
            private String product_cate;
            private int img_index;
            private boolean show_status;
            private String date_and_time;
            private boolean delete_status;
            private String updatedAt;
            private String createdAt;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getImg_path() {
                return img_path;
            }

            public void setImg_path(String img_path) {
                this.img_path = img_path;
            }

            public String getProduct_cate() {
                return product_cate;
            }

            public void setProduct_cate(String product_cate) {
                this.product_cate = product_cate;
            }

            public int getImg_index() {
                return img_index;
            }

            public void setImg_index(int img_index) {
                this.img_index = img_index;
            }

            public boolean isShow_status() {
                return show_status;
            }

            public void setShow_status(boolean show_status) {
                this.show_status = show_status;
            }

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }

            public boolean isDelete_status() {
                return delete_status;
            }

            public void setDelete_status(boolean delete_status) {
                this.delete_status = delete_status;
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
        }
    }
}
