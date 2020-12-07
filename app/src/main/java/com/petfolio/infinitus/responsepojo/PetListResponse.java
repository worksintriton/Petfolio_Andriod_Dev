package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class PetListResponse {

    /**
     * Status : Success
     * Message : pet details List
     * Data : [{"_id":"5fc8e2745a976d673ec15e2f","user_id":"5fc61b82b750da703e48da78","pet_img":"http://52.25.163.13:3000/api/uploads/5fc61b82b750da703e48da7803-12-2020 06:33 PMPetfolio1.jpg","pet_name":"Lion","pet_type":"Dog","pet_breed":"Testing - 1","pet_gender":"","pet_color":"","pet_weight":0,"pet_age":0,"vaccinated":false,"last_vaccination_date":"","default_status":true,"date_and_time":"03-12-2020 06:34 PM","mobile_type":"Android","__v":0},{"_id":"5fcdb5dcb1206b3e8f659b15","user_id":"5fc61b82b750da703e48da78","pet_img":"http://52.25.163.13:3000/api/uploads/logo-png.png","pet_name":"test-1","pet_type":"Dog","pet_breed":"Testing - 1","pet_gender":"Male","pet_color":"red","pet_weight":2,"pet_age":1,"vaccinated":true,"last_vaccination_date":"Tue Dec 01 2020 00:00:00 GMT+0530 (India Standard Time)","default_status":true,"date_and_time":"Mon Dec 07 2020 10:26:03 GMT+0530 (India Standard Time)","mobile_type":"Admin","__v":0},{"_id":"5fcdb6e7b1206b3e8f659b16","user_id":"5fc61b82b750da703e48da78","pet_img":"http://52.25.163.13:3000/api/uploads/logo-png.png","pet_name":"test-2","pet_type":"Dog","pet_breed":"Testing - 1","pet_gender":"Male","pet_color":"blue","pet_weight":5,"pet_age":1,"vaccinated":true,"last_vaccination_date":"Tue Dec 01 2020 00:00:00 GMT+0530 (India Standard Time)","default_status":true,"date_and_time":"Mon Dec 07 2020 10:30:30 GMT+0530 (India Standard Time)","mobile_type":"Admin","__v":0}]
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

    public static class DataBean {
        /**
         * _id : 5fc8e2745a976d673ec15e2f
         * user_id : 5fc61b82b750da703e48da78
         * pet_img : http://52.25.163.13:3000/api/uploads/5fc61b82b750da703e48da7803-12-2020 06:33 PMPetfolio1.jpg
         * pet_name : Lion
         * pet_type : Dog
         * pet_breed : Testing - 1
         * pet_gender :
         * pet_color :
         * pet_weight : 0
         * pet_age : 0
         * vaccinated : false
         * last_vaccination_date :
         * default_status : true
         * date_and_time : 03-12-2020 06:34 PM
         * mobile_type : Android
         * __v : 0
         */

        private String _id;
        private String user_id;
        private String pet_img;
        private String pet_name;
        private String pet_type;
        private String pet_breed;
        private String pet_gender;
        private String pet_color;
        private int pet_weight;
        private int pet_age;
        private boolean vaccinated;
        private String last_vaccination_date;
        private boolean default_status;
        private String date_and_time;
        private String mobile_type;
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


        public String getPet_img() {
            return pet_img;
        }

        public void setPet_img(String pet_img) {
            this.pet_img = pet_img;

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


        public int getPet_age() {
            return pet_age;
        }

        public void setPet_age(int pet_age) {
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

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}
