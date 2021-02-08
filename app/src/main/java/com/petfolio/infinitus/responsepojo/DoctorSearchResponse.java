package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class DoctorSearchResponse {


    /**
     * Status : Success
     * Message : Vehicledetails
     * Data : [{"_id":"5fd2f81cd380f60fd0525642","user_id":"5fd2f7c3d380f60fd0525641","dr_title":"Dr","doctor_name":"Vignesh","clinic_name":"cli","specialization":[{"specialization":"Testing - 1"}],"doctor_img":"http://52.25.163.13:3000/api/uploads/Slide1.jpg","clinic_loc":"loc","distance":2,"star_count":2.5,"review_count":234},{"_id":"5fd2fe531978e618628c9667","user_id":"5fd2fdceb8f10b159576fe93","dr_title":"Dr","doctor_name":"DINESH","clinic_name":"APOLLO HOSPITAL","specialization":[{"specialization":"special - 3"}],"doctor_img":"http://52.25.163.13:3000/api/uploads/5fd2fdceb8f10b159576fe9311-12-2020 10:34 AMPetfolio1.jpg","clinic_loc":"4/3 Marriyamman Kovil street","distance":2,"star_count":2.5,"review_count":234},{"_id":"5fd36a7d721c403653e67154","user_id":"5fd369fd721c403653e67153","dr_title":"Dr","doctor_name":"Imthi","clinic_name":"APOLLO HOSPITAL","specialization":[{"specialization":"special - 3"}],"doctor_img":"http://52.25.163.13:3000/api/uploads/5fd369fd721c403653e6715311-12-2020 06:16 PMPetfolio1.jpg","clinic_loc":"Chennai","communication_type":"Online","distance":2,"star_count":2.5,"review_count":234},{"_id":"5fd6f61f824d74099c542b5c","user_id":"5fd6f476824d74099c542b5b","dr_title":"Dr","doctor_name":"ishaaq","clinic_name":"ishaaq test","specialization":[{"specialization":"Testing - 1"},{"specialization":"special - 3"}],"doctor_img":"http://52.25.163.13:3000/api/uploads/5fd6f476824d74099c542b5b14-12-2020 10:45 AMFB_IMG_1607537784105.jpg","clinic_loc":"Perambur","communication_type":"Online Or Visit","distance":2,"star_count":2.5,"review_count":234}]
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
         * _id : 5fd2f81cd380f60fd0525642
         * user_id : 5fd2f7c3d380f60fd0525641
         * dr_title : Dr
         * doctor_name : Vignesh
         * clinic_name : cli
         * specialization : [{"specialization":"Testing - 1"}]
         * doctor_img : http://52.25.163.13:3000/api/uploads/Slide1.jpg
         * clinic_loc : loc
         * distance : 2
         * star_count : 2.5
         * review_count : 234
         * communication_type : Online
         *
         */

        private String _id;
        private String user_id;
        private String dr_title;
        private String doctor_name;
        private String clinic_name;
        private String doctor_img;
        private String clinic_loc;
        private String distance;
        private double star_count;
        private int review_count;
        private int amount;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        private String communication_type;
        private List<SpecializationBean> specialization;

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


        public String getDr_title() {
            return dr_title;
        }

        public void setDr_title(String dr_title) {
            this.dr_title = dr_title;

        }

        public String getDoctor_name() {
            return doctor_name;
        }

        public void setDoctor_name(String doctor_name) {
            this.doctor_name = doctor_name;
        }


        public String getClinic_name() {
            return clinic_name;
        }

        public void setClinic_name(String clinic_name) {
            this.clinic_name = clinic_name;

        }


        public String getDoctor_img() {
            return doctor_img;
        }

        public void setDoctor_img(String doctor_img) {
            this.doctor_img = doctor_img;
        }


        public String getClinic_loc() {
            return clinic_loc;
        }

        public void setClinic_loc(String clinic_loc) {
            this.clinic_loc = clinic_loc;
        }


        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;

        }


        public double getStar_count() {
            return star_count;
        }

        public void setStar_count(double star_count) {
            this.star_count = star_count;

        }


        public int getReview_count() {
            return review_count;
        }

        public void setReview_count(int review_count) {
            this.review_count = review_count;
        }

        public String getCommunication_type() {
            return communication_type;
        }

        public void setCommunication_type(String communication_type) {
            this.communication_type = communication_type;
        }

        public List<SpecializationBean> getSpecialization() {
            return specialization;
        }

        public void setSpecialization(List<SpecializationBean> specialization) {
            this.specialization = specialization;
        }

        public static class SpecializationBean  {
            /**
             * specialization : Testing - 1
             */

            private String specialization;

            public String getSpecialization() {
                return specialization;
            }

            public void setSpecialization(String specialization) {
                this.specialization = specialization;
            }
        }
    }
}