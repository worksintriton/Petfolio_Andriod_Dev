package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class SPDetailsRepsonse {

    /**
     * Status : Success
     * Message : SP Details
     * Data : {"bus_service_list":[{"bus_service_list":"SP - 1"}],"bus_spec_list":[{"bus_spec_list":"Specialization - 1"}],"bus_service_gall":[{"bus_service_gall":"http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PMPetfolio1.jpg"}],"bus_certif":[{"bus_certif":"http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PM1606464394712_4893XXXXXXXXXX79_13-11-2020.PDF"}],"_id":"5fe1e6c7094d0471dabf9296","user_id":"5fe1e675094d0471dabf9295","bus_user_name":"Dk","bus_user_email":"iddineshkumar@gmail.com","bussiness_name":"Gromming","bus_user_phone":"9842670816","bus_profile":"http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PM1606464394712_4893XXXXXXXXXX79_13-11-2020.PDF","bus_proof":"http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PM1606464394712_4893XXXXXXXXXX79_13-11-2020.PDF","date_and_time":"23/12/2020 07:22 PM","mobile_type":"Android","profile_status":true,"profile_verification_status":"Verified","sp_loc":"Unnamed Road, Tamil Nadu 621006, India","sp_lat":11.0557284,"sp_long":78.6326037,"delete_status":false,"updatedAt":"2020-12-24T04:17:10.260Z","createdAt":"2020-12-22T12:29:59.324Z","__v":0,"distance":0,"rating":0,"comments":0}
     * Details : {"image_path":"SP - 6","title":"http://mysalveo.com/api/uploads/images.jpeg","count":0}
     * Code : 200
     */

    private String Status;
    private String Message;
    private DataBean Data;
    private DetailsBean Details;
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


    public DetailsBean getDetails() {
        return Details;
    }

    public void setDetails(DetailsBean Details) {
        this.Details = Details;

    }


    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;

    }

    public static class DataBean  {
        /**
         * bus_service_list : [{"bus_service_list":"SP - 1"}]
         * bus_spec_list : [{"bus_spec_list":"Specialization - 1"}]
         * bus_service_gall : [{"bus_service_gall":"http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PMPetfolio1.jpg"}]
         * bus_certif : [{"bus_certif":"http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PM1606464394712_4893XXXXXXXXXX79_13-11-2020.PDF"}]
         * _id : 5fe1e6c7094d0471dabf9296
         * user_id : 5fe1e675094d0471dabf9295
         * bus_user_name : Dk
         * bus_user_email : iddineshkumar@gmail.com
         * bussiness_name : Gromming
         * bus_user_phone : 9842670816
         * bus_profile : http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PM1606464394712_4893XXXXXXXXXX79_13-11-2020.PDF
         * bus_proof : http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PM1606464394712_4893XXXXXXXXXX79_13-11-2020.PDF
         * date_and_time : 23/12/2020 07:22 PM
         * mobile_type : Android
         * profile_status : true
         * profile_verification_status : Verified
         * sp_loc : Unnamed Road, Tamil Nadu 621006, India
         * sp_lat : 11.0557284
         * sp_long : 78.6326037
         * delete_status : false
         * updatedAt : 2020-12-24T04:17:10.260Z
         * createdAt : 2020-12-22T12:29:59.324Z
         * __v : 0
         * distance : 0
         * rating : 0
         * comments : 0
         */

        private String _id;
        private String user_id;
        private String bus_user_name;
        private String bus_user_email;
        private String bussiness_name;
        private String bus_user_phone;
        private String bus_profile;
        private String bus_proof;
        private String date_and_time;
        private String mobile_type;
        private boolean profile_status;
        private String profile_verification_status;
        private String sp_loc;
        private double sp_lat;
        private double sp_long;
        private boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private int distance;
        private int rating;
        private int comments;
        private List<BusServiceListBean> bus_service_list;
        private List<BusSpecListBean> bus_spec_list;
        private List<BusServiceGallBean> bus_service_gall;
        private List<BusCertifBean> bus_certif;


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


        public String getBus_user_name() {
            return bus_user_name;
        }

        public void setBus_user_name(String bus_user_name) {
            this.bus_user_name = bus_user_name;

        }


        public String getBus_user_email() {
            return bus_user_email;
        }

        public void setBus_user_email(String bus_user_email) {
            this.bus_user_email = bus_user_email;

        }


        public String getBussiness_name() {
            return bussiness_name;
        }

        public void setBussiness_name(String bussiness_name) {
            this.bussiness_name = bussiness_name;

        }


        public String getBus_user_phone() {
            return bus_user_phone;
        }

        public void setBus_user_phone(String bus_user_phone) {
            this.bus_user_phone = bus_user_phone;

        }


        public String getBus_profile() {
            return bus_profile;
        }

        public void setBus_profile(String bus_profile) {
            this.bus_profile = bus_profile;

        }


        public String getBus_proof() {
            return bus_proof;
        }

        public void setBus_proof(String bus_proof) {
            this.bus_proof = bus_proof;
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


        public String getSp_loc() {
            return sp_loc;
        }

        public void setSp_loc(String sp_loc) {
            this.sp_loc = sp_loc;

        }


        public double getSp_lat() {
            return sp_lat;
        }

        public void setSp_lat(double sp_lat) {
            this.sp_lat = sp_lat;

        }


        public double getSp_long() {
            return sp_long;
        }

        public void setSp_long(double sp_long) {
            this.sp_long = sp_long;

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

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }


        public List<BusServiceListBean> getBus_service_list() {
            return bus_service_list;
        }

        public void setBus_service_list(List<BusServiceListBean> bus_service_list) {
            this.bus_service_list = bus_service_list;

        }


        public List<BusSpecListBean> getBus_spec_list() {
            return bus_spec_list;
        }

        public void setBus_spec_list(List<BusSpecListBean> bus_spec_list) {
            this.bus_spec_list = bus_spec_list;

        }


        public List<BusServiceGallBean> getBus_service_gall() {
            return bus_service_gall;
        }

        public void setBus_service_gall(List<BusServiceGallBean> bus_service_gall) {
            this.bus_service_gall = bus_service_gall;

        }


        public List<BusCertifBean> getBus_certif() {
            return bus_certif;
        }

        public void setBus_certif(List<BusCertifBean> bus_certif) {
            this.bus_certif = bus_certif;

        }

        public static class BusServiceListBean  {
            /**
             * bus_service_list : SP - 1
             */

            private String bus_service_list;


            public String getBus_service_list() {
                return bus_service_list;
            }

            public void setBus_service_list(String bus_service_list) {
                this.bus_service_list = bus_service_list;
            }
        }

        public static class BusSpecListBean {
            /**
             * bus_spec_list : Specialization - 1
             */

            private String bus_spec_list;


            public String getBus_spec_list() {
                return bus_spec_list;
            }

            public void setBus_spec_list(String bus_spec_list) {
                this.bus_spec_list = bus_spec_list;

            }
        }

        public static class BusServiceGallBean {
            /**
             * bus_service_gall : http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PMPetfolio1.jpg
             */

            private String bus_service_gall;


            public String getBus_service_gall() {
                return bus_service_gall;
            }

            public void setBus_service_gall(String bus_service_gall) {
                this.bus_service_gall = bus_service_gall;
            }
        }

        public static class BusCertifBean  {
            /**
             * bus_certif : http://52.25.163.13:3000/api/uploads/5fe1e675094d0471dabf929522-12-2020 05:58 PM1606464394712_4893XXXXXXXXXX79_13-11-2020.PDF
             */

            private String bus_certif;


            public String getBus_certif() {
                return bus_certif;
            }

            public void setBus_certif(String bus_certif) {
                this.bus_certif = bus_certif;

            }
        }
    }

    public static class DetailsBean  {
        /**
         * image_path : SP - 6
         * title : http://mysalveo.com/api/uploads/images.jpeg
         * count : 0
         *"amount": 200,
         * "time": "15 mins"
         */

        private String image_path;
        private String title;
        private int count;
        private int amount;
        private String time;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;

        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;

        }


        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;

        }
    }
}
