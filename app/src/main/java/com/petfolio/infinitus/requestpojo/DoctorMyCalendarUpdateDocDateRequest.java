package com.petfolio.infinitus.requestpojo;

import java.util.List;

public class DoctorMyCalendarUpdateDocDateRequest {

    /**
     * Doctor_email_id : 123123131313123123123123
     * days : ["Monday","Tuesday","Wednesday"]
     * timing : [{"Time":"01:00 AM","Status":true},{"Time":"02:00 AM","Status":true},{"Time":"03:00 AM","Status":true},{"Time":"04:00 AM","Status":true}]
     */

    private String Doctor_email_id;
    private List<String> days;


    private List<TimingBean> timing;

    public String getDoctor_email_id() {
        return Doctor_email_id;
    }

    public void setDoctor_email_id(String doctor_email_id) {
        Doctor_email_id = doctor_email_id;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<TimingBean> getTiming() {
        return timing;
    }

    public void setTiming(List<TimingBean> timing) {
        this.timing = timing;
    }

    public static class TimingBean {
        /**
         * Time : 01:00 AM
         * Status : true
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
