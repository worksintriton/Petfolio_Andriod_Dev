package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class DoctorDetailsByUserIdResponse {

    /**
     * Status : Success
     * Message : Docotor Details
     * Data : {"education_details":[{"education":"MBBS","year":"2014"}],"experience_details":[{"company":"MBBS","from":"2014","to":"2018"}],"specialization":[{"specialization":"Surgeon"},{"specialization":"Internal Medicine Physician"},{"specialization":"Psychiatrist"}],"pet_handled":[{"pet_handled":"Dog"},{"pet_handled":"Cow"}],"clinic_pic":[{"clinic_pic":"http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMPetfolio1.jpg"}],"certificate_pic":[{"certificate_pic":"http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMfaq.pdf"}],"govt_id_pic":[{"govt_id_pic":"http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMfaq.pdf"}],"photo_id_pic":[{"photo_id_pic":"http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMfaq.pdf"}],"_id":"5fec589a9c25086a03d72d3b","user_id":"5fec55a29c25086a03d72d38","dr_title":"Dr","dr_name":"DINESHKUMAR","clinic_name":"APOLLO HOSPITALS","clinic_loc":"4/3 Marriyamman Kovil street, Iluppaiyur.","clinic_lat":11.055839499999998,"clinic_long":78.63248159999999,"profile_status":true,"profile_verification_status":"Verified","date_and_time":"30/12/2020 04:08:17","mobile_type":"Android","communication_type":"Online Or Visit","delete_status":false,"consultancy_fees":200,"calender_status":true,"updatedAt":"2020-12-30T10:45:24.521Z","createdAt":"2020-12-30T10:38:18.498Z","__v":0}
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
         * education_details : [{"education":"MBBS","year":"2014"}]
         * experience_details : [{"company":"MBBS","from":"2014","to":"2018"}]
         * specialization : [{"specialization":"Surgeon"},{"specialization":"Internal Medicine Physician"},{"specialization":"Psychiatrist"}]
         * pet_handled : [{"pet_handled":"Dog"},{"pet_handled":"Cow"}]
         * clinic_pic : [{"clinic_pic":"http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMPetfolio1.jpg"}]
         * certificate_pic : [{"certificate_pic":"http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMfaq.pdf"}]
         * govt_id_pic : [{"govt_id_pic":"http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMfaq.pdf"}]
         * photo_id_pic : [{"photo_id_pic":"http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMfaq.pdf"}]
         * _id : 5fec589a9c25086a03d72d3b
         * user_id : 5fec55a29c25086a03d72d38
         * dr_title : Dr
         * dr_name : DINESHKUMAR
         * clinic_name : APOLLO HOSPITALS
         * clinic_loc : 4/3 Marriyamman Kovil street, Iluppaiyur.
         * clinic_lat : 11.055839499999998
         * clinic_long : 78.63248159999999
         * profile_status : true
         * profile_verification_status : Verified
         * date_and_time : 30/12/2020 04:08:17
         * mobile_type : Android
         * communication_type : Online Or Visit
         * delete_status : false
         * consultancy_fees : 200
         * calender_status : true
         * updatedAt : 2020-12-30T10:45:24.521Z
         * createdAt : 2020-12-30T10:38:18.498Z
         * __v : 0
         *  profile_img;
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
        private String mobile_type;
        private String communication_type;
        private boolean delete_status;
        private int consultancy_fees;
        private boolean calender_status;
        private String updatedAt;
        private String createdAt;
        private int __v;


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


        public String getMobile_type() {
            return mobile_type;
        }

        public void setMobile_type(String mobile_type) {
            this.mobile_type = mobile_type;

        }


        public String getCommunication_type() {
            return communication_type;
        }

        public void setCommunication_type(String communication_type) {
            this.communication_type = communication_type;

        }


        public boolean isDelete_status() {
            return delete_status;
        }

        public void setDelete_status(boolean delete_status) {
            this.delete_status = delete_status;

        }


        public int getConsultancy_fees() {
            return consultancy_fees;
        }

        public void setConsultancy_fees(int consultancy_fees) {
            this.consultancy_fees = consultancy_fees;

        }


        public boolean isCalender_status() {
            return calender_status;
        }

        public void setCalender_status(boolean calender_status) {
            this.calender_status = calender_status;

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

        public static class EducationDetailsBean {
            /**
             * education : MBBS
             * year : 2014
             */

            private String education;
            private String year;


            public String getEducation() {
                return education;
            }

            public void setEducation(String education) {
                this.education = education;

            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;

            }
        }

        public static class ExperienceDetailsBean  {
            /**
             * company : MBBS
             * from : 2014
             * to : 2018
             * yearsofexperience
             */

            private String company;
            private String from;
            private String to;
            private int yearsofexperience;

            public int getYearsofexperience() {
                return yearsofexperience;
            }

            public void setYearsofexperience(int yearsofexperience) {
                this.yearsofexperience = yearsofexperience;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;

            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;

            }


            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;

            }
        }

        public static class SpecializationBean  {
            /**
             * specialization : Surgeon
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
             * pet_handled : Dog
             */

            private String pet_handled;


            public String getPet_handled() {
                return pet_handled;
            }

            public void setPet_handled(String pet_handled) {
                this.pet_handled = pet_handled;

            }
        }

        public static class ClinicPicBean  {
            /**
             * clinic_pic : http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMPetfolio1.jpg
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
             * certificate_pic : http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMfaq.pdf
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
             * govt_id_pic : http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMfaq.pdf
             */

            private String govt_id_pic;

            public String getGovt_id_pic() {
                return govt_id_pic;
            }

            public void setGovt_id_pic(String govt_id_pic) {
                this.govt_id_pic = govt_id_pic;

            }
        }

        public static class PhotoIdPicBean{
            /**
             * photo_id_pic : http://52.25.163.13:3000/api/uploads/5fec55a29c25086a03d72d3830-12-2020 04:04 PMfaq.pdf
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
