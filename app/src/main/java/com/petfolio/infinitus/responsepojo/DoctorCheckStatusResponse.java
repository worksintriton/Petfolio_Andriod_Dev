package com.petfolio.infinitus.responsepojo;

public class DoctorCheckStatusResponse {


    /**
     * Status : Success
     * Message : Docotor Status
     * Data : {"user_id":"5fc7970c07fcd32b6c98c01a","profile_status":0,"profile_verification_status":"Not verified"}
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
         * user_id : 5fc7970c07fcd32b6c98c01a
         * profile_status : 0
         * profile_verification_status : Not verified
         */

        private String user_id;
        private int profile_status;
        private String profile_verification_status;


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;

        }


        public int getProfile_status() {
            return profile_status;
        }

        public void setProfile_status(int profile_status) {
            this.profile_status = profile_status;
        }


        public String getProfile_verification_status() {
            return profile_verification_status;
        }

        public void setProfile_verification_status(String profile_verification_status) {
            this.profile_verification_status = profile_verification_status;
        }
    }
}
