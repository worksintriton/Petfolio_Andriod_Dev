package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class ShopDashboardResponse {

    /**
     * Status : Success
     * Message : product details
     * Data : {"Banner_details":[{"banner_img":"http://54.212.108.156:3000/api/uploads/banner_empty.jpg","banner_title":"Title 1"},{"banner_img":"http://54.212.108.156:3000/api/uploads/banner_empty.jpg","banner_title":"Title 1"},{"banner_img":"http://54.212.108.156:3000/api/uploads/banner_empty.jpg","banner_title":"Title 1"},{"banner_img":"http://54.212.108.156:3000/api/uploads/banner_empty.jpg","banner_title":"Title 1"}],"Today_Special":[{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232}],"Product_details":[{"cat_name":"CAT - 1","product_list":[{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232}]},{"cat_name":"CAT - 1","product_list":[{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232}]},{"cat_name":"CAT - 2","product_list":[{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232}]},{"cat_name":"CAT - 3","product_list":[{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232}]}]}
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
        private List<BannerDetailsBean> Banner_details;
        private List<TodaySpecialBean> Today_Special;
        private List<ProductDetailsBean> Product_details;

        public List<BannerDetailsBean> getBanner_details() {
            return Banner_details;
        }

        public void setBanner_details(List<BannerDetailsBean> Banner_details) {
            this.Banner_details = Banner_details;

        }


        public List<TodaySpecialBean> getToday_Special() {
            return Today_Special;
        }

        public void setToday_Special(List<TodaySpecialBean> Today_Special) {
            this.Today_Special = Today_Special;

        }


        public List<ProductDetailsBean> getProduct_details() {
            return Product_details;
        }

        public void setProduct_details(List<ProductDetailsBean> Product_details) {
            this.Product_details = Product_details;
        }

        public static class BannerDetailsBean {
            /**
             * banner_img : http://54.212.108.156:3000/api/uploads/banner_empty.jpg
             * banner_title : Title 1
             */

            private String banner_img;
            private String banner_title;


            public String getBanner_img() {
                return banner_img;
            }

            public void setBanner_img(String banner_img) {
                this.banner_img = banner_img;

            }


            public String getBanner_title() {
                return banner_title;
            }

            public void setBanner_title(String banner_title) {
                this.banner_title = banner_title;

            }
        }

        public static class TodaySpecialBean  {
            /**
             * _id : 1234567890
             * product_img : http://54.212.108.156:3000/api/uploads/Pic_empty.jpg
             * product_title : Title 1
             * product_price : 200
             * product_discount : 0
             * product_fav : false
             * product_rating : 4.8
             * product_review : 232
             */

            private String _id;
            private String product_img;
            private String product_title;
            private int product_price;
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


            public int getProduct_price() {
                return product_price;
            }

            public void setProduct_price(int product_price) {
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

        public static class ProductDetailsBean {
            /**
             * cat_id
             * cat_name : CAT - 1
             * product_list : [{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232},{"_id":"1234567890","product_img":"http://54.212.108.156:3000/api/uploads/Pic_empty.jpg","product_title":"Title 1","product_price":200,"product_discount":0,"product_fav":false,"product_rating":4.8,"product_review":232}]
             */

            private String cat_name;
            private String cat_id;

            public String getCat_id() {
                return cat_id;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            private List<ProductListBean> product_list;


            public String getCat_name() {
                return cat_name;
            }

            public void setCat_name(String cat_name) {
                this.cat_name = cat_name;

            }


            public List<ProductListBean> getProduct_list() {
                return product_list;
            }

            public void setProduct_list(List<ProductListBean> product_list) {
                this.product_list = product_list;

            }

            public static class ProductListBean  {
                /**
                 * _id : 1234567890
                 * product_img : http://54.212.108.156:3000/api/uploads/Pic_empty.jpg
                 * product_title : Title 1
                 * product_price : 200
                 * product_discount : 0
                 * product_fav : false
                 * product_rating : 4.8
                 * product_review : 232
                 */

                private String _id;
                private String product_img;
                private String product_title;
                private int product_price;
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


                public int getProduct_price() {
                    return product_price;
                }

                public void setProduct_price(int product_price) {
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
}
