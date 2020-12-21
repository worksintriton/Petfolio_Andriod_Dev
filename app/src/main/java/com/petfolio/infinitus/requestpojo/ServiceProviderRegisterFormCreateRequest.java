package com.petfolio.infinitus.requestpojo;

import java.util.List;

public class ServiceProviderRegisterFormCreateRequest {


    /**
     * user_id : 5fc61b82b750da703e48da78
     * bus_user_name : mohammed Imthiyas
     * bus_user_email : mohammed@gmail.com
     * bussiness_name : Mohammed imthiyas
     * bus_user_phone : 9876543210
     * bus_service_list : [{"bus_service_list":"Service - 1"},{"bus_service_list":"Service - 2"},{"bus_service_list":"Service - 3"}]
     * bus_spec_list : [{"bus_spec_list":"Spec -1"},{"bus_spec_list":"Spec -1"},{"bus_spec_list":"Spec -1"}]
     * bus_service_gall : [{"bus_service_gall":"http://mysalveo.com/api/uploads/images.jpeg"},{"bus_service_gall":"http://mysalveo.com/api/uploads/images.jpeg"},{"bus_service_gall":"http://mysalveo.com/api/uploads/images.jpeg"}]
     * bus_profile : http://mysalveo.com/api/uploads/images.jpeg
     * bus_proof : http://mysalveo.com/api/uploads/images.jpeg
     * bus_certif : [{"bus_certif":"http://mysalveo.com/api/uploads/images.jpeg"},{"bus_certif":"http://mysalveo.com/api/uploads/images.jpeg"},{"bus_certif":"http://mysalveo.com/api/uploads/images.jpeg"}]
     * date_and_time : 23-10-2020 12:00 AM
     * mobile_type : Admin
     * profile_status : true
     * profile_verification_status : Not verified
     */

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

    private List<BusServiceListBean> bus_service_list;
    private List<BusSpecListBean> bus_spec_list;
    private List<BusServiceGallBean> bus_service_gall;
    private List<BusCertifBean> bus_certif;


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
         * bus_service_list : Service - 1
         */

        private String bus_service_list;

        public String getBus_service_list() {
            return bus_service_list;
        }

        public void setBus_service_list(String bus_service_list) {
            this.bus_service_list = bus_service_list;
        }
    }

    public static class BusSpecListBean  {
        /**
         * bus_spec_list : Spec -1
         */

        private String bus_spec_list;

        public String getBus_spec_list() {
            return bus_spec_list;
        }

        public void setBus_spec_list(String bus_spec_list) {
            this.bus_spec_list = bus_spec_list;
        }
    }

    public static class BusServiceGallBean  {
        /**
         * bus_service_gall : http://mysalveo.com/api/uploads/images.jpeg
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
         * bus_certif : http://mysalveo.com/api/uploads/images.jpeg
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
