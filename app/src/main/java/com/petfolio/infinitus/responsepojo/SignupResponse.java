package com.petfolio.infinitus.responsepojo;

public class SignupResponse {

    /**
     * Status : Success
     * Message : Sign up successfully! welcome to petfolio
     * Data : {"user_details":{"_id":"5fb390f35998215074b1c982","first_name":"mohammed","last_name":"imthiyas","user_email":"m@gmail.com","user_phone":"987987989","date_of_reg":"23/10/2019 12:12:00","user_type":1,"otp":323938,"fb_token":"","device_id":"","device_type":"","__v":0}}
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
         * user_details : {"_id":"5fb390f35998215074b1c982","first_name":"mohammed","last_name":"imthiyas","user_email":"m@gmail.com","user_phone":"987987989","date_of_reg":"23/10/2019 12:12:00","user_type":1,"otp":323938,"fb_token":"","device_id":"","device_type":"","__v":0}
         */

        private UserDetailsBean user_details;

        public UserDetailsBean getUser_details() {
            return user_details;
        }

        public void setUser_details(UserDetailsBean user_details) {
            this.user_details = user_details;
        }

        public static class UserDetailsBean {
            /**
             * _id : 5fb390f35998215074b1c982
             * first_name : mohammed
             * last_name : imthiyas
             * user_email : m@gmail.com
             * user_phone : 987987989
             * date_of_reg : 23/10/2019 12:12:00
             * user_type : 1
             * otp : 323938
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
    }
}
