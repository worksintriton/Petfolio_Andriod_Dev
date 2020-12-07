package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class PetLoverDashboardResponse {


    /**
     * Status : Success
     * Message : Pet Lover Dashboard Details
     * Data : {"SOS":[],"LocationDetails":[],"PetDetails":[],"userdetails":{"_id":"5fb63307b223363ad0039b0e","first_name":"Dk","last_name":"dj","user_email":"iddinesh@gmail.com","user_phone":"6383791451","date_of_reg":"19/11/2020 02:25 PM","user_type":1,"user_status":"Incomplete","otp":790294,"fb_token":"","device_id":"","device_type":"","__v":0},"Dashboarddata":{"Banner_details":[{"_id":"1231231","title":"banner - 1","img_path":"http://mysalveo.com/api/uploads/images.jpeg"},{"_id":"1231231","title":"banner - 2","img_path":"http://mysalveo.com/api/uploads/images.jpeg"},{"_id":"1231231","title":"banner - 3","img_path":"http://mysalveo.com/api/uploads/images.jpeg"}],"Doctor_details":[{"_id":"12345678909865432","doctor_name":"Dr Mohammed imthiyas","doctor_img":"http://mysalveo.com/api/uploads/images.jpeg","specialization":[{"specialization":"spec - 1"},{"specialization":"spec - 2"},{"specialization":"spec - 3"},{"specialization":"spec - 4"}],"star_count":4,"review_count":223},{"_id":"12345678909865432","doctor_name":"Dr Mohammed imthiyas","doctor_img":"http://mysalveo.com/api/uploads/images.jpeg","specialization":[{"specialization":"spec - 1"},{"specialization":"spec - 2"},{"specialization":"spec - 3"},{"specialization":"spec - 4"}],"star_count":4,"review_count":223},{"_id":"12345678909865432","doctor_name":"Dr Mohammed imthiyas","doctor_img":"http://mysalveo.com/api/uploads/images.jpeg","specialization":[{"specialization":"spec - 1"},{"specialization":"spec - 2"},{"specialization":"spec - 3"},{"specialization":"spec - 4"}],"star_count":4,"review_count":223},{"_id":"12345678909865432","doctor_name":"Dr Mohammed imthiyas","doctor_img":"http://mysalveo.com/api/uploads/images.jpeg","specialization":[{"specialization":"spec - 1"},{"specialization":"spec - 2"},{"specialization":"spec - 3"},{"specialization":"spec - 4"}],"star_count":4,"review_count":223}],"Service_details":[{"_id":"123456","service_icon":"http://mysalveo.com/api/uploads/images.jpeg","service_title":"Bathing","background_color":"#00FFA2"},{"_id":"123456","service_icon":"http://mysalveo.com/api/uploads/images.jpeg","service_title":"Bathing","background_color":"#00FFA2"},{"_id":"123456","service_icon":"http://mysalveo.com/api/uploads/images.jpeg","service_title":"Bathing","background_color":"#00FFA2"}],"Products_details":[{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":true,"product_offer_value":10,"product_rate":4.5,"review_count":123,"product_fav_status":true},{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":false,"product_offer_value":0,"product_rate":4.5,"review_count":123,"product_fav_status":false},{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":true,"product_offer_value":10,"product_rate":4.5,"review_count":123,"product_fav_status":true},{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":true,"product_offer_value":10,"product_rate":4.5,"review_count":123,"product_fav_status":true},{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":false,"product_offer_value":0,"product_rate":4.5,"review_count":123,"product_fav_status":false}]}}
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

    public static class DataBean {
        /**
         * SOS : []
         * LocationDetails : []
         * PetDetails : []
         * userdetails : {"_id":"5fb63307b223363ad0039b0e","first_name":"Dk","last_name":"dj","user_email":"iddinesh@gmail.com","user_phone":"6383791451","date_of_reg":"19/11/2020 02:25 PM","user_type":1,"user_status":"Incomplete","otp":790294,"fb_token":"","device_id":"","device_type":"","__v":0}
         * Dashboarddata : {"Banner_details":[{"_id":"1231231","title":"banner - 1","img_path":"http://mysalveo.com/api/uploads/images.jpeg"},{"_id":"1231231","title":"banner - 2","img_path":"http://mysalveo.com/api/uploads/images.jpeg"},{"_id":"1231231","title":"banner - 3","img_path":"http://mysalveo.com/api/uploads/images.jpeg"}],"Doctor_details":[{"_id":"12345678909865432","doctor_name":"Dr Mohammed imthiyas","doctor_img":"http://mysalveo.com/api/uploads/images.jpeg","specialization":[{"specialization":"spec - 1"},{"specialization":"spec - 2"},{"specialization":"spec - 3"},{"specialization":"spec - 4"}],"star_count":4,"review_count":223},{"_id":"12345678909865432","doctor_name":"Dr Mohammed imthiyas","doctor_img":"http://mysalveo.com/api/uploads/images.jpeg","specialization":[{"specialization":"spec - 1"},{"specialization":"spec - 2"},{"specialization":"spec - 3"},{"specialization":"spec - 4"}],"star_count":4,"review_count":223},{"_id":"12345678909865432","doctor_name":"Dr Mohammed imthiyas","doctor_img":"http://mysalveo.com/api/uploads/images.jpeg","specialization":[{"specialization":"spec - 1"},{"specialization":"spec - 2"},{"specialization":"spec - 3"},{"specialization":"spec - 4"}],"star_count":4,"review_count":223},{"_id":"12345678909865432","doctor_name":"Dr Mohammed imthiyas","doctor_img":"http://mysalveo.com/api/uploads/images.jpeg","specialization":[{"specialization":"spec - 1"},{"specialization":"spec - 2"},{"specialization":"spec - 3"},{"specialization":"spec - 4"}],"star_count":4,"review_count":223}],"Service_details":[{"_id":"123456","service_icon":"http://mysalveo.com/api/uploads/images.jpeg","service_title":"Bathing","background_color":"#00FFA2"},{"_id":"123456","service_icon":"http://mysalveo.com/api/uploads/images.jpeg","service_title":"Bathing","background_color":"#00FFA2"},{"_id":"123456","service_icon":"http://mysalveo.com/api/uploads/images.jpeg","service_title":"Bathing","background_color":"#00FFA2"}],"Products_details":[{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":true,"product_offer_value":10,"product_rate":4.5,"review_count":123,"product_fav_status":true},{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":false,"product_offer_value":0,"product_rate":4.5,"review_count":123,"product_fav_status":false},{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":true,"product_offer_value":10,"product_rate":4.5,"review_count":123,"product_fav_status":true},{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":true,"product_offer_value":10,"product_rate":4.5,"review_count":123,"product_fav_status":true},{"_id":"1234567890","products_img":"http://mysalveo.com/api/uploads/images.jpeg","product_title":"Title","product_prices":200,"product_offer_status":false,"product_offer_value":0,"product_rate":4.5,"review_count":123,"product_fav_status":false}]}
         */

        private UserdetailsBean userdetails;
        private DashboarddataBean Dashboarddata;
        private List<?> SOS;
        private List<?> LocationDetails;
        private List<?> PetDetails;

        public UserdetailsBean getUserdetails() {
            return userdetails;
        }

        public void setUserdetails(UserdetailsBean userdetails) {
            this.userdetails = userdetails;
        }

        public DashboarddataBean getDashboarddata() {
            return Dashboarddata;
        }

        public void setDashboarddata(DashboarddataBean Dashboarddata) {
            this.Dashboarddata = Dashboarddata;
        }

        public List<?> getSOS() {
            return SOS;
        }

        public void setSOS(List<?> SOS) {
            this.SOS = SOS;
        }

        public List<?> getLocationDetails() {
            return LocationDetails;
        }

        public void setLocationDetails(List<?> LocationDetails) {
            this.LocationDetails = LocationDetails;
        }

        public List<?> getPetDetails() {
            return PetDetails;
        }

        public void setPetDetails(List<?> PetDetails) {
            this.PetDetails = PetDetails;
        }

        public static class UserdetailsBean {
            /**
             * _id : 5fb63307b223363ad0039b0e
             * first_name : Dk
             * last_name : dj
             * user_email : iddinesh@gmail.com
             * user_phone : 6383791451
             * date_of_reg : 19/11/2020 02:25 PM
             * user_type : 1
             * user_status : Incomplete
             * otp : 790294
             * fb_token :
             * device_id :
             * device_type :
             * __v : 0
             */

            private String _id;
            private String first_name;
            private String last_name;
            private String user_email;
            private String user_phone;
            private String date_of_reg;
            private int user_type;
            private String user_status;
            private int otp;
            private String fb_token;
            private String device_id;
            private String device_type;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getUser_email() {
                return user_email;
            }

            public void setUser_email(String user_email) {
                this.user_email = user_email;
            }

            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
            }

            public String getDate_of_reg() {
                return date_of_reg;
            }

            public void setDate_of_reg(String date_of_reg) {
                this.date_of_reg = date_of_reg;
            }

            public int getUser_type() {
                return user_type;
            }

            public void setUser_type(int user_type) {
                this.user_type = user_type;
            }

            public String getUser_status() {
                return user_status;
            }

            public void setUser_status(String user_status) {
                this.user_status = user_status;
            }

            public int getOtp() {
                return otp;
            }

            public void setOtp(int otp) {
                this.otp = otp;
            }

            public String getFb_token() {
                return fb_token;
            }

            public void setFb_token(String fb_token) {
                this.fb_token = fb_token;
            }

            public String getDevice_id() {
                return device_id;
            }

            public void setDevice_id(String device_id) {
                this.device_id = device_id;
            }

            public String getDevice_type() {
                return device_type;
            }

            public void setDevice_type(String device_type) {
                this.device_type = device_type;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }
        }

        public static class DashboarddataBean {
            private List<BannerDetailsBean> Banner_details;
            private List<DoctorDetailsBean> Doctor_details;
            private List<ServiceDetailsBean> Service_details;
            private List<ProductsDetailsBean> Products_details;

            public List<BannerDetailsBean> getBanner_details() {
                return Banner_details;
            }

            public void setBanner_details(List<BannerDetailsBean> Banner_details) {
                this.Banner_details = Banner_details;
            }

            public List<DoctorDetailsBean> getDoctor_details() {
                return Doctor_details;
            }

            public void setDoctor_details(List<DoctorDetailsBean> Doctor_details) {
                this.Doctor_details = Doctor_details;
            }

            public List<ServiceDetailsBean> getService_details() {
                return Service_details;
            }

            public void setService_details(List<ServiceDetailsBean> Service_details) {
                this.Service_details = Service_details;
            }

            public List<ProductsDetailsBean> getProducts_details() {
                return Products_details;
            }

            public void setProducts_details(List<ProductsDetailsBean> Products_details) {
                this.Products_details = Products_details;
            }

            public static class BannerDetailsBean {
                /**
                 * _id : 1231231
                 * title : banner - 1
                 * img_path : http://mysalveo.com/api/uploads/images.jpeg
                 */

                private String _id;
                private String title;
                private String img_path;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getImg_path() {
                    return img_path;
                }

                public void setImg_path(String img_path) {
                    this.img_path = img_path;
                }
            }

            public static class DoctorDetailsBean {
                /**
                 * _id : 12345678909865432
                 * doctor_name : Dr Mohammed imthiyas
                 * doctor_img : http://mysalveo.com/api/uploads/images.jpeg
                 * specialization : [{"specialization":"spec - 1"},{"specialization":"spec - 2"},{"specialization":"spec - 3"},{"specialization":"spec - 4"}]
                 * star_count : 4
                 * review_count : 223
                 */

                private String _id;
                private String doctor_name;
                private String doctor_img;
                private int star_count;
                private String distance;

                public String getDistance() {
                    return distance;
                }

                public void setDistance(String distance) {
                    this.distance = distance;
                }
                private int review_count;
                private List<SpecializationBean> specialization;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getDoctor_name() {
                    return doctor_name;
                }

                public void setDoctor_name(String doctor_name) {
                    this.doctor_name = doctor_name;
                }

                public String getDoctor_img() {
                    return doctor_img;
                }

                public void setDoctor_img(String doctor_img) {
                    this.doctor_img = doctor_img;
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

                public List<SpecializationBean> getSpecialization() {
                    return specialization;
                }

                public void setSpecialization(List<SpecializationBean> specialization) {
                    this.specialization = specialization;
                }

                public static class SpecializationBean {
                    /**
                     * specialization : spec - 1
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

            public static class ServiceDetailsBean {
                /**
                 * _id : 123456
                 * service_icon : http://mysalveo.com/api/uploads/images.jpeg
                 * service_title : Bathing
                 * background_color : #00FFA2
                 */

                private String _id;
                private String service_icon;
                private String service_title;
                private String background_color;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getService_icon() {
                    return service_icon;
                }

                public void setService_icon(String service_icon) {
                    this.service_icon = service_icon;
                }

                public String getService_title() {
                    return service_title;
                }

                public void setService_title(String service_title) {
                    this.service_title = service_title;
                }

                public String getBackground_color() {
                    return background_color;
                }

                public void setBackground_color(String background_color) {
                    this.background_color = background_color;
                }
            }

            public static class ProductsDetailsBean {
                /**
                 * _id : 1234567890
                 * products_img : http://mysalveo.com/api/uploads/images.jpeg
                 * product_title : Title
                 * product_prices : 200
                 * product_offer_status : true
                 * product_offer_value : 10
                 * product_rate : 4.5
                 * review_count : 123
                 * product_fav_status : true
                 */

                private String _id;
                private String products_img;
                private String product_title;
                private int product_prices;
                private boolean product_offer_status;
                private int product_offer_value;
                private double product_rate;
                private int review_count;
                private boolean product_fav_status;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getProducts_img() {
                    return products_img;
                }

                public void setProducts_img(String products_img) {
                    this.products_img = products_img;
                }

                public String getProduct_title() {
                    return product_title;
                }

                public void setProduct_title(String product_title) {
                    this.product_title = product_title;
                }

                public int getProduct_prices() {
                    return product_prices;
                }

                public void setProduct_prices(int product_prices) {
                    this.product_prices = product_prices;
                }

                public boolean isProduct_offer_status() {
                    return product_offer_status;
                }

                public void setProduct_offer_status(boolean product_offer_status) {
                    this.product_offer_status = product_offer_status;
                }

                public int getProduct_offer_value() {
                    return product_offer_value;
                }

                public void setProduct_offer_value(int product_offer_value) {
                    this.product_offer_value = product_offer_value;
                }

                public double getProduct_rate() {
                    return product_rate;
                }

                public void setProduct_rate(double product_rate) {
                    this.product_rate = product_rate;
                }

                public int getReview_count() {
                    return review_count;
                }

                public void setReview_count(int review_count) {
                    this.review_count = review_count;
                }

                public boolean isProduct_fav_status() {
                    return product_fav_status;
                }

                public void setProduct_fav_status(boolean product_fav_status) {
                    this.product_fav_status = product_fav_status;
                }
            }
        }
    }
}