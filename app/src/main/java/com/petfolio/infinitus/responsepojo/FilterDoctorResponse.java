package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class FilterDoctorResponse {

    /**
     * Status : Success
     * Message : Filtered Doctor List
     * Data : [{"_id":"5fd8409e7aa4cc1c6a1e5635","user_id":"5fd8400d7aa4cc1c6a1e5634","dr_title":"Dr","doctor_name":"Dinesh","clinic_name":"Apollo pharmacy","specialization":[{"specialization":"special - 3"}],"doctor_img":"http://52.25.163.13:3000/api/uploads/5fd8400d7aa4cc1c6a1e563415-12-2020 10:18 AMPetfolio1.jpg","clinic_loc":"4/3 Main road, Chennai","communication_type":"Online Or Visit","distance":2,"star_count":2.5,"review_count":234}]
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
         * _id : 5fd8409e7aa4cc1c6a1e5635
         * user_id : 5fd8400d7aa4cc1c6a1e5634
         * dr_title : Dr
         * doctor_name : Dinesh
         * clinic_name : Apollo pharmacy
         * specialization : [{"specialization":"special - 3"}]
         * doctor_img : http://52.25.163.13:3000/api/uploads/5fd8400d7aa4cc1c6a1e563415-12-2020 10:18 AMPetfolio1.jpg
         * clinic_loc : 4/3 Main road, Chennai
         * communication_type : Online Or Visit
         * distance : 2
         * star_count : 2.5
         * review_count : 234
         */

        private String _id;
        private String user_id;
        private String dr_title;
        private String doctor_name;
        private String clinic_name;
        private String doctor_img;
        private String clinic_loc;
        private String communication_type;
        private int distance;
        private double star_count;
        private int review_count;
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


        public String getCommunication_type() {
            return communication_type;
        }

        public void setCommunication_type(String communication_type) {
            this.communication_type = communication_type;

        }


        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
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


        public List<SpecializationBean> getSpecialization() {
            return specialization;
        }

        public void setSpecialization(List<SpecializationBean> specialization) {
            this.specialization = specialization;

        }

        public static class SpecializationBean  {
            /**
             * specialization : special - 3
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
