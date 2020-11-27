package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class PetTypeListResponse {

    /**
     * Status : Success
     * Message : PET type Details
     * Data : {"usertypedata":[{"_id":"5fb8b15be0ba3b0e6268dd2d","pet_type_title":"Dog","date_and_time":"23-10-2020 11:00 AM","__v":0},{"_id":"5fb8b160e0ba3b0e6268dd2e","pet_type_title":"Cat","date_and_time":"23-10-2020 11:00 AM","__v":0},{"_id":"5fbf5a333c8a080aea0c9511","pet_type_title":"Cow","date_and_time":"11/26/2020, 1:03:06 PM","__v":0},{"_id":"5fc09826cce7b84055227ac6","pet_type_title":"Dog type 1","date_and_time":"11/27/2020, 11:39:39 AM","__v":0},{"_id":"5fc09860cce7b84055227ac7","pet_type_title":"Dog type 2","date_and_time":"11/27/2020, 11:40:38 AM","__v":0}]}
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
        private List<UsertypedataBean> usertypedata;


        public List<UsertypedataBean> getUsertypedata() {
            return usertypedata;
        }

        public void setUsertypedata(List<UsertypedataBean> usertypedata) {
            this.usertypedata = usertypedata;

        }

        public static class UsertypedataBean  {
            /**
             * _id : 5fb8b15be0ba3b0e6268dd2d
             * pet_type_title : Dog
             * date_and_time : 23-10-2020 11:00 AM
             * __v : 0
             */

            private String _id;
            private String pet_type_title;
            private String date_and_time;
            private int __v;


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


            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;

            }
        }
    }
}
