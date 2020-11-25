package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class DoctorMyCalendarAvlTimesResponse {

    /**
     * Status : Success
     * Message : Time list Details 30 Mins
     * Data : [{"Time":"01:00 AM","Status":false},{"Time":"01:30 AM","Status":false},{"Time":"02:00 AM","Status":false},{"Time":"02:30 AM","Status":false},{"Time":"03:00 AM","Status":false},{"Time":"03:30 AM","Status":false},{"Time":"04:00 AM","Status":false},{"Time":"04:30 AM","Status":false},{"Time":"05:00 AM","Status":false},{"Time":"05:30 AM","Status":false},{"Time":"06:00 AM","Status":false},{"Time":"06:30 AM","Status":false},{"Time":"07:00 AM","Status":false},{"Time":"07:30 AM","Status":false},{"Time":"08:00 AM","Status":false},{"Time":"08:30 AM","Status":false},{"Time":"09:00 AM","Status":false},{"Time":"09:30 AM","Status":false},{"Time":"10:00 AM","Status":false},{"Time":"10:30 AM","Status":false},{"Time":"11:00 AM","Status":false},{"Time":"11:30 AM","Status":false},{"Time":"12:00 PM","Status":false},{"Time":"12:30 PM","Status":false},{"Time":"01:00 PM","Status":false},{"Time":"01:30 PM","Status":false},{"Time":"02:00 PM","Status":false},{"Time":"02:30 PM","Status":false},{"Time":"03:00 PM","Status":false},{"Time":"03:30 PM","Status":false},{"Time":"04:00 PM","Status":false},{"Time":"04:30 PM","Status":false},{"Time":"05:00 PM","Status":false},{"Time":"05:30 PM","Status":false},{"Time":"06:00 PM","Status":false},{"Time":"06:30 PM","Status":false},{"Time":"07:00 PM","Status":false},{"Time":"07:30 PM","Status":false},{"Time":"08:00 PM","Status":false},{"Time":"08:30 PM","Status":false},{"Time":"09:00 PM","Status":false},{"Time":"09:30 PM","Status":false},{"Time":"10:00 PM","Status":false},{"Time":"10:30 PM","Status":false},{"Time":"11:00 PM","Status":false},{"Time":"11:30 PM","Status":false},{"Time":"12:00 AM","Status":false},{"Time":"12:30 AM","Status":false}]
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

    public static class DataBean {
        /**
         * Time : 01:00 AM
         * Status : false
         */

        private String Time;
        private boolean Status;

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public boolean isStatus() {
            return Status;
        }

        public void setStatus(boolean Status) {
            this.Status = Status;
        }
    }
}
