package com.petfolio.infinitus.responsepojo;

public class PetAddImageResponse {

    /**
     * Status : Success
     * Message : pet details Updated
     * Data : {"_id":"5fce1ae2928d5f5634501b2d","user_id":"5fb36ca169f71e30a0ffd3f7","pet_img":"","pet_name":"POP","pet_type":"Dog","pet_breed":"breed 1","pet_gender":"Male","pet_color":"white","pet_weight":120,"pet_age":20,"vaccinated":true,"last_vaccination_date":"23-10-1996","default_status":true,"date_and_time":"23-10-1996 12:09 AM","__v":0}
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

    public static class DataBean  {
        /**
         * _id : 5fce1ae2928d5f5634501b2d
         * user_id : 5fb36ca169f71e30a0ffd3f7
         * pet_img :
         * pet_name : POP
         * pet_type : Dog
         * pet_breed : breed 1
         * pet_gender : Male
         * pet_color : white
         * pet_weight : 120
         * pet_age : 20
         * vaccinated : true
         * last_vaccination_date : 23-10-1996
         * default_status : true
         * date_and_time : 23-10-1996 12:09 AM
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

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}
