package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class SPSpecificServiceDetailsResponse {

    /**
     * Status : Success
     * Message : Service Provider List
     * Data : {"Service_Details":{"image_path":"SP - 6","title":"","count":0},"Service_provider":[{"_id":"1234QWERTYUIOP","image":"","service_provider_name":"Mohammed","service_price":100,"service_offer":23,"service_place":"Chennai","distance":2,"rating_count":5,"comments_count":12},{"_id":"1234QWERTYUIOP","image":"","service_provider_name":"Mohammed","service_price":100,"service_offer":23,"service_place":"Chennai","distance":2,"rating_count":5,"comments_count":12},{"_id":"1234QWERTYUIOP","image":"","service_provider_name":"Mohammed","service_price":100,"service_offer":23,"service_place":"Chennai","distance":2,"rating_count":5,"comments_count":12},{"_id":"1234QWERTYUIOP","image":"","service_provider_name":"Mohammed","service_price":100,"service_offer":23,"service_place":"Chennai","distance":2,"rating_count":5,"comments_count":12}]}
     * Code : 200
     */

    private String Status;
    private String Message;
    private String alert_msg;

    public String getAlert_msg() {
        return alert_msg;
    }

    public void setAlert_msg(String alert_msg) {
        this.alert_msg = alert_msg;
    }

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
         * Service_Details : {"image_path":"SP - 6","title":"","count":0}
         * Service_provider : [{"_id":"1234QWERTYUIOP","image":"http://mysalveo.com/api/uploads/images.jpeg","service_provider_name":"Mohammed","service_price":100,"service_offer":23,"service_place":"Chennai","distance":2,"rating_count":5,"comments_count":12},{"_id":"1234QWERTYUIOP","image":"http://mysalveo.com/api/uploads/images.jpeg","service_provider_name":"Mohammed","service_price":100,"service_offer":23,"service_place":"Chennai","distance":2,"rating_count":5,"comments_count":12},{"_id":"1234QWERTYUIOP","image":"http://mysalveo.com/api/uploads/images.jpeg","service_provider_name":"Mohammed","service_price":100,"service_offer":23,"service_place":"Chennai","distance":2,"rating_count":5,"comments_count":12},{"_id":"1234QWERTYUIOP","image":"http://mysalveo.com/api/uploads/images.jpeg","service_provider_name":"Mohammed","service_price":100,"service_offer":23,"service_place":"Chennai","distance":2,"rating_count":5,"comments_count":12}]
         */

        private ServiceDetailsBean Service_Details;
        private List<ServiceProviderBean> Service_provider;


        public ServiceDetailsBean getService_Details() {
            return Service_Details;
        }

        public void setService_Details(ServiceDetailsBean Service_Details) {
            this.Service_Details = Service_Details;

        }

        public List<ServiceProviderBean> getService_provider() {
            return Service_provider;
        }

        public void setService_provider(List<ServiceProviderBean> Service_provider) {
            this.Service_provider = Service_provider;

        }

        public static class ServiceDetailsBean  {
            /**
             * _id
             * image_path : SP - 6
             * title : http://mysalveo.com/api/uploads/images.jpeg
             * count : 0
             */

            private String _id;
            private String image_path;
            private String title;
            private int count;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
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

        public static class ServiceProviderBean {
            /**
             * _id : 1234QWERTYUIOP
             * image : http://mysalveo.com/api/uploads/images.jpeg
             * service_provider_name : Mohammed
             * service_price : 100
             * service_offer : 23
             * service_place : Chennai
             * distance : 2
             * rating_count : 5
             * comments_count : 12
             */

            private String _id;
            private String image;
            private String service_provider_name;
            private int service_price;
            private int service_offer;
            private String service_place;
            private double distance;

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }

            private int rating_count;
            private int comments_count;


            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;

            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;

            }

            public String getService_provider_name() {
                return service_provider_name;
            }

            public void setService_provider_name(String service_provider_name) {
                this.service_provider_name = service_provider_name;

            }

            public int getService_price() {
                return service_price;
            }

            public void setService_price(int service_price) {
                this.service_price = service_price;

            }

            public int getService_offer() {
                return service_offer;
            }

            public void setService_offer(int service_offer) {
                this.service_offer = service_offer;

            }

            public String getService_place() {
                return service_place;
            }

            public void setService_place(String service_place) {
                this.service_place = service_place;

            }



            public int getRating_count() {
                return rating_count;
            }

            public void setRating_count(int rating_count) {
                this.rating_count = rating_count;
            }

            public int getComments_count() {
                return comments_count;
            }

            public void setComments_count(int comments_count) {
                this.comments_count = comments_count;
            }
        }
    }
}
