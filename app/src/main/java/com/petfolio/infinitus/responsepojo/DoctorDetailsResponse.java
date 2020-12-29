package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class DoctorDetailsResponse {


    /**
     * Status : Success
     * Message : Docotor Details
     * Data : {"_id":"5fc0ac986c8c864614276c42","user_id":"5fc0a14d96ce26431e9f3a7d","dr_title":"","dr_name":"","clinic_name":"sri","clinic_loc":"My address","clinic_lat":12.9675,"clinic_long":80.1491,"education_details":[{"year":"2020","education":"edu"}],"experience_details":[{"company":"comp","to":"2020","from":"2020"}],"specialization":[{"specialization":"specialzation - 1"},{"specialization":"specialzation - 3"}],"pet_handled":[{"pet_handled":"pet_handle - 1"},{"pet_handled":"pet_handle - 2"}],"clinic_pic":[{"clinic_pic":"http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7d2711130630"}],"certificate_pic":[{"certificate_pic":"http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7dcertificate.pdf"}],"govt_id_pic":[{"govt_id_pic":"http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7dcertificate.pdf"}],"photo_id_pic":[{"photo_id_pic":"http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7dcertificate.pdf"}],"profile_status":false,"profile_verification_status":"Not verified","date_and_time":"27/11/2020 01:06 PM","descri":"A class of medical instruction in which patients are examined and discussed. 2 : a group meeting devoted to the analysis and solution of concrete problems or to the acquiring of specific skills or knowledge writing clinics golf clinic","star_count":4,"review_count":223}
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
         * _id : 5fc0ac986c8c864614276c42
         * user_id : 5fc0a14d96ce26431e9f3a7d
         * dr_title :
         * dr_name :
         * clinic_name : sri
         * clinic_loc : My address
         * clinic_lat : 12.9675
         * clinic_long : 80.1491
         * education_details : [{"year":"2020","education":"edu"}]
         * experience_details : [{"company":"comp","to":"2020","from":"2020"}]
         * specialization : [{"specialization":"specialzation - 1"},{"specialization":"specialzation - 3"}]
         * pet_handled : [{"pet_handled":"pet_handle - 1"},{"pet_handled":"pet_handle - 2"}]
         * clinic_pic : [{"clinic_pic":"http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7d2711130630"}]
         * certificate_pic : [{"certificate_pic":"http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7dcertificate.pdf"}]
         * govt_id_pic : [{"govt_id_pic":"http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7dcertificate.pdf"}]
         * photo_id_pic : [{"photo_id_pic":"http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7dcertificate.pdf"}]
         * profile_status : false
         * profile_verification_status : Not verified
         * date_and_time : 27/11/2020 01:06 PM
         * descri : A class of medical instruction in which patients are examined and discussed. 2 : a group meeting devoted to the analysis and solution of concrete problems or to the acquiring of specific skills or knowledge writing clinics golf clinic
         * star_count : 4
         * review_count : 223
         */

        private String _id;
        private String user_id;
        private String dr_title;
        private String dr_name;
        private String clinic_name;
        private String clinic_loc;
        private double clinic_lat;
        private double clinic_long;
        private boolean profile_status;
        private String profile_verification_status;
        private String date_and_time;
        private String descri;
        private int star_count;
        private String distance;

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        private int review_count;
        private int amount;
        private String communication_type;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getCommunication_type() {
            return communication_type;
        }

        public void setCommunication_type(String communication_type) {
            this.communication_type = communication_type;
        }

        private List<EducationDetailsBean> education_details;
        private List<ExperienceDetailsBean> experience_details;
        private List<SpecializationBean> specialization;
        private List<PetHandledBean> pet_handled;
        private List<ClinicPicBean> clinic_pic;
        private List<CertificatePicBean> certificate_pic;
        private List<GovtIdPicBean> govt_id_pic;
        private List<PhotoIdPicBean> photo_id_pic;


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


        public String getDr_name() {
            return dr_name;
        }

        public void setDr_name(String dr_name) {
            this.dr_name = dr_name;
        }


        public String getClinic_name() {
            return clinic_name;
        }

        public void setClinic_name(String clinic_name) {
            this.clinic_name = clinic_name;

        }

        public String getClinic_loc() {
            return clinic_loc;
        }

        public void setClinic_loc(String clinic_loc) {
            this.clinic_loc = clinic_loc;
        }


        public double getClinic_lat() {
            return clinic_lat;
        }

        public void setClinic_lat(double clinic_lat) {
            this.clinic_lat = clinic_lat;
        }


        public double getClinic_long() {
            return clinic_long;
        }

        public void setClinic_long(double clinic_long) {
            this.clinic_long = clinic_long;

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


        public String getDate_and_time() {
            return date_and_time;
        }

        public void setDate_and_time(String date_and_time) {
            this.date_and_time = date_and_time;

        }


        public String getDescri() {
            return descri;
        }

        public void setDescri(String descri) {
            this.descri = descri;

        }


        public int getStar_count() {
            return star_count;
        }

        public void setStar_count(int star_count) {
            this.star_count = star_count;

        }


        public int getReview_count() {
            return review_count;
        }

        public void setReview_count(int review_count) {
            this.review_count = review_count;

        }


        public List<EducationDetailsBean> getEducation_details() {
            return education_details;
        }

        public void setEducation_details(List<EducationDetailsBean> education_details) {
            this.education_details = education_details;
        }


        public List<ExperienceDetailsBean> getExperience_details() {
            return experience_details;
        }

        public void setExperience_details(List<ExperienceDetailsBean> experience_details) {
            this.experience_details = experience_details;

        }


        public List<SpecializationBean> getSpecialization() {
            return specialization;
        }

        public void setSpecialization(List<SpecializationBean> specialization) {
            this.specialization = specialization;
        }


        public List<PetHandledBean> getPet_handled() {
            return pet_handled;
        }

        public void setPet_handled(List<PetHandledBean> pet_handled) {
            this.pet_handled = pet_handled;

        }


        public List<ClinicPicBean> getClinic_pic() {
            return clinic_pic;
        }

        public void setClinic_pic(List<ClinicPicBean> clinic_pic) {
            this.clinic_pic = clinic_pic;

        }


        public List<CertificatePicBean> getCertificate_pic() {
            return certificate_pic;
        }

        public void setCertificate_pic(List<CertificatePicBean> certificate_pic) {
            this.certificate_pic = certificate_pic;

        }


        public List<GovtIdPicBean> getGovt_id_pic() {
            return govt_id_pic;
        }

        public void setGovt_id_pic(List<GovtIdPicBean> govt_id_pic) {
            this.govt_id_pic = govt_id_pic;
        }


        public List<PhotoIdPicBean> getPhoto_id_pic() {
            return photo_id_pic;
        }

        public void setPhoto_id_pic(List<PhotoIdPicBean> photo_id_pic) {
            this.photo_id_pic = photo_id_pic;

        }

        public static class EducationDetailsBean  {
            /**
             * year : 2020
             * education : edu
             */

            private String year;
            private String education;


            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;

            }


            public String getEducation() {
                return education;
            }

            public void setEducation(String education) {
                this.education = education;

            }
        }

        public static class ExperienceDetailsBean  {
            /**
             * company : comp
             * to : 2020
             * from : 2020
             */

            private String company;
            private String to;
            private String from;


            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;

            }


            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;

            }


            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }
        }

        public static class SpecializationBean {
            /**
             * specialization : specialzation - 1
             */

            private String specialization;


            public String getSpecialization() {
                return specialization;
            }

            public void setSpecialization(String specialization) {
                this.specialization = specialization;

            }
        }

        public static class PetHandledBean  {
            /**
             * pet_handled : pet_handle - 1
             */

            private String pet_handled;


            public String getPet_handled() {
                return pet_handled;
            }

            public void setPet_handled(String pet_handled) {
                this.pet_handled = pet_handled;

            }
        }

        public static class ClinicPicBean {
            /**
             * clinic_pic : http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7d2711130630
             */

            private String clinic_pic;


            public String getClinic_pic() {
                return clinic_pic;
            }

            public void setClinic_pic(String clinic_pic) {
                this.clinic_pic = clinic_pic;

            }
        }

        public static class CertificatePicBean  {
            /**
             * certificate_pic : http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7dcertificate.pdf
             */

            private String certificate_pic;


            public String getCertificate_pic() {
                return certificate_pic;
            }

            public void setCertificate_pic(String certificate_pic) {
                this.certificate_pic = certificate_pic;

            }
        }

        public static class GovtIdPicBean  {
            /**
             * govt_id_pic : http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7dcertificate.pdf
             */

            private String govt_id_pic;


            public String getGovt_id_pic() {
                return govt_id_pic;
            }

            public void setGovt_id_pic(String govt_id_pic) {
                this.govt_id_pic = govt_id_pic;

            }
        }

        public static class PhotoIdPicBean  {
            /**
             * photo_id_pic : http://52.25.163.13:3000/api/uploads/5fc0a14d96ce26431e9f3a7dcertificate.pdf
             */

            private String photo_id_pic;


            public String getPhoto_id_pic() {
                return photo_id_pic;
            }

            public void setPhoto_id_pic(String photo_id_pic) {
                this.photo_id_pic = photo_id_pic;

            }
        }
    }
}
