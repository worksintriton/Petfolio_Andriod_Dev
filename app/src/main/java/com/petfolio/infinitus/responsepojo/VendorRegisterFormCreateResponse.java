package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class VendorRegisterFormCreateResponse {

    /**
     * Status : Success
     * Message : Vendor Updated
     * Data : {"bussiness_gallery":[],"photo_id_proof":[],"govt_id_proof":[],"certifi":[],"_id":"5fe49c7335ed19762b918519","user_id":"123123213","user_name":"Mohammed imthiyas","user_email":"mohammed@gmail.com","bussiness_name":"Mohammed","bussiness_email":"mohammed@gmail.com","bussiness":"Mohammed","bussiness_phone":"9876543210","business_reg":"Resdf","date_and_time":"23-10-2020 12:00 AM","mobile_type":"Admin","profile_status":true,"profile_verification_status":"Not Verified","bussiness_loc":12,"bussiness_lat":80,"bussiness_long":"Moolakadai","delete_status":false,"updatedAt":"2020-12-24T13:51:37.826Z","createdAt":"2020-12-24T13:49:39.726Z","__v":0}
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
         * bussiness_gallery : []
         * photo_id_proof : []
         * govt_id_proof : []
         * certifi : []
         * _id : 5fe49c7335ed19762b918519
         * user_id : 123123213
         * user_name : Mohammed imthiyas
         * user_email : mohammed@gmail.com
         * bussiness_name : Mohammed
         * bussiness_email : mohammed@gmail.com
         * bussiness : Mohammed
         * bussiness_phone : 9876543210
         * business_reg : Resdf
         * date_and_time : 23-10-2020 12:00 AM
         * mobile_type : Admin
         * profile_status : true
         * profile_verification_status : Not Verified
         * bussiness_loc : 12
         * bussiness_lat : 80
         * bussiness_long : Moolakadai
         * delete_status : false
         * updatedAt : 2020-12-24T13:51:37.826Z
         * createdAt : 2020-12-24T13:49:39.726Z
         * __v : 0
         */

        private String _id;
        private String user_id;
        private String user_name;
        private String user_email;
        private String bussiness_name;
        private String bussiness_email;
        private String bussiness;
        private String bussiness_phone;
        private String business_reg;
        private String date_and_time;
        private String mobile_type;
        private boolean profile_status;
        private String profile_verification_status;
        private int bussiness_loc;
        private int bussiness_lat;
        private String bussiness_long;
        private boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private List<?> bussiness_gallery;
        private List<?> photo_id_proof;
        private List<?> govt_id_proof;
        private List<?> certifi;


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


        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;

        }


        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;

        }


        public String getBussiness_name() {
            return bussiness_name;
        }

        public void setBussiness_name(String bussiness_name) {
            this.bussiness_name = bussiness_name;

        }


        public String getBussiness_email() {
            return bussiness_email;
        }

        public void setBussiness_email(String bussiness_email) {
            this.bussiness_email = bussiness_email;

        }

        public String getBussiness() {
            return bussiness;
        }

        public void setBussiness(String bussiness) {
            this.bussiness = bussiness;

        }

        public String getBussiness_phone() {
            return bussiness_phone;
        }

        public void setBussiness_phone(String bussiness_phone) {
            this.bussiness_phone = bussiness_phone;

        }


        public String getBusiness_reg() {
            return business_reg;
        }

        public void setBusiness_reg(String business_reg) {
            this.business_reg = business_reg;

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


        public boolean isProfile_status() {
            return profile_status;
        }

        public void setProfile_status(boolean profile_status) {
            this.profile_status = profile_status;

        }


        public String getProfile_verification_status() {
            return profile_verification_status;
        }

        public void setProfile_verification_status(String profile_verification_status) {
            this.profile_verification_status = profile_verification_status;

        }


        public int getBussiness_loc() {
            return bussiness_loc;
        }

        public void setBussiness_loc(int bussiness_loc) {
            this.bussiness_loc = bussiness_loc;

        }


        public int getBussiness_lat() {
            return bussiness_lat;
        }

        public void setBussiness_lat(int bussiness_lat) {
            this.bussiness_lat = bussiness_lat;

        }

        public String getBussiness_long() {
            return bussiness_long;
        }

        public void setBussiness_long(String bussiness_long) {
            this.bussiness_long = bussiness_long;

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


        public List<?> getBussiness_gallery() {
            return bussiness_gallery;
        }

        public void setBussiness_gallery(List<?> bussiness_gallery) {
            this.bussiness_gallery = bussiness_gallery;

        }


        public List<?> getPhoto_id_proof() {
            return photo_id_proof;
        }

        public void setPhoto_id_proof(List<?> photo_id_proof) {
            this.photo_id_proof = photo_id_proof;

        }


        public List<?> getGovt_id_proof() {
            return govt_id_proof;
        }

        public void setGovt_id_proof(List<?> govt_id_proof) {
            this.govt_id_proof = govt_id_proof;

        }

        public List<?> getCertifi() {
            return certifi;
        }

        public void setCertifi(List<?> certifi) {
            this.certifi = certifi;

        }
    }
}
