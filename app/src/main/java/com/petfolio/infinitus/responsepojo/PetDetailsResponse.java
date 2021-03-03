package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class PetDetailsResponse {


    /**
     * Status : Success
     * Message : pet details List
     * Data : [{"pet_img":["http://54.212.108.156:3000/api/uploads/1614247250740.jpeg"],"_id":"60377548757a214c0415b6d3","user_id":"6025040ee15519672cd0dc02","pet_name":"Deenu","pet_type":"Dog","pet_breed":"Golden Retrieve","pet_gender":"Male","pet_color":"White","pet_weight":5,"pet_age":"2","vaccinated":true,"last_vaccination_date":"10-02-2021","default_status":true,"date_and_time":"25-02-2021 03:30 PM","mobile_type":"Android","delete_status":false,"updatedAt":"2021-02-25T10:00:53.541Z","createdAt":"2021-02-25T10:00:40.606Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
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
         * pet_img : ["http://54.212.108.156:3000/api/uploads/1614247250740.jpeg"]
         * _id : 60377548757a214c0415b6d3
         * user_id : 6025040ee15519672cd0dc02
         * pet_name : Deenu
         * pet_type : Dog
         * pet_breed : Golden Retrieve
         * pet_gender : Male
         * pet_color : White
         * pet_weight : 5
         * pet_age : 2
         * vaccinated : true
         * last_vaccination_date : 10-02-2021
         * default_status : true
         * date_and_time : 25-02-2021 03:30 PM
         * mobile_type : Android
         * delete_status : false
         * updatedAt : 2021-02-25T10:00:53.541Z
         * createdAt : 2021-02-25T10:00:40.606Z
         * __v : 0
         */

        private String _id;
        private String user_id;
        private String pet_name;
        private String pet_type;
        private String pet_breed;
        private String pet_gender;
        private String pet_color;
        private int pet_weight;
        private String pet_age;
        private boolean vaccinated;
        private String last_vaccination_date;
        private boolean default_status;
        private String date_and_time;
        private String mobile_type;
        private boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private List<String> pet_img;


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


        public String getPet_name() {
            return pet_name;
        }

        public void setPet_name(String pet_name) {
            this.pet_name = pet_name;

        }


        public String getPet_type() {
            return pet_type;
        }

        public void setPet_type(String pet_type) {
            this.pet_type = pet_type;

        }


        public String getPet_breed() {
            return pet_breed;
        }

        public void setPet_breed(String pet_breed) {
            this.pet_breed = pet_breed;

        }

        public String getPet_gender() {
            return pet_gender;
        }

        public void setPet_gender(String pet_gender) {
            this.pet_gender = pet_gender;

        }


        public String getPet_color() {
            return pet_color;
        }

        public void setPet_color(String pet_color) {
            this.pet_color = pet_color;

        }


        public int getPet_weight() {
            return pet_weight;
        }

        public void setPet_weight(int pet_weight) {
            this.pet_weight = pet_weight;

        }


        public String getPet_age() {
            return pet_age;
        }

        public void setPet_age(String pet_age) {
            this.pet_age = pet_age;

        }


        public boolean isVaccinated() {
            return vaccinated;
        }

        public void setVaccinated(boolean vaccinated) {
            this.vaccinated = vaccinated;

        }


        public String getLast_vaccination_date() {
            return last_vaccination_date;
        }

        public void setLast_vaccination_date(String last_vaccination_date) {
            this.last_vaccination_date = last_vaccination_date;

        }


        public boolean isDefault_status() {
            return default_status;
        }

        public void setDefault_status(boolean default_status) {
            this.default_status = default_status;

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


        public List<String> getPet_img() {
            return pet_img;
        }

        public void setPet_img(List<String> pet_img) {
            this.pet_img = pet_img;

        }
    }
}
