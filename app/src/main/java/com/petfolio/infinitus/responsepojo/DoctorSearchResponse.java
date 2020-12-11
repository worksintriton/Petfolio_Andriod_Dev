package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class DoctorSearchResponse {

    /**
     * Status : Success
     * Message : Vehicledetails
     * Data : [{"education_details":[{"education":"BE","year":"2015"}],"experience_details":[{"company":"triton","from":"2020-11-30T18:30:00.000Z","to":"2020-12-11T18:30:00.000Z"}],"specialization":[{"specialization":"Testing - 1"}],"pet_handled":[{"pet_handled":"handled"}],"clinic_pic":[{"clinic_pic":"http://52.25.163.13:3000/api/uploads/Slide1.jpg"}],"certificate_pic":[{"certificate_pic":"http://52.25.163.13:3000/api/uploads/0c3b3adb1a7530892e55ef36d3be6cb8.png"}],"govt_id_pic":[{"govt_id_pic":"http://52.25.163.13:3000/api/uploads/0c3b3adb1a7530892e55ef36d3be6cb8.png"}],"photo_id_pic":[{"photo_id_pic":"http://52.25.163.13:3000/api/uploads/0c3b3adb1a7530892e55ef36d3be6cb8.png"}],"_id":"5fd2f81cd380f60fd0525642","user_id":"5fd2f7c3d380f60fd0525641","dr_title":"Dr","dr_name":"Vignesh","clinic_name":"cli","clinic_loc":"loc","clinic_lat":12.0252,"clinic_long":80.25588,"profile_status":false,"profile_verification_status":"Not verified","date_and_time":"Fri Dec 11 2020 10:18:04 GMT+0530 (India Standard Time)","delete_status":false,"__v":0}]
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
         * education_details : [{"education":"BE","year":"2015"}]
         * experience_details : [{"company":"triton","from":"2020-11-30T18:30:00.000Z","to":"2020-12-11T18:30:00.000Z"}]
         * specialization : [{"specialization":"Testing - 1"}]
         * pet_handled : [{"pet_handled":"handled"}]
         * clinic_pic : [{"clinic_pic":"http://52.25.163.13:3000/api/uploads/Slide1.jpg"}]
         * certificate_pic : [{"certificate_pic":"http://52.25.163.13:3000/api/uploads/0c3b3adb1a7530892e55ef36d3be6cb8.png"}]
         * govt_id_pic : [{"govt_id_pic":"http://52.25.163.13:3000/api/uploads/0c3b3adb1a7530892e55ef36d3be6cb8.png"}]
         * photo_id_pic : [{"photo_id_pic":"http://52.25.163.13:3000/api/uploads/0c3b3adb1a7530892e55ef36d3be6cb8.png"}]
         * _id : 5fd2f81cd380f60fd0525642
         * user_id : 5fd2f7c3d380f60fd0525641
         * dr_title : Dr
         * dr_name : Vignesh
         * clinic_name : cli
         * clinic_loc : loc
         * clinic_lat : 12.0252
         * clinic_long : 80.25588
         * profile_status : false
         * profile_verification_status : Not verified
         * date_and_time : Fri Dec 11 2020 10:18:04 GMT+0530 (India Standard Time)
         * delete_status : false
         * __v : 0
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
        private boolean delete_status;
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


        public boolean isDelete_status() {
            return delete_status;
        }

        public void setDelete_status(boolean delete_status) {
            this.delete_status = delete_status;
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
             * education : BE
             * year : 2015
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

        public static class ExperienceDetailsBean {
            /**
             * company : triton
             * from : 2020-11-30T18:30:00.000Z
             * to : 2020-12-11T18:30:00.000Z
             */

            private String company;
            private String from;
            private String to;


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

        public static class SpecializationBean {
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

        public static class PetHandledBean  {
            /**
             * pet_handled : handled
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
             * clinic_pic : http://52.25.163.13:3000/api/uploads/Slide1.jpg
             */

            private String clinic_pic;


            public String getClinic_pic() {
                return clinic_pic;
            }

            public void setClinic_pic(String clinic_pic) {
                this.clinic_pic = clinic_pic;

            }
        }

        public static class CertificatePicBean {
            /**
             * certificate_pic : http://52.25.163.13:3000/api/uploads/0c3b3adb1a7530892e55ef36d3be6cb8.png
             */

            private String certificate_pic;


            public String getCertificate_pic() {
                return certificate_pic;
            }

            public void setCertificate_pic(String certificate_pic) {
                this.certificate_pic = certificate_pic;
            }
        }

        public static class GovtIdPicBean{
            /**
             * govt_id_pic : http://52.25.163.13:3000/api/uploads/0c3b3adb1a7530892e55ef36d3be6cb8.png
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
             * photo_id_pic : http://52.25.163.13:3000/api/uploads/0c3b3adb1a7530892e55ef36d3be6cb8.png
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
