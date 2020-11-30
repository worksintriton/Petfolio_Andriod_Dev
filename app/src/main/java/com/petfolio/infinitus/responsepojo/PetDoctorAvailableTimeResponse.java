package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class PetDoctorAvailableTimeResponse {

    /**
     * Status : Success
     * Message : Doctor Available
     * Data : [{"Comm_type_chat":"No","Comm_type_video":"No","Doctor_email_id":"","Doctor_ava_Date":"31-11-2020","Doctor_name":"","Times":[{"time":"01:00 AM"},{"time":"02:00 AM"},{"time":"03:00 AM"}]}]
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
         * Comm_type_chat : No
         * Comm_type_video : No
         * Doctor_email_id :
         * Doctor_ava_Date : 31-11-2020
         * Doctor_name :
         * Times : [{"time":"01:00 AM"},{"time":"02:00 AM"},{"time":"03:00 AM"}]
         */

        private String Comm_type_chat;
        private String Comm_type_video;
        private String Doctor_email_id;
        private String Doctor_ava_Date;
        private String Doctor_name;
        private List<TimesBean> Times;


        public String getComm_type_chat() {
            return Comm_type_chat;
        }

        public void setComm_type_chat(String Comm_type_chat) {
            this.Comm_type_chat = Comm_type_chat;

        }


        public String getComm_type_video() {
            return Comm_type_video;
        }

        public void setComm_type_video(String Comm_type_video) {
            this.Comm_type_video = Comm_type_video;

        }


        public String getDoctor_email_id() {
            return Doctor_email_id;
        }

        public void setDoctor_email_id(String Doctor_email_id) {
            this.Doctor_email_id = Doctor_email_id;

        }


        public String getDoctor_ava_Date() {
            return Doctor_ava_Date;
        }

        public void setDoctor_ava_Date(String Doctor_ava_Date) {
            this.Doctor_ava_Date = Doctor_ava_Date;

        }


        public String getDoctor_name() {
            return Doctor_name;
        }

        public void setDoctor_name(String Doctor_name) {
            this.Doctor_name = Doctor_name;

        }


        public List<TimesBean> getTimes() {
            return Times;
        }

        public void setTimes(List<TimesBean> Times) {
            this.Times = Times;

        }

        public static class TimesBean  {
            /**
             * time : 01:00 AM
             */

            private String time;


            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;

            }
        }
    }
}
