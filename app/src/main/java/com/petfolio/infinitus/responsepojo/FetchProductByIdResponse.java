package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class FetchProductByIdResponse {


    /**
     * Status : Success
     * Message : product list
     * Product_details : {"_id":"6034d6a5888af7628e7e17d4","product_img":["http://54.212.108.156:3000/api/uploads/1614075552394.jpg"],"product_title":"Cat Dinner","product_price":1000,"product_discount":10,"breed_type":[{"_id":"602d1c20562e0916bc9b3218","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Pug","date_and_time":"2/17/2021, 7:07:35 PM","delete_status":false,"updatedAt":"2021-02-17T13:37:36.277Z","createdAt":"2021-02-17T13:37:36.277Z","__v":0}],"pet_type":[{"_id":"602d1c6b562e0916bc9b321d","pet_type_title":"Cat","date_and_time":"2/17/2021, 7:08:50 PM","delete_status":false,"updatedAt":"2021-02-17T13:38:51.432Z","createdAt":"2021-02-17T13:38:51.432Z","__v":0}],"age":[3],"cat_id":{"_id":"5fec14a5ea832e2e73c1fc79","img_path":"http://52.25.163.13:3000/api/uploads/template%20(3).jpg","product_cate":"Cat Food","img_index":0,"show_status":true,"date_and_time":"Thu Feb 18 2021 15:05:46 GMT+0530 (India Standard Time)","delete_status":true,"updatedAt":"2021-02-18T09:35:47.194Z","createdAt":"2020-12-30T05:48:21.363Z","__v":0},"threshould":"100","product_discription":"This cat  food","product_fav":false,"product_rating":4.8,"product_review":232,"product_related":[{"_id":"602e467ff62e8d2089fba973","product_img":"http://54.212.108.156:3000/api/uploads/cats.jpg","product_title":"Cat Food","product_price":1000,"product_discount":2,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"602e478af62e8d2089fba976","product_img":"http://54.212.108.156:3000/api/uploads/Product \u2013 2.png","product_title":"Bellotta | Cat Food","product_price":2580,"product_discount":2,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d193295b555a1379a632","product_img":"http://54.212.108.156:3000/api/uploads/1614074256386.jpg","product_title":"Cat Breakfast","product_price":20000,"product_discount":29,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d41f295b555a1379a634","product_img":"http://54.212.108.156:3000/api/uploads/1614074906327.jpg","product_title":"Cat Breakfast","product_price":10000,"product_discount":50,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d473295b555a1379a635","product_img":"http://54.212.108.156:3000/api/uploads/1614074990879.jpg","product_title":"Cat Lunch","product_price":3000,"product_discount":30,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d66598fa826140f6a3a3","product_img":"http://54.212.108.156:3000/api/uploads/1614075490400.jpg","product_title":"CAT Lunch","product_price":40000,"product_discount":40,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d6a5888af7628e7e17d4","product_img":"http://54.212.108.156:3000/api/uploads/1614075552394.jpg","product_title":"Cat Dinner","product_price":1000,"product_discount":10,"product_fav":false,"product_rating":4.8,"product_review":232}],"product_cart_count":1}
     * Code : 200
     */

    private String Status;
    private String Message;
    private ProductDetailsBean Product_details;
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


    public ProductDetailsBean getProduct_details() {
        return Product_details;
    }

    public void setProduct_details(ProductDetailsBean Product_details) {
        this.Product_details = Product_details;

    }


    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;

    }

    public static class ProductDetailsBean  {
        /**
         * _id : 6034d6a5888af7628e7e17d4
         * product_img : ["http://54.212.108.156:3000/api/uploads/1614075552394.jpg"]
         * product_title : Cat Dinner
         * product_price : 1000
         * product_discount : 10
         * breed_type : [{"_id":"602d1c20562e0916bc9b3218","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Pug","date_and_time":"2/17/2021, 7:07:35 PM","delete_status":false,"updatedAt":"2021-02-17T13:37:36.277Z","createdAt":"2021-02-17T13:37:36.277Z","__v":0}]
         * pet_type : [{"_id":"602d1c6b562e0916bc9b321d","pet_type_title":"Cat","date_and_time":"2/17/2021, 7:08:50 PM","delete_status":false,"updatedAt":"2021-02-17T13:38:51.432Z","createdAt":"2021-02-17T13:38:51.432Z","__v":0}]
         * age : [3]
         * cat_id : {"_id":"5fec14a5ea832e2e73c1fc79","img_path":"http://52.25.163.13:3000/api/uploads/template%20(3).jpg","product_cate":"Cat Food","img_index":0,"show_status":true,"date_and_time":"Thu Feb 18 2021 15:05:46 GMT+0530 (India Standard Time)","delete_status":true,"updatedAt":"2021-02-18T09:35:47.194Z","createdAt":"2020-12-30T05:48:21.363Z","__v":0}
         * threshould : 100
         * product_discription : This cat  food
         * product_fav : false
         * product_rating : 4.8
         * product_review : 232
         * product_related : [{"_id":"602e467ff62e8d2089fba973","product_img":"http://54.212.108.156:3000/api/uploads/cats.jpg","product_title":"Cat Food","product_price":1000,"product_discount":2,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"602e478af62e8d2089fba976","product_img":"http://54.212.108.156:3000/api/uploads/Product \u2013 2.png","product_title":"Bellotta | Cat Food","product_price":2580,"product_discount":2,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d193295b555a1379a632","product_img":"http://54.212.108.156:3000/api/uploads/1614074256386.jpg","product_title":"Cat Breakfast","product_price":20000,"product_discount":29,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d41f295b555a1379a634","product_img":"http://54.212.108.156:3000/api/uploads/1614074906327.jpg","product_title":"Cat Breakfast","product_price":10000,"product_discount":50,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d473295b555a1379a635","product_img":"http://54.212.108.156:3000/api/uploads/1614074990879.jpg","product_title":"Cat Lunch","product_price":3000,"product_discount":30,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d66598fa826140f6a3a3","product_img":"http://54.212.108.156:3000/api/uploads/1614075490400.jpg","product_title":"CAT Lunch","product_price":40000,"product_discount":40,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"6034d6a5888af7628e7e17d4","product_img":"http://54.212.108.156:3000/api/uploads/1614075552394.jpg","product_title":"Cat Dinner","product_price":1000,"product_discount":10,"product_fav":false,"product_rating":4.8,"product_review":232}]
         * product_cart_count : 1
         */

        private String _id;
        private String product_title;
        private double product_price;
        private int product_discount;
        private CatIdBean cat_id;
        private String threshould;
        private String product_discription;
        private boolean product_fav;
        private double product_rating;
        private int product_review;
        private int product_cart_count;
        private List<String> product_img;
        private List<BreedTypeBean> breed_type;
        private List<PetTypeBean> pet_type;
        private List<Integer> age;
        private List<ProductRelatedBean> product_related;


        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;

        }


        public String getProduct_title() {
            return product_title;
        }

        public void setProduct_title(String product_title) {
            this.product_title = product_title;

        }


        public double getProduct_price() {
            return product_price;
        }

        public void setProduct_price(double product_price) {
            this.product_price = product_price;

        }


        public int getProduct_discount() {
            return product_discount;
        }

        public void setProduct_discount(int product_discount) {
            this.product_discount = product_discount;

        }


        public CatIdBean getCat_id() {
            return cat_id;
        }

        public void setCat_id(CatIdBean cat_id) {
            this.cat_id = cat_id;

        }

        public String getThreshould() {
            return threshould;
        }

        public void setThreshould(String threshould) {
            this.threshould = threshould;

        }


        public String getProduct_discription() {
            return product_discription;
        }

        public void setProduct_discription(String product_discription) {
            this.product_discription = product_discription;

        }


        public boolean isProduct_fav() {
            return product_fav;
        }

        public void setProduct_fav(boolean product_fav) {
            this.product_fav = product_fav;

        }


        public double getProduct_rating() {
            return product_rating;
        }

        public void setProduct_rating(double product_rating) {
            this.product_rating = product_rating;

        }


        public int getProduct_review() {
            return product_review;
        }

        public void setProduct_review(int product_review) {
            this.product_review = product_review;

        }


        public int getProduct_cart_count() {
            return product_cart_count;
        }

        public void setProduct_cart_count(int product_cart_count) {
            this.product_cart_count = product_cart_count;

        }


        public List<String> getProduct_img() {
            return product_img;
        }

        public void setProduct_img(List<String> product_img) {
            this.product_img = product_img;

        }


        public List<BreedTypeBean> getBreed_type() {
            return breed_type;
        }

        public void setBreed_type(List<BreedTypeBean> breed_type) {
            this.breed_type = breed_type;

        }

        public List<PetTypeBean> getPet_type() {
            return pet_type;
        }

        public void setPet_type(List<PetTypeBean> pet_type) {
            this.pet_type = pet_type;

        }


        public List<Integer> getAge() {
            return age;
        }

        public void setAge(List<Integer> age) {
            this.age = age;

        }


        public List<ProductRelatedBean> getProduct_related() {
            return product_related;
        }

        public void setProduct_related(List<ProductRelatedBean> product_related) {
            this.product_related = product_related;

        }

        public static class CatIdBean {
            /**
             * _id : 5fec14a5ea832e2e73c1fc79
             * img_path : http://52.25.163.13:3000/api/uploads/template%20(3).jpg
             * product_cate : Cat Food
             * img_index : 0
             * show_status : true
             * date_and_time : Thu Feb 18 2021 15:05:46 GMT+0530 (India Standard Time)
             * delete_status : true
             * updatedAt : 2021-02-18T09:35:47.194Z
             * createdAt : 2020-12-30T05:48:21.363Z
             * __v : 0
             */

            private String _id;
            private String img_path;
            private String product_cate;
            private int img_index;
            private boolean show_status;
            private String date_and_time;



            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;

            }


            public String getImg_path() {
                return img_path;
            }

            public void setImg_path(String img_path) {
                this.img_path = img_path;
            }

            public String getProduct_cate() {
                return product_cate;
            }

            public void setProduct_cate(String product_cate) {
                this.product_cate = product_cate;
            }

            public int getImg_index() {
                return img_index;
            }

            public void setImg_index(int img_index) {
                this.img_index = img_index;
            }

            public boolean isShow_status() {
                return show_status;
            }

            public void setShow_status(boolean show_status) {
                this.show_status = show_status;
            }

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }


        }

        public static class BreedTypeBean {
            /**
             * _id : 602d1c20562e0916bc9b3218
             * pet_type_id : 602d1bf4562e0916bc9b3215
             * pet_breed : Pug
             * date_and_time : 2/17/2021, 7:07:35 PM
             * delete_status : false
             * updatedAt : 2021-02-17T13:37:36.277Z
             * createdAt : 2021-02-17T13:37:36.277Z
             * __v : 0
             */

            private String _id;
            private String pet_type_id;
            private String pet_breed;
            private String date_and_time;


            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;

            }


            public String getPet_type_id() {
                return pet_type_id;
            }

            public void setPet_type_id(String pet_type_id) {
                this.pet_type_id = pet_type_id;

            }


            public String getPet_breed() {
                return pet_breed;
            }

            public void setPet_breed(String pet_breed) {
                this.pet_breed = pet_breed;

            }


            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;

            }


        }

        public static class PetTypeBean  {
            /**
             * _id : 602d1c6b562e0916bc9b321d
             * pet_type_title : Cat
             * date_and_time : 2/17/2021, 7:08:50 PM
             * delete_status : false
             * updatedAt : 2021-02-17T13:38:51.432Z
             * createdAt : 2021-02-17T13:38:51.432Z
             * __v : 0
             */

            private String _id;
            private String pet_type_title;
            private String date_and_time;


            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }


            public String getPet_type_title() {
                return pet_type_title;
            }

            public void setPet_type_title(String pet_type_title) {
                this.pet_type_title = pet_type_title;

            }


            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;

            }


        }

        public static class ProductRelatedBean  {
            /**
             * _id : 602e467ff62e8d2089fba973
             * product_img : http://54.212.108.156:3000/api/uploads/cats.jpg
             * product_title : Cat Food
             * product_price : 1000
             * product_discount : 2
             * product_fav : false
             * product_rating : 4.8
             * product_review : 232
             */

            private String _id;
            private String product_img;
            private String product_title;
            private double product_price;
            private int product_discount;
            private boolean product_fav;
            private double product_rating;
            private int product_review;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getProduct_img() {
                return product_img;
            }

            public void setProduct_img(String product_img) {
                this.product_img = product_img;
            }

            public String getProduct_title() {
                return product_title;
            }

            public void setProduct_title(String product_title) {
                this.product_title = product_title;
            }

            public double getProduct_price() {
                return product_price;
            }

            public void setProduct_price(double product_price) {
                this.product_price = product_price;
            }

            public int getProduct_discount() {
                return product_discount;
            }

            public void setProduct_discount(int product_discount) {
                this.product_discount = product_discount;
            }


            public boolean isProduct_fav() {
                return product_fav;
            }

            public void setProduct_fav(boolean product_fav) {
                this.product_fav = product_fav;
            }


            public double getProduct_rating() {
                return product_rating;
            }

            public void setProduct_rating(double product_rating) {
                this.product_rating = product_rating;
            }

            public int getProduct_review() {
                return product_review;
            }

            public void setProduct_review(int product_review) {
                this.product_review = product_review;
            }
        }
    }
}
